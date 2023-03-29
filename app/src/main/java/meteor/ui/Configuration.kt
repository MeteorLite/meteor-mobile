package meteor.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.Octicons
import compose.icons.octicons.ChevronLeft24
import meteor.config.Config
import meteor.config.ConfigManager
import meteor.plugins.PluginDescriptor
import meteor.ui.composables.items.*
import meteor.ui.composables.nodes.*
import meteor.ui.preferences.background
import meteor.ui.preferences.configOpen
import meteor.ui.preferences.lastPlugin
import meteor.ui.preferences.pluginsOpen
import meteor.ui.preferences.surface
import meteor.ui.preferences.uiColor

@Composable
fun configPanel() {
    val mod = Modifier.background(background
    ).fillMaxHeight().width(300.dp)
    Column {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top,
                modifier = mod
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    configPanelHeader()
                    configs()
                }
            }
        }
    }
}

val stringValues = HashMap<String, MutableState<String?>>()

val intValues = HashMap<String, MutableState<Int?>>()

val booleanValues = HashMap<String, MutableState<Boolean?>>()

val enumValues = HashMap<String, MutableState<String?>>()

val colorValues = HashMap<String, MutableState<java.awt.Color?>>()

fun updateStringValue(group: String, key: String, stringValue: String) {
    stringValues["$group:$key"]?.value = stringValue
}

fun updateIntValue(group: String, key: String, intValue: Int) {
    intValues["$group:$key"]?.value = intValue
}

fun updateBooleanValue(group: String, key: String, booleanValue: Boolean) {
    booleanValues["$group:$key"]?.value = booleanValue
}

val composePanelMap = HashMap<String, @Composable () -> Unit?>()

val currentConfigView = mutableStateOf<Config?>(null)

