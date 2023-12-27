package com.game;

import java.awt.image.BufferedImage;

public abstract class Entity {
    protected Node position;
    protected Game game;
    protected BufferedImage tile;
    int drawPriority;

    public abstract void draw();

    public void setTile(String name, int scale) {
        tile = TileLoader.loadTile(name);
        tile = TileScaler.scale(tile, scale, scale);
    }

    public void fitTile(String name) {
        Settings settings = game.getSettings();

        tile = TileLoader.loadTile(name);
        tile = TileScaler.fit(tile, settings.getTileSize(), settings.getTileSize());
    }

    public void drawTile() {
        GamePanel panel = game.getGamePanel();
        panel.drawImage(tile, position.getPoint());
    }

    public Entity(Game game, Node node) {
        this.game = game;
        this.position = node;
        drawPriority = 0;
    }

    public void setDrawPriority(int drawPriority) {
        this.drawPriority = drawPriority;
    }

    public int getDrawPriority() {
        return drawPriority;
    }

    public Node getPosition() {
        return position;
    }
}
