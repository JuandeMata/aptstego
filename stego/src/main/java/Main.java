import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException {
        ZipPacker zipPacker = new ZipPacker();
        Path zipPath = Paths.get(zipPacker.ZipCreator());
        byte[] zipFile = Files.readAllBytes(zipPath);

        new ImageStego().encodeTextIntoLibrary(zipFile);

        Byte[] decodedInfo = new ImageStego().getTextFromLibrary();

        zipPacker.DecodeZip(decodedInfo);
    }
}
