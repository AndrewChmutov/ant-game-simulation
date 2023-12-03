package com.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Node {
    int x, y;
    int lavrae;

    ArrayList<Entity> entities;
    GamePanel gp;

    public Node(GamePanel gp, int x, int y) {
        this.x = x;
        this.y = y;
        entities = new ArrayList<>();
        this.gp = gp;
        lavrae = ThreadLocalRandom.current().nextInt(0, 100);
    }
    
    public int getLavraeAmount() {
        return lavrae;
    }

    public int collectLavrae(int capacity) {
        int toCollect = Math.min(lavrae, capacity);
        lavrae -= toCollect;

        return toCollect;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void draw(Graphics2D g2) {
        int chroma = Math.min((int)(1.0 * lavrae / 100 * 128), 128);
        g2.setColor(new Color(255, 255, 255, chroma));

        g2.fillRect(gp.sidePanelSize + x * gp.tileSize, y * gp.tileSize, gp.tileSize, gp.tileSize);

        for (Entity entity : entities) {
            entity.draw(g2);
        }
    }
}
