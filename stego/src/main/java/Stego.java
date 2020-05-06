public class Stego {

    private static final int BITS_PIXEL_POSITION = 28;
    private static final int BITS_PIXEL_SIZE = 2;
    private static final int WIDTH_POSITION = 18;
    private static final int WIDTH_SIZE = 4;
    private static final int HEIGHT_POSITION = 22;
    private static final int HEIGHT_SIZE = 4;

    public byte[] encodeTextIntoImage(byte[] inputImage, byte[] text) {

        return null;
    }

    public byte[] decodeTextIntoImage(byte[] inputImage) {
        return null;
    }

    private boolean isPossibleToHide(int bitsPerPixel, long imageWidth) {
        return (imageWidth * bitsPerPixel) % 4 != 0;
    }

    private long getAmountOfBytes(int bitsPerPixel, long imageWidth, long imageHeight) {
        return ((imageWidth * bitsPerPixel) % 4) * imageHeight;
    }

    private int getBitsPerPixel(byte[] image) {
        return image[BITS_PIXEL_POSITION + 1] * 8 + image[BITS_PIXEL_POSITION];
    }

    private long getWidth(byte[] image) {
        return image[WIDTH_POSITION] + (image[WIDTH_POSITION + 1] * 8) + (image[WIDTH_POSITION + 2] * 8 * 8) + (image[WIDTH_POSITION + 3] * 8 * 8 * 8);
    }

    private long getHeight(byte[] image) {
        return image[HEIGHT_POSITION] + (image[HEIGHT_POSITION + 1] * 8) + (image[HEIGHT_POSITION + 2] * 8 * 8) + (image[HEIGHT_POSITION + 3] * 8 * 8 * 8);
    }

}
