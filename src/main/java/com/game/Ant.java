package com.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Random;

public class Ant extends Entity implements Runnable, Movable {
    enum Team {
        BLUE,
        RED
    }

    String name;
    int strength;
    int health;
    Team color;

    public Ant(Node position, Team team) {
        super(position, Entity.Type.MOVABLE);
        this.color = team;
        health = 10;
    }

    @Override
    public void run() {
        while (health > 0) {
            move();

            Random random = new Random();
            try {
                Thread.sleep(300 + random.nextInt(200));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public synchronized void draw(Graphics2D g2) {
        if (System.getProperty("os.name").contains("Linux")) 
            Toolkit.getDefaultToolkit().sync();

        switch (color) {
            case RED:
                g2.setColor(Color.red);
                break;
        
            case BLUE:
                g2.setColor(Color.blue);
                break;
        }

        Point originalPosition = position.getWindowCoords();

        g2.fillOval(
            (int)originalPosition.getX(), 
            (int)originalPosition.getY(), 
            position.getTileSize(),
            position.getTileSize()
        );
    }

    @Override
    public synchronized void move() {
        ArrayList<Node> neighbors = position.getNeighbors();

        Random random = new Random();
        int nextMove = random.nextInt(neighbors.size());

        Node nextPosition = neighbors.get(nextMove);

        synchronized(position) {
            synchronized(nextPosition) {
                position.deleteEntity(this);
                nextPosition.insertEntity(this);
                position = nextPosition;
            }
        }
    }
}
