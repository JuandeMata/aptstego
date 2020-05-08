import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.EncryptionMethod;

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

        // Juanker image
        ImageModifier imageModifier = new ImageModifier(ImageModifier.class.getClassLoader()
                .getResource("foto.bmp").toURI());
        byte[] juankerImage = imageModifier.encodeTextIntoImage(zipFile);
        Files.write(Paths.get("juankerfoto.bmp"), juankerImage);

        // Dejuanker image
        ImageModifier imageModifier2 = new ImageModifier(ImageModifier.class.getClassLoader()
                .getResource("juankerfoto.bmp").toURI());
        Byte[] juankerImage2 = imageModifier.decodeTextFromImage();
        System.out.println(juankerImage);
    }

}

/* TODO:
    Selector de origen de imágenes ->
    Formatear y recortar la imagen para esconder info (que no tenga 4n bytes de info por fila) (ImageCropper.java) -> Pablo
    Selector de origen del mensaje (MessageReader.java) -> Jorge
    Cifrar la información -> Jorge
    Spliteo de bytes en diferentes fotos -> Juande
    Incluir orden en el fichero -> Juande
 */