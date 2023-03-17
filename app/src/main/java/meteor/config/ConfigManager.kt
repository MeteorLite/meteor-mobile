@file:Suppress("UNCHECKED_CAST")

package meteor.config

import meteor.Main
import com.google.common.base.Strings
import eventbus.Events
import meteor.Configuration.CONFIG_FILE
import meteor.config.legacy.*
import eventbus.events.ConfigChanged
import meteor.Configuration.METEOR_DIR
import meteor.config.descriptor.*
import meteor.eventbus.KEventBus
import meteor.plugins.Plugin
import net.runelite.api.coords.WorldPoint
import java.awt.Color
import java.awt.Dimension
import java.awt.Point
import java.awt.Rectangle
import java.io.*
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import java.lang.reflect.Proxy
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import java.util.*
import java.util.function.Consumer
import java.util.stream.Collectors

object ConfigManager {
    private val properties = Properties()
    private val handler: ConfigInvocationHandler = ConfigInvocationHandler(this)
    private val consumers: HashMap<String, Consumer<in Plugin?>> = HashMap()
    private const val KEY_SPLITTER_GROUP = 0
    private const val KEY_SPLITTER_KEY = 1
    var loaded = false

    fun setPlayerConfiguration(attribute: String, value: Any) {
        Main.client.localPlayer?.let {
            it.name?.let { name ->
                if (name.isNotEmpty())
                    setConfiguration("$name-attributes", attribute, value)
            }
        }
    }

    fun unsetPlayerConfiguration(attribute: String) {
        Main.client.localPlayer?.let {
            it.name?.let { name ->
                if (name.isNotEmpty())
                    unsetConfiguration("$name-attributes", attribute)
            }
        }
    }

    fun getPlayerConfiguration(attribute: String) : String? {
        Main.client.localPlayer?.let {
            it.name?.let { name ->
                if (name.isNotEmpty())
                    return getConfiguration("$name-attributes", attribute)
            }
        }
        return null
    }

    fun stringToObject(str: String, type: Class<*>): Any? {
        if (type == Boolean::class.javaPrimitiveType || type == Boolean::class.java) {
            return (str).toBoolean()
        }
        if (type == Int::class.javaPrimitiveType || type == Int::class.java) {
            return try {
                str.toInt()
            } catch (e: NumberFormatException) {
                1
            }
        }
        if (type == Double::class.javaPrimitiveType || type == Double::class.java) {
            return str.toDouble()
        }
        if (type == Color::class.java) {
            return colorFromString(str)
        }
        if (type == Dimension::class.java) {
            val splitStr = str.split("x").toTypedArray()
            val width = splitStr[0].toInt()
            val height = splitStr[1].toInt()
            return Dimension(width, height)
        }
        if (type == Point::class.java) {
            val splitStr = str.split(":").toTypedArray()
            val width = splitStr[0].toInt()
            val height = splitStr[1].toInt()
            return Point(width, height)
        }
        if (type == Rectangle::class.java) {
            val splitStr = str.split(":").toTypedArray()
            val x = splitStr[0].toInt()
            val y = splitStr[1].toInt()
            val width = splitStr[2].toInt()
            val height = splitStr[3].toInt()
            return Rectangle(x, y, width, height)
        }
        if (type.isEnum) {
            return try {
                java.lang.Enum.valueOf(type as Class<out Enum<*>?>, str)
            } catch (e: Exception) {
                Main.logger.warn("Outdated enum config ($str) could not be loaded")
                null
            }
        }
        if (type == Instant::class.java) {
            return Instant.parse(str)
        }
        if (type == WorldPoint::class.java) {
            val splitStr = str.split(":").toTypedArray()
            val x = splitStr[0].toInt()
            val y = splitStr[1].toInt()
            val plane = splitStr[2].toInt()
            return WorldPoint(x, y, plane)
        }
        if (type == Duration::class.java) {
            return Duration.ofMillis(str.toLong())
        }
        if (type == ByteArray::class.java) {
            return Base64.getUrlDecoder().decode(str)
        }
        return str
    }

    fun objectToString(type: Any?): String? {
        if (type is Color) {
            return type.rgb.toString()
        }
        if (type is Enum<*>) {
            return type.name
        }
        if (type is Dimension) {
            return type.width.toString() + "x" + type.height
        }
        if (type is Point) {
            return type.x.toString() + ":" + type.y
        }
        if (type is Rectangle) {
            return type.x.toString() + ":" + type.y + ":" + type.width + ":" + type.height
        }
        if (type is Instant) {
            return type.toString()
        }
        if (type is WorldPoint) {
            return type.x.toString() + ":" + type.y + ":" + type.plane
        }
        if (type is Duration) {
            return type.toMillis().toString()
        }
        if (type is ByteArray) {
            return Base64.getUrlEncoder().encodeToString(type as ByteArray?)
        }
        return if (type is EnumSet<*>) {
            if (type.size == 0) {
                getElementType(type as EnumSet<*>?)!!.canonicalName + "{}"
            } else type.toTypedArray()[0].javaClass.canonicalName + "{" + type + "}"
        } else type?.toString()
    }

