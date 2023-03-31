package meteor.plugins

import androidx.compose.runtime.mutableStateListOf
import meteor.Main
import meteor.config.ConfigManager
import meteor.plugins.agility.AgilityPlugin
import meteor.plugins.autologin.AutoLoginPlugin
import meteor.plugins.interacthighlight.InteractHighlightPlugin
import meteor.plugins.meteor.MeteorPlugin
import meteor.plugins.mousetooltips.MouseTooltipPlugin
import meteor.plugins.neverlog.NeverLogoutPlugin
import meteor.plugins.onetapagility.OneTapAgilityPlugin
import meteor.plugins.playeroutline.PlayerOutlinePlugin
import meteor.plugins.prayerflicker.PrayerFlickerPlugin
import meteor.plugins.privateserver.PrivateServerPlugin
import meteor.plugins.reportbutton.ReportButtonPlugin
import meteor.plugins.statusbars.StatusBarsPlugin
import java.io.File
import java.net.URLClassLoader
import java.util.jar.Manifest
import kotlin.system.exitProcess


object PluginManager {
    var plugins = mutableStateListOf<Plugin>()
/*    val externalsDir = File(Configuration.METEOR_DIR, "externalplugins")*/
    val runningMap = HashMap<Plugin, Boolean>()
    val pluginsEnabled = true
    init {
        //init<Meteor>()
        if (pluginsEnabled) {
            init<MeteorPlugin>()
            init<AgilityPlugin>()
            init<AutoLoginPlugin>()
            init<InteractHighlightPlugin>()
            init<MouseTooltipPlugin>()
            init<NeverLogoutPlugin>()
            init<OneTapAgilityPlugin>()
            init<PlayerOutlinePlugin>()
            init<PrayerFlickerPlugin>()
            init<PrivateServerPlugin>()
            init<ReportButtonPlugin>()
            init<StatusBarsPlugin>()
        }
        Main.logger.warn("Loaded ${plugins.size} plugins")
    }

    inline fun <reified T : Plugin> init() {
        val plugin = T::class.java.getDeclaredConstructor().newInstance()
        if (plugins.filterIsInstance<T>().isNotEmpty())
            throw RuntimeException("Duplicate plugin ${plugin::class.simpleName} not allowed")

        plugin.configuration?.let {
            ConfigManager.setDefaultConfiguration(it::class.java, false)
        }

        if (ConfigManager.getConfiguration(
                plugin.javaClass.simpleName,
                "pluginEnabled"
            ) != null && plugin.javaClass.getAnnotation(PluginDescriptor::class.java)!!.disabledOnStartup
        )
            ConfigManager.setConfiguration(plugin.javaClass.simpleName, "pluginEnabled", false)

        runningMap[plugin] = plugin.shouldEnable()
        plugins.add(plugin)

        if (runningMap[plugin]!!)
            start(plugin)
    }

    private fun initExternalPlugin(jar: File, manifest: Manifest) {
        try {
            URLClassLoader(arrayOf(jar.toURI().toURL())).use { classLoader ->
                val plugin =
                    classLoader.loadClass(manifest.mainAttributes.getValue("Main-Class")).getDeclaredConstructor()
                        .newInstance() as Plugin
                if (plugins.any { p -> p.getName().equals(plugin.getName()) })
                    throw RuntimeException("Duplicate plugin (${plugin.getName()}) not allowed")

                if (ConfigManager.getConfiguration(
                        plugin.javaClass.simpleName,
                        "pluginEnabled"
                    ) != null && plugin.javaClass.getAnnotation(PluginDescriptor::class.java)!!.disabledOnStartup
                )
                    ConfigManager.setConfiguration(plugin.javaClass.simpleName, "pluginEnabled", false)

                plugins.add(plugin)
                runningMap[plugin] = plugin.shouldEnable()
                if (runningMap[plugin]!!)
                    start(plugin)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is java.lang.RuntimeException) {
                e.printStackTrace()
                exitProcess(-1)
            }

            Main.logger.error(e.toString())
            Main.logger.error("Failed to load external plugin: (${jar.absolutePath})")
        }

    }
    fun get(p: Class<out Plugin>): Plugin {
        return plugins.first { it.javaClass == p }
    }

    inline fun <reified T : Plugin> get(): T {
        return plugins.filterIsInstance<T>().first()
    }

    inline fun <reified T : Plugin> restart() {
        val it = plugins.filterIsInstance<T>().first()
        stop(it)
        start(it)
    }

    inline fun <reified T : Plugin> toggle() {
        val it = plugins.filterIsInstance<T>().first()
        if (runningMap[it]!!)
            stop(it)
        else
            start(it)
    }

    fun toggle(it: Plugin) {
        if (runningMap[it]!!)
            stop(it)
        else
            start(it)
    }

    fun stop(plugin: Plugin) {
        plugin.stop()
        plugin.onStop()
        runningMap[plugin] = false
    }

    fun start(plugin: Plugin) {
        plugin.start()
        plugin.onStart()
        runningMap[plugin] = true
    }

    fun shutdown() {
        for (plugin in plugins) {
            stop(plugin)
        }
    }
}