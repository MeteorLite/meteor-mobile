package java.awt.event

import java.awt.Point

class MouseEvent(val point: Point, val button: Int) {
    var isConsumed = false

    fun consume() {

    }
}
