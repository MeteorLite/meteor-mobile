package meteor.ui.composables.nodes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import meteor.config.ConfigItem
import meteor.config.ConfigManager
import meteor.config.descriptor.ConfigDescriptor
import meteor.config.descriptor.ConfigItemDescriptor
import meteor.ui.preferences.background
import meteor.ui.preferences.uiColor
import java.lang.Exception

@Composable
fun intAreaTextNode(configItem: ConfigItem<*>, intValue: MutableState<Int?>) {
    Row(modifier = Modifier.fillMaxWidth().height(100.dp).background(Color(0xFF242424))) {
        Row(
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth().height(100.dp).background(background)
        ) {
            OutlinedTextField(
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    value = "${ConfigManager.getConfiguration(configItem.group, configItem.keyName)?.toInt()}",
                    onValueChange = {
                        intValue.value = it.toInt()
                        ConfigManager.setConfiguration(
                                configItem.group,
                                configItem.keyName,
                                it
                        )
                    },
                    label = {
                        Text(
                                configItem.name,
                                style = TextStyle(color = uiColor.value, fontSize = 14.sp)
                        )
                    },
                    maxLines = 30,
                    textStyle = TextStyle(color = uiColor.value, fontSize = 14.sp)
            )
        }
    }
    Spacer(Modifier.height(4.dp).background(background ) )
}

@Composable
fun intTextNode(configItem: ConfigItem<*>, intValue: MutableState<Int?>) {
    val textColor = mutableStateOf(uiColor.value)
    Row(modifier = Modifier.fillMaxWidth().height(60.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().height(60.dp).background(background )
        ) {
            OutlinedTextField(
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    value = "${intValue.value}",
                    visualTransformation = if (configItem.secret) PasswordVisualTransformation() else VisualTransformation.None,
                    onValueChange = { valueChanged ->
                        val newValue = try {
                            valueChanged.toInt()
                        } catch (e: Exception) {
                            // Return previous value
                            intValue.value ?: -1
                        }

                        var inRange = true
                        configItem.range?.let {
                            if (newValue > configItem.max || newValue < configItem.min)
                                inRange = false
                        }
                        intValue.value = newValue
                        if (inRange) {
                            ConfigManager.setConfiguration(
                                    configItem.group, configItem.keyName,
                                    "${intValue.value}"
                            )
                            textColor.value = uiColor.value
                        } else {
                            textColor.value = Color.Red
                        }
                    },
                    maxLines = 30,
                    label = {
                        Text(
                                configItem.name,
                                style = TextStyle(color = textColor.value, fontSize = 14.sp)
                        )
                    },
                    textStyle = TextStyle(color = uiColor.value, fontSize = 14.sp)
            )
        }
    }
    Spacer(Modifier.height(4.dp).background(background ) )
}