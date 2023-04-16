/*
 * Copyright (c) 2018, Lotto <https://github.com/devLotto>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package meteor.plugins.stretchedmode

import eventbus.events.ConfigChanged
import eventbus.events.ResizeableChanged
import meteor.input.MouseManager
import meteor.input.TranslateMouseListener
import meteor.plugins.Plugin
import meteor.plugins.PluginDescriptor

@PluginDescriptor(
    name = "Stretched Mode", configGroup = "stretchedmode",
    description = "Stretches the game in fixed and resizable modes.",
    tags = ["resize", "ui", "interface", "stretch", "scaling", "fixed"],
    enabledByDefault = true
)
class StretchedModePlugin : Plugin() {
    val config = configuration<StretchedModeConfig>()

    private val mouseManager = MouseManager
    private val mouseListener = TranslateMouseListener

    override fun onStart() {
        mouseManager.registerMouseListener(0, mouseListener)
        client.isStretchedEnabled = true
        updateConfig()
    }

    override fun onStop() {
        client.isStretchedEnabled = false
        client.invalidateStretching(true)
        mouseManager.unregisterMouseListener(mouseListener)
    }

    override fun onResizeableChanged(it: ResizeableChanged) {
        client.invalidateStretching(true)
    }

    override fun onConfigChanged(it: ConfigChanged) {
        if (it.group == "stretchedmode")
            updateConfig()
    }

    fun updateConfig() {
        client.setStretchedIntegerScaling(config.integerScaling.get()!!)
        client.setStretchedKeepAspectRatio(config.keepAspectRatio.get()!!)
        client.isStretchedFast = config.increasedPerformance.get()!!
        client.setScalingFactor(config.scalingFactor.get()!!)
        client.invalidateStretching(true)
    }
}