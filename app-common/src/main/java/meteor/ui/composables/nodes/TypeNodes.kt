package meteor.ui.composables.nodes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.godaddy.android.colorpicker.harmony.ColorHarmonyMode
import com.godaddy.android.colorpicker.harmony.HarmonyColorPicker
import meteor.config.ConfigItem
import meteor.config.ConfigManager
import meteor.ui.preferences.background
import meteor.ui.preferences.surface
import meteor.ui.preferences.uiColor
import meteor.util.ColorUtil

@Composable
fun booleanNode(configItem: ConfigItem<*>, booleanValue: MutableState<Boolean?>) {
    Row(modifier = Modifier.fillMaxWidth().height(32.dp).background(background)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth(0.8f).height(32.dp).background(background)) {
            Text(configItem.name, style = TextStyle(color = uiColor.value, fontSize = 14.sp))
        }
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth().height(32.dp).background(background)) {
            Checkbox(booleanValue.value!!, onCheckedChange = {
                ConfigManager.setConfiguration(configItem.group, configItem.keyName, it)
                booleanValue.value = it
            }, enabled = true, modifier = Modifier.scale(0.85f), colors = CheckboxDefaults.colors(checkedColor = uiColor.value, uncheckedColor = surface))
        }
    }
    Spacer(Modifier.height(4.dp).background(background))
}

var ctrlPressed = false
var altPressed = false

/*@Composable
fun hotKeyNode(descriptor: ConfigDescriptor, configItemDescriptor: ConfigItemDescriptor) {
    val configStr = ConfigManager.getConfiguration(descriptor.group.value, configItemDescriptor.key())!!
    var keyBind by remember {
        mutableStateOf(ConfigManager.stringToObject(configStr, ModifierlessKeybind::class.java) as ModifierlessKeybind)
    }
    MaterialTheme(colors = darkThemeColors) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth().height(60.dp)) {
            OutlinedTextField(textStyle = TextStyle(color = Color.Cyan, fontSize = 14.sp, textDirection = TextDirection.Ltr, fontFamily = FontUtil.righteous), label = {
                Text(configItemDescriptor.name(), style = TextStyle(color = uiColor.value, fontSize = 14.sp))
            }, value = keyBind.toString(), onValueChange = {
                keyBind.toString()
            }, modifier = Modifier.onKeyEvent {
                if (it.type == KeyEventType.KeyDown) {
                    if (it.key.nativeKeyCode == KeyEvent.VK_ALT) {
                        altPressed = true
                    }
                    if (it.key.nativeKeyCode == KeyEvent.VK_CONTROL) {
                        ctrlPressed = true
                    }
                }
                if (it.type == KeyEventType.KeyUp) {
                    if (it.key.nativeKeyCode == KeyEvent.VK_ALT) {
                        altPressed = false
                    }
                    if (it.key.nativeKeyCode == KeyEvent.VK_CONTROL) {
                        ctrlPressed = false
                    }
                }

                if (it.type == KeyEventType.KeyDown) {
                    var modifier = 0

                    if (altPressed) {
                        modifier = InputEvent.ALT_DOWN_MASK
                    }
                    else if (ctrlPressed)
                        modifier = InputEvent.CTRL_DOWN_MASK

                    keyBind = ModifierlessKeybind(it.key.nativeKeyCode, modifier)
                    ConfigManager.setConfiguration(descriptor.group.value, configItemDescriptor.key(), keyBind)
                }
                true
            }.background(darkThemeColors.background, RoundedCornerShape(4.dp)).width(150.dp))
        }
        Spacer(Modifier.height(4.dp).background(background))
    }
}*/

@Composable
fun colorPickerNode(configItem: ConfigItem<*>, mutableState: MutableState<java.awt.Color?>) {
    val configColor = mutableState.value!!.rgb
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth(0.6f).height(32.dp).background(background)) {
        Text(configItem.name, style = TextStyle(color = uiColor.value, fontSize = 14.sp))
    }
    val color = Color(configColor)
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth().height(275.dp)) {
        HarmonyColorPicker(modifier = Modifier.height(275.dp).width(275.dp), color = color, onColorChanged = {
            val thisCol = ColorUtil.hsvToRgb(floatArrayOf(it.hue, it.saturation, it.value))
            val setCol = java.awt.Color(thisCol.component1(), thisCol.component2(), thisCol.component3())
            mutableState.value = setCol
            if (configItem.keyName == "meteorColor") {
                uiColor.value = color
            }
            configItem.set(setCol)
        }, harmonyMode = ColorHarmonyMode.SHADES)
    }
}


@Composable
fun enumNode(configItem: ConfigItem<*>, mutableState: MutableState<String?>?) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText = mutableState!!.value!!.replace(", ", "_")

    configItem.defaultValue!!::class.java.enumConstants?.let {
        var foundMatch = false

        for (enum in it) {
            if (selectedText?.equals(enum.toString()) == true) {
                selectedText = enum.toString().split("_").joinToString(" ") { it.capitalize() }
                foundMatch = true
                break
            }
            if (enum.toString().equals(selectedText?.split("_")?.joinToString(" "), ignoreCase = true)) {
                selectedText = enum.toString().split("_").joinToString(" ") { it.capitalize() }
                foundMatch = true
                break
            }
        }

        if (!foundMatch) {
            println("Mismatching enum member/name")
            println("expected: $selectedText")
            for (enum in it) {
                println("found: $enum")
            }
        }
    }

    Row(modifier = Modifier.fillMaxWidth().height(32.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth(0.6f).height(32.dp).background(background)) {
            Text(configItem.name, style = TextStyle(color = uiColor.value, fontSize = 14.sp))
        }
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth().height(32.dp)) {
            Box(modifier = Modifier.fillMaxWidth().height(30.dp).wrapContentSize(Alignment.TopStart).border(border = BorderStroke(2.dp, surface), shape = RoundedCornerShape(4.dp))) {

                Text(mutableState.value ?: "", color = uiColor.value, textAlign = TextAlign.Center, fontSize = 12.sp, modifier = Modifier.fillMaxWidth().fillMaxHeight().clickable(onClick = { expanded = true }).background(
                        background).wrapContentHeight())

                DropdownMenu(offset = DpOffset(x = -2.dp, y = 1.dp), expanded = expanded, onDismissRequest = { expanded = false }, modifier = Modifier.width(122.dp).background(
                        surface).border(border = BorderStroke(4.dp, surface))) {
                    configItem.defaultValue::class.java.enumConstants?.forEach {
                        DropdownMenuItem(modifier = Modifier.background(surface), onClick = {
                            expanded = false
                            configItem.set(it)
                            mutableState.value = it.toString().split("_").joinToString { it.capitalize() }
                        }, text = {
                            Text(text = it.toString().split("_").joinToString(" ") { it.capitalize() }, color = uiColor.value, textAlign = TextAlign.Center, fontSize = 12.sp, maxLines = 1, modifier = Modifier.fillMaxWidth().fillMaxHeight().wrapContentWidth())
                        })
                    }
                }
            }
        }
    }
    Spacer(Modifier.height(4.dp).background(background))
}