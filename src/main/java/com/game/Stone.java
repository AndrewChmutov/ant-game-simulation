package com.game;

import java.util.concurrent.ThreadLocalRandom;

public class Stone extends Entity implements IInteractive {
    public Stone(Game game, Node position) {
        super(game, position);
        super.setDrawPriority(3);
        super.fitTile("stone");
        setupInfo();
    }

    @Override
    public void interact(Entity entity) {
        double speed = game
            .getSettings()
            .getSpeed();

        try {
            Thread.sleep(
                ThreadLocalRandom
                    .current()
                    .nextInt((int)(speed * 400)) + (int)(speed * 300));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw() {
        GamePanel panel = game.getGamePanel();

        panel.drawImage(tile, position.getPoint());
    }

    @Override
    public void setupInfo() {
        entityInfo.addComponent(new InfoLabel("stone", tile));
    }

}
