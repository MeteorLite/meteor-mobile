package java.awt;

public class Component
{
//    final transient Toolkit toolkit = Toolkit.getDefaultToolkit();

    public boolean prepareImage(Image img, int w, int h, MediaTracker.TrackingImage trackingImage) {
//        toolkit.lockAWT();
//        try {
//            return toolkit.prepareImage(image, -1, -1, observer);
//        } finally {
//            toolkit.unlockAWT();
//        }
        return true;
    }

    public int checkImage(Image img, int w, int h, MediaTracker.TrackingImage trackingImage) {
//        toolkit.lockAWT();
//        try {
//            return toolkit.checkImage(image, width, height, observer);
//        } finally {
//            toolkit.unlockAWT();
//        }
        return 0;
    }
}
