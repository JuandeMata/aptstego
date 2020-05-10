import net.lingala.zip4j.ZipFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException {
        new ImageStego().encodeTextIntoLibrary("\"But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, or avoids pleasure itself, because it is pleasure, but because those who do not know how to pursue pleasure rationally encounter consequences that are extremely painful. Nor again is there anyone who loves or pursues or desires to obtain pain of itself, because it is pain, but because occasionally circumstances occur in which toil and pain can procure him some great pleasure. To take a trivial example, which of us ever undertakes laborious physical exercise, except to obtain some advantage from it? But who has any right to find fault with a man who chooses to enjoy a pleasure that has no annoying consequences, or one who avoids a pain that produces no resultant pleasure?\"".getBytes());
        Byte[] decoded = new ImageStego().getTextFromLibrary();
        System.exit(0);
        ZipPacker zipPacker = new ZipPacker();
        Path zipPath = Paths.get(zipPacker.ZipCreator());
        byte[] zipFile = Files.readAllBytes(zipPath);

        String localDir = System.getProperty("user.dir"); // Path local
        ImageStego imageStego = new ImageStego();

        // Juanker image
        imageStego.encodeTextIntoLibrary(zipFile);
        //Files.write(Paths.get(localDir + "\\stego\\src\\main\\resources\\juankerfoto.bmp"), juankerImage); // Para guardar en resources en vez de en la raiz

        // Dejuanker image
        Byte[] juankerImage2 = imageStego.getTextFromLibrary();

        zipPacker.DecodeZip(juankerImage2);

        System.out.println(juankerImage2); //FIXME: no seria juankerImage2?
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