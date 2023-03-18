package meteor

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.Color.BLACK
import android.graphics.Insets
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.View.OnHoverListener
import android.view.View.OnTouchListener
import android.view.WindowInsets
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jaredrummler.android.device.DeviceName
import eventbus.Events
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
import meteor.task.Scheduler
import meteor.ui.overlay.OverlayManager
import meteor.ui.overlay.OverlayRenderer
import meteor.ui.overlay.TooltipManager
import meteor.util.ExecutorServiceExceptionLogger
import net.runelite.api.GameState
import osrs.*
import java.awt.Point
import java.awt.image.BufferedImage
import java.io.*
import java.util.concurrent.Executors
import kotlin.math.abs


class Main : AppCompatActivity() {
    companion object {
        var activity: AppCompatActivity? = null
        lateinit var client: net.runelite.api.Client
        val logger = Logger("Meteor")
        lateinit var overlayManager: OverlayManager
        lateinit var meteorConfig: MeteorConfig
        lateinit var tooltipManager: TooltipManager
        lateinit var overlayRenderer: OverlayRenderer
        val executor = ExecutorServiceExceptionLogger(Executors.newSingleThreadScheduledExecutor())
        val scheduler = Scheduler()
        var INSTANCE : Main? = null
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
    var mouseDown : Long = -1

    var shouldRender = false

    var splashFinished = mutableStateOf(false)

    fun initConfigs() {
        // load configs immediately
        ConfigManager.loadSavedProperties()
        ConfigManager.setDefaultConfiguration(MeteorConfig::class.java, false)
        ConfigManager.saveProperties()

        // init meteor config
        meteorConfig = ConfigManager.getConfig(MeteorConfig::class.java)!!
    }

    override fun onStart() {
        INSTANCE = this
        super.onStart()
        activity = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DeviceName.init(this)
        setContentView(R.layout.activity_main)
        window.decorView.setBackgroundColor(android.graphics.Color.BLACK)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        updateHideUi()
    }

    private val overlayVisible = mutableStateOf(false)

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

    @Composable
    fun splashContent() {
        BoxWithConstraints(modifier = Modifier
                .fillMaxSize()
                .background(background), contentAlignment = Alignment.Center) {
            brandBadge()
        }
    }

    @Composable
    fun overlayContent() {
        if (!splashFinished.value) {
            splashContent()
            return
        }
        if (overlayVisible.value)
            Box(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray.copy(alpha = .6f))) {
                pluginsPanel()
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
            initDisplay()
            startOSRS()
            initManagers()
            subscribeEvents()
            startedOSRS = true
        }
    }

    fun initManagers() {
        overlayManager = OverlayManager
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
        overlayRenderer = OverlayRenderer()
        client.callbacks = Hooks()
        var wait = true
        Thread {
            wait = false
        }.start()
        while (wait)
            Thread.sleep(100)
    }

    fun subscribeEvents() {
        eventBus.subscribe<LoginStateChanged>(Events.LOGIN_STATE_CHANGED) {
            println("new Login state: ${it.data.index}")
        }
        eventBus.subscribe<GameStateChanged>(Events.GAME_STATE_CHANGED) {
            println("new Game state: ${it.data.gameState}")
        }
        eventBus.subscribe<LoginIndexChanged>(Events.LOGIN_INDEX_CHANGED) {
/*            if (it.data.newLoginIndex == 2) {
                //we freeze here
                Login.Login_username = Secrets.username
                Login.Login_password = Secrets.password
                class19.updateGameState(20)
            }*/
            println("new Login index: ${it.data.newLoginIndex}")
        }
        eventBus.subscribe<Draw>(Events.DRAW) {
            pendingLeftClick?.let {
                MouseHandler.mousePressed(it, 1)
                pendingLeftClick = null
            }
            pendingRightClick?.let {
                MouseHandler.mousePressed(it, 2)
                pendingRightClick = null
            }
        }
        eventBus.subscribe<SelectedLoginField>(Events.SELECTED_LOGIN_FIELD) {
            if (client.gameState == GameState.LOGIN_SCREEN) {

                val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }
        eventBus.subscribe<LoadingTextChanged>(Events.LOADING_TEXT_CHANGED) {
            if (it.data.newText.contains("Loaded title screen", ignoreCase = true)) {
                splashFinished.value = true
            }

            println("Loading text changed: ${it.data.newText}")
        }
    }

    /*    fun calculateNotchOffset() {
            val deviceName = DeviceName.getDeviceName()
            println("Device: $deviceName")

            //Notches
            //S22 Ultra
            if (deviceName == "b0q") {
                GameEngine.width += 125
            }
        }*/

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

    fun showKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
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

    var downTime: Long? = null

    @SuppressLint("ClickableViewAccessibility")
    fun touchListener() : OnTouchListener {
        return OnTouchListener{ _: View, event: MotionEvent ->
            val touchX = event.x.toInt()
            val touchY = event.y.toInt()
            val point = Point(touchX, touchY)
            if (overlayVisible.value)
                return@OnTouchListener true
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
                gameView = findViewById(R.id.imageView)
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

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        var asciiKey = event.getUnicodeChar(event.metaState) //metasState = ctrl/shift etc
        if (event.keyCode == KeyEvent.KEYCODE_DEL)
            asciiKey = 8

        //TODO
        /*        if (event.action == KeyEvent.ACTION_DOWN) {
                    KeyHandler.keyPressed(asciiKey)
                }
                if (event.action == KeyEvent.ACTION_UP) {
                    KeyHandler.keyReleased(asciiKey)
                    KeyHandler.keyTyped(event)
                }*/
        return super.dispatchKeyEvent(event)
    }
}