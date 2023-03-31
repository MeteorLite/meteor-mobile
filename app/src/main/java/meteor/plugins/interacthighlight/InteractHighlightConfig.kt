/*
 * Copyright (c) 2021, Adam <Adam@sigterm.info>
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
package meteor.plugins.interacthighlight

import meteor.config.Config
import meteor.config.legacy.*
import java.awt.Color


class InteractHighlightConfig : Config("interacthighlight") {

    val npcShowHover = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "npcShowHover",
            name = "Show on hover",
            description = "Outline NPCs when hovered",
            section = "NPCs",
            defaultValue = true
    )

    val npcShowInteract = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "npcShowInteract",
            name = "Show on interact",
            description = "Outline NPCs when interacted",
            section = "NPCs",
            defaultValue = true,
    )

    val npcHoverHighlightColor = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "npcHoverHighlightColor",
            name = "NPC hover",
            description = "The color of the hover outline for NPCs",
            section = "NPCs",
            alpha = true,
            defaultValue = Color(-0x6f000100, true),
    )

    val npcAttackHoverHighlightColor = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "npcAttackHoverHighlightColor",
            name = "NPC attack hover",
            description = "The color of the attack hover outline for NPCs",
            section = "NPCs",
            alpha = true,
            defaultValue = Color(-0x6f000100, true),
    )

    val npcInteractHighlightColor = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "npcInteractHighlightColor",
            name = "NPC interact",
            description = "The color of the target outline for NPCs",
            section = "NPCs",
            alpha = true,
            defaultValue = Color(-0x6f010000, true),
    )

    val npcAttackHighlightColor = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "npcAttackHighlightColor",
            name = "NPC attack",
            description = "The color of the outline on attacked NPCs",
            section = "NPCs",
            alpha = true,
            defaultValue = Color(-0x6f010000, true),
    )

    val objectShowHover = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "objectShowHover",
            name = "Show on hover",
            description = "Outline objects when hovered",
            section = "Objects",
            defaultValue = true,
    )

    val objectShowInteract = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "objectShowInteract",
            name = "Show on interact",
            description = "Outline objects when interacted",
            section = "Objects",
            defaultValue = true,
    )

    val objectHoverHighlightColor = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "objectHoverHighlightColor",
            name = "Object hover",
            description = "The color of the hover outline for objects",
            section = "Objects",
            alpha = true,
            defaultValue = Color(-0x6fff0001, true),
    )

    val objectInteractHighlightColor = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "objectInteractHighlightColor",
            name = "Object interact",
            description = "The color of the target outline for objects",
            section = "Objects",
            alpha = true,
            defaultValue = Color(-0x6f010000, true),
    )

    val borderWidth = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "borderWidth",
            name = "Border Width",
            description = "Width of the outlined border",
            defaultValue = 4,
    )

    val outlineFeather = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "outlineFeather",
            name = "Outline feather",
            description = "Specify between 0-4 how much of the model outline should be faded",
            defaultValue = 4,
    )
}