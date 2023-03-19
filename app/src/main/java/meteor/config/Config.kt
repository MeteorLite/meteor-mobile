package meteor.config

open class Config(val group: String) {
    val configItems = ArrayList<ConfigItem<Any?>>()

    inline fun <reified T> value(value: Any): T {
        return value as T
    }
}