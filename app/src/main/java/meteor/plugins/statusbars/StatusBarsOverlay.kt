/*
 * Copyright (c) 2019, Jos <Malevolentdev@gmail.com>
 * Copyright (c) 2019, Rheon <https://github.com/Rheon-D>
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
package meteor.plugins.statusbars

import meteor.game.AlternateSprites
import meteor.game.SkillIconManager
import meteor.game.SpriteManager
import meteor.plugins.statusbars.config.BarMode
import meteor.ui.overlay.Overlay
import meteor.ui.overlay.OverlayLayer
import meteor.ui.overlay.OverlayPosition
import meteor.util.ImageUtil
import net.runelite.api.*
import net.runelite.api.widgets.Widget
import net.runelite.api.widgets.WidgetInfo

import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics2D
import java.awt.Image
import java.awt.image.BufferedImage
import java.util.*
import java.util.function.Supplier

internal class StatusBarsOverlay(var plugin: StatusBarsPlugin, var config: StatusBarsConfig) : Overlay() {

    //private val itemStatService: ItemStatChangesService = ItemStatPlugin.itemStatChangesService
    private val spriteManager: SpriteManager = SpriteManager
    private val prayerIcon: Image
    private val heartDisease: Image
    private val heartPoison: Image
    private val heartVenom: Image
    private var heartIcon: Image? = null
    private var specialIcon: Image? = null
    private var energyIcon: Image? = null
    private val barRenderers: EnumMap<BarMode, BarRenderer> = EnumMap<BarMode, BarRenderer>(
            BarMode::class.java
    )

    init {
        position = (OverlayPosition.DYNAMIC)
        layer = (OverlayLayer.ABOVE_WIDGETS)
        prayerIcon = ImageUtil.resizeCanvas(ImageUtil.resizeImage(SkillIconManager.getSkillImage(Skill.PRAYER, true), IMAGE_SIZE, IMAGE_SIZE), ICON_DIMENSIONS.width, ICON_DIMENSIONS.height)
        heartDisease = ImageUtil.resizeCanvas(ImageUtil.loadImageResource(AlternateSprites::class.java, AlternateSprites.DISEASE_HEART), ICON_DIMENSIONS.width, ICON_DIMENSIONS.height)
        heartPoison = ImageUtil.resizeCanvas(ImageUtil.loadImageResource(AlternateSprites::class.java, AlternateSprites.POISON_HEART), ICON_DIMENSIONS.width, ICON_DIMENSIONS.height)
        heartVenom = ImageUtil.resizeCanvas(ImageUtil.loadImageResource(AlternateSprites::class.java, AlternateSprites.VENOM_HEART), ICON_DIMENSIONS.width, ICON_DIMENSIONS.height)
        initRenderers()
    }

    private fun initRenderers() {
        barRenderers[BarMode.DISABLED] = null
        barRenderers[BarMode.HITPOINTS] = BarRenderer(
                { if (inLms()) Experience.MAX_REAL_LEVEL else client.getRealSkillLevel(Skill.HITPOINTS) },
                { client.getBoostedSkillLevel(Skill.HITPOINTS) },
                { getRestoreValue(Skill.HITPOINTS.getName()) },
                Supplier {
                    val poisonState = client.getVarpValue(VarPlayer.IS_POISONED)
                    if (poisonState >= 1000000) {
                        return@Supplier VENOMED_COLOR
                    }
                    if (poisonState > 0) {
                        return@Supplier POISONED_COLOR
                    }
                    if (client.getVarpValue(VarPlayer.DISEASE_VALUE) > 0) {
                        return@Supplier DISEASE_COLOR
                    }
                    if (client.getVarbitValue(Varbits.PARASITE) >= 1) {
                        return@Supplier PARASITE_COLOR
                    }
                    HEALTH_COLOR
                },
                { HEAL_COLOR },
                Supplier {
                    val poisonState = client.getVarpValue(VarPlayer.IS_POISONED)
                    if (poisonState in 1..49) {
                        return@Supplier heartPoison
                    }
                    if (poisonState >= 1000000) {
                        return@Supplier heartVenom
                    }
                    if (client.getVarpValue(VarPlayer.DISEASE_VALUE) > 0) {
                        return@Supplier heartDisease
                    }
                    heartIcon!!
                }
        )
        barRenderers[BarMode.PRAYER] = BarRenderer(
                { if (inLms()) Experience.MAX_REAL_LEVEL else client.getRealSkillLevel(Skill.PRAYER) },
                { client.getBoostedSkillLevel(Skill.PRAYER) },
                { getRestoreValue(Skill.PRAYER.getName()) },
                {
                    var prayerColor = PRAYER_COLOR
                    for (pray in Prayer.values()) {
                        if (client.isPrayerActive(pray)) {
                            prayerColor = ACTIVE_PRAYER_COLOR
                            break
                        }
                    }
                    prayerColor
                },
                { PRAYER_HEAL_COLOR },
                { prayerIcon }
        )
        barRenderers[BarMode.RUN_ENERGY] = BarRenderer(
                { MAX_RUN_ENERGY_VALUE }, { client.energy },
                { getRestoreValue("Run Energy") },
                Supplier {
                    if (client.getVarbitValue(Varbits.RUN_SLOWED_DEPLETION_ACTIVE) != 0) {
                        return@Supplier RUN_STAMINA_COLOR
                    } else {
                        return@Supplier ENERGY_COLOR
                    }
                },
                { ENERGY_HEAL_COLOR },
                { energyIcon!! }
        )
        barRenderers[BarMode.SPECIAL_ATTACK] = BarRenderer(
                { MAX_SPECIAL_ATTACK_VALUE },
                { client.getVarpValue(VarPlayer.SPECIAL_ATTACK_PERCENT) / 10 },
                { 0 },
                { SPECIAL_ATTACK_COLOR },
                { SPECIAL_ATTACK_COLOR },
                { specialIcon!! }
        )
    }

    override fun render(g: Graphics2D): Dimension? {
        if (!plugin.barsDisplayed) {
            return null
        }
        var curViewport: Viewport? = null
        var curWidget: Widget? = null
        for (viewport in Viewport.values()) {
            val viewportWidget: Widget? = client.getWidget(viewport.viewport)
            if (viewportWidget != null && !viewportWidget.isHidden) {
                curViewport = viewport
                curWidget = viewportWidget
                break
            }
        }
        if (curViewport == null) {
            return null
        }
        val offsetLeft: Point = curViewport.offsetLeft
        val offsetRight: Point = curViewport.offsetRight
        val location = curWidget!!.canvasLocation
        val width: Int
        val height: Int
        val offsetLeftBarX: Int
        val offsetLeftBarY: Int
        val offsetRightBarX: Int
        val offsetRightBarY: Int
        if (curViewport == Viewport.RESIZED_BOTTOM) {
            width = config.barWidth.get()!!
            height = RESIZED_BOTTOM_HEIGHT
            val barWidthOffset = width - BarRenderer.DEFAULT_WIDTH
            offsetLeftBarX = location.x + RESIZED_BOTTOM_OFFSET_X - offsetLeft.x - 2 * barWidthOffset
            offsetLeftBarY = location.y - RESIZED_BOTTOM_OFFSET_Y - offsetLeft.y
            offsetRightBarX = location.x + RESIZED_BOTTOM_OFFSET_X - offsetRight.x - barWidthOffset
            offsetRightBarY = location.y - RESIZED_BOTTOM_OFFSET_Y - offsetRight.y
        } else {
            width = BarRenderer.DEFAULT_WIDTH
            height = HEIGHT
            offsetLeftBarX = location.x - offsetLeft.x
            offsetLeftBarY = location.y - offsetLeft.y
            offsetRightBarX = location.x - offsetRight.x + curWidget.width
            offsetRightBarY = location.y - offsetRight.y
        }
        buildIcons()
        val left = barRenderers[config.leftBarMode.get()]
        val right = barRenderers[config.rightBarMode.get()]
        left?.renderBar(config, g, offsetLeftBarX, offsetLeftBarY, width, height)
        right?.renderBar(config, g, offsetRightBarX, offsetRightBarY, width, height)
        return null
    }

    private fun getRestoreValue(skill: String): Int {
        val menu = client.menuEntries
        val menuSize = menu.size
        if (menuSize == 0) {
            return 0
        }
        val entry = menu[menuSize - 1]
        val widget = entry.widget
        var restoreValue = 0
/*        if (widget != null && widget.id == WidgetInfo.INVENTORY.id) {
            val change = itemStatService.getItemStatChanges(widget.itemId)
            if (change != null) {
                for (c in change.calculate(client).statChanges) {
                    val value = c!!.theoretical
                    if (value != 0 && c.stat!!.name == skill) {
                        restoreValue = value
                    }
                }
            }
        }*/
        return restoreValue
    }

    private fun buildIcons() {
        if (heartIcon == null) {
            heartIcon = loadAndResize(SpriteID.MINIMAP_ORB_HITPOINTS_ICON)
        }
        if (energyIcon == null) {
            energyIcon = loadAndResize(SpriteID.MINIMAP_ORB_WALK_ICON)
        }
        if (specialIcon == null) {
            specialIcon = loadAndResize(SpriteID.MINIMAP_ORB_SPECIAL_ICON)
        }
    }

    private fun loadAndResize(spriteId: Int): BufferedImage? {
        val image: BufferedImage = spriteManager.getSprite(spriteId, 0) ?: return null
        return ImageUtil.resizeCanvas(image, ICON_DIMENSIONS.width, ICON_DIMENSIONS.height)
    }

    private fun inLms(): Boolean {
        return client.getWidget(WidgetInfo.LMS_KDA) != null
    }

    companion object {
        private val PRAYER_COLOR = Color(50, 200, 200, 175)
        private val ACTIVE_PRAYER_COLOR = Color(57, 255, 186, 225)
        private val HEALTH_COLOR = Color(225, 35, 0, 125)
        private val POISONED_COLOR = Color(0, 145, 0, 150)
        private val VENOMED_COLOR = Color(0, 65, 0, 150)
        private val HEAL_COLOR = Color(255, 112, 6, 150)
        private val PRAYER_HEAL_COLOR = Color(57, 255, 186, 75)
        private val ENERGY_HEAL_COLOR = Color(199, 118, 0, 218)
        private val RUN_STAMINA_COLOR = Color(160, 124, 72, 255)
        private val SPECIAL_ATTACK_COLOR = Color(3, 153, 0, 195)
        private val ENERGY_COLOR = Color(199, 174, 0, 220)
        private val DISEASE_COLOR = Color(255, 193, 75, 181)
        private val PARASITE_COLOR = Color(196, 62, 109, 181)
        private const val HEIGHT = 252
        private const val RESIZED_BOTTOM_HEIGHT = 272
        private const val IMAGE_SIZE = 17
        private val ICON_DIMENSIONS = Dimension(26, 25)
        private const val RESIZED_BOTTOM_OFFSET_Y = 12
        private const val RESIZED_BOTTOM_OFFSET_X = 10
        private const val MAX_SPECIAL_ATTACK_VALUE = 100
        private const val MAX_RUN_ENERGY_VALUE = 100
    }
}