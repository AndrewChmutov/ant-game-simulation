package com.game;

import java.awt.Color;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Ant extends Behaving implements Runnable {
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
        super.setDrawPriority(1);

        addBehavior(new WanderBehavior(game, this));

        this.color = team;
        health = 10;
    }

    public Ant(Game game, Node position, Team team, ArrayList<Behavior> behaviors) {
        super(game, position);
        super.setDrawPriority(1);

        this.behaviors = behaviors;

        this.color = team;
        health = 10;
    }

    @Override
    public void run() {
        while (health > 0) {
            behave();

            ArrayList<IInteractive> toInterract = new ArrayList<>();

            synchronized (game) {
                ArrayList<Entity> inSameNode = position.getEntities();
                for (Entity e : inSameNode) {
                    if (e instanceof IInteractive)
                        toInterract.add(((IInteractive)e));
                }
            }

            for (IInteractive obj : toInterract)
                obj.interact(this);

            try {
                Thread.sleep(300 + ThreadLocalRandom.current().nextInt(200));
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
    public void setupInfo(InfoBundler infoBundler) {}

    @Override
    public void behave() {
        for (Behavior behavior : behaviors)
            behavior.activate();
    }
}
