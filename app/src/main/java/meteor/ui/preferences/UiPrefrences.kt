package meteor.ui.preferences

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import meteor.Main
import meteor.plugins.Plugin


var pluginsOpen = mutableStateOf(false)
var configOpen = mutableStateOf(false)
var hiscoreOpen = mutableStateOf( false)
var xpTrackerOpen = mutableStateOf(false)
var lootTrackerOpen = mutableStateOf(false)
var externalsOpen = mutableStateOf(false)
var notesOpen = mutableStateOf(false)
var infoPanelOpen = mutableStateOf(false)
var pluginPanelIsOpen = mutableStateOf(false)
var error = mutableStateOf(false)
var errorText = mutableStateOf("")
//var toolBarOpen = mutableStateOf(Main.meteorConfig.toolbarExpanded())
const val consoleHeight = 500
const val minimumHeight = 542
/*val totalClientWidth = Applet().clientWidth + Main.meteorConfig.toolbarWidth()
val totalMinimalWidth = Applet().minimalWidth + Main.meteorConfig.toolbarWidth()*/
/*var result: HiscoreResult? = null
var pluginPanel = mutableStateOf<PluginPanel?>(null)*/
var searchValue = mutableStateOf("")
//var lastButtonClicked : ToolbarButton? = null
lateinit var lastPlugin: Plugin
val pluginListSize = mutableStateOf(Main.meteorConfig.pluginListTextSize.get<Int>()!!)
val pluginSpacer = mutableStateOf(Main.meteorConfig.pluginListSpacer.get<Int>()!!)
val darkLightMode = mutableStateOf(Main.meteorConfig.isLightTheme.get<Boolean>()!!)
val uiColor = mutableStateOf(Color((Main.meteorConfig.uiColor.get<java.awt.Color>()!!).rgb))
val secondColor = mutableStateOf(Color((Main.meteorConfig.uiAccentColor.get<java.awt.Color>()!!).rgb))
val surface: Color
    get() = if (darkLightMode.value) Color(0xFF212121)
    else Color(0xFFf3f5f7)
val background: Color
    get() = if (darkLightMode.value) Color(0xFF191919)
    else Color(0xFFFFFFFF)

/*val darkThemeColors = darkColors(
    primary = uiColor.value,
    primaryVariant = Color(0xFF3E2723),
    secondary = Color.Cyan,
    background = Color(0xFF191919),
    surface = Color(0xFF212121),
    error = Color.Red,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    onError = Color.Black
)
val lightThemeColors = lightColors(
    primary = Color(0xFF0070D8),
    primaryVariant = Color(0xFF0070D8),
    onPrimary = Color(0xFFFFFFFFF),
    secondary = Color(0xFF9c27b0),
    onBackground = Color(0xFF212934),
    onSecondary = Color(0xFF595858),
    error = Color(0xFFc93838),
    onError = Color(0xFFFFFFFF),
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFf3f5f7),
)*/

fun setErrorState(errorMessage: String) {
    error.value = true
    errorText.value = errorMessage
}

/*@Composable
fun ErrorDialog(){
    if(error.value) {
        Popup(onDismissRequest = { error.value = false}, alignment = Alignment.Center, focusable = true) {
            Card(shape = RoundedCornerShape(5.dp), backgroundColor = surface) {
                Row(Modifier.width(200.dp)) {
                    Icon(imageVector = Octicons.Alert24,contentDescription = null, tint = uiColor.value)
                    Text(errorText.value, color = secondColor.value)
                }
            }
        }
    }
}*/
fun setOpenValues(openValue: Boolean) {
    pluginPanelIsOpen.value = false
    configOpen.value = false
    pluginsOpen.value = openValue
}



/*
val pluginListButton = addButton(
    ToolbarButton(
        "Plugins",
        LineAwesomeIcons.PlugSolid,
        iconColor = uiColor,
        description = "Opens Plugins list",
        onClick = {
            if (configOpen.value) {
                configOpen.value = false
                pluginsOpen.value = true
                return@ToolbarButton
            }
            if (pluginPanelIsOpen.value) {
                pluginPanelIsOpen.value = false
                pluginsOpen.value = true
                return@ToolbarButton
            }
            setOpenValues(!pluginsOpen.value)
        },
        position = 0)
)*/
