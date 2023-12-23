package com.game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TileLoader {
    public static BufferedImage loadTile(String name, int scale) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("src/main/resources/" + name + ".png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        BufferedImage bi = new BufferedImage(scale * image.getWidth(null), scale * image.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics2D grph = (Graphics2D) bi.getGraphics();
        grph.scale(scale, scale);

        grph.drawImage(image, 0, 0, null);
        grph.dispose();
        
        return bi;
    }
}
