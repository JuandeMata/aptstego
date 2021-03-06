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

    public byte[] encodeTextIntoImage(Byte[] text) {
        if (!isPossibleToHide()) {
            ImageCropper imgCropper = new ImageCropper(bufferedImage);
            bufferedImage = imgCropper.getCroppedImage(); //Si no vale, le quitamos una columna de pixeles
//            throw new IllegalArgumentException("Not possible to hide info ☹");
        }
        var pixelIterator = new PixelIterator();
        for (var character : text) {
            if (pixelIterator.hasNext()) {
                byteImage[pixelIterator.next()] = character;
            } else {
                throw new IllegalArgumentException("Text too long to be hidden ☹");
            }
        }
        return byteImage;
    }

    public Byte[] decodeTextFromImage() {
        List<Byte> message = new ArrayList<>();
        var pixelIterator = new PixelIterator();
        while(pixelIterator.hasNext()) {
            var position = pixelIterator.next();
            if (position > byteImage.length) {
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

    public int getAmountOfBytesToHide() {
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
        return Byte.toUnsignedInt(byteImage[IMAGE_START_POSITION]);
    }

    private class PixelIterator implements Iterator<Integer> {

        private int position;
        private boolean init;

        public PixelIterator() {
            position = getImageStart();
            init = false;
        }

        @Override
        public boolean hasNext() {
            return position < byteImage.length - 1;
        }

        @Override
        public Integer next() {
            if (position % 4 != 3 && init) {
                position = position + 1;
            } else {
                init = true;
                position = position + (getImageWidth() * (getBitsPerPixel() / 8));
            }
            return position;
        }

    }

}
