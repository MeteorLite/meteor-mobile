package meteor.plugins.privateserver

import meteor.plugins.Plugin
import meteor.plugins.PluginDescriptor

@PluginDescriptor(
        name = "Private Server",
        description = "Configuration for connecting to private servers",
        enabledByDefault = false,
        cantDisable = true
)
class PrivateServerPlugin : Plugin() {
    init {
        configuration<PrivateServerConfig>()
    }
}