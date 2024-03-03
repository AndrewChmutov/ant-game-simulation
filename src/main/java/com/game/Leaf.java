package com.game;

import java.util.ArrayList;

import javax.swing.JLabel;


public class Leaf extends Entity implements IAffecting {
    public Leaf(Game game, Node node) {
        super(game, node);
        super.setDrawPriority(0);
        super.fitTile("leaf");
        setupInfo();
    }

    @Override
    public ArrayList<Entity> affect(ArrayList<Entity> entities) {
        ArrayList<Entity> affected = new ArrayList<Entity>(entities);

        for (Entity e : entities) {
            affected.remove(e);
        }

        return affected;
    }

    @Override
    public void draw() {
        GamePanel panel = game.getGamePanel();

        panel.drawImage(tile, position.getPoint());
    }

    @Override
    public void setupInfo() {
        entityInfo.addComponent(new InfoLabel("Leaf", tile));
    }
}
