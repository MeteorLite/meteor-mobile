package meteor.plugins.keyboard

import eventbus.events.ScriptCallbackEvent
import meteor.AndroidMain
import meteor.AndroidMain.Companion.focusAndShowKeyboard
import meteor.plugins.Plugin
import meteor.plugins.PluginDescriptor

@PluginDescriptor(name = "Keyboard", description = "", cantDisable = true, enabledByDefault = true)
class KeyboardPlugin : Plugin() {
    override fun onScriptCallbackEvent(it: ScriptCallbackEvent) {
        if (it.eventName == "setSearchBankInputText") {
            AndroidMain.overlayView?.rootView?.focusAndShowKeyboard()
        }
    }
}