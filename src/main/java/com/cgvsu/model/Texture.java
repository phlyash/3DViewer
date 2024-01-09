package com.cgvsu.model;

import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Texture {
    private final Color[][] pixels;
    public Texture(String file) throws IOException {
        var image = ImageIO.read(new File(file));
        pixels = new Color[image.getWidth()][image.getHeight()];
        int rgb;
        for(int i = 0; i < image.getWidth(); i++) {
            for(int j = 0; j < image.getHeight(); j++) {
                rgb = image.getRGB(i, j);
                pixels[i][j] = new Color(((rgb >> 16) & 0xFF) / 256f, ((rgb >> 8) & 0xFF) / 256f,
                        (rgb & 0xFF) / 256f, ((rgb >> 24) & 0xFF) / 256f);
            }
        }
    }

    public Texture(Color color) {
        pixels = new Color[1][1];
        pixels[0][0] = color;
    }

    public Color getPixel(int x, int y) {
        return pixels[x][y];
    }

    public int getWidth() {
        return pixels.length;
    }

    public int getHeight() {
        return pixels[0].length;
    }
}
