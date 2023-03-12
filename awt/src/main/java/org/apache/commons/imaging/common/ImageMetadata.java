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
package org.apache.commons.imaging.common;

import java.util.ArrayList;
import java.util.List;

public class ImageMetadata implements IImageMetadata {
    private static final String NEWLINE = System.getProperty("line.separator");
    private final List<IImageMetadataItem> items = new ArrayList<IImageMetadataItem>();

    public void add(final String keyword, final String text) {
        add(new Item(keyword, text));
    }

    public void add(final IImageMetadataItem item) {
        items.add(item);
    }

    public List<? extends IImageMetadataItem> getItems() {
        return new ArrayList<IImageMetadataItem>(items);
    }

    @Override
    public String toString() {
        return toString(null);
    }

    public String toString(String prefix) {
        if (null == prefix) {
            prefix = "";
        }

        final StringBuilder result = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            if (i > 0) {
                result.append(NEWLINE);
            }
            // if (null != prefix)
            // result.append(prefix);

            final IImageMetadataItem item = items.get(i);
            result.append(item.toString(prefix + "\t"));

            // Debug.debug("prefix", prefix);
            // Debug.debug("item", items.get(i));
            // Debug.debug();
        }
        return result.toString();
    }

    public static class Item implements IImageMetadataItem {
        private final String keyword;
        private final String text;

        public Item(final String keyword, final String text) {
            this.keyword = keyword;
            this.text = text;
        }

        public String getKeyword() {
            return keyword;
        }

        public String getText() {
            return text;
        }

        @Override
        public String toString() {
            return toString(null);
        }

        public String toString(final String prefix) {
            final String result = keyword + ": " + text;
            if (null != prefix) {
                return prefix + result;
            } else {
                return result;
            }
        }
    }

}
