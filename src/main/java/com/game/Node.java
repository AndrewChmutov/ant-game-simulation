package com.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Node {
    Point position;
    int lavrae;

    ArrayList<Entity> entities;
    GamePanel gp;

    public Node(GamePanel gp, int x, int y) {
        position = new Point(x, y);
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

    public synchronized void insertEntity(Entity e) {
        entities.add(e);
    }

    public synchronized void deleteEntity(Entity e) {
        entities.remove(entities.indexOf(e));
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public Point getWindowCoords() {
        return gp.getWindowCoords(position);
    }

    public int getTileSize() {
        return gp.getTileSize();
    }

    public ArrayList<Node> getNeighbors() {
        ArrayList<Node> neighbors = new ArrayList<>();

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == j)
                    continue;
                
                Node temp = gp.getNode((int)position.getX() + i, (int)position.getY() + j);
                if (temp != null)
                    neighbors.add(temp);
            }
        }

        return neighbors;
    }

    public synchronized void draw(Graphics2D g2) {
        int chroma = Math.min((int)(1.0 * lavrae / 100 * 128), 128);
        g2.setColor(new Color(255, 255, 255, chroma));

        Point pointToPlot = getWindowCoords();

        g2.fillRect(pointToPlot.x, pointToPlot.y, getTileSize(), getTileSize());

        for (Entity entity : entities) {
            entity.draw(g2);
        }
    }
}
