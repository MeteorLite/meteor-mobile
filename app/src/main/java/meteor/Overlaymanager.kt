package meteor

import meteor.MainActivity
import osrs.GameEngine

object Overlaymanager {
    val overlays = ArrayList<Overlay>()

    fun renderOverlays() {
        for (overlay in overlays) {
            GameEngine.gameImage
        }
    }
}