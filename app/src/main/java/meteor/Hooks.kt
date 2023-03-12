package meteor

import meteor.eventbus.Events
import meteor.eventbus.KEventBus
import meteor.eventbus.events.GameStateChanged
import net.runelite.api.hooks.Callbacks

import java.awt.image.BufferedImage
import java.awt.image.VolatileImage


class Hooks : Callbacks {
    private var lastCheck: Long = 0
/*    private val CHECK: Long = RSTimeUnit.GAME_TICKS.duration
        .toNanos()*/
    //private val GAME_TICK = GameTick()
    private var shouldProcessGameTick = false
/*    private var ignoreNextNpcUpdate = false
    private var lastMainBufferProvider: MainBufferProvider? = null
    private var lastGraphics: Graphics2D? = null
    private var drawManager = meteor.ui.DrawManager
    private var lastStretchedDimensions: Dimension? = null
    private var stretchedImage: VolatileImage? = null
    private var stretchedGraphics: Graphics2D? = null
    private var clientThread = ClientThread*/

    class PendingEvent(val type: Enum<*>, val event: Any)

    private var pendingEvents = ArrayList<PendingEvent>()

    init {
        KEventBus.INSTANCE.subscribe<GameStateChanged>(Events.GAME_STATE_CHANGED) { event ->
/*            when (event.data.gameState) {
                GameState.LOGGING_IN, GameState.HOPPING -> {
                    ignoreNextNpcUpdate = true
                }
                else -> {}
            }*/
        }
    }

    override fun post(type: Enum<*>, event: Any) {
        KEventBus.INSTANCE.post(type, event)
    }


    override fun postDeferred(type: Enum<*>, event: Any) {
        pendingEvents.add(PendingEvent(type, event))
    }

    override fun tick() {
        if (shouldProcessGameTick) {
            shouldProcessGameTick = false
            //KEventBus.INSTANCE.post(Events.GAME_TICK, GAME_TICK)
/*            val tick: Int = client.tickCount
            client.tickCount = tick + 1*/
        }

/*
        clientThread.invoke()

        val now = System.nanoTime()

        if (now - lastCheck < CHECK) {
            return
        }
*/

        //lastCheck = now
    }

    override fun frame() {
        TODO("Not yet implemented")
    }

    override fun updateNpcs() {
        TODO("Not yet implemented")
    }

    override fun drawScene() {
        TODO("Not yet implemented")
    }

    override fun drawAboveOverheads() {
        TODO("Not yet implemented")
    }

    companion object {
/*        @JvmStatic
        fun clearColorBuffer(x: Int, y: Int, width: Int, height: Int) {
            val bp = client.bufferProvider
            val canvasWidth = bp.width
            val pixels = bp.pixels

            var pixelPos = y * canvasWidth + x
            val pixelJump = canvasWidth - width

            for (cy in y until y + height) {
                for (cx in x until x + width) {
                    pixels[pixelPos++] = 0
                }
                pixelPos += pixelJump
            }
        }

        @JvmStatic
        fun renderDraw(
            renderable: Renderable, orientation: Int, pitchSin: Int, pitchCos: Int,
            yawSin: Int, yawCos: Int, x: Int, y: Int, z: Int, hash: Long
        ) {
            val drawCallbacks: DrawCallbacks? = client.drawCallbacks
            if (drawCallbacks != null) {
                drawCallbacks
                    .draw(renderable, orientation, pitchSin, pitchCos, yawSin, yawCos, x, y, z, hash)
            } else {
                renderable.`draw$api`(orientation, pitchSin, pitchCos, yawSin, yawCos, x, y, z, hash)
            }
        }

        @JvmStatic
        fun drawMenu(): Boolean {
            val event = BeforeMenuRender()
            client.callbacks.post(Events.BEFORE_MENU_RENDER, event)
            return event.consumed
        }*/
    }
}