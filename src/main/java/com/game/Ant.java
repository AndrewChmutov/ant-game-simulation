package com.game;

import java.awt.Color;
import java.awt.Graphics2D;

public class Ant extends Entity implements Runnable {
    enum Team {
        BLUE,
        RED
    }

    String name;
    int strength;
    int health;
    Team color;

    GamePanel gp;

    public Ant(GamePanel gp, int x, int y) {
        super(x, y);
        this.gp = gp;
    }

    @Override
    public void run() {
        
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.red);
        g2.fillOval(gp.sidePanelSize + x * gp.tileSize, y * gp.tileSize, gp.tileSize, gp.tileSize);
    }
}
