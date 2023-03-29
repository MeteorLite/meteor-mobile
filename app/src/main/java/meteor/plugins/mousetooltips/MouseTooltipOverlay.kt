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
package meteor.plugins.mousetooltips

import com.google.common.base.Strings
import com.google.common.collect.ImmutableSet
import meteor.Main
import meteor.ui.overlay.Overlay
import meteor.ui.overlay.OverlayLayer
import meteor.ui.overlay.OverlayPosition
import meteor.ui.overlay.Tooltip
import net.runelite.api.MenuAction
import net.runelite.api.VarClientInt
import net.runelite.api.widgets.WidgetID
import net.runelite.api.widgets.WidgetInfo
import java.awt.Dimension
import java.awt.Graphics2D

internal class MouseTooltipOverlay(var config: MouseTooltipConfig) : Overlay() {
    private val tooltipManager = Main.tooltipManager
    override fun render(graphics: Graphics2D): Dimension? {
        if (client.isMenuOpen) {
            return null
        }
        val menuEntries = client.menuEntries
        val last = menuEntries.size - 1
        if (last < 0) {
            return null
        }
        val menuEntry = menuEntries[last]
        val target = menuEntry.target
        val option = menuEntry.option
        val type = MenuAction.of(menuEntry.type.id)
        if (type == MenuAction.RUNELITE_OVERLAY || type == MenuAction.CC_OP_LOW_PRIORITY) {
            // These are always right click only
            return null
        }
        if (Strings.isNullOrEmpty(option)) {
            return null
        }
        when (option) {
            "Walk here", "Cancel", "Continue" -> return null
            "Move" ->         // Hide overlay on sliding puzzle boxes
                if (target.contains("Sliding piece")) {
                    return null
                }
        }
        if (WIDGET_MENU_ACTIONS.contains(type)) {
            val widgetId = menuEntry.param1
            val groupId = WidgetInfo.TO_GROUP(widgetId)
            if (!config.uiTooltip.get()!!) {
                return null
            }
            if (!config.chatboxTooltip.get()!! && groupId == WidgetInfo.CHATBOX.groupId) {
                return null
            }
            if (config.disableSpellbooktooltip.get()!! && groupId == WidgetID.SPELLBOOK_GROUP_ID) {
                return null
            }
        }

        // If this varc is set, a tooltip will be displayed soon
        val tooltipTimeout = client.getVarcIntValue(VarClientInt.TOOLTIP_TIMEOUT.index)
        if (tooltipTimeout > client.gameCycle) {
            return null
        }

        // If this varc is set, a tooltip is already being displayed
        val tooltipDisplayed = client.getVarcIntValue(VarClientInt.TOOLTIP_VISIBLE.index)
        if (tooltipDisplayed == 1) {
            return null
        }
        tooltipManager
            .addFront(Tooltip(option + if (Strings.isNullOrEmpty(target)) "" else " $target"))
        return null
    }

    companion object {
        /**
         * Menu types which are on widgets.
         */
        private val WIDGET_MENU_ACTIONS: Set<MenuAction> = ImmutableSet.of(
            MenuAction.WIDGET_TYPE_1,
            MenuAction.WIDGET_TARGET,
            MenuAction.WIDGET_CLOSE,
            MenuAction.WIDGET_TYPE_4,
            MenuAction.WIDGET_TYPE_5,
            MenuAction.WIDGET_CONTINUE,
            MenuAction.ITEM_USE_ON_ITEM,
            MenuAction.WIDGET_USE_ON_ITEM,
            MenuAction.ITEM_FIRST_OPTION,
            MenuAction.ITEM_SECOND_OPTION,
            MenuAction.ITEM_THIRD_OPTION,
            MenuAction.WIDGET_FOURTH_OPTION,
            MenuAction.WIDGET_FIFTH_OPTION,
            MenuAction.EXAMINE_ITEM,
            MenuAction.WIDGET_TARGET_ON_WIDGET,
            MenuAction.CC_OP_LOW_PRIORITY,
            MenuAction.CC_OP
        )
    }

    init {
        position = OverlayPosition.DYNAMIC
        layer = OverlayLayer.ABOVE_WIDGETS
        // additionally allow tooltips above the full screen world map and welcome screen
        drawAfterInterface(WidgetID.FULLSCREEN_CONTAINER_TLI)
    }
}