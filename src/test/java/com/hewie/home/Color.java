package com.hewie.home;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Color {
    public static void main(String[] args) {
        try {
            File file = new File("/Users/hewie/IdeaProjects/home/D25A8714.jpg");
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
