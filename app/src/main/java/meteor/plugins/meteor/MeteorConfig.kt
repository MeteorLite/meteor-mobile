package meteor.plugins.meteor


import meteor.Configuration
import meteor.config.Config
import meteor.config.ConfigItem
import meteor.config.legacy.Range
import meteor.ui.components.ComponentConstants
import java.awt.Color

class MeteorConfig() : Config(Configuration.MASTER_GROUP) {
    val infoBoxVertical = ConfigItem(
            this,
            group = group,
            keyName = "infoBoxVertical",
            name = "Display infoboxes vertically",
            description = "Toggles the infoboxes to display vertically",
            defaultValue = false
    )

    val infoBoxSize = ConfigItem(
            this,
            group = group,
            keyName = "infoBoxSize",
            name = "Infobox size",
            description = "Configures the size of each infobox in pixels",
            defaultValue = 35
    )

    val infoBoxTextOutline = ConfigItem(
            this,
            group = group,
            keyName = "infoBoxTextOutline",
            name = "Outline infobox text",
            description = "Draw a full outline instead of a simple shadow for infobox text",
            defaultValue = true
    )

    val overlayBackgroundColor = ConfigItem(
            this,
            group = group,
            keyName = "overlayBackgroundColor",
            name = "Overlay Color",
            description = "Configures the background color of infoboxes and overlays",
            defaultValue = ComponentConstants.STANDARD_BACKGROUND_COLOR
    )

    val pluginListSpacer = ConfigItem(
            this,
            group = group,
            keyName = "pluginListSpacer",
            name = "Plugin List Spacer",
            description = "",
            defaultValue = 7,
    )

    val isLightTheme = ConfigItem(
            this,
            group = group,
            keyName = "themeColor",
            name = "Light theme/Dark theme",
            description = "",
            defaultValue = true,
    )

    val uiColor = ConfigItem(
            this,
            group = group,
            keyName = "meteorColor",
            name = "Meteor Color",
            description = "",
            defaultValue = Color.CYAN,
    )

    val uiAccentColor = ConfigItem(
            this,
            group = group,
            keyName = "secondaryColor",
            name = "Accent Color",
            description = "",
            defaultValue = Color(156, 217, 209),
    )

    val pluginListTextSize = ConfigItem(
            this,
            group = group,
            name = "pluginListTextSize",
            keyName = "Plugin list Text Size",
            description = "Slide this to change the plugin list text size",
            defaultValue = 14
    )

    val toolbarWidth = ConfigItem(
            this,
            group = group,
            name = "toolbar width",
            keyName = "toolbar width",
            description = "",
            defaultValue = 45,
            min = 35,
            max = 100
    )
}
