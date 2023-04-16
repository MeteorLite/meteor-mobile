package meteor.ui.composables.nodes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import meteor.config.ConfigItem
import meteor.config.ConfigManager
import meteor.ui.preferences.background
import meteor.ui.preferences.uiColor


@Composable
fun stringAreaTextNode(configItem: ConfigItem<*>) {
    var text by remember {
        mutableStateOf(
                configItem.get() as String
        )
    }
    Row(modifier = Modifier.fillMaxWidth().height(100.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth().height(100.dp).background(background )
        ) {
            OutlinedTextField(
                    modifier = Modifier.fillMaxWidth().height(100.dp).padding(all = 3.dp),
                    value = text,
                    onValueChange = {
                        text = it
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
fun stringTextNode(configItem: ConfigItem<*>, mutableText: MutableState<String?>) {

    Row(modifier = Modifier.fillMaxWidth().height(60.dp).background(background ) ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth().height(60.dp)
        ) {
            OutlinedTextField(
                    value = mutableText.value ?: "",
                    visualTransformation = if (!configItem.secret) VisualTransformation.None else PasswordVisualTransformation(),
                    onValueChange = {

                        ConfigManager.setConfiguration(
                                configItem.group, configItem.keyName,
                                it
                        )
                        mutableText.value = it

                    },
                    label = {
                        Text(
                                configItem.name,
                                style = TextStyle(color = uiColor.value, fontSize = 12.sp)
                        )
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = uiColor.value, fontSize = 14.sp)
            )
        }
    }
    Spacer(Modifier.height(4.dp).background(background ) )
}
