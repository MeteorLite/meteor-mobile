package meteor

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.Color.BLACK
import android.graphics.Insets
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.text.InputType
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.view.View.OnHoverListener
import android.view.View.OnTouchListener
import android.view.WindowInsets
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import com.jaredrummler.android.device.DeviceName
import eventbus.Events
import eventbus.events.ConfigChanged
import eventbus.events.Draw
import eventbus.events.GameStateChanged
import eventbus.events.LoadingTextChanged
import eventbus.events.LoginIndexChanged
import eventbus.events.LoginStateChanged
import eventbus.events.SelectedLoginField
import meteor.config.ConfigManager
import meteor.eventbus.KEventBus
import meteor.plugins.PluginManager
import meteor.plugins.meteor.MeteorConfig
import meteor.plugins.privateserver.PrivateServerConfig
import meteor.task.Scheduler
import meteor.ui.composables.toolbar.ToolbarPanel
import meteor.ui.configPanel
import meteor.ui.overlay.OverlayManager
import meteor.ui.overlay.OverlayRenderer
import meteor.ui.overlay.TooltipManager
import meteor.ui.overlay.WidgetOverlay
import meteor.ui.overlay.tooltips.TooltipOverlay
import meteor.ui.pluginsPanel
import meteor.ui.preferences.configOpen
import meteor.ui.preferences.pluginsOpen
import meteor.ui.preferences.uiColor
import meteor.util.ExecutorServiceExceptionLogger
import net.runelite.api.GameState
import okhttp3.OkHttpClient
import osrs.*
import java.awt.Point
import java.awt.event.MouseWheelEvent
import java.awt.image.BufferedImage
import java.io.*
import java.math.BigInteger
import java.util.concurrent.Executors
import kotlin.math.abs


class Main : AppCompatActivity() {
    companion object {
        var activity: AppCompatActivity? = null
        lateinit var client: net.runelite.api.Client
        val logger = Logger("Meteor")
        lateinit var overlayManager: OverlayManager
        lateinit var meteorConfig: MeteorConfig
        lateinit var rspsConfig: PrivateServerConfig
        lateinit var tooltipManager: TooltipManager
        lateinit var overlayRenderer: OverlayRenderer
        val executor = ExecutorServiceExceptionLogger(Executors.newSingleThreadScheduledExecutor())
        val scheduler = Scheduler()
        val httpClient = OkHttpClient()
        var INSTANCE : Main? = null
        val overlayVisible = mutableStateOf(false)
        var zooming = false
    }

    init {
        initConfigs()
    }

    val eventBus = KEventBus.INSTANCE
    var deobClient: Client? = null

    //Game View
    var gameView: ImageView? = null
    var gameViewBitmap: Bitmap? = null
    var hasSetupGameView = false

    var overlayView: ComposeView? = null

    //Mouse tracking
    var downStartPosition : Point? = null
    var pendingLeftClick : Point? = null
    var pendingRightClick : Point? = null
    var pendingMouseRelease = false
    var mouseDown : Long = -1

    var shouldRender = false

    var splashFinished = mutableStateOf(false)

    fun initOverlays() {
        //WidgetOverlay.createOverlays().forEach{ overlay: WidgetOverlay -> overlayManager.add(overlay) }
        overlayManager.add(TooltipOverlay())
    }

    fun initConfigs() {
        // load configs immediately
        ConfigManager.loadSavedProperties()
        ConfigManager.setDefaultConfiguration(MeteorConfig::class.java, false)
        ConfigManager.setDefaultConfiguration(PrivateServerConfig::class.java, false)
        ConfigManager.saveProperties()

        // init meteor config
        meteorConfig = ConfigManager.getConfig(MeteorConfig::class.java)!!
        rspsConfig = ConfigManager.getConfig(PrivateServerConfig::class.java)!!
    }

