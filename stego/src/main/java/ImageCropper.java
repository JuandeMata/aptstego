import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageCropper {
    private final static int PADDING_SIZE = 4;
    BufferedImage originalImage;
    int bitsPerPixel;
    int imageWidth;
    int imageHeight;

    public ImageCropper(BufferedImage originalImage) {
        this.originalImage = originalImage;
        this.bitsPerPixel = originalImage.getColorModel().getPixelSize();
        this.imageWidth = originalImage.getWidth();
        this.imageHeight = originalImage.getHeight();
    }

    public BufferedImage getCroppedImage(){
        if (isPossibleToHide(originalImage)){
            return originalImage; // Image already hide-ready
        }else{
            BufferedImage img = originalImage.getSubimage(0, 0, imageWidth - 1, imageHeight);
            BufferedImage croppedImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics g = croppedImage.createGraphics();
            g.drawImage(img, 0, 0, null);
/*            try {
                ImageIO.write(img, "BMP", new File("croppedImage.bmp"));
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            return croppedImage; //or use it however you want
        }
    }

    //Tests if hiding is possible in a given image
    private boolean isPossibleToHide(BufferedImage image) {
        int imageWidth = image.getWidth();
        int bitsPerPixel = image.getColorModel().getPixelSize();
        return (imageWidth * (bitsPerPixel / 8)) % PADDING_SIZE != 0;
    }
}