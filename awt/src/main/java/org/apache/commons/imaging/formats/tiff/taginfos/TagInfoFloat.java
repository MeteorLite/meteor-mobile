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
package org.apache.commons.imaging.formats.tiff.taginfos;

import java.nio.ByteOrder;

import org.apache.commons.imaging.common.ByteConversions;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryType;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;

public class TagInfoFloat extends TagInfo {
    public TagInfoFloat(final String name, final int tag, final int length, final TiffDirectoryType directoryType) {
        super(name, tag, FieldType.FLOAT, length, directoryType);
    }
    
    public float[] getValue(final ByteOrder byteOrder, final byte[] bytes) {
        return ByteConversions.toFloats(bytes, byteOrder);
    }
    
    public byte[] encodeValue(final ByteOrder byteOrder, final float... values) {
        return ByteConversions.toBytes(values, byteOrder);
    }
}
