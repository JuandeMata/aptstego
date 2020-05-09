import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import net.lingala.zip4j.util.FileUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ZipPacker {
    private static ZipParameters zipParameters = new ZipParameters();
    private static String zipName = "hiddenZip.zip";
    private static String passwd = "APTStego";
    private static String zipToDecode = "/resources/zipToDecode.zip";

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

    public void DecodeZip (Byte[] zipInBytes) throws ZipException {
        byte[] bytes = ArrayUtils.toPrimitive(zipInBytes);
        try (FileOutputStream stream = new FileOutputStream("/resources/zipToDecode.zip")) {
            stream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ZipUnpacker();
    }

    private void ZipUnpacker () throws ZipException {
        new ZipFile(zipToDecode, passwd.toCharArray()).extractAll("/resources");
    }
}