    fun initRSPSConfig() {
        if (rspsConfig.host.get()!!.isNotEmpty()) {
            client.setHost(rspsConfig.host.get()!!)
            if (rspsConfig.modulus.get()!!.isNotEmpty())
                client.modulus = BigInteger(rspsConfig.modulus.get()!!, 16)
        }
    }

    override fun onStart() {
        INSTANCE = this
        super.onStart()
        activity = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        System.setProperty("jagex.disableBouncyCastle", "true")
        DeviceName.init(this)
        setContentView(R.layout.activity_main)
        window.decorView.setBackgroundColor(BLACK)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        updateHideUi()
    }

    val primary: Color = Color.Cyan
    val secondary: Color = Color.Cyan
    val background: Color = Color(0xFF242424)
    val surface: Color = Color.Black
    val error: Color = Color.Red
    val onPrimary: Color = Color.White
    val onSecondary: Color = Color.White
    val onBackground: Color = Color.White
    val onSurface: Color = Color.White
    val onError: Color = Color.Black


    @Composable
    fun brandBadge() {
        Box(modifier = Modifier
                .width(150.dp)
                .background(background)) {
            Image( painter = painterResource(id = R.drawable.meteor), contentDescription = "Brand Badge")
        }
    }

    val loadingText = mutableStateOf("Starting OSRS")