    fun getElementType(enumSet: EnumSet<*>?): Class<*>? {
        var thisSet = enumSet
        if (thisSet!!.isEmpty()) {
            thisSet = EnumSet.complementOf(thisSet)
        }
        return thisSet!!.iterator().next().javaClass.declaringClass
    }



    fun colorFromString(string: String): Color? {
        return try {
            val i = Integer.decode(string)
            Color(i, true)
        } catch (e: NumberFormatException) {
            null
        }
    }

    fun splitKey(key: String): Array<String>? {
        var newKey = key
        val i = newKey.indexOf('.')
        if (i == -1) {
            // all keys must have a group and key
            return null
        }
        val group = newKey.substring(0, i)
        newKey = newKey.substring(i + 1)
        return arrayOf(group, newKey)
    }

    fun saveProperties() {
        if (!METEOR_DIR.exists())
            METEOR_DIR.mkdirs()
        if (!CONFIG_FILE.exists()) {
            CONFIG_FILE.createNewFile()
        }

        CONFIG_FILE.writer(charset = StandardCharsets.UTF_8).use { out ->
            properties.store(out, "Meteor configuration")
        }
    }

    fun <T : Config?> getConfig(clazz: Class<T>): T? {
        if (!Modifier.isPublic(clazz.modifiers)) {
            throw RuntimeException(
                    "Non-public configuration classes can't have default methods invoked")
        }
        return clazz.newInstance() as T ?: throw RuntimeException("Configuration fucked")
    }

    fun getConfigurationKeys(prefix: String): List<String> {
        return properties.keys.stream().filter { v: Any -> (v as String).startsWith(prefix) }
                .map { obj: Any? -> String::class.java.cast(obj) }.collect(Collectors.toList())
    }

    fun getConfiguration(groupName: String, key: String): String? {
        return properties.getProperty(getWholeKey(groupName, key))
    }

    inline fun <reified T> getConfiguration(groupName: String, key: String, clazz: Class<T>): T? {
        val value = getConfiguration(groupName, key)
        if (!Strings.isNullOrEmpty(value)) {
            try {
                when (T::class) {
                    Boolean::class -> return stringToObject(value!!, clazz).toString().toBoolean() as T
                    Int::class -> return stringToObject(value!!, clazz).toString().toInt() as T
                    Color::class.java -> return colorFromString(stringToObject(value!!, clazz).toString()) as T
                    Double::class -> return stringToObject(value!!, clazz).toString().toDouble() as T
                    Dimension::class.java -> return {
                        val splitStr = stringToObject(value!!, clazz).toString().split("x").toTypedArray()
                        val width = splitStr[0].toInt()
                        val height = splitStr[1].toInt()
                        Dimension(width, height)
                    } as T
                    Point::class.java -> return {
                        val splitStr = stringToObject(value!!, clazz).toString().split(":").toTypedArray()
                        val width = splitStr[0].toInt()
                        val height = splitStr[1].toInt()
                        Point(width, height)
                    } as T
                    Rectangle::class.java -> return {
                        val splitStr = stringToObject(value!!, clazz).toString().split(":").toTypedArray()
                        val x = splitStr[0].toInt()
                        val y = splitStr[1].toInt()
                        val width = splitStr[2].toInt()
                        val height = splitStr[3].toInt()
                        Rectangle(x, y, width, height)
                    } as T
                }
                return stringToObject(value!!, clazz) as T
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                Main.logger.warn("Unable to unmarshal {} ", getWholeKey(groupName, key), e);
            }
        }
        return null
    }

    fun getWholeKey(groupName: String, key: String): String {
        return "$groupName.$key"
    }

    fun unsetConfiguration(groupName: String, key: String) {
        val wholeKey = getWholeKey(groupName, key)
        var oldValue: String?
        synchronized(this) { oldValue = properties.remove(wholeKey) as String? }
        if (oldValue == null) {
            return
        }

        //log.debug("Unsetting configuration value for {}", wholeKey);
        handler.invalidate()
        val configChanged = ConfigChanged()
        configChanged.group = groupName
        configChanged.key = key
        configChanged.oldValue = oldValue
        KEventBus.INSTANCE.post(Events.CONFIG_CHANGED, configChanged)
    }

    fun getAllDeclaredInterfaceFields(clazz: Class<*>): Collection<Field> {
        val methods: MutableCollection<Field> = HashSet()
        val interfaces = Stack<Class<*>>()
        interfaces.push(clazz)
        while (!interfaces.isEmpty()) {
            val inter = interfaces.pop()
            Collections.addAll(methods, *inter.declaredFields)
            Collections.addAll(interfaces, *inter.interfaces)
        }
        return methods
    }

