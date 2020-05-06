import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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

    public byte[] encodeTextIntoImage() {
        if (!isPossibleToHide()) {
            throw new IllegalArgumentException("Not possible to hide info");
        }
        return null;
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

    private int getImageStart(byte[] image) {
        return image[IMAGE_START_POSITION];
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        Stego stego = new Stego(Stego.class.getClassLoader()
                .getResource("foto.bmp").toURI());
    }

}
