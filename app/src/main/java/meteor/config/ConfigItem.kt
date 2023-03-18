package meteor.config

class ConfigItem(    val group: String,
                     val position: Int = Int.MAX_VALUE,
                     val keyName: String,
                     val name: String,
                     val description: String,
                     val defaultValue: Any,
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
                     val max: Int = -1,) {

    inline fun <reified T> get() : T? {
        return ConfigManager.getConfiguration(group, keyName, T::class.java)
    }

    fun getSavedValue() : Any? {
        return ConfigManager.getConfiguration(group, keyName, defaultValue.javaClass)
    }

    fun set(value: Any) {
        ConfigManager.setConfiguration(group, keyName, value)
    }

    override operator fun equals(other: Any?): Boolean {
        return getSavedValue() == other
    }
}