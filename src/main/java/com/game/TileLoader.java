package com.game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TileLoader {
    public static BufferedImage loadTile(String name) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("src/main/resources/" + name + ".png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}
