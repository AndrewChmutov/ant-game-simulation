package com.game;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

public class Stone extends Entity implements IInteractive {
    public Stone(Game game, Node node) {
        super(game, node);
    }

    @Override
    public void interact(Entity e) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(200));
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void draw() {
        GamePanel panel = game.getGamePanel();

        panel.fillOval(position.getPoint(), Color.yellow);
    }
    
}
