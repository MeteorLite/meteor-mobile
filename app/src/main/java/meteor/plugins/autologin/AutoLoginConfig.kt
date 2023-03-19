package meteor.plugins.autologin

import meteor.config.Config


class AutoLoginConfig : Config("autologin") {

    val username = meteor.config.ConfigItem(
            group = group,
            keyName = "username",
            name = "Username",
            description = "Username",
            defaultValue = "Username"
    )

    val password = meteor.config.ConfigItem(
            group = group,
            keyName = "password",
            name = "Password",
            description = "Password",
            defaultValue = "Password",
            secret = true
    )
}