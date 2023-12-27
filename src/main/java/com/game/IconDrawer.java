package com.game;

import java.awt.image.BufferedImage;

public abstract class IconDrawer {
    protected Game game;

    IconDrawer(Game game) {
        this.game = game;
    }

    abstract void draw(BufferedImage image);
}
