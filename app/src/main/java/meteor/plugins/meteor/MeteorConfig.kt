package meteor.plugins.meteor


import meteor.Configuration
import meteor.config.Config
import meteor.config.ConfigItem
import meteor.ui.components.ComponentConstants

open class MeteorConfig() : Config(Configuration.MASTER_GROUP) {
    val infoBoxVertical = ConfigItem(
            keyName = "infoBoxVertical",
            name = "Display infoboxes vertically",
            description = "Toggles the infoboxes to display vertically",
            defaultValue = false
    )

    val infoBoxSize = ConfigItem(
            keyName = "infoBoxSize",
            name = "Infobox size",
            description = "Configures the size of each infobox in pixels",
            defaultValue = 35
    )

    val infoBoxTextOutline = ConfigItem(
            keyName = "infoBoxTextOutline",
            name = "Outline infobox text",
            description = "Draw a full outline instead of a simple shadow for infobox text",
            defaultValue = 43
    )

    val overlayBackgroundColor = ConfigItem(
            keyName = "overlayBackgroundColor",
            name = "Overlay Color",
            description = "Configures the background color of infoboxes and overlays",
            defaultValue = ComponentConstants.STANDARD_BACKGROUND_COLOR
    )

    init {
        configItems.add(infoBoxVertical)
        configItems.add(infoBoxSize)
        configItems.add(infoBoxTextOutline)
        configItems.add(overlayBackgroundColor)
    }
}
