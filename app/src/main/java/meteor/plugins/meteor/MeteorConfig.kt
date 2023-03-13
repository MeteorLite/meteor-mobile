package meteor.plugins.meteor


import androidx.compose.ui.unit.LayoutDirection
import meteor.Configuration
import meteor.config.legacy.*
import meteor.ui.components.ComponentConstants
import java.awt.Color

@ConfigGroup(Configuration.MASTER_GROUP)
interface MeteorConfig : Config {
    @Secret
    @ConfigItem(
        keyName = "authKey",
        name = "Auth Key",
        description = "",
        position = 0
    )
    fun authKey(): String {
        return ""
    }

    @ConfigItem(
        name = "Always on top",
        keyName = "alwaysOnTop",
        description = "",
        position = 1,
    )
    fun alwaysOnTop(): Boolean {
        return false
    }
    @ConfigItem(
        name = "Lock window size",
        keyName = "lockWindowSize",
        description = "",
        position = 1,
    )
    fun lockWindowSize(): Boolean {
        return false
    }

    @ConfigItem(
        name = "lockedWindowSize",
        keyName = "lockedWindowSize",
        description = "",
        position = 1,
        hidden = true,
        unhide = "nothingBITCH"
    )
    fun lockedWindowSize(): String {
        return "1280:720"
    }
    @ConfigItem(
        name = "MeteorColor",
        keyName = "MeteorColor",
        description = "",
        position = 2,
    )
    fun uiColor(): Color {
        return Color.CYAN
    }
    @ConfigItem(
        name = "SecondaryColor",
        keyName = "Secondary Color",
        description = "",
        position = 3,
    )
    fun secondColor(): Color {
        return Color(156, 217, 209)
    }
    @ConfigItem(
        name = "Light theme/Dark theme",
        keyName = "Light theme/Dark theme",
        description = "",
        position = 1,
    )
    fun theme(): Boolean {
        return true
    }
    @ConfigItem(
        name = "Enable console",
        keyName = "console",
        description = "",
        position = 1,
    )
    fun console(): Boolean {
        return false
    }


    @Range(min = 35, max = 100, textInput = false)
    @ConfigItem(
        name = "toolbar width",
        keyName = "toolbar width",
        description = "",
        position = 4,
    )
    fun toolbarWidth(): Int {
        return 45
    }

    @ConfigItem(
        name = "toolbar placement",
        keyName = "toolbar placement",
        description = "",
        position = 7
    )
    fun toolbarPlacement(): LayoutDirection {
        return LayoutDirection.Rtl
    }

    @Range(min = 8, max = 45, textInput = false)
    @ConfigItem(
        name = "Plugin list Text Size",
        keyName = "Plugin list Text Size",
        description = "Slide this to change the plugin list text size",
        position = 5,
    )
    fun pluginListTextSize(): Int {
        return 14
    }

    @Range(min = 1, max = 19, textInput = false)
    @ConfigItem(
        name = "Plugin List Spacer",
        keyName = "Plugin list Spacer",
        description = "",
        position = 6,
    )
    fun pluginSpaceBetween(): Int {
        return 7
    }

    @ConfigItem(
        keyName = "infoBoxVertical",
        name = "Display infoboxes vertically",
        description = "Toggles the infoboxes to display vertically",
        position = 40,
        section = "Overlay Settings",
        hidden = true
    )
    fun infoBoxVertical(): Boolean {
        return false
    }

    @ConfigItem(
        keyName = "infoBoxSize",
        name = "Infobox size",
        description = "Configures the size of each infobox in pixels",
        position = 42,
        section = "Overlay Settings"
    )
    @Units(Units.PIXELS)
    fun infoBoxSize(): Int {
        return 35
    }

    @ConfigItem(
        keyName = "infoBoxTextOutline",
        name = "Outline infobox text",
        description = "Draw a full outline instead of a simple shadow for infobox text",
        position = 43,
        section = "Overlay Settings"
    )
    fun infoBoxTextOutline(): Boolean {
        return false
    }

    @Alpha
    @ConfigItem(
        keyName = "overlayBackgroundColor",
        name = "Overlay Color",
        description = "Configures the background color of infoboxes and overlays",
        position = 44,
        section = "Overlay Settings"
    )
    fun overlayBackgroundColor(): Color {
        return ComponentConstants.STANDARD_BACKGROUND_COLOR
    }

    @ConfigItem(
            keyName = "fullscreen",
            name = "Borderless Fullscreen",
            description = "",
            position = 45
    )
    fun fullscreen(): Boolean {
        return false
    }

    @ConfigItem(
            keyName = "toolbarExpanded",
            name = "Open Toolbar Automatically",
            description = "",
            position = 46
    )
    fun toolbarExpanded(): Boolean {
        return true
    }

    @ConfigItem(
        keyName = "naturalMouse",
        name = "Natural mouse",
        description = "Uses the 'natural mouse' algorithm to move and click",
        section = "Interaction Manager",
        position = 2
    )
    fun naturalMouse(): Boolean {
        return false
    }

//	@ConfigItem(
//			keyName = "mouseOffScreen",
//			name = "Mouse off screen when idle",
//			description = "Move the mouse off screen if idle",
//			section = "Interaction Manager",
//			position = 3
//	)
//	default boolean mouseOffScreen()
//	{
//		return false;
//	}

    //	@ConfigItem(
    //			keyName = "mouseOffScreen",
    //			name = "Mouse off screen when idle",
    //			description = "Move the mouse off screen if idle",
    //			section = "Interaction Manager",
    //			position = 3
    //	)
    //	default boolean mouseOffScreen()
    //	{
    //		return false;
    //	}

    @ConfigItem(
        keyName = "debugAutoClicks",
        name = "Draw auto clicks",
        description = "Draws the sent mouse click packets on screen",
        position = 6
    )
    fun drawAutoClicks(): Boolean {
        return false
    }



        @ConfigSection(
            name = "Interaction Manager",
            description = "Interaction settings",
            closedByDefault = true,
            position = 3
        )
         val InteractionManager:String


        @ConfigSection(
            name = "Overlay Settings",
            description = "Settings relating to fonts",
            position = 6,
            closedByDefault = true
        )
        val OverlaySettings:String

    }