    @Composable
    fun splashContent() {
        BoxWithConstraints(modifier = Modifier
                .fillMaxSize()
                .background(background), contentAlignment = Alignment.Center) {

            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Row {
                    brandBadge()
                }
                Row {
                    Text(text = loadingText.value, color = Color.Cyan)
                }
            }
        }
    }

    @Composable
    fun overlayContent() {
        if (!splashFinished.value) {
            splashContent()
            return
        }
        if (overlayVisible.value)
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                Box(modifier = Modifier
                        .fillMaxSize()
                        .background(Color.DarkGray.copy(alpha = .6f))) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        ToolbarPanel()
                        if (configOpen.value) configPanel()
                        else if (pluginsOpen.value) pluginsPanel()
                    }
                }
            }

    }

    //Prevent back from closing
    override fun onBackPressed() {
        overlayVisible.value = !overlayVisible.value
    }

    var startedOSRS = false

    public override fun onResume() {
        super.onResume()
        updateHideUi()
        overlayView = findViewById(R.id.overlayView)
        overlayView!!.setContent { overlayContent() }
        if (!startedOSRS) {
            val policy = ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            initDisplay()
            startOSRS()
            initManagers()
            initOverlays()
            subscribeEvents()
            startedOSRS = true
        }
        if (client.gameState == GameState.LOGIN_SCREEN_AUTHENTICATOR) {
            overlayView!!.rootView.focusAndShowKeyboard()
        }
    }

    fun initManagers() {
        overlayManager = OverlayManager
        tooltipManager = TooltipManager
        PluginManager
    }

    fun initDisplay() {
        val windowMetrics = activity!!.windowManager.currentWindowMetrics
        val insets: Insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
        if (windowMetrics.bounds.width() > windowMetrics.bounds.height()) {
            GameEngine.width = windowMetrics.bounds.width()// - insets.left - insets.right
            GameEngine.height = windowMetrics.bounds.height()// - insets.top - insets.bottom
        } else {
            GameEngine.width = windowMetrics.bounds.height()// - insets.left - insets.right
            GameEngine.height = windowMetrics.bounds.width()// - insets.top - insets.bottom
        }

        println("Width: ${GameEngine.width}")
        println("Height: ${GameEngine.height}")
        gameViewBitmap =
                Bitmap.createBitmap(IntArray(GameEngine.width * GameEngine.height), GameEngine.width, GameEngine.height, Bitmap.Config.RGB_565)
                        .copy(Bitmap.Config.RGB_565, true)
        GameEngine.gameImage = BufferedImage(GameEngine.width, GameEngine.height, BufferedImage.TYPE_INT_RGB)
    }

    fun startOSRS() {
        class392.client = Client()
        deobClient = class392.client
        Client.androidActivity = this
        deobClient!!.init()
        deobClient!!.start()

        client = deobClient as net.runelite.api.Client
        initRSPSConfig()
        overlayRenderer = OverlayRenderer()
        client.callbacks = Hooks()
        var wait = true
        Thread {
            wait = false
        }.start()
        while (wait)
            Thread.sleep(100)
    }

    fun showSoftKeyboard() {
        runOnUiThread {
            window.decorView.focusAndShowKeyboard()
        }
    }

    fun hideSoftKeyboard() {
        runOnUiThread {
            window.decorView.hideKeyboard()
        }
    }


    fun View.focusAndShowKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this, InputMethodManager.SHOW_FORCED)
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
    }

    fun subscribeEvents() {
        eventBus.subscribe<LoginStateChanged>(Events.LOGIN_STATE_CHANGED) {
            println("new Login state: ${it.data.index}")
        }
        eventBus.subscribe<GameStateChanged>(Events.GAME_STATE_CHANGED) {
            if (it.data.gameState == GameState.LOGIN_SCREEN)
                splashFinished.value = true
            if (it.data.gameState == GameState.LOGGING_IN)
                hideSoftKeyboard()
            println("new Game state: ${it.data.gameState}")
        }
        eventBus.subscribe<LoginIndexChanged>(Events.LOGIN_INDEX_CHANGED) {
            println("new Login Index: ${it.data.newLoginIndex}")
        }
        eventBus.subscribe<ConfigChanged>(Events.CONFIG_CHANGED) {
            if (it.data.group == Configuration.MASTER_GROUP)
                if (it.data.key == "meteorColor") {
                    uiColor.value = Color(meteorConfig.uiColor.get()!!.rgb)
                }
        }
        eventBus.subscribe<Draw>(Events.DRAW) {
            pendingLeftClick?.let {
                if (client.gameState == GameState.LOGIN_SCREEN_AUTHENTICATOR) {
                    val editText = EditText(this)
                    editText.inputType = InputType.TYPE_CLASS_NUMBER

                    println("Showing Keyboard")
                    runOnUiThread {
                        showSoftKeyboard()
                    }
                    pendingLeftClick = null
                    return@subscribe
                }
                MouseHandler.mousePressed(it, 1)
                MouseHandler.mouseReleased()
                pendingLeftClick = null
            }
            pendingRightClick?.let {
                MouseHandler.mousePressed(it, 2)
                MouseHandler.mouseReleased()
                pendingRightClick = null
            }
        }
        eventBus.subscribe<SelectedLoginField>(Events.SELECTED_LOGIN_FIELD) {
            if (client.gameState == GameState.LOGIN_SCREEN || client.gameState == GameState.LOGIN_SCREEN_AUTHENTICATOR) {
                showSoftKeyboard()
            }
        }
        eventBus.subscribe<LoadingTextChanged>(Events.LOADING_TEXT_CHANGED) {
            loadingText.value = it.data.newText
        }
    }

    @SuppressLint("NewApi")
    private fun updateHideUi() {
        val decorView = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES)
        if (gameView != null) gameView!!.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES)
        window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
    }

    fun hoverListener() : OnHoverListener {
        return OnHoverListener{ _: View, event: MotionEvent ->
            val touchX = event.x.toInt()
            val touchY = event.y.toInt()
            val point = Point(touchX, touchY)
            MouseHandler.mouseMoved(point)
            return@OnHoverListener true
        }
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        var asciiKey = event.getUnicodeChar(event.metaState) //metasState = ctrl/shift etc
        if (event.keyCode == KeyEvent.KEYCODE_DEL)
            asciiKey = 8
        val awtEvent = java.awt.event.KeyEvent(asciiKey)
        if (event.action == KeyEvent.ACTION_DOWN) {
            KeyHandler.keyPressed(awtEvent)
        }
        if (event.action == KeyEvent.ACTION_UP) {
            KeyHandler.keyReleased(awtEvent)
            KeyHandler.keyTyped(awtEvent)
        }
        return super.dispatchKeyEvent(event)
    }

    var downTime: Long? = null


    class CustomOnScaleGestureListener :
            ScaleGestureDetector.SimpleOnScaleGestureListener() {
        var toSkip = 0
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            val scaleFactor = detector.scaleFactor
            if (toSkip <= 0) {
                if (scaleFactor > 1) {
                    // Zooming Out
                    MouseWheelHandler.mouseWheelMoved(MouseWheelEvent(-1))
                } else {
                    // Zooming In
                    MouseWheelHandler.mouseWheelMoved(MouseWheelEvent(1))
                }
                toSkip = 2
            } else {
                toSkip--
            }
            zooming = true
            println(scaleFactor)
            return true
        }

        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector) { }
    }

    var scaleGestureDetector: ScaleGestureDetector? = null

    @SuppressLint("ClickableViewAccessibility")
    fun touchListener() : OnTouchListener {
        return OnTouchListener{ _: View, event: MotionEvent ->
            val touchX = event.x.toInt()
            val touchY = event.y.toInt()
            val point = Point(touchX, touchY)
            if (overlayVisible.value)
                return@OnTouchListener true
            if (scaleGestureDetector == null)
                scaleGestureDetector = ScaleGestureDetector(this, CustomOnScaleGestureListener())
            scaleGestureDetector!!.onTouchEvent(event)
            if (zooming) {
                zooming = false
                return@OnTouchListener true
            }
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    downStartPosition = point
                    mouseDown = System.currentTimeMillis()
                    MouseHandler.mouseMoved(point)
                    if (client.gameState == GameState.LOGIN_SCREEN) {
                        pendingLeftClick = point
                        return@OnTouchListener true
                    }
                    downTime = System.currentTimeMillis()
                    println("Touch down x:$touchX y:$touchY")
                }
                MotionEvent.ACTION_UP -> {
                    downTime?.let {
                        if (System.currentTimeMillis() < (mouseDown + 250)) {
                            pendingLeftClick = point
                            return@OnTouchListener true
                        } else if (abs(downStartPosition!!.x - point.x) < 30
                                && abs(downStartPosition!!.y - point.y) < 30) {
                            pendingRightClick = point
                            pendingLeftClick = null
                        }
                        downStartPosition = null
                        GameEngine.movingCamera = false
                        println("Touch up x:$touchX y:$touchY")
                        MouseHandler.mouseReleased()
                    }
                }
                MotionEvent.ACTION_MOVE -> {
                    if (System.currentTimeMillis() > (mouseDown + 250)) {
                        GameEngine.movingCamera = true
                    }
                    MouseHandler.mouseMoved(point)
                }
            }
            return@OnTouchListener true
        }
    }

    var renders = 0;

    fun render() {
        runOnUiThread {
            if (!hasSetupGameView) {
                println("First Render")
                gameView = findViewById(R.id.gameView)
                gameView!!.setBackgroundColor(BLACK)
                gameView!!.setOnHoverListener(hoverListener())
                gameView!!.setOnTouchListener(touchListener())
                hasSetupGameView = true
            }
            gameView!!.setImageBitmap(gameViewBitmap)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun updateGameImage() {
        try {
            if (StudioGame.rasterProvider == null || StudioGame.rasterProvider.pixels == null)
                return

            shouldRender = true

            try {
                gameViewBitmap!!.setPixels(
                        client.graphicsPixels, 0, StudioGame.rasterProvider.width,
                        0, 0, StudioGame.rasterProvider.width, StudioGame.rasterProvider.height
                )
            } catch (_: java.lang.Exception) {
                shouldRender = false
            }

            if (shouldRender)
                render()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}