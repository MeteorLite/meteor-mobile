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
package org.apache.commons.imaging.formats.tiff.write;

import java.io.IOException;

import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.common.BinaryOutputStream;

abstract class TiffOutputItem {
    public static final long UNDEFINED_VALUE = -1;

    private long offset = UNDEFINED_VALUE;

    protected long getOffset() {
        return offset;
    }

    protected void setOffset(final long offset) {
        this.offset = offset;
    }

    public abstract int getItemLength();

    public abstract String getItemDescription();

    public abstract void writeItem(BinaryOutputStream bos) throws IOException,
            ImageWriteException;

    public static class Value extends TiffOutputItem {
        private final byte[] bytes;
        private final String name;

        public Value(final String name, final byte[] bytes) {
            this.name = name;
            this.bytes = bytes;
        }

        @Override
        public int getItemLength() {
            return bytes.length;
        }

        @Override
        public String getItemDescription() {
            return name;
        }

        public void updateValue(final byte[] bytes) throws ImageWriteException {
            if (this.bytes.length != bytes.length) {
                throw new ImageWriteException("Updated data size mismatch: "
                        + this.bytes.length + " vs. " + bytes.length);
            }
            System.arraycopy(bytes, 0, this.bytes, 0, bytes.length);
        }

        @Override
        public void writeItem(final BinaryOutputStream bos) throws IOException,
                ImageWriteException {
            bos.write(bytes);
        }
    }
}
