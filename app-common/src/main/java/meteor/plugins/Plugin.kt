package meteor.plugins

import meteor.Main
import meteor.config.ConfigManager
import meteor.config.ConfigManager.getConfig
import meteor.config.ConfigManager.setDefaultConfiguration
import meteor.config.Config
import meteor.ui.overlay.Overlay

open class Plugin(override var daemon: Boolean = false) : EventSubscriber(daemon = daemon) {
    var configuration: Config? = null

    open var client = Main.client
    open val overlayManager = Main.overlayManager
    val overlays = ArrayList<Overlay>()
    var running = false

    fun getDescriptor(): PluginDescriptor? {
        return javaClass.getAnnotation(PluginDescriptor::class.java)
    }

    open fun getName(): String? {
        return getDescriptor()?.name
    }

    fun <T : Config?> configuration(clazz: Class<out Config?>): T {
        val c: T = getConfig(clazz)!! as T
        setDefaultConfiguration(c!!::class.java, false)
        configuration = c
        return c
    }

    inline fun <reified T : Config?> configuration(): T {
        val c: Config = getConfig(T::class.java)!!
        setDefaultConfiguration(c::class.java, false)
        configuration = c
        return c as T
    }

    inline fun <reified T : Overlay> overlay(overlay: T): T {
        overlays.add(overlay)
        return overlay
    }

    fun start() {
        subscribe()
        eventListening = true
        for (overlay in overlays)
            overlayManager.add(overlay)
        running = true
    }

    fun stop() {
        unsubscribe()
        eventListening = false
        for (overlay in overlays)
            overlayManager.remove(overlay)
        running = false
    }

    open fun onStart() {}
    open fun onStop() {}

    fun shouldEnable(): Boolean {
        val enabledConfig: String? = ConfigManager.getConfiguration(javaClass.simpleName, "pluginEnabled")
        val descriptor: PluginDescriptor? = javaClass.getAnnotation(PluginDescriptor::class.java)
        if (enabledConfig == null) {
            if (descriptor != null) {
                val enabledByDefault = descriptor.enabledByDefault || descriptor.cantDisable
                ConfigManager.setConfiguration(javaClass.simpleName, "pluginEnabled", enabledByDefault)
            }
        }

        var isEnabled = false

        if (ConfigManager.getConfiguration(javaClass.simpleName, "pluginEnabled").toBoolean())
            isEnabled = true

        return isEnabled
    }

    fun isFavorite() : Boolean {
        val savedValue = ConfigManager.getConfiguration(getName()!!.replace(" ", ""), "isFavorite") ?: return false
        return savedValue.toBoolean()
    }

    fun setFavorite(isFavorite: Boolean) {
        ConfigManager.setConfiguration(getName()!!.replace(" ", ""), "isFavorite", isFavorite)
    }

    open fun resetConfiguration() {}
}