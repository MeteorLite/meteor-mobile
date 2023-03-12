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
 * @author Oleg V. Khaschansky
 */
package org.apache.harmony.awt.gl.color;
import java.awt.color.ICC_Profile;
import java.util.HashMap;
import ro.andob.awtcompat.nativec.AwtCompatNativeComponents;


/**
 * This class is a wrapper for the native CMM library
 */
public class NativeCMM {

    /**
     * Storage for profile handles, since they are private
     * in ICC_Profile, but we need access to them.
     */
    private static HashMap<ICC_Profile, Long> profileHandles = new HashMap<ICC_Profile, Long>();

    public static void addHandle(ICC_Profile key, long handle) {
        profileHandles.put(key, new Long(handle));
    }

    public static void removeHandle(ICC_Profile key) {
        profileHandles.remove(key);
    }

    public static long getHandle(ICC_Profile key) {
        return profileHandles.get(key).longValue();
    }

    /* ICC profile management */
    public static long cmmOpenProfile(byte[] data) {
        return AwtCompatNativeComponents.cmmOpenProfile(data);
    }

    public static void cmmCloseProfile(long profileID) {
        AwtCompatNativeComponents.cmmCloseProfile(profileID);
    }

    public static int cmmGetProfileSize(long profileID) {
        return AwtCompatNativeComponents.cmmGetProfileSize(profileID);
    }

    public static void cmmGetProfile(long profileID, byte[] data) {
        AwtCompatNativeComponents.cmmGetProfile(profileID, data);
    }

    public static int cmmGetProfileElementSize(long profileID, int signature) {
        return AwtCompatNativeComponents.cmmGetProfileElementSize(profileID, signature);
    }

    public static void cmmGetProfileElement(long profileID, int signature, byte[] data) {
        AwtCompatNativeComponents.cmmGetProfileElement(profileID, signature, data);
    }

    public static void cmmSetProfileElement(long profileID, int tagSignature, byte[] data) {
        AwtCompatNativeComponents.cmmSetProfileElement(profileID, tagSignature, data);
    }


    /* ICC transforms */
    public static long cmmCreateMultiprofileTransform(long[] profileHandles, int[] renderingIntents) {
        return AwtCompatNativeComponents.cmmCreateMultiprofileTransform(profileHandles, renderingIntents);
    }

    public static void cmmDeleteTransform(long transformHandle) {
        AwtCompatNativeComponents.cmmDeleteTransform(transformHandle);
    }

    public static void cmmTranslateColors(long transformHandle, NativeImageFormat src, NativeImageFormat dest) {
        AwtCompatNativeComponents.cmmTranslateColors(transformHandle, src, dest);
    }
}
