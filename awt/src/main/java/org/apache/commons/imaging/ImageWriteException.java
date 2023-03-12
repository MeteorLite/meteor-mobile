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
package org.apache.commons.imaging;

/**
 * A custom exception thrown when an ImageParser or other utility
 * encounters a format-violation, non-supported element, or other
 * condition that renders image data unwritable.
 */
public class ImageWriteException extends ImagingException {
    private static final long serialVersionUID = -1L;

    public ImageWriteException(final String message) {
        super(message);
    }

    public ImageWriteException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ImageWriteException(final String message, final Object data) {
        super(message + ": " + data + " (" + getType(data) + ")");
    }

    private static String getType(final Object value) {
        if (value == null) {
            return "null";
        } else if (value instanceof Object[]) {
            return "[Object[]: " + ((Object[]) value).length + "]";
        } else if (value instanceof char[]) {
            return "[char[]: " + ((char[]) value).length + "]";
        } else if (value instanceof byte[]) {
            return "[byte[]: " + ((byte[]) value).length + "]";
        } else if (value instanceof short[]) {
            return "[short[]: " + ((short[]) value).length + "]";
        } else if (value instanceof int[]) {
            return "[int[]: " + ((int[]) value).length + "]";
        } else if (value instanceof long[]) {
            return "[long[]: " + ((long[]) value).length + "]";
        } else if (value instanceof float[]) {
            return "[float[]: " + ((float[]) value).length + "]";
        } else if (value instanceof double[]) {
            return "[double[]: " + ((double[]) value).length + "]";
        } else if (value instanceof boolean[]) {
            return "[boolean[]: " + ((boolean[]) value).length + "]";
        } else {
            return value.getClass().getName();
        }
    }
}
