package project.mediavault.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.mediavault.MediaVaultApplication;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;

/**
 * File Upload Service.
 */
@Service
public class FileService {

    private static final String BASE_DIR = MediaVaultApplication.BASE_DIR + "/data/";

    private SecureRandom random = new SecureRandom();
    private String fileNameRandomSeed() {
        return new BigInteger(130, random).toString(32);
    }

    public String writeFile(MultipartFile file)  {
        String fileName = fileNameRandomSeed() + "-" + file.getOriginalFilename();
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(BASE_DIR + fileName, "rw")) {
            FileChannel channel = randomAccessFile.getChannel();
            byte[] bytes = file.getBytes();
            ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
            byteBuffer.clear();
            byteBuffer.put(bytes);
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                channel.write(byteBuffer);
            }

            return "/user-data/" + fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteFile(String fileName) throws IOException {
        String absoluteFilePath = BASE_DIR + fileName;
        Path path = Paths.get(absoluteFilePath);

        if (Files.exists(path)) {
            Files.delete(path);
        }
    }

}
