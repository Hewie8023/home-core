package com.hewie.home.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ColorUtil {
    public static String getColor(MultipartFile multipartFile) {
        try {
            File file = convertMultipartFileToFile(multipartFile);
            BufferedImage image = ImageIO.read(file);

            int[] colorCount = new int[256 * 256 * 256];

            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int rgb = image.getRGB(x, y);
                    int red = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8) & 0xFF;
                    int blue = rgb & 0xFF;
                    int index = (red << 16) + (green << 8) + blue;
                    colorCount[index]++;
                }
            }

            int maxCount = 0;
            int mainColor = 0;

            for (int i = 0; i < colorCount.length; i++) {
                if (colorCount[i] > maxCount) {
                    maxCount = colorCount[i];
                    mainColor = i;
                }
            }

            int red = (mainColor >> 16) & 0xFF;
            int green = (mainColor >> 8) & 0xFF;
            int blue = mainColor & 0xFF;
            System.out.println("Main color: RGB(" + red + "," + green + "," + blue + ")");
            return "rgb(" + red + "," + green + "," + blue + ")";
        } catch (IOException e) {
            e.printStackTrace();
            return "rgb(0, 0, 0)";
        }
    }

    public static File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            System.out.println("File is empty or its size is zero, please check!");
        } else {
            InputStream inputStream = file.getInputStream();
            convFile = new File(file.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(convFile);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            fos.close();
            inputStream.close();
        }
        return convFile;
    }
}
