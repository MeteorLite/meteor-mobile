/*
 * Copyright (c) 2021, neilrush
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
package meteor.plugins.playeroutline

import meteor.config.Config
import meteor.config.legacy.ConfigGroup
import java.awt.Color

class PlayerOutlineConfig : Config("playeroutline") {

    val playerOutlineColor = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "playerOutlineColor",
            name = "Outline Color",
            description = "The color for the players outline",
            defaultValue = Color(0x3D000000, true)
    )

    val borderWidth = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "borderWidth",
            name = "Border Width",
            description = "Width of the player outline border",
            defaultValue = 4
    )

    val outlineFeather = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "outlineFeather",
            name = "Outline Feather",
            description = "Specify between 0-4 how much the player outline should be faded",
            defaultValue = 4,
            max = 4
    )
}