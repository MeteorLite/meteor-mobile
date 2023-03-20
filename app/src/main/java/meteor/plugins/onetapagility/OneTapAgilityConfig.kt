package meteor.plugins.onetapagility

import meteor.config.Config
import meteor.config.ConfigItem

class OneTapAgilityConfig : Config("onetapagility") {

    val courseSelection = ConfigItem(
            group = group,
            keyName = "courseSelection",
            name = "Select course",
            description = "",
            unhideKey = "courseSelection",
            defaultValue = AgilityCourse.SEERS_VILLAGE
    )

    val seersTele = ConfigItem(
            group = group,
            keyName = "seersTP",
            name = "Seers Teleport",
            description = "Uses the Seer's village teleport at the end of the Seer's course",
            hidden = true,
            unhide = "courseSelection",
            unhideValue = "SEERS_VILLAGE",
            defaultValue = false
    )

    val skillBoost = ConfigItem(
            group = group,
            keyName = "skillBoost",
            name = "Boost Agility",
            description = "Eat summer pies to boost your agility level",
            unhideKey = "skillBoost",
            defaultValue = false
    )

    val boostAmount = ConfigItem(
            group = group,
            keyName = "boostAmount",
            name = "Minimum boost",
            description = "",
            hidden = true,
            unhide = "skillBoost",
            min = 1,
            max = 5,
            defaultValue = 3
    )

    val consumeMisclicks = ConfigItem(
            group = group,
            keyName = "consumeMisclicks",
            name = "Stop Misclicks",
            description = "Allows you to spam left click",
            defaultValue = true
    )

    val pickUpMarks = ConfigItem(
            group = group,
            keyName = "pickUpMarks",
            name = "Pick up Marks of Grace",
            description = "Pick up Marks of Grace",
            defaultValue = true
    )
}