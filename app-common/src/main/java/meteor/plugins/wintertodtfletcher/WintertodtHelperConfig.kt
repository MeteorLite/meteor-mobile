package meteor.plugins.wintertodtfletcher

import meteor.config.Config
class WintertodtHelperConfig : Config("wintertodthelper") {

    val healAt = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "healAt",
            name = "Heal at",
            description = "Automatically eat any available food when health hits this value",
            defaultValue = 7,
            min = 5, max = 89
    )
}