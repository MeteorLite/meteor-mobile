/*
 * Copyright (c) 2017, Tomas Slusny <slusnucky@gmail.com>
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
package meteor.ui.overlay.tooltips

import meteor.Main
import meteor.ui.components.LayoutableRenderableEntity
import meteor.ui.overlay.*
import net.runelite.api.Client
import net.runelite.api.widgets.WidgetID
import net.runelite.rs.api.RSClient
import java.awt.*
import java.lang.Exception

class TooltipOverlay : Overlay() {
    val STANDARD_BACKGROUND_COLOR = Color(70, 61, 50, 156)
    val tooltipManager = Main.tooltipManager
    var mousePos: net.runelite.api.Point? = null
    override fun render(graphics: Graphics2D): Dimension? {
        val tooltips: List<Tooltip> = tooltipManager.tooltips
        if (tooltips.isEmpty()) {
            return null
        }
        try {
            mousePos = client.mouseCanvasPosition
            renderTooltips(graphics, tooltips)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        tooltipManager.clear()
        return null
    }

    private fun renderTooltips(graphics: Graphics2D, tooltips: List<Tooltip>): Dimension {
        client as RSClient
        val canvasWidth = client.gameImage.width
        val canvasHeight = client.gameImage.height
        val tooltipX = Math.min(canvasWidth - bounds!!.width, mousePos!!.x)
        val tooltipY = Math.min(canvasHeight - bounds!!.height, mousePos!!.y + UNDER_OFFSET)
        val newBounds = Rectangle(tooltipX, tooltipY, 0, 0)
        for (tooltip in tooltips) {
            val entity: LayoutableRenderableEntity
            if (tooltip.component != null) {
                entity = tooltip.component!!
                if (entity is PanelComponent) {
                    entity.backgroundColor = STANDARD_BACKGROUND_COLOR
                }
            } else {
                val tooltipComponent = TooltipComponent()
                tooltipComponent.modIcons = ((client as Client).modIcons)
                tooltipComponent.text = (tooltip.text)
                tooltipComponent.backgroundColor = (STANDARD_BACKGROUND_COLOR)
                entity = tooltipComponent
            }
            entity.setPreferredLocation(Point(tooltipX, tooltipY + newBounds.height))
            val dimension = entity.render(graphics)

            // Create incremental tooltip newBounds
            newBounds.height += dimension!!.height + PADDING
            newBounds.width = Math.max(newBounds.width, dimension.width)
        }
        return newBounds.size
    }

    companion object {
        private const val UNDER_OFFSET = 24
        private const val PADDING = 2
    }

    init {
        position = OverlayPosition.TOOLTIP
        priority = OverlayPriority.HIGHEST
        layer = OverlayLayer.ABOVE_WIDGETS
        // additionally allow tooltips above the full screen world map and welcome screen
        drawAfterInterface(WidgetID.FULLSCREEN_CONTAINER_TLI)
    }
}