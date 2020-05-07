import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class Stego {

    private final static int IMAGE_START_POSITION = 10;
    private final static int PADDING_SIZE = 4;

    private BufferedImage bufferedImage;
    private byte[] byteImage;

    public Stego(URI imageUri) {
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

        return byteImage;
    }

    public byte[] decodeTextIntoImage() {
        return null;
    }

    private boolean isPossibleToHide() {
        return (getImageWidth() * getBitsPerPixel()) % PADDING_SIZE != 0;
    }

    private long getAmountOfBytes() {
        return ((getImageWidth() * getBitsPerPixel()) % PADDING_SIZE) * getImageHeight();
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

    public static void main(String[] args) throws URISyntaxException, IOException {
        Stego stego = new Stego(Stego.class.getClassLoader()
                .getResource("foto.bmp").toURI());
        byte[] juankerImage = stego.encodeTextIntoImage("patata".getBytes());
        URI juankerFoto = Stego.class.getClassLoader()
                .getResource("juankerfoto.bmp").toURI();
        Files.write(Paths.get(juankerFoto), juankerImage);
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
