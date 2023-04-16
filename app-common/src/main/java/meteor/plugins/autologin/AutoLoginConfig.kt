package meteor.plugins.autologin

import meteor.config.Config


class AutoLoginConfig : Config("autologin") {

    val username = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "username",
            name = "Username",
            description = "Username",
            defaultValue = "nulled"
    )

    val password = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "password",
            name = "Password",
            description = "Password",
            defaultValue = "nulled",
            secret = true
    )
}