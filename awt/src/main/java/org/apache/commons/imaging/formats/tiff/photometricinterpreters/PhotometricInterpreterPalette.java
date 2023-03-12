/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.imaging.formats.tiff.photometricinterpreters;

import java.io.IOException;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.common.ImageBuilder;

public class PhotometricInterpreterPalette extends PhotometricInterpreter {

    /**
     * The color map of integer ARGB values tied to the pixel index of the
     * palette
     */
    private final int[] indexColorMap;

    public PhotometricInterpreterPalette(final int samplesPerPixel,
            final int[] bitsPerSample, final int predictor, final int width, final int height,
            final int[] colorMap) {
        super(samplesPerPixel, bitsPerSample, predictor, width, height);

        final int bitsPerPixel = this.bitsPerSample[0];
        final int colormapScale = (1 << bitsPerPixel);
        indexColorMap = new int[colormapScale];
        for (int index = 0; index < colormapScale; index++) {
            final int red = (colorMap[index] >> 8) & 0xff;
            final int green = (colorMap[index + (colormapScale)] >> 8) & 0xff;
            final int blue = (colorMap[index + (2 * colormapScale)] >> 8) & 0xff;
            indexColorMap[index] = 0xff000000 | (red << 16) | (green << 8)
                    | blue;
        }

    }

    @Override
    public void interpretPixel(final ImageBuilder imageBuilder, final int[] samples, final int x,
            final int y) throws ImageReadException, IOException {
        imageBuilder.setRGB(x, y, indexColorMap[samples[0]]);
    }
}
