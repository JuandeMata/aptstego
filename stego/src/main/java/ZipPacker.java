import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ZipPacker {
    ZipParameters zipParameters = new ZipParameters();
    String zipName = "hiddenZip.zip";
    String passwd = "APTStego";

    public String ZipCreator() throws IOException {

        MessageReader messageReader = new MessageReader();
        String inputPath = messageReader.inputSelector();
        if (inputPath== null){
            System.exit(0);
        }
        zipParameters.setEncryptFiles(true);
        zipParameters.setEncryptionMethod(EncryptionMethod.AES);
        zipParameters.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_128);
        List<File> filesToAdd = Arrays.asList(new File(inputPath));

        ZipFile zipFile = new ZipFile(zipName, passwd.toCharArray());
        zipFile.addFiles(filesToAdd, zipParameters);

        return zipName;
    }

    public void ZipUnpacker () throws ZipException {
        new ZipFile(zipName, passwd.toCharArray()).extractAll("/destination_directory");
    }
}
