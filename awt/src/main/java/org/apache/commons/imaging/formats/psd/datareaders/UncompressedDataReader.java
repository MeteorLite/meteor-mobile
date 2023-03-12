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
package org.apache.commons.imaging.formats.psd.datareaders;

import java.awt.image.BufferedImage;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.common.BinaryFileParser;
import org.apache.commons.imaging.common.mylzw.BitsToByteInputStream;
import org.apache.commons.imaging.common.mylzw.MyBitInputStream;
import org.apache.commons.imaging.formats.psd.ImageContents;
import org.apache.commons.imaging.formats.psd.PsdHeaderInfo;
import org.apache.commons.imaging.formats.psd.dataparsers.DataParser;
import org.apache.commons.imaging.util.IoUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;

public class UncompressedDataReader extends DataReader {
    public UncompressedDataReader(final DataParser fDataParser) {
        super(fDataParser);
    }

    @Override
    public void readData(final InputStream is, final BufferedImage bi,
            final ImageContents imageContents, final BinaryFileParser bfp)
            throws ImageReadException, IOException {
        final PsdHeaderInfo header = imageContents.header;
        final int width = header.columns;
        final int height = header.rows;

        bfp.setDebug(false);

        final int channelCount = dataParser.getBasicChannelsCount();
        final int depth = header.depth;
        final MyBitInputStream mbis = new MyBitInputStream(is, ByteOrder.BIG_ENDIAN);
        // we want all samples to be bytes
        BitsToByteInputStream bbis = null;
        boolean canThrow = false;
        try {
            bbis = new BitsToByteInputStream(mbis, 8);

            final int[][][] data = new int[channelCount][height][width];
            for (int channel = 0; channel < channelCount; channel++) {
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        final int b = bbis.readBits(depth);
    
                        data[channel][y][x] = (byte) b;
                    }
                }
            }
    
            dataParser.parseData(data, bi, imageContents);
            canThrow = true;
        } finally {
            IoUtils.closeQuietly(canThrow, bbis);
        }
    }
}