fun colorFromString(string: String): java.awt.Color? {
    return try {
        val i = Integer.decode(string)
        java.awt.Color(i, true)
    } catch (e: NumberFormatException) {
        null
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun configs(){
    LazyColumn(
        horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Top,
        modifier = Modifier.width(300.dp).fillMaxHeight().background(background ).padding(start = 4.dp)
    ) {
        val composePanelItems = currentConfigView.value!!.configItems.filter { it.composePanel && it.section.isEmpty() }.toMutableList()
        items(items = composePanelItems) { composePanel ->
            composePanelItems.forEach {
                val key = "${currentConfigView.value!!.group}:${it.keyName}"
                if (composePanel.position == 0) {
                    composePanelMap[key]!!.invoke()
                }
            }
        }
/*        val title = descriptor.titles.sortedBy { it.title.position }.toMutableList()
        items(items = title) {
            BoxWithConstraints(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth().padding(it.title.padding.dp),
                    text = it.name(),
                    style = TextStyle(
                        color = uiColor.value,
                        fontSize = it.title.size.sp,
                        textAlign = TextAlign.Center,
                    )
                )
            }
        }*/
        items(items = currentConfigView.value!!.configItems)
        { config ->
            val key = "${config.group}:${config.keyName}"

            var hidden = true
            if (!config.hidden) {
                hidden = false
            }
            if (config.hidden && config.unhide.isNotEmpty()) {
                val value = ConfigManager.getConfiguration(
                        config.group,
                        config.unhide
                )
                value?.let {
                    if (it.isNotEmpty())
                        hidden = !it.toBoolean()
                }
            }
            if (config.composePanel && config.position != 0) {
                composePanelMap[key]!!.invoke()
            }
            else if (!hidden)
                when (config.defaultValue!!.javaClass.toString()) {
                    "class java.lang.Boolean" -> {
                        booleanValues[key]?.let {
                            it.value = ConfigManager.getConfiguration(config.group, config.keyName)?.toBoolean()
                        }
                        if (booleanValues[key] == null)
                            booleanValues[key] = mutableStateOf(ConfigManager.getConfiguration(config.group, config.keyName)?.toBoolean())

                        booleanNode(config, booleanValues[key]!!)
                    }
                    "class java.lang.Integer" -> {
                        intValues[key]?.let {
                            it.value = ConfigManager.getConfiguration(config.group, config.keyName)?.toInt()
                        }
                        if (intValues[key] == null)
                            intValues[key] = mutableStateOf(ConfigManager.getConfiguration(config.group, config.keyName)?.toInt())
                        when {
                            config.textArea -> intAreaTextNode(
                                    config, intValues[key]!!
                            )
                            else -> intTextNode(config, intValues[key]!!)
                        }
                    }
                    "class java.lang.String" -> {
                        stringValues[key]?.let {
                            it.value = ConfigManager.getConfiguration(config.group, config.keyName)
                        }
                        if (stringValues[key] == null)
                            stringValues[key] = mutableStateOf(ConfigManager.getConfiguration(config.group, config.keyName))

                        when {
                            config.textArea -> stringAreaTextNode(config)
                            else -> {
                                stringTextNode(config, stringValues[key]!!)
                            }
                        }
                    }
                    "class java.awt.Color" -> {
                        colorValues[key]?.let {
                            it.value = colorFromString(ConfigManager.getConfiguration(config.group, config.keyName)!!)
                        }
                        if (colorValues[key] == null)
                            colorValues[key] = mutableStateOf(colorFromString(ConfigManager.getConfiguration(config.group, config.keyName)!!))

                        colorPickerNode(config, colorValues[key]!!)
                    }
                    else -> {
                        if (config.defaultValue.javaClass.isEnum) {
                            if (enumValues[key] == null)
                                enumValues[key] = mutableStateOf(ConfigManager.getConfiguration(config.group, config.keyName))

                            enumNode(config, enumValues[key])
                        } else {
                            println(config.defaultValue!!.javaClass.toString())
                        }
                    }
                }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun configPanelHeader() {
        Row(
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth().height(42.dp).background(surface)
        ) {
            Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.Start, modifier = Modifier.width(40.dp)) {
                IconButton( onClick = {
                    when {
                        configOpen.value -> configOpen.value = false
                    }
                    pluginsOpen.value = true
                }) {
                    Icon(
                        imageVector = Octicons.ChevronLeft24,
                        contentDescription = "Back to plugin page",
                        tint = uiColor.value,
                    )
                }
            }
            Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.Start, modifier = Modifier.width(180.dp)) {
                Text(
                        lastPlugin.javaClass.getDeclaredAnnotation(PluginDescriptor::class.java)!!.name,
                        style = TextStyle(color = uiColor.value, fontSize = 18.sp, textAlign = TextAlign.Left),
                )
            }
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                        onClick = {
                            ConfigManager.setDefaultConfiguration(lastPlugin.configuration!!::class.java, true)
                            lastPlugin.resetConfiguration()

                            for (configItem in lastPlugin.configuration!!.configItems) {
                                val key = "${configItem.group}:${configItem.keyName}"
                                stringValues.remove(key)
                                intValues.remove(key)
                                booleanValues.remove(key)
                                enumValues.remove(key)
                            }
                            //I'm lazy af so we just close the panel. FUCKING SUE ME
                            configOpen.value = false
                            pluginsOpen.value = true
                        },

                        ) {
                    Icon(
                            Icons.Outlined.Refresh,
                            contentDescription = "Reset plugin configuration",
                            tint = uiColor.value
                    )
                }

                val switchState = remember { mutableStateOf(lastPlugin.shouldEnable()) }
                Switch(
                    switchState.value,
                    onPluginToggled(switchState, lastPlugin,true),
                    enabled = true,
                    modifier = Modifier.scale(0.75f),
                    colors = SwitchDefaults.colors(checkedThumbColor = uiColor.value, checkedTrackColor = uiColor.value.darker(), uncheckedThumbColor = surface, uncheckedTrackColor = background)
                )
            }
    }
}










