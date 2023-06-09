package meteor

import eventbus.Events
import eventbus.events.BeforeMenuRender
import eventbus.events.BeforeRender
import eventbus.events.GameStateChanged
import eventbus.events.GameTick
import meteor.eventbus.KEventBus
import meteor.rs.ClientThread
import meteor.ui.overlay.OverlayLayer
import meteor.util.RSTimeUnit
import net.runelite.api.GameState
import net.runelite.api.MainBufferProvider
import net.runelite.api.Renderable
import net.runelite.api.hooks.Callbacks
import net.runelite.api.hooks.DrawCallbacks
import net.runelite.api.widgets.Widget
import net.runelite.api.widgets.WidgetItem
import osrs.StudioGame
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Image
import java.awt.RenderingHints
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent
import java.awt.event.MouseWheelEvent
import java.awt.image.BufferedImage
import java.awt.image.WritableRaster

class Hooks : Callbacks {
    private var lastCheck: Long = 0
    private val CHECK: Long = RSTimeUnit.GAME_TICKS.duration
        .toNanos()
    private val GAME_TICK = GameTick()
    private var shouldProcessGameTick = false
    private var ignoreNextNpcUpdate = false
    private var lastMainBufferProvider: MainBufferProvider? = null
    private var lastGraphics: Graphics2D? = null
    //private var drawManager = meteor.ui.DrawManager
    private var lastStretchedDimensions: Dimension? = null
    private var stretchedImage: BufferedImage? = null
    private var stretchedGraphics: Graphics2D? = null
    private var clientThread = ClientThread
    private var client = Main.client
    private var overlayRenderer = Main.overlayRenderer
    class PendingEvent(val type: Enum<*>, val event: Any)

    private var pendingEvents = ArrayList<PendingEvent>()