    private fun getAllDeclaredInterfaceMethods(clazz: Class<*>): Collection<Method> {
        val methods: HashSet<Method> = HashSet()
        val interfaces = Stack<Class<*>>()
        interfaces.push(clazz)
        while (!interfaces.isEmpty()) {
            val inter: Class<*> = interfaces.pop()
            Collections.addAll(methods, *inter.declaredMethods)
            Collections.addAll(interfaces, *inter.interfaces)
        }
        return methods
    }

/*    fun getDefaultConfigValue(config: Any, methodName: String) : String?{
        var value: String? = null
        val clazz = config.javaClass.interfaces[0]
        for (method in getAllDeclaredInterfaceMethods(clazz)) {
            if (method.name == methodName) {
                val item: ConfigItem? = method.getAnnotation(ConfigItem::class.java)

                // only get default configuration for methods which read configuration (0 args)
                if (item == null || method.parameterCount != 0) {
                    continue
                }

                val defaultValue: Any = try {
                    ConfigInvocationHandler.callDefaultMethod(config, method, null)
                } catch (ex: Throwable) {
                    ex.printStackTrace()
                    continue
                }
                value = objectToString(defaultValue)
            }
        }
        return value
    }*/

    fun setDefaultConfiguration(config: Class<out Config>, override: Boolean) {
        val clazz = config.newInstance()
        for (field in clazz.javaClass.declaredFields) {
            field.isAccessible = true
            if (field.type.name == ConfigItem::class.java.name) {
                val configItem = field.get(clazz) as ConfigItem
                clazz.configItems.add(configItem)
            }
        }
        for (configItem in clazz.configItems) {
            if (!override) {
                // This checks if it is set and is also unmarshallable to the correct type; so
                // we will overwrite invalid config values with the default
                val current = getConfiguration(clazz.group, configItem.keyName, configItem.defaultValue.javaClass)
                if (current != null) {
                    continue  // something else is already set
                }
            }
            val current = getConfiguration(clazz.group, configItem.keyName)
            val valueString = objectToString(configItem.defaultValue)
            // null and the empty string are treated identically in sendConfig and treated as an unset
            // If a config value defaults to "" and the current value is null, it will cause an extra
            // unset to be sent, so treat them as equal
            if (current == valueString || current.isNullOrEmpty() && valueString.isNullOrEmpty()) {
                continue  // already set to the default value
            }

            //log.debug("Setting default configuration value for {}.{} to {}", clazz.group(), item.keyName(), defaultValue);
            setConfiguration(clazz.group, configItem.keyName, valueString!!)
        }
    }

    fun setConfiguration(groupName: String, key: String, value: Any) {
        // do not save consumers for buttons, they cannot be changed anyway
        if (value is Consumer<*>) {
            return
        }

        require(!(groupName.isEmpty() || key.isEmpty() || key.indexOf(':') != -1))

        val wholeKey = getWholeKey(groupName, key)
        var oldValue: String?
        synchronized(this) { oldValue = properties.setProperty(wholeKey, objectToString(value)) as String? }

        handler.invalidate()

        val configChanged = ConfigChanged()
        configChanged.group = groupName
        configChanged.key = key
        configChanged.oldValue = oldValue
        configChanged.newValue = objectToString(value)
        KEventBus.INSTANCE.post(Events.CONFIG_CHANGED, configChanged)

        saveProperties()
    }

    @Synchronized
    fun loadSavedProperties() {
        consumers.clear()
        val newProperties = Properties()


        try {
            if (CONFIG_FILE.exists())
            FileInputStream(CONFIG_FILE).use { `in` ->
                newProperties.load(InputStreamReader(`in`, StandardCharsets.UTF_8))
                swapProperties(newProperties)
                loaded = true
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        if (!loaded) {
            saveProperties()
            loaded = true
        }
    }

    private fun swapProperties(newProperties: Properties) {
        val newKeys: Set<Any> = HashSet(newProperties.keys)
        val oldKeys: Set<Any> = HashSet(properties.keys)
        synchronized(this) { handler.invalidate() }
        for (wholeKey in oldKeys) {
            val split = splitKey((wholeKey as String)) ?: continue
            val groupName = split[KEY_SPLITTER_GROUP]
            val key = split[KEY_SPLITTER_KEY]
            val newValue = properties[wholeKey] as String?
            setConfiguration(groupName, key, newValue!!)
        }
        for (wholeKey in newKeys) {
            val split = splitKey((wholeKey as String)) ?: continue
            val groupName = split[KEY_SPLITTER_GROUP]
            val key = split[KEY_SPLITTER_KEY]
            val newValue = newProperties[wholeKey] as String?
            setConfiguration(groupName, key, newValue!!)
        }
    }

}