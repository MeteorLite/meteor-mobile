package ro.andob.awtcompat.nativec;

import org.apache.harmony.awt.gl.color.NativeImageFormat;
import org.apache.harmony.awt.gl.image.GifDecoder;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.concurrent.atomic.AtomicBoolean;

public class AwtCompatNativeComponents
{
    private static final AtomicBoolean isLibraryLoaded = new AtomicBoolean(false);

    static
    {
        if (!isLibraryLoaded.get())
        {
            AccessController.doPrivileged(
                    (PrivilegedAction<Void>) () -> {
                        System.loadLibrary("awtcompat-native-components");
                        isLibraryLoaded.set(true);
                        return null;
                    });
        }
    }

    public static native String getHelloWorldMesssage();

    public static native long cmmOpenProfile(byte[] data);
    public static native void cmmCloseProfile(long profileID);
    public static native int cmmGetProfileSize(long profileID);
    public static native void cmmGetProfile(long profileID, byte[] data);
    public static native int cmmGetProfileElementSize(long profileID, int signature);
    public static native void cmmGetProfileElement(long profileID, int signature, byte[] data);
    public static native void cmmSetProfileElement(long profileID, int tagSignature, byte[] data);
    public static native long cmmCreateMultiprofileTransform(long[] profileHandles, int[] renderingIntents);
    public static native void cmmDeleteTransform(long transformHandle);
    public static native void cmmTranslateColors(long transformHandle, NativeImageFormat src, NativeImageFormat dest);

    public static native int[] gifDecoder_toRGB(byte imageData[], byte colormap[], int transparentColor);
    public static native void gifDecoder_releaseNativeDecoder(long hDecoder);
    public static native int gifDecoder_decode(byte input[], int bytesInBuffer, long hDecoder,
                                               GifDecoder.GifDataStream dataStream,
                                               GifDecoder.GifGraphicBlock currBlock,
                                               NativePointerContainer hDecoderContainer);

    public static native long pngDecoder_decode(byte[] input, int bytesInBuffer, long hDecoder);
    public static native void pngDecoder_releaseNativeDecoder(long hDecoder);

    public static native void jpegDecoder_releaseNativeDecoder(long hDecoder);
    public static native Object jpegDecoder_decode(byte[] input, int bytesInBuffer, long hDecoder,
                                                   NativePointerContainer hDecoderContainer);

    public static final class NativePointerContainer
    {
        public long pointer;

        public NativePointerContainer(long pointer)
        {
            this.pointer=pointer;
        }
    }
}
