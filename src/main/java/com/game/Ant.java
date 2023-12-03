package com.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class Ant extends Entity implements Runnable {
    enum Team {
        BLUE,
        RED
    }

    String name;
    int strength;
    int health;
    Team color;

    public Ant(Node position) {
        super(position);
    }

    @Override
    public void run() {
        
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.red);

        Point originalPosition = position.getWindowCoords();

        g2.fillOval(
            (int)originalPosition.getX(), 
            (int)originalPosition.getY(), 
            position.getTileSize(),
            position.getTileSize()
        );
    }
}
