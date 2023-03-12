#include "awtcompat_native_components.h"
#include <stdio.h>
#include "NativeCMM.h"

JNIEXPORT jstring JNICALL Java_ro_andob_awtcompat_nativec_AwtCompatNativeComponents_getHelloWorldMesssage(JNIEnv *env, jobject obj) {
    char msg[] = "Hello world from C!";
    return (*env)->NewStringUTF(env, msg);
}

JNIEXPORT jlong JNICALL Java_ro_andob_awtcompat_nativec_AwtCompatNativeComponents_cmmOpenProfile(JNIEnv *env, jclass clazz, jbyteArray data)
{
    return Java_org_apache_harmony_awt_gl_color_NativeCMM_cmmOpenProfile(env, clazz, data);
}

JNIEXPORT void JNICALL Java_ro_andob_awtcompat_nativec_AwtCompatNativeComponents_cmmCloseProfile(JNIEnv *env, jclass clazz, jlong profile_id)
{
    Java_org_apache_harmony_awt_gl_color_NativeCMM_cmmCloseProfile(env, clazz, profile_id);
}

JNIEXPORT jint JNICALL Java_ro_andob_awtcompat_nativec_AwtCompatNativeComponents_cmmGetProfileSize(JNIEnv *env, jclass clazz, jlong profile_id)
{
    return Java_org_apache_harmony_awt_gl_color_NativeCMM_cmmGetProfileSize(env, clazz, profile_id);
}

JNIEXPORT void JNICALL Java_ro_andob_awtcompat_nativec_AwtCompatNativeComponents_cmmGetProfile(JNIEnv *env, jclass clazz, jlong profile_id, jbyteArray data)
{
    Java_org_apache_harmony_awt_gl_color_NativeCMM_cmmGetProfile(env, clazz, profile_id, data);
}

JNIEXPORT jint JNICALL Java_ro_andob_awtcompat_nativec_AwtCompatNativeComponents_cmmGetProfileElementSize(JNIEnv *env, jclass clazz, jlong profile_id, jint signature)
{
    return Java_org_apache_harmony_awt_gl_color_NativeCMM_cmmGetProfileElementSize(env, clazz, profile_id, signature);

}

JNIEXPORT void JNICALL Java_ro_andob_awtcompat_nativec_AwtCompatNativeComponents_cmmGetProfileElement(JNIEnv *env, jclass clazz, jlong profile_id, jint signature, jbyteArray data)
{
    return Java_org_apache_harmony_awt_gl_color_NativeCMM_cmmGetProfileElement(env, clazz, profile_id, signature, data);
}

JNIEXPORT void JNICALL Java_ro_andob_awtcompat_nativec_AwtCompatNativeComponents_cmmSetProfileElement(JNIEnv *env, jclass clazz, jlong profile_id, jint tag_signature, jbyteArray data)
{
    Java_org_apache_harmony_awt_gl_color_NativeCMM_cmmSetProfileElement(env, clazz, profile_id, tag_signature, data);
}

JNIEXPORT jlong JNICALL Java_ro_andob_awtcompat_nativec_AwtCompatNativeComponents_cmmCreateMultiprofileTransform(JNIEnv *env, jclass clazz, jlongArray profile_handles, jintArray rendering_intents)
{
    return Java_org_apache_harmony_awt_gl_color_NativeCMM_cmmCreateMultiprofileTransform(env, clazz, profile_handles, rendering_intents);
}

JNIEXPORT void JNICALL Java_ro_andob_awtcompat_nativec_AwtCompatNativeComponents_cmmDeleteTransform(JNIEnv *env, jclass clazz, jlong transform_handle)
{
    Java_org_apache_harmony_awt_gl_color_NativeCMM_cmmDeleteTransform(env, clazz, transform_handle);
}

JNIEXPORT void JNICALL Java_ro_andob_awtcompat_nativec_AwtCompatNativeComponents_cmmTranslateColors(JNIEnv *env, jclass clazz, jlong transform_handle, jobject src, jobject dest)
{
    Java_org_apache_harmony_awt_gl_color_NativeCMM_cmmTranslateColors(env, clazz, transform_handle, src, dest);
}

JNIEXPORT jintArray JNICALL Java_ro_andob_awtcompat_nativec_AwtCompatNativeComponents_gifDecoder_1toRGB(JNIEnv *env, jclass clazz, jbyteArray image_data, jbyteArray colormap, jint transparent_color)
{
    return 0;
}

JNIEXPORT void JNICALL Java_ro_andob_awtcompat_nativec_AwtCompatNativeComponents_gifDecoder_1releaseNativeDecoder(JNIEnv *env, jclass clazz, jlong h_decoder)
{

}

JNIEXPORT jint JNICALL Java_ro_andob_awtcompat_nativec_AwtCompatNativeComponents_gifDecoder_1decode(JNIEnv *env, jclass clazz, jbyteArray input, jint bytes_in_buffer, jlong h_decoder, jobject data_stream, jobject curr_block, jobject h_decoder_container)
{
    return 0;
}

JNIEXPORT jlong JNICALL Java_ro_andob_awtcompat_nativec_AwtCompatNativeComponents_pngDecoder_1decode(JNIEnv *env, jclass clazz, jbyteArray input, jint bytes_in_buffer, jlong h_decoder)
{
    return 0;
}

JNIEXPORT void JNICALL Java_ro_andob_awtcompat_nativec_AwtCompatNativeComponents_pngDecoder_1releaseNativeDecoder(JNIEnv *env, jclass clazz, jlong h_decoder)
{

}

JNIEXPORT void JNICALL Java_ro_andob_awtcompat_nativec_AwtCompatNativeComponents_jpegDecoder_1releaseNativeDecoder(JNIEnv *env, jclass clazz, jlong h_decoder)
{

}

JNIEXPORT jobject JNICALL Java_ro_andob_awtcompat_nativec_AwtCompatNativeComponents_jpegDecoder_1decode(JNIEnv *env, jclass clazz, jbyteArray input, jint bytes_in_buffer, jlong h_decoder, jobject h_decoder_container)
{
    return 0;
}
