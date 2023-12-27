package com.game;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class TileScaler {
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

    public static BufferedImage fit(BufferedImage image, int widthBound, int heightBound) {
        double scaleWidth = (double)widthBound / image.getWidth();
        double scaleHeight = (double)heightBound / image.getHeight();

        return scale(image, scaleWidth, scaleHeight);
    }
}
