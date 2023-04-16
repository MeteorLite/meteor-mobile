package meteor

import androidx.compose.runtime.mutableStateOf
import meteor.plugins.Plugin
import meteor.plugins.meteor.MeteorConfig
import meteor.plugins.privateserver.PrivateServerConfig
import meteor.task.Scheduler
import meteor.ui.overlay.OverlayManager
import meteor.ui.overlay.OverlayRenderer
import meteor.ui.overlay.TooltipManager
import meteor.util.ExecutorServiceExceptionLogger
import net.runelite.http.api.xp.XpClient
import okhttp3.OkHttpClient
import java.util.concurrent.Executors

object Main {
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
    val xpClient = XpClient(httpClient)
    val overlayVisible = mutableStateOf(false)
    var platformPlugins = arrayListOf<Class<out Plugin>>()
}