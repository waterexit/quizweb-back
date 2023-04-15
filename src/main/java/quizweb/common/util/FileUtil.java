package quizweb.common.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.ResourceUtils;

public class FileUtil {
    public static String saveImageByBase64(String dirPath, String base64DataURL) throws IOException {
        String extensionReg = "data:image/(.*?);base64,";
        Pattern pt = Pattern.compile(extensionReg);
        Matcher mt = pt.matcher(base64DataURL);

        mt.find();

        String extension = mt.group(1);
        String fileName = String.valueOf(System.currentTimeMillis()) + "." + extension;
        String absolutePath = ResourceUtils.getFile(dirPath).getAbsolutePath();
        try (FileOutputStream fos = new FileOutputStream(absolutePath + "/" + fileName)) {
            byte[] data = Base64.getDecoder().decode(base64DataURL.replaceFirst(extensionReg, "").getBytes());
            checkImage(Arrays.copyOf(data, 8));
            fos.write(data);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return fileName;
    }

    private static void checkImage(byte[] imageByteHeader) {
        final String JPEG_NO = "ffd8";
        final String BMP_NO = "424d";
        final String GIF_NO = "47494638";
        final String TIFF_NO = "49492a00";
        final String PNG_NO = "89504e470d0a1a0a";

        final String[] IMG_MAGIC_NO_ARRAY = { JPEG_NO, BMP_NO, GIF_NO, TIFF_NO, PNG_NO };

        StringBuilder headerString = new StringBuilder();
        for (byte b : imageByteHeader) {
            headerString.append(String.format("%02x", b));
        }

        if (!Arrays.stream(IMG_MAGIC_NO_ARRAY).anyMatch(mn -> headerString.toString().startsWith(mn))) {
            throw new RuntimeException();
        }
    }
}
