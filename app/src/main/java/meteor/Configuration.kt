package meteor

import osrs.Client
import java.io.File

object Configuration {
    val METEOR_DIR = Main.activity!!.filesDir
    val CACHE_DIR = File(METEOR_DIR, "cache")
    val EXTERNALS_DIR = File(METEOR_DIR, "externals")
    var CONFIG_FILE = File(METEOR_DIR, "settings.properties")
    val rspsConfigFile = File(METEOR_DIR, "rsps.json")
    const val MASTER_GROUP = "MeteorLite"
    var BLOCK_MOUSE_4_PLUS = true
    var allowGPU = true
}