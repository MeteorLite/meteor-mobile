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
package org.apache.commons.imaging.icc;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.util.IoUtils;

import static org.apache.commons.imaging.common.BinaryFunctions.*;

public enum IccTagDataTypes implements IccTagDataType {
    DESC_TYPE(
            "descType", 0x64657363) {
        public void dump(final String prefix, final byte[] bytes)
                throws ImageReadException, IOException
        {
            InputStream bis = null;
            boolean canThrow = false;
            try {
                bis = new ByteArrayInputStream(bytes);
                read4Bytes("type_signature", bis, "ICC: corrupt tag data", ByteOrder.BIG_ENDIAN);
    
                //            bis.setDebug(true);
                read4Bytes("ignore", bis, "ICC: corrupt tag data", ByteOrder.BIG_ENDIAN);
                final int stringLength = read4Bytes("stringLength", bis, "ICC: corrupt tag data", ByteOrder.BIG_ENDIAN);
    
                //            bis.readByteArray("ignore", bytes.length -12, "none");
                final String s = new String(bytes, 12, stringLength - 1, "US-ASCII");
                System.out.println(prefix + "s: '" + s + "'");
                canThrow = true;
            } finally {
                IoUtils.closeQuietly(canThrow, bis);
            }
        }

    },

    DATA_TYPE(
            "dataType", 0x64617461) {
        public void dump(final String prefix, final byte[] bytes)
                throws ImageReadException, IOException
        {
            InputStream bis = null;
            boolean canThrow = false;
            try {
                bis = new ByteArrayInputStream(bytes);
                read4Bytes("type_signature", bis, "ICC: corrupt tag data", ByteOrder.BIG_ENDIAN);
                canThrow = true;
            } finally {
                IoUtils.closeQuietly(canThrow, bis);
            }
        }

    },

    MULTI_LOCALIZED_UNICODE_TYPE(
            "multiLocalizedUnicodeType", (0x6D6C7563)) {
        public void dump(final String prefix, final byte[] bytes)
                throws ImageReadException, IOException
        {
            InputStream bis = null;
            boolean canThrow = false;
            try {
                bis = new ByteArrayInputStream(bytes);
                read4Bytes("type_signature", bis, "ICC: corrupt tag data", ByteOrder.BIG_ENDIAN);
                canThrow = true;
            } finally {
                IoUtils.closeQuietly(canThrow, bis);
            }
        }

    },

    SIGNATURE_TYPE(
            "signatureType", ((0x73696720))) {
        public void dump(final String prefix, final byte[] bytes)
                throws ImageReadException, IOException
        {
            InputStream bis = null;
            boolean canThrow = false;
            try {
                bis = new ByteArrayInputStream(bytes);
                read4Bytes("type_signature", bis, "ICC: corrupt tag data", ByteOrder.BIG_ENDIAN);
                read4Bytes("ignore", bis, "ICC: corrupt tag data", ByteOrder.BIG_ENDIAN);
                final int thesignature = read4Bytes("thesignature ", bis, "ICC: corrupt tag data", ByteOrder.BIG_ENDIAN);
                System.out.println(prefix
                        + "thesignature: "
                        + Integer.toHexString(thesignature)
                        + " ("
                        + new String(new byte[]{
                                (byte) (0xff & (thesignature >> 24)),
                                (byte) (0xff & (thesignature >> 16)),
                                (byte) (0xff & (thesignature >> 8)),
                                (byte) (0xff & (thesignature >> 0)), }, "US-ASCII")
                        + ")");
                canThrow = true;
            } finally {
                IoUtils.closeQuietly(canThrow, bis);
            }
        }

    },

    TEXT_TYPE(
            "textType", 0x74657874) {
        public void dump(final String prefix, final byte[] bytes)
                throws ImageReadException, IOException
        {
            InputStream bis = null;
            boolean canThrow = false;
            try {
                bis = new ByteArrayInputStream(bytes);
                read4Bytes("type_signature", bis, "ICC: corrupt tag data", ByteOrder.BIG_ENDIAN);
                read4Bytes("ignore", bis, "ICC: corrupt tag data", ByteOrder.BIG_ENDIAN);
                final String s = new String(bytes, 8, bytes.length - 8, "US-ASCII");
                System.out.println(prefix + "s: '" + s + "'");
                canThrow = true;
            } finally {
                IoUtils.closeQuietly(canThrow, bis);
            }
        }

    };

    public final String name;
    public final int signature;

    IccTagDataTypes(final String name, final int signature) {
        this.name = name;
        this.signature = signature;
    }
    
    public String getName() {
        return name;
    }
    
    public int getSignature() {
        return signature;
    }
}
