#ifndef ANDROID_AWT_AWTCOMPAT_NATIVE_COMPONENTS_H
#define ANDROID_AWT_AWTCOMPAT_NATIVE_COMPONENTS_H

#include <jni.h>

JNIEXPORT jstring JNICALL Java_ro_andob_awtcompat_nativec_AwtCompatNativeComponents_getHelloWorldMesssage(JNIEnv *env, jclass clazz);

JNIEXPORT jlong JNICALL Java_ro_andob_awtcompat_nativec_AwtCompatNativeComponents_cmmOpenProfile(JNIEnv *env, jclass clazz, jbyteArray data);
JNIEXPORT void JNICALL Java_ro_andob_awtcompat_nativec_AwtCompatNativeComponents_cmmCloseProfile(JNIEnv *env, jclass clazz, jlong profile_id);
JNIEXPORT jint JNICALL Java_ro_andob_awtcompat_nativec_AwtCompatNativeComponents_cmmGetProfileSize(JNIEnv *env, jclass clazz, jlong profile_id);
JNIEXPORT void JNICALL Java_ro_andob_awtcompat_nativec_AwtCompatNativeComponents_cmmGetProfile(JNIEnv *env, jclass clazz, jlong profile_id, jbyteArray data);
JNIEXPORT jint JNICALL Java_ro_andob_awtcompat_nativec_AwtCompatNativeComponents_cmmGetProfileElementSize(JNIEnv *env, jclass clazz, jlong profile_id, jint signature);
JNIEXPORT void JNICALL Java_ro_andob_awtcompat_nativec_AwtCompatNativeComponents_cmmGetProfileElement(JNIEnv *env, jclass clazz, jlong profile_id, jint signature, jbyteArray data);
JNIEXPORT void JNICALL Java_ro_andob_awtcompat_nativec_AwtCompatNativeComponents_cmmSetProfileElement(JNIEnv *env, jclass clazz, jlong profile_id, jint tag_signature, jbyteArray data);
JNIEXPORT jlong JNICALL Java_ro_andob_awtcompat_nativec_AwtCompatNativeComponents_cmmCreateMultiprofileTransform(JNIEnv *env, jclass clazz, jlongArray profile_handles, jintArray rendering_intents);
JNIEXPORT void JNICALL Java_ro_andob_awtcompat_nativec_AwtCompatNativeComponents_cmmDeleteTransform(JNIEnv *env, jclass clazz, jlong transform_handle);
JNIEXPORT void JNICALL Java_ro_andob_awtcompat_nativec_AwtCompatNativeComponents_cmmTranslateColors(JNIEnv *env, jclass clazz, jlong transform_handle, jobject src, jobject dest);

JNIEXPORT jintArray JNICALL Java_ro_andob_awtcompat_nativec_AwtCompatNativeComponents_gifDecoder_1toRGB(JNIEnv *env, jclass clazz, jbyteArray image_data, jbyteArray colormap, jint transparent_color);
JNIEXPORT void JNICALL Java_ro_andob_awtcompat_nativec_AwtCompatNativeComponents_gifDecoder_1releaseNativeDecoder(JNIEnv *env, jclass clazz, jlong h_decoder);
JNIEXPORT jint JNICALL Java_ro_andob_awtcompat_nativec_AwtCompatNativeComponents_gifDecoder_1decode(JNIEnv *env, jclass clazz, jbyteArray input, jint bytes_in_buffer, jlong h_decoder, jobject data_stream, jobject curr_block, jobject h_decoder_container);

JNIEXPORT jlong JNICALL Java_ro_andob_awtcompat_nativec_AwtCompatNativeComponents_pngDecoder_1decode(JNIEnv *env, jclass clazz, jbyteArray input, jint bytes_in_buffer, jlong h_decoder);
JNIEXPORT void JNICALL Java_ro_andob_awtcompat_nativec_AwtCompatNativeComponents_pngDecoder_1releaseNativeDecoder(JNIEnv *env, jclass clazz, jlong h_decoder);

JNIEXPORT void JNICALL Java_ro_andob_awtcompat_nativec_AwtCompatNativeComponents_jpegDecoder_1releaseNativeDecoder(JNIEnv *env, jclass clazz, jlong h_decoder);
JNIEXPORT jobject JNICALL Java_ro_andob_awtcompat_nativec_AwtCompatNativeComponents_jpegDecoder_1decode(JNIEnv *env, jclass clazz, jbyteArray input, jint bytes_in_buffer, jlong h_decoder, jobject h_decoder_container);

#endif //ANDROID_AWT_AWTCOMPAT_NATIVE_COMPONENTS_H
