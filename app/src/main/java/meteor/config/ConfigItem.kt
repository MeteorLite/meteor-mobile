package meteor.config

import meteor.Main
import java.awt.Color
import java.awt.Dimension
import java.awt.Point
import java.awt.Rectangle
import java.util.EnumSet

class ConfigItem<T>(val config: Config,
                    val group: String,
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
                           val range: Boolean = false,
                           val composePanel: Boolean = false,
                           val min: Int = -1,
                           val max: Int = -1,
                           val secret: Boolean = false) {

    init {
        config.configItems.add(this)
    }

    @Suppress("UNCHECKED_CAST")
    fun get() : T? {
        val config = ConfigManager.getConfiguration(group, keyName)
        config?.let {
            val type = defaultValue!!::class.toString()
            if (type.contains("java.lang.String"))
                return config as T
            if (type.contains("java.lang.Boolean"))
                return config.toBoolean() as T
            if (type.contains("java.lang.Integer"))
                return config.toInt() as T
            if (type.contains("java.awt.Color"))
                return ConfigManager.colorFromString(config) as T
            if (type.contains("java.lang.Double"))
                return config.toDouble() as T
            if (type.contains("java.awt.Dimension"))
                return {
                    val splitStr = config.split("x").toTypedArray()
                    val width = splitStr[0].toInt()
                    val height = splitStr[1].toInt()
                    Dimension(width, height)
                } as T
            if (type.contains("java.awt.Point"))
                return {
                    val splitStr = config.split(":").toTypedArray()
                    val width = splitStr[0].toInt()
                    val height = splitStr[1].toInt()
                    Point(width, height)
                } as T
            if (type.contains("java.awt.Rectangle"))
                return {
                    val splitStr = config.split(":").toTypedArray()
                    val x = splitStr[0].toInt()
                    val y = splitStr[1].toInt()
                    val width = splitStr[2].toInt()
                    val height = splitStr[3].toInt()
                    Rectangle(x, y, width, height)
                } as T

            if (defaultValue!!::class.java.isEnum) {
                return try {
                    java.lang.Enum.valueOf(defaultValue!!::class.java as Class<out Enum<*>?>, config) as T
                } catch (e: Exception) {
                    e.printStackTrace()
                    throw RuntimeException("Failed to load enum")
                }
            }

            throw RuntimeException(defaultValue!!::class.toString())
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