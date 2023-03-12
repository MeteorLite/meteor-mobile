/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
/**
 * @author Alexey A. Petrenko, Oleg V. Khaschansky
 */
package org.apache.harmony.awt.gl;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.image.BufferedImage;
import org.apache.harmony.awt.gl.font.FontManager;
import org.apache.harmony.awt.gl.image.BufferedImageGraphics2D;

import java.util.ArrayList;
import java.util.Locale;


/**
 * Common GraphicsEnvironment implementation
 *
 */
public abstract class CommonGraphicsEnvironment extends GraphicsEnvironment {

    @Override
    public Graphics2D createGraphics(BufferedImage bufferedImage) {
        return new BufferedImageGraphics2D(bufferedImage);
    }

    @Override
    public String[] getAvailableFontFamilyNames(Locale locale) {
        Font[] fonts = getAllFonts();
        ArrayList<String> familyNames = new ArrayList<String>();

        for (Font element : fonts) {
            String name = element.getFamily(locale);
            if (!familyNames.contains(name)) {
                familyNames.add(name);
            }
        }

        return familyNames.toArray(new String[familyNames.size()]);
    }

    @Override
    public Font[] getAllFonts() {
        return FontManager.getInstance().getAllFonts();
    }

    @Override
    public String[] getAvailableFontFamilyNames() {
        return FontManager.getInstance().getAllFamilies();
    }

	public abstract GraphicsDevice getDefaultScreenDevice() throws HeadlessException;

	public abstract GraphicsDevice[] getScreenDevices() throws HeadlessException;
}
