/*
 * Copyright (c) 2017, Aria <aria@ar1as.space>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package meteor.plugins.grounditems

import meteor.config.Config
import meteor.config.legacy.ConfigGroup
import meteor.config.legacy.ConfigSection
import meteor.plugins.grounditems.config.DespawnTimerMode
import meteor.plugins.grounditems.config.HighlightTier
import meteor.plugins.grounditems.config.ItemHighlightMode
import meteor.plugins.grounditems.config.MenuHighlightMode
import meteor.plugins.grounditems.config.PriceDisplayMode
import meteor.plugins.grounditems.config.ValueCalculationMode
import java.awt.Color

@ConfigGroup("grounditems")
class GroundItemsConfig : Config("grounditems") {

    val highlightedItems = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "highlightedItems",
            name = "Highlighted Items",
            description = "Configures specifically highlighted ground items. Format: (item), (item)",
            section = itemLists,
            defaultValue = "nulled"
    )

    val hiddenItems = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "hiddenItems",
            name = "Hidden Items",
            description = "Configures hidden ground items. Format: (item), (item)",
            section = itemLists,
            defaultValue = "Vial, Ashes, Coins, Bones, Bucket, Jug, Seaweed"
    )

    val showHighlightedOnly = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "showHighlightedOnly",
            name = "Show Highlighted items only",
            description = "Configures whether or not to draw items only on your highlighted list",
            defaultValue = false
    )

    val dontHideUntradeables = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "dontHideUntradeables",
            name = "Do not hide untradeables",
            description = "Configures whether or not untradeable items ignore hiding under settings",
            defaultValue = true
    )

    val showMenuItemQuantities = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "showMenuItemQuantities",
            name = "Show Menu Item Quantities",
            description = "Configures whether or not to show the item quantities in the menu",
            defaultValue = true
    )

    val recolorMenuHiddenItems = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "recolorMenuHiddenItems",
            name = "Recolor Menu Hidden Items",
            description = "Configures whether or not hidden items in right-click menu will be recolored",
            defaultValue = false
    )

    val deprioritizeHiddenItems = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "deprioritizeHiddenItems",
            name = "Deprioritize Menu Hidden Items",
            description = "Depriotizies the menu options for items which are hidden, requiring a right click to pick up.",
            defaultValue = false
    )

    val highlightTiles = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "highlightTiles",
            name = "Highlight Tiles",
            description = "Configures whether or not to highlight tiles containing ground items",
            defaultValue = false
    )

    val notifyHighlightedDrops = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "notifyHighlightedDrops",
            name = "Notify for Highlighted drops",
            description = "Configures whether or not to notify for drops on your highlighted list",
            defaultValue = false
    )

    val notifyTier = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "notifyTier",
            name = "Notify tier",
            description = "Configures which price tiers will trigger a notification on drop",
            defaultValue = HighlightTier.OFF
    )

    val priceDisplayMode = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "priceDisplayMode",
            name = "Price Display Mode",
            description = "Configures which price types are shown alongside ground item name",
            defaultValue = PriceDisplayMode.BOTH
    )

    val itemHighlightMode = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "itemHighlightMode",
            name = "Item Highlight Mode",
            description = "Configures how ground items will be highlighted",
            defaultValue = ItemHighlightMode.BOTH
    )

    val menuHighlightMode = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "menuHighlightMode",
            name = "Menu Highlight Mode",
            description = "Configures what to highlight in right-click menu",
            defaultValue = MenuHighlightMode.ITEM_NAME
    )

    val highlightValueCalculation = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "highlightValueCalculation",
            name = "Highlight Value Calculation",
            description = "Configures which coin value is used to determine highlight color",
            defaultValue = ValueCalculationMode.HIGHEST
    )

    val hideUnderValue = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "hideUnderValue",
            name = "Hide under value",
            description = "Configures hidden ground items under both GE and HA value",
            defaultValue = 0
    )

    val defaultColor = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "defaultColor",
            name = "Default items",
            description = "Configures the color for default, non-highlighted items",
            alpha = true,
            defaultValue = Color.WHITE
    )

    val highlightedColor = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "highlightedColor",
            name = "Highlighted items",
            description = "Configures the color for highlighted items",
            alpha = true,
            defaultValue = Color.decode("#AA00FF")
    )

    val hiddenColor = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "hiddenColor",
            name = "Hidden items",
            description = "Configures the color for hidden items in right-click menu and when holding ALT",
            alpha = true,
            defaultValue = Color.GRAY
    )

    val lowValueColor = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "lowValueColor",
            name = "Low value items",
            description = "Configures the color for low value items",
            alpha = true,
            defaultValue = Color.decode("#66B2FF")
    )

    val lowValuePrice = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "lowValuePrice",
            name = "Low value price",
            description = "Configures the start price for low value items",
            defaultValue = 20000
    )

    val mediumValueColor = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "mediumValueColor",
            name = "Medium value items",
            description = "Configures the color for medium value items",
            alpha = true,
            defaultValue = Color.decode("#99FF99")
    )

    val mediumValuePrice = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "mediumValuePrice",
            name = "Medium value price",
            description = "Configures the start price for medium value items",
            defaultValue = 100000
    )

    val highValueColor = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "highValueColor",
            name = "High value items",
            description = "Configures the color for high value items",
            alpha = true,
            defaultValue = Color.decode("#FF9600")
    )

    val highValuePrice = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "highValuePrice",
            name = "High value price",
            description = "Configures the start price for high value items",
            defaultValue = 1000000
    )

    val insaneValueColor = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "insaneValueColor",
            name = "Insane value items",
            description = "Configures the color for insane value items",
            alpha = true,
            defaultValue = Color.decode("#FF66B2")
    )

    val insaneValuePrice = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "insaneValuePrice",
            name = "Insane value price",
            description = "Configures the start price for insane value items",
            defaultValue = 10000000
    )

    val onlyShowLoot = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "onlyShowLoot",
            name = "Only show loot",
            description = "Only shows drops from NPCs and players",
            defaultValue = false
    )

    val doubleTapDelay = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "doubleTapDelay",
            name = "Double-tap delay",
            description = "Delay for the double-tap ALT to hide ground items. 0 to disable.",
            defaultValue = 250
    )

    val collapseEntries = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "collapseEntries",
            name = "Collapse ground item menu",
            description = "Collapses ground item menu entries together and appends count",
            defaultValue = false
    )

    val groundItemTimers = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "groundItemTimers",
            name = "Despawn timer",
            description = "Shows despawn timers for items you've dropped and received as loot",
            defaultValue = DespawnTimerMode.OFF
    )

    val textOutline = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "textOutline",
            name = "Text Outline",
            description = "Use an outline around text instead of a text shadow",
            defaultValue = false
    )

    val showLootbeamForHighlighted = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "showLootbeamForHighlighted",
            name = "Highlighted item lootbeams",
            description = "Configures lootbeams to show for all highlighted items.",
            defaultValue = false
    )

    val showLootbeamTier = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "showLootbeamTier",
            name = "Lootbeam tier",
            description = "Configures which price tiers will trigger a lootbeam",
            defaultValue = HighlightTier.HIGH
    )

    val lootbeamStyle = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "lootbeamStyle",
            name = "Lootbeam Style",
            description = "Style of lootbeam to use",
            defaultValue = Lootbeam.Style.MODERN
    )

    companion object {
        @ConfigSection(name = "Item Lists", description = "The highlighted and hidden item lists", position = 0)
        const val itemLists = "itemLists"
    }
}