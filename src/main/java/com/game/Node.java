package com.game;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Node {
    Point position;
    int lavrae;

    ArrayList<Entity> entities;
    Game game;

    public Node(Game game, int x, int y) {
        position = new Point(x, y);
        entities = new ArrayList<>();
        this.game = game;
        lavrae = ThreadLocalRandom.current().nextInt(0, 100);
    }

    public Node(Game game, int x, int y, int lavrae) {
        position = new Point(x, y);
        entities = new ArrayList<>();
        this.game = game;
        this.lavrae = lavrae;
    }
    
    public int getLavraeAmount() {
        return lavrae;
    }

    public int collectLavrae(int capacity) {
        int toCollect;
        synchronized (game) {
            toCollect = Math.min(lavrae, capacity);
            lavrae -= toCollect;
        }

        return toCollect;
    }

    public synchronized void insertEntity(Entity e) {
        entities.add(e);
    }

    public synchronized void deleteEntity(Entity e) {
        entities.remove(entities.indexOf(e));
    }

    public ArrayList<Entity> getEntities() {
        ArrayList<IAffecting> affecting = new ArrayList<>();

        for (Entity e : entities) {
            if (e instanceof IAffecting) {
                affecting.add((IAffecting)e);
            }
        }

        ArrayList<Entity> tempEntities = new ArrayList<>(entities);
        for (IAffecting a : affecting) {
            tempEntities = a.affect(tempEntities);
        }

        return entities;
    }

    public ArrayList<Node> getNeighbors() {
        ArrayList<Node> neighbors = new ArrayList<>();

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == j)
                    continue;
                
                Node temp = game.getNode((int)position.getX() + i, (int)position.getY() + j);
                if (temp != null)
                    neighbors.add(temp);
            }
        }

        return neighbors;
    }

    public Point getPoint() {
        return position;
    }

    public void draw() {
        int chroma = Math.min((int)(1.0 * lavrae / 100 * 128), 128);

        GamePanel panel = game.getGamePanel();

        panel.fillRect(position, new Color(chroma, chroma, chroma, 128));

        entities.sort((Entity e1, Entity e2) -> {
            return e1.getDrawPriority() - e2.getDrawPriority();
        });

        for (Entity entity : entities) {
            entity.draw();
        }
    }


    public synchronized boolean isEmpty() {
        return entities.isEmpty();
    }
}
