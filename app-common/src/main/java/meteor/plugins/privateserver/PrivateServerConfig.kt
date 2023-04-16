package meteor.plugins.privateserver

import meteor.config.Config
class PrivateServerConfig : Config("privateServer") {

    // 10.0.2.2 - Android Emulator <-> localhost
    // 127.0.0.1 - localhost
    val host = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "host",
            name = "Host",
            description = "",
            defaultValue = ""
    )

    val modulus = meteor.config.ConfigItem(
            this,
            group = group,
            keyName = "modulus",
            name = "Modulus",
            description = "",
            defaultValue = "91b9313ef649724124a7cf58cba3a02eee862a1ae0cd7fc2ce7b26cb8b00042ef34cff814b0d08dd897b091dcbaf4512a86f1be2ba97e52119e563f6bef9b7d3348f570ff8f17edc0da2c56f52444ab9d71187443c0181ab6dbef04852a5daf71ef12f63a36b8bddce41674a97b7cf78f58425f9b575b152c24033b70ceab5d9f9de8749b1e92ea93fc312a8cbec4e6663ec44d42d35e5c72b0b2a3d5a2d27a83a96799a4637e614a8e6f883d96db21d37411f7e6777d8d53b18ba14b7cca2522e4f63eaa33a782a2284157b68f1a6dd8540de8c7d6bc7daf42cbab5befc4066ce0f20fd900075db2bbdc507c4acf8f4ccf95cee9ca4c90d6053dcad0527e2d9"
    )
}