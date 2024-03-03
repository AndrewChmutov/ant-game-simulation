package com.game;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

/**
 * The {@code TileScaler} class provides utility methods for scaling and fitting {@code BufferedImage} tiles.
 *
 * <p>This class contains methods for scaling an image with a specified width and height ratio using the
 * {@code scale} method, as well as fitting an image within specified width and height bounds using the
 * {@code fit} method.
 *
 */
public class TileScaler {
    /**
     * Scales the given image with the specified width and height ratios.
     *
     * @param image       The {@code BufferedImage} to be scaled.
     * @param scaleWidth  The ratio by which to scale the image width.
     * @param scaleHeight The ratio by which to scale the image height.
     * @return The scaled {@code BufferedImage} representing the image after scaling.
     */
    public static BufferedImage scale(BufferedImage image, double scaleWidth, double scaleHeight) {
        BufferedImage bi = new BufferedImage(
            (int)(scaleWidth * image.getWidth(null)),
            (int)(scaleHeight * image.getHeight(null)),
            BufferedImage.TYPE_INT_ARGB);

        Graphics2D grph = (Graphics2D) bi.getGraphics();
        grph.scale(scaleWidth, scaleHeight);

        grph.drawImage(image, 0, 0, null);
        grph.dispose();

        return bi;
    }

    /**
     * Fits the given image within specified width and height bounds.
     *
     * @param image       The {@code BufferedImage} to be fitted.
     * @param widthBound  The maximum width for the fitted image.
     * @param heightBound The maximum height for the fitted image.
     * @return The fitted {@code BufferedImage} representing the image after fitting.
     */
    public static BufferedImage fit(BufferedImage image, int widthBound, int heightBound) {
        double scaleWidth = (double)widthBound / image.getWidth();
        double scaleHeight = (double)heightBound / image.getHeight();

        return scale(image, scaleWidth, scaleHeight);
    }
}
