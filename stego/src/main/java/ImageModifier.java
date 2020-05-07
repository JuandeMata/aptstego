import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ImageModifier {

    private final static int IMAGE_START_POSITION = 10;
    private final static int PADDING_SIZE = 4;
    private final static byte END_OF_MESSAGE = '\0';

    private BufferedImage bufferedImage;
    private byte[] byteImage;

    public ImageModifier(URI imageUri) {
        try {
            bufferedImage = ImageIO.read(new File(imageUri));
            byteImage = Files.readAllBytes(Paths.get(imageUri));
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to get image");
        }

    }

    public byte[] encodeTextIntoImage(byte[] text) {
        if (!isPossibleToHide()) {
            throw new IllegalArgumentException("Not possible to hide info \uD83D\uDE22");
        }
        var pixelIterator = new PixelIterator();
        for (var character : text) {
            if (pixelIterator.hasNext()) {
                byteImage[pixelIterator.next()] = character;
            } else {
                throw new IllegalArgumentException("Text too long to be hidden \uD83D\uDE22");
            }
        }
        byteImage[pixelIterator.next()] = END_OF_MESSAGE;
        return byteImage;
    }

    public Byte[] decodeTextFromImage() {
        List<Byte> message = new ArrayList<>();
        var pixelIterator = new PixelIterator();
        while(pixelIterator.hasNext()) {
            var position = pixelIterator.next();
            if (byteImage[position] == END_OF_MESSAGE) {
                break;
            }
            message.add(byteImage[position]);
        }
        Byte[] intArray = new Byte[message.size()];
        return message.toArray(intArray);
    }

    private boolean isPossibleToHide() {
        return (getImageWidth() * (getBitsPerPixel() / 8)) % PADDING_SIZE != 0;
    }

    private int getAmountOfBytes() {
        return ((getImageWidth() * (getBitsPerPixel() / 8)) % PADDING_SIZE) * getImageHeight();
    }

    private int getBitsPerPixel() {
        return bufferedImage.getColorModel().getPixelSize();
    }

    private int getImageWidth() {
        return bufferedImage.getWidth();
    }

    private int getImageHeight() {
        return bufferedImage.getHeight();
    }

    private int getImageStart() {
        return byteImage[IMAGE_START_POSITION];
    }

    private class PixelIterator implements Iterator<Integer> {

        private int position;

        public PixelIterator() {
            position = getImageStart();
        }

        @Override
        public boolean hasNext() {
            return position < byteImage.length - 1;
        }

        @Override
        public Integer next() {
            if (position % 4 != 3) {
                position = position + 1;
            } else {
                position = position + (getImageWidth() * (getBitsPerPixel() / 8));
            }
            return position;
        }

    }

}
