/*
 * Copyright (c) 2019, Jos <Malevolentdev@gmail.com>
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

import meteor.config.Config
import meteor.config.legacy.*
import meteor.plugins.reportbutton.TimeStyle
import meteor.plugins.statusbars.config.BarMode

class StatusBarsConfig : Config(GROUP) {

    val enableCounter = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "enableCounter",
            name = "Show counters",
            description = "Shows current value of the status on the bar",
            defaultValue = true
    )

    val enableSkillIcon = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "enableSkillIcon",
            name = "Show icons",
            description = "Adds skill icons at the top of the bars.",
            defaultValue = true
    )

    val enableRestorationBars = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "enableRestorationBars",
            name = "Show restores",
            description = "Visually shows how much will be restored to your status bar.",
            defaultValue = true
    )

    val leftBarMode = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "leftBarMode",
            name = "Left Bar",
            description = "Configures the left status bar",
            defaultValue = BarMode.HITPOINTS
    )

    val rightBarMode = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "rightBarMode",
            name = "Right Bar",
            description = "Configures the right status bar",
            defaultValue = BarMode.PRAYER
    )

    val hideAfterCombatDelay = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "hideAfterCombatDelay",
            name = "Hide after combat delay",
            description = "Amount of ticks before hiding status bars after no longer in combat. 0 = always show status bars.",
            defaultValue = 0,
            min = 0,
            max = 12
    )

    val barWidth = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "barWidth",
            name = "Bar Width",
            description = "",
            defaultValue = 20,
            min = 0,
            max = 12
    )

    companion object {
        const val GROUP = "statusbars"
    }
}