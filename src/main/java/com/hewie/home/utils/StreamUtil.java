package com.hewie.home.utils;

import java.io.*;

public class StreamUtil {
    public static FileInputStream convertToFileInputStream(InputStream inputStream) throws IOException {
        File tempFile = File.createTempFile("temp", ".tmp");
        tempFile.deleteOnExit();

        try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

        return new FileInputStream(tempFile);
    }
}
