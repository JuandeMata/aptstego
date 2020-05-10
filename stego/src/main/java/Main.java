import net.lingala.zip4j.ZipFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException {
        new ImageStego().encodeTextIntoLibrary(("\n" +
                "\n" +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur ac turpis sapien. Nulla dictum fringilla nunc sit amet varius. Sed quis mauris eu enim sodales pulvinar eu vitae augue. Pellentesque tellus eros, varius eget magna ac, auctor mollis neque. Curabitur a iaculis sem. Donec feugiat, ex eget varius mattis, felis risus auctor felis, at eleifend arcu purus eget felis. Integer consequat purus et diam sodales sagittis.\n" +
                "\n" +
                "Fusce id luctus metus. Sed imperdiet ultricies dui, sed vulputate orci egestas et. Suspendisse mi orci, interdum placerat dapibus quis, luctus vel orci. Fusce semper neque id elit elementum aliquet. Nulla facilisi. Mauris eget cursus ipsum. Sed lacinia lorem vitae ex iaculis condimentum. Maecenas mattis eu nisl eget laoreet. Curabitur ac varius purus. Praesent lacinia ac felis nec tempus. Donec maximus libero eget leo tempor porttitor. Vivamus congue condimentum tortor ut maximus. Vestibulum fringilla metus euismod aliquet ultricies. Fusce vel tristique nulla. Sed egestas dui ac massa eleifend vestibulum. Suspendisse congue rutrum dolor eget sodales.\n" +
                "\n" +
                "Praesent a lorem sit amet augue vehicula sodales vitae et risus. Proin porttitor nisl quis nisl porta, quis condimentum ligula mollis. Duis porta sagittis imperdiet. Ut ac dui in ante ultrices aliquam. Quisque a purus eleifend, ornare est vitae, porta lorem. Maecenas imperdiet odio eget velit mollis, quis lobortis justo cursus. Donec molestie augue ut euismod venenatis. Praesent ultricies mauris dui, ut cursus magna dignissim a. Interdum et malesuada fames ac ante ipsum primis in faucibus. In dolor risus, elementum id magna non, imperdiet pulvinar ipsum. Mauris in neque metus. Suspendisse potenti. Proin et quam risus. Donec eu tellus id mauris molestie facilisis eu at quam. Duis ut nisi lobortis, fermentum magna at, egestas nisl. Nulla sagittis ornare neque, sed tincidunt quam bibendum placerat.\n" +
                "\n" +
                "Vivamus bibendum mi ac eros tempus, ac maximus sem condimentum. Nam luctus lacus sit amet mauris imperdiet mollis. Nam suscipit turpis nec rutrum convallis. Quisque sodales nunc iaculis nunc semper cursus. Donec scelerisque turpis eu aliquet maximus. Vivamus bibendum, tellus nec dictum elementum, turpis lorem porta orci, at imperdiet augue enim non arcu. Sed suscipit nisl at sollicitudin convallis. Mauris sit amet erat mauris. Morbi molestie neque sit amet mi dapibus tincidunt. Etiam pretium tincidunt blandit. Integer efficitur rutrum lorem sed mollis. Curabitur semper, sapien tristique ultrices finibus, nulla velit aliquet odio, vitae gravida diam enim ut ligula. Fusce a arcu non velit convallis efficitur et ut augue. Praesent vitae metus a nisi sodales sagittis. Pellentesque et pellentesque justo, vitae lacinia tellus.\n" +
                "\n" +
                "In sed tempor nisl. Morbi at quam nisi. Mauris quam lectus, laoreet vitae sollicitudin quis, mollis eget ante. Cras molestie tellus lectus, volutpat tincidunt erat venenatis quis. Proin ut blandit libero. Nunc turpis ipsum, semper vitae nisi id, egestas cursus ipsum. Integer in eleifend risus. Duis id porttitor enim. Ut ac laoreet nulla, a pharetra risus. Quisque vestibulum sapien non vestibulum interdum. Integer faucibus mattis velit, sit amet ultricies diam. Morbi sapien metus, pharetra sed nunc ac, ullamcorper porta nunc.").getBytes());
        Byte[] decoded = new ImageStego().getTextFromLibrary();
        System.out.println(Arrays.stream(decoded).map(by -> new byte[]{by}).map(String::new).collect(Collectors.joining()));
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