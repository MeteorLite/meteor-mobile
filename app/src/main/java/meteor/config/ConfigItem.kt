package meteor.config

import java.awt.Color
import java.awt.Dimension
import java.awt.Point
import java.awt.Rectangle

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
        val config = ConfigManager.getConfiguration(group, keyName)
        config?.let {
            when (defaultValue!!::class) {
                Boolean::class -> return config.toBoolean() as T
                Int::class -> return config.toInt() as T
                Color::class.java -> return ConfigManager.colorFromString(config) as T
                Double::class -> return config.toDouble() as T
                Dimension::class.java -> return {
                    val splitStr = config.split("x").toTypedArray()
                    val width = splitStr[0].toInt()
                    val height = splitStr[1].toInt()
                    Dimension(width, height)
                } as T
                Point::class.java -> return {
                    val splitStr = config.split(":").toTypedArray()
                    val width = splitStr[0].toInt()
                    val height = splitStr[1].toInt()
                    Point(width, height)
                } as T
                Rectangle::class.java -> return {
                    val splitStr = config.split(":").toTypedArray()
                    val x = splitStr[0].toInt()
                    val y = splitStr[1].toInt()
                    val width = splitStr[2].toInt()
                    val height = splitStr[3].toInt()
                    Rectangle(x, y, width, height)
                } as T
                else -> {}
            }
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