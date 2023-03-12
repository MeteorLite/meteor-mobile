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
 * 
 */

#ifndef _Included_NativeCMM
#define _Included_NativeCMM


#include <stdio.h>

#include "cmmapi.h"
#include "cmmerror.h"
#include "NativeImageFormat.h"

#define CMM_TOO_SMALL_BUFFER_MSG "CMM Error. Buffer is too small."

#define MAX_CHANNELS 10

// Tag signatures
#define TAG_SIGNATURE(a,b,c,d) \
                    ((unsigned)(a) << 24 | \
                     (unsigned)(b) << 16 | \
                     (unsigned)(c) <<  8 | \
                     (unsigned)(d))

#define HEADER_TAG_ID  TAG_SIGNATURE('h', 'e', 'a', 'd')
#define PROFILE_TAG_ID TAG_SIGNATURE('a', 'c', 's', 'p')

#define HEADER_SIZE 128

/*JNIEXPORT*/ jlong /*JNICALL*/ Java_org_apache_harmony_awt_gl_color_NativeCMM_cmmOpenProfile(JNIEnv *env, jclass cls, jbyteArray data);
/*JNIEXPORT*/ void /*JNICALL*/ Java_org_apache_harmony_awt_gl_color_NativeCMM_cmmCloseProfile(JNIEnv *env, jclass cls, jlong profileID);
/*JNIEXPORT*/ jint /*JNICALL*/Java_org_apache_harmony_awt_gl_color_NativeCMM_cmmGetProfileSize(JNIEnv *env, jclass cls, jlong profileID);
/*JNIEXPORT*/ void /*JNICALL*/ Java_org_apache_harmony_awt_gl_color_NativeCMM_cmmGetProfile(JNIEnv *env, jclass cls, jlong profileID, jbyteArray data);
/*JNIEXPORT*/ void /*JNICALL*/ Java_org_apache_harmony_awt_gl_color_NativeCMM_cmmGetProfileElement(JNIEnv *env, jclass cls, jlong profileID, jint tagSignature, jbyteArray data);
/*JNIEXPORT*/ jint /*JNICALL*/ Java_org_apache_harmony_awt_gl_color_NativeCMM_cmmGetProfileElementSize(JNIEnv *env, jclass cls, jlong profileID, jint tagSignature);
/*JNIEXPORT*/ void /*JNICALL*/ Java_org_apache_harmony_awt_gl_color_NativeCMM_cmmSetProfileElement(JNIEnv *env, jclass cls, jlong profileID, jint tagSignature, jbyteArray data);
/*JNIEXPORT*/ jlong /*JNICALL*/ Java_org_apache_harmony_awt_gl_color_NativeCMM_cmmCreateMultiprofileTransform(JNIEnv *env, jclass cls, jlongArray jProfileHandles, jintArray jRenderingIntents);
/*JNIEXPORT*/ void /*JNICALL*/ Java_org_apache_harmony_awt_gl_color_NativeCMM_cmmDeleteTransform(JNIEnv *env, jclass cls, jlong transformHandle);
/*JNIEXPORT*/ void /*JNICALL*/ Java_org_apache_harmony_awt_gl_color_NativeCMM_cmmTranslateColors(JNIEnv *env, jclass cls, jlong transformHandle, jobject src, jobject dst);

#endif // _Included_NativeCMM
