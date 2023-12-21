package com.game;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

public class Stone extends Entity implements IInteractive {
    public Stone(Game game, Node node) {
        super(game, node);
        super.setDrawPriority(3);
    }

    @Override
    public void interact(Entity e) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(400) + 300);
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
