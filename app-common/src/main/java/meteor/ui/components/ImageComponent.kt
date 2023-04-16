/*
 * Copyright (c) 2018, Tomas Slusny <slusnucky@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package meteor.ui.components

import java.awt.Dimension
import java.awt.Graphics2D
import java.awt.Point
import java.awt.Rectangle
import java.awt.image.BufferedImage


class ImageComponent(var image: BufferedImage?) : LayoutableRenderableEntity {
    override fun getPreferredSize(): Dimension {
        return bounds!!.size
    }

    private var preferredLocationp: Point? = Point()
    private var preferredSizep: Dimension? = Dimension(ComponentConstants.STANDARD_WIDTH, 16)
    var bounds: Rectangle? = Rectangle()

    override fun getPreferredLocation(): Point? {
        return preferredLocationp
    }

    override fun setPreferredLocation(position: Point?) {
        preferredLocationp = position
    }

    override fun setPreferredSize(position: Dimension?) {
        this.preferredSizep = position
    }

    override fun render(graphics: Graphics2D): Dimension? {
        if (image == null) {
            return null
        }
        graphics.drawImage(image, getPreferredLocation()!!.x, getPreferredLocation()!!.y, null)
        val dimension = Dimension(image!!.width, image!!.height)
        bounds!!.location = getPreferredLocation()!!
        bounds!!.size = dimension
        return dimension
    }

}