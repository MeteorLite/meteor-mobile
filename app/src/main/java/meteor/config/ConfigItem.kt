package meteor.config

class ConfigItem<T>(val group: String,
                           val position: Int = Int.MAX_VALUE,
                           val keyName: String,
                           val name: String,
                           val description: String,
                           val defaultValue: T,
                           val hidden: Boolean = false,
                           val warning: String = "",
                           val section: String = "",
                           val title: String = "",
                           val parse: Boolean = false,
                           val method: String = "",
                           val unhide: String = "",
                           val unhideKey: String = "",
                           val unhideValue: String = "",
                           val hide: String = "",
                           val hideValue: String = "",
                           val enabledBy: String = "",
                           val enabledByValue: String = "",
                           val disabledBy: String = "",
                           val disabledByValue: String = "",
                           val textArea: Boolean = false,
                           val textField: Boolean = false,
                           val composePanel: Boolean = false,
                           val min: Int = -1,
                           val max: Int = -1,
                           val secret: Boolean = false) {

    @Suppress("UNCHECKED_CAST")
    fun get() : T? {
        val config = ConfigManager.getConfiguration(group, keyName, defaultValue!!::class.java)
        config?.let {
            return config as T?
        }
        return defaultValue
    }

    fun set(value: Any) {
        ConfigManager.setConfiguration(group, keyName, value)
    }

    override operator fun equals(other: Any?): Boolean {
        return get() == other
    }
}