    init {
        KEventBus.INSTANCE.subscribe<GameStateChanged>(Events.GAME_STATE_CHANGED) { event ->
            when (event.data.gameState) {
                GameState.LOGGING_IN, GameState.HOPPING -> {
                    ignoreNextNpcUpdate = true
                }
                else -> {}
            }
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
            KEventBus.INSTANCE.post(Events.GAME_TICK, GameTick())
            val tick: Int = client.tickCount
            client.tickCount = tick + 1
        }

        clientThread.invoke()

        val now = System.nanoTime()

        if (now - lastCheck < CHECK) {
            return
        }

        lastCheck = now

        try {
            // tick pending scheduled tasks
            Main.scheduler.tick()
            //chatMessageManager.process()
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
    }

    override fun frame() {
        KEventBus.INSTANCE.post(Events.BEFORE_RENDER, BeforeRender)
    }

    override fun updateNpcs() {
        if (ignoreNextNpcUpdate) {
            ignoreNextNpcUpdate = false
        } else {
            shouldProcessGameTick = true
        }

        pendingEvents.forEach { post(it.type, it.event) }
        pendingEvents.clear()
    }

    private fun getGraphics(mainBufferProvider: MainBufferProvider): Graphics2D {
        if (lastGraphics == null || lastMainBufferProvider !== mainBufferProvider) {
            lastGraphics?.dispose()
            lastMainBufferProvider = mainBufferProvider
            lastGraphics = mainBufferProvider.image.graphics as Graphics2D
        }
        return lastGraphics as Graphics2D
    }

    override fun drawScene() {
        val graphics2d: Graphics2D = getGraphics(client.bufferProvider as MainBufferProvider)
        try {
            overlayRenderer.renderOverlayLayer(graphics2d, OverlayLayer.ABOVE_SCENE)
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
    }

    override fun drawAboveOverheads() {
        val bufferProvider = client.bufferProvider as MainBufferProvider
        val graphics2d: Graphics2D = getGraphics(bufferProvider)

        try {
            overlayRenderer.renderOverlayLayer(graphics2d, OverlayLayer.UNDER_WIDGETS)
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
    }



    fun getImageFromArray(pixels: IntArray, width: Int, height: Int): BufferedImage {
        val image = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
        val raster = image.data as WritableRaster
        raster.setPixels(0, 0, width, height, pixels)
        return image
    }

    override fun draw(gameImage: BufferedImage?, graphics: Graphics?, x: Int, y: Int) {
        if (graphics == null) {
            return
        }

        val graphics2d: Graphics2D = gameImage!!.graphics as Graphics2D

        try {
            overlayRenderer.renderOverlayLayer(graphics2d, OverlayLayer.ALWAYS_ON_TOP)
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }

        if (client.isGpu) {
            // processDrawComplete gets called on GPU by the gpu plugin at the end of its
            // drawing cycle, which is later on.
            return
        }

        var gameImage: BufferedImage? = null

        if (false/*client.isStretchedEnabled*/) {
            val stretchedDimensions: Dimension = client.stretchedDimensions
            if (stretchedDimensions != lastStretchedDimensions || stretchedImage == null) {
                // if IMAGE_INCOMPATIBLE the image and g2d need to be rebuilt, otherwise
                // if IMAGE_RESTORED only the g2d needs to be rebuilt
                stretchedGraphics?.dispose()

                // VolatileImage javadocs says this proactively releases the resources used by the VolatileImage
                stretchedImage?.flush()

                if (stretchedDimensions.width <= 0 || stretchedDimensions.height <= 0)
                    return
                stretchedImage =
                        BufferedImage(stretchedDimensions.width, stretchedDimensions.height, BufferedImage.TYPE_INT_RGB)
                lastStretchedDimensions = stretchedDimensions

                stretchedGraphics = stretchedImage!!.graphics as Graphics2D
            }

            stretchedGraphics!!.setRenderingHint(
                    RenderingHints.KEY_INTERPOLATION,
                    if (client.isStretchedFast) RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR else RenderingHints.VALUE_INTERPOLATION_BILINEAR
            )
            stretchedImage!!.setRGB(0, 0, StudioGame.rasterProvider.width, StudioGame.rasterProvider.height, client.graphicsPixels, 0, StudioGame.rasterProvider.width)
            //gameImage = getImageFromArray(client.graphicsPixels, StudioGame.rasterProvider.width, StudioGame.rasterProvider.height)
            //stretchedGraphics!!.drawImage(gameImage, 0, 0, stretchedDimensions.width, stretchedDimensions.height, null)

            finalImage = stretchedImage!!
        } else {
            finalImage = null
        }

        AndroidMain.INSTANCE!!.updateGameImage()
    }

    private fun copy(src: Image): Image {
        val width = src.getWidth(null)
        val height = src.getHeight(null)
        val image = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        val graphics = image.graphics
        graphics.drawImage(src, 0, 0, width, height, null)
        graphics.dispose()
        return image
    }

    fun interface RenderableDrawListener {
        fun draw(renderable: Renderable?, ui: Boolean): Boolean
    }

    private val renderableDrawListeners: List<RenderableDrawListener> = java.util.ArrayList()

    override fun draw(renderable: Renderable?, drawingUi: Boolean): Boolean {
        try {
            for (renderableDrawListener in renderableDrawListeners) {
                if (!renderableDrawListener.draw(renderable, drawingUi)) {
                    return false
                }
            }
        } catch (ex: java.lang.Exception) {
            Main.logger.error("exception from renderable draw listener", ex)
        }
        return true
    }

    override fun drawInterface(interfaceId: Int, widgetItems: MutableList<WidgetItem>) {
        val graphics2d: Graphics2D = getGraphics(client.bufferProvider as MainBufferProvider)

        try {
            overlayRenderer.renderAfterInterface(graphics2d, interfaceId, widgetItems)
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
    }

    override fun drawLayer(layer: Widget, widgetItems: MutableList<WidgetItem>) {
        val bufferProvider = client.bufferProvider as MainBufferProvider
        val graphics2d: Graphics2D = getGraphics(bufferProvider)

        try {
            overlayRenderer.renderAfterLayer(graphics2d, layer, widgetItems)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun mousePressed(mouseEvent: MouseEvent): MouseEvent {
        return mouseEvent
    }

    override fun mouseReleased(mouseEvent: MouseEvent): MouseEvent {
        return mouseEvent
    }

    override fun mouseClicked(mouseEvent: MouseEvent): MouseEvent {
        return mouseEvent
    }

    override fun mouseEntered(mouseEvent: MouseEvent): MouseEvent {
        return mouseEvent
    }

    override fun mouseExited(mouseEvent: MouseEvent): MouseEvent {
        return mouseEvent
    }

    override fun mouseDragged(mouseEvent: MouseEvent): MouseEvent {
        return mouseEvent
    }

    override fun mouseMoved(mouseEvent: MouseEvent): MouseEvent {
        return mouseEvent
    }

    override fun mouseWheelMoved(event: MouseWheelEvent): MouseWheelEvent {
        return event
    }

    override fun keyPressed(keyEvent: KeyEvent) {

    }

    override fun keyReleased(keyEvent: KeyEvent?) {
        
    }

    override fun keyTyped(keyEvent: KeyEvent?) {
        
    }

    companion object {
        var finalImage: BufferedImage? = null

        @JvmStatic
        fun clearColorBuffer(x: Int, y: Int, width: Int, height: Int) {
            val bp = Main.client.bufferProvider
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
            val drawCallbacks: DrawCallbacks? = Main.client.drawCallbacks
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
            Main.client.callbacks.post(Events.BEFORE_MENU_RENDER, event)
            return event.consumed
        }
    }
}