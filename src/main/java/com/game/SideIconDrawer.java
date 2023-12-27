package com.game;

import java.awt.Point;
import java.awt.image.BufferedImage;

public class SideIconDrawer extends IconDrawer {
    Direction direction;

    enum Direction {
        NorthEast,
        NorthWest,
        SouthEast,
        SouthWest
    }

    SideIconDrawer(Game game) {
        this(game, Direction.SouthWest);
    }

    SideIconDrawer(Game game, Direction direction) {
        super(game);
        this.direction = direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    Point getPosition() {
        GamePanel gamePanel = game.getGamePanel();
        Settings settings = game.getSettings();
        Point result;
        int shiftX = 0;
        int shiftY = 0;

        Node node;
        switch (direction) {
            case NorthWest:
                node = game.getNode(0, 0);
                shiftX = -2 * settings.getTileSize(); 
                break;

            case NorthEast:
                node = game.getNode(settings.getMaxX() - 1, 0);
                shiftX = settings.getTileSize(); 
                break;

            case SouthEast:
                node = game.getNode(settings.getMaxX() - 1, settings.getMaxY() - 1);
                shiftX = settings.getTileSize(); 
                shiftY = - settings.getTileSize();
                break;

            default:
            case SouthWest:
                node = game.getNode(0, settings.getMaxY() - 1);
                shiftX = - 2 * settings.getTileSize(); 
                shiftY = - settings.getTileSize();
                break;
        }

        result = gamePanel.getOriginalPoint(node.getPoint());
        result = new Point((int)result.getX() + shiftX, (int)result.getY() + shiftY);

        return result;
    }

    @Override
    void draw(BufferedImage image) {
        GamePanel gamePanel = game.getGamePanel();
        Point truePosition = getPosition();


        gamePanel.drawImageTruePosition(image, truePosition);
    }

}
