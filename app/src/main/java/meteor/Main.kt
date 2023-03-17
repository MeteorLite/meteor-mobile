package meteor

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.View.OnHoverListener
import android.view.View.OnTouchListener
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.jaredrummler.android.device.DeviceName
import eventbus.Events
import eventbus.events.ClientTick
import eventbus.events.Draw
import eventbus.events.GameStateChanged
import eventbus.events.LoginIndexChanged
import eventbus.events.LoginStateChanged
import meteor.config.ConfigManager
import meteor.eventbus.KEventBus
import meteor.plugins.PluginManager
import meteor.plugins.meteor.MeteorConfig
import meteor.task.Scheduler
import meteor.ui.overlay.OverlayManager
import meteor.ui.overlay.OverlayRenderer
import meteor.ui.overlay.TooltipManager
import meteor.util.ExecutorServiceExceptionLogger
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
    }

    init {
        initConfigs()
    }

    val eventBus = KEventBus.INSTANCE
    var deobClient: Client? = null

    //Game View
    var gameView: View? = null
    var gameViewBitmap: Bitmap? = null
    var hasSetupGameView = false

    //Mouse tracking
    var downStartPosition : Point? = null
    var pendingLeftClick : Point? = null
    var pendingRightClick : Point? = null
    var mouseDown : Long = -1

    var shouldRender = false

    fun initConfigs() {
        // load configs immediately
        ConfigManager.loadSavedProperties()
        ConfigManager.setDefaultConfiguration(MeteorConfig::class.java, false)
        ConfigManager.saveProperties()

        // init meteor config
        meteorConfig = ConfigManager.getConfig(MeteorConfig::class.java)!!
    }

    override fun onStart() {
        super.onStart()
        activity = this
        initDisplay()
        startOSRS()
        initManagers()
        subscribeEvents()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DeviceName.init(this)
        setContentView(R.layout.activity_main)
        window.decorView.setBackgroundColor(Color.BLACK)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        updateHideUi()
    }

    public override fun onResume() {
        super.onResume()
        updateHideUi()
    }

    fun initManagers() {
        overlayManager = OverlayManager
        PluginManager
    }

    fun initDisplay() {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        GameEngine.width = displayMetrics.widthPixels
        GameEngine.height = displayMetrics.heightPixels
        calculateNotchOffset()
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
        eventBus.subscribe<ClientTick>(Events.CLIENT_TICK) {
            pendingLeftClick?.let {
                MouseHandler.mousePressed(it, 1)
                pendingLeftClick = null
            }
            pendingRightClick?.let {
                MouseHandler.mousePressed(it, 2)
                pendingRightClick = null
            }
        }
        eventBus.subscribe<LoginStateChanged>(Events.LOGIN_STATE_CHANGED) {
            println("new Login state: ${it.data.index}")
        }
        eventBus.subscribe<GameStateChanged>(Events.GAME_STATE_CHANGED) {
            println("new Game state: ${it.data.gameState}")
        }
        eventBus.subscribe<LoginIndexChanged>(Events.LOGIN_INDEX_CHANGED) {
            if (it.data.newLoginIndex == 2) {
                //we freeze here
                Login.Login_username = Secrets.username
                Login.Login_password = Secrets.password
                class19.updateGameState(20)
            }
            println("new Login index: ${it.data.newLoginIndex}")
        }
        eventBus.subscribe<Draw>(Events.DRAW) {
            updateGameImage()
        }
    }

    fun calculateNotchOffset() {
        val deviceName = DeviceName.getDeviceName()
        println("Device: $deviceName")

        //Notches
        //S22 Ultra
        if (deviceName == "b0q") {
            GameEngine.width += 125
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

    @SuppressLint("ClickableViewAccessibility")
    fun touchListener() : OnTouchListener {
        return OnTouchListener{ _: View, event: MotionEvent ->
            val touchX = event.x.toInt()
            val touchY = event.y.toInt()
            val point = Point(touchX, touchY)
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    downStartPosition = point
                    mouseDown = System.currentTimeMillis()
                    MouseHandler.mouseMoved(point)
                    pendingLeftClick = point
                    println("Touch down x:$touchX y:$touchY")
                }
                MotionEvent.ACTION_UP -> {
                    if (System.currentTimeMillis() > (mouseDown + 500)
                            && abs(downStartPosition!!.x - point.x) < 30
                            && abs(downStartPosition!!.y - point.y) < 30) {
                        pendingRightClick = point
                        pendingLeftClick = null
                    } else {
                        mouseDown = -1
                    }
                    downStartPosition = null
                    GameEngine.movingCamera = false
                    println("Touch up x:$touchX y:$touchY")
                    MouseHandler.mouseReleased()
                }
                MotionEvent.ACTION_MOVE -> {
                    if (System.currentTimeMillis() > (mouseDown + 500)) {
                        GameEngine.movingCamera = true
                    }
                    MouseHandler.mouseMoved(point)
                }
            }
            return@OnTouchListener true
        }
    }

    fun render() {
        runOnUiThread {
            if (!hasSetupGameView) {
                println("First Render")
                gameView = findViewById(R.id.imageView)
                (gameView as ImageView?)!!.setBackgroundColor(Color.BLACK)
                gameView!!.setOnHoverListener(hoverListener())
                gameView!!.setOnTouchListener(touchListener())
                hasSetupGameView = true
            }
            (gameView as ImageView?)!!.setImageBitmap(gameViewBitmap)
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
                        StudioGame.rasterProvider.pixels, 0, StudioGame.rasterProvider.width,
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