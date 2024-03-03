package com.game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * The {@code TileLoader} class provides a utility for loading image tiles from the resources directory.
 *
 * <p>This class allows loading image tiles using the {@code loadTile} method. The images are expected to be
 * located in the "src/main/resources/" directory and named with the specified name and ".png" extension.
 *
 */
public class TileLoader {

    /**
     * Loads an image tile with the specified name from the resources directory.
     *
     * @param name The name of the image tile (excluding extension).
     * @return The loaded {@code BufferedImage} representing the image tile, or {@code null} if the image
     *         could not be loaded.
     */
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
