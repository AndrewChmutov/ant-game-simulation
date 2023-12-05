package com.game;

import java.awt.Color;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Ant extends Entity implements Runnable, IMovable {
    enum Team {
        BLUE,
        RED
    }

    String name;
    int strength;
    int health;
    Team color;

    public Ant(Game game, Node position, Team team) {
        super(game, position);
        this.color = team;
        super.setDrawPriority(1);
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
    public void draw() {
        if (System.getProperty("os.name").contains("Linux")) 
            Toolkit.getDefaultToolkit().sync();

        GamePanel panel = game.getGamePanel();
        Color color = Color.white;

        switch (this.color) {
            case RED:
                color = Color.red;
                break;
        
            case BLUE:
                color = Color.blue;
                break;
        }
    
        panel.fillOval(position.getPoint(), color);
    }

    @Override
    public void move() {
        ArrayList<Node> neighbors = position.getNeighbors();

        int nextMove = ThreadLocalRandom.current().nextInt(neighbors.size());

        Node nextPosition = neighbors.get(nextMove);

        game.move(this, position, nextPosition);
        position = nextPosition;
    }
}
