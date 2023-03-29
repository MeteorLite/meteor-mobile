package meteor.plugins.meteor

import meteor.plugins.Plugin
import meteor.plugins.PluginDescriptor

@PluginDescriptor(name = "Meteor", description = "", cantDisable = true)
class MeteorPlugin : Plugin() {
    val config = configuration<MeteorConfig>()
}