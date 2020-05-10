import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ImageStego {

    public void encodeTextIntoLibrary(byte[] text) throws URISyntaxException, IOException {
        cleanTargetFolder();
        var imagesPath = Paths.get(this.getClass().getClassLoader().getResource("./original").toURI());
        var imageUris = Files.walk(imagesPath).filter(file -> file.toString().endsWith(".bmp")).map(Path::toUri).collect(Collectors.toList());
        for (int imageIndex = 0, currentOffset = 0; imageIndex < imageUris.size(); imageIndex++) {
            ImageModifier imageModifier = new ImageModifier(imageUris.get(imageIndex));
            int bytesAvailable = imageModifier.getAmountOfBytesToHide() - 4;
            if (currentOffset + bytesAvailable < text.length) {
                writeImage(imageModifier.encodeTextIntoImage(getFormattedMessage(imageIndex, currentOffset, bytesAvailable, text)), imageIndex);
                currentOffset = currentOffset + bytesAvailable;
            } else {
                writeImage(imageModifier.encodeTextIntoImage(getFormattedMessage(imageIndex, currentOffset, text.length - currentOffset, text)), imageIndex);
                break;
            }
        }
    }

    private void cleanTargetFolder() throws URISyntaxException, IOException {
        var targetPath = Paths.get(this.getClass().getClassLoader().getResource("./hidden").toURI());
        Files.walk(targetPath).filter(file -> file.toString().endsWith(".bmp")).forEach(path -> {
            try {
                Files.deleteIfExists(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private Byte[] getFormattedMessage(int currentIndex, int currentOffset, int size, byte[] message) {
        var bytes = new ArrayList<Byte>();
        bytes.add((byte) (currentIndex & 0xFF));
        bytes.add((byte) ((currentIndex >> 8) & 0xFF));
        bytes.add((byte) (size & 0xFF));
        bytes.add((byte) ((size >> 8) & 0xFF));
        for (int index = 0; index < size; index++) {
            bytes.add(message[index + currentOffset]);
        }
        return bytes.toArray(new Byte[size]);
    }

    private void writeImage(byte[] image, int currentOffset) {
        try {
            var outputFolder = Paths.get(this.getClass().getClassLoader().getResource("./hidden").toURI());
            Files.write(outputFolder.resolve(currentOffset + ".bmp"), image);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public Byte[] getTextFromLibrary() throws URISyntaxException, IOException {
        var imagesPath = Paths.get(this.getClass().getClassLoader().getResource("./hidden").toURI());
        var imageUris = Files.walk(imagesPath).filter(file -> file.toString().endsWith(".bmp")).map(Path::toUri).collect(Collectors.toList());
        var messages = new TreeMap<Integer, Byte[]>();
        for (int imageIndex = 0, currentOffset = 0; imageIndex < imageUris.size(); imageIndex++) {
            ImageModifier imageModifier = new ImageModifier(imageUris.get(imageIndex));
            var message = imageModifier.decodeTextFromImage();
            var messagePosition = Byte.toUnsignedInt(message[0]) + (Byte.toUnsignedInt(message[1]) << 8);
            var messageSize = Byte.toUnsignedInt(message[2]) + (Byte.toUnsignedInt(message[3]) << 8);
            messages.put(messagePosition, Arrays.copyOfRange(message, 4, messageSize + 4));
        }
        var orderedMessage = messages.entrySet().stream().map(Map.Entry::getValue).flatMap(Arrays::stream).collect(Collectors.toList());

        return orderedMessage.toArray(new Byte[orderedMessage.size()]);
    }

}
