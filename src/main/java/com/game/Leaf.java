package com.game;

import java.util.ArrayList;

public class Leaf extends Entity implements IAffecting {
    public Leaf(Game game, Node node) {
        super(game, node);
        super.setDrawPriority(0);
        super.setTile("leaf", 3);
    }

    @Override
    public ArrayList<Entity> affect(ArrayList<Entity> entities) {
        ArrayList<Entity> affected = new ArrayList<Entity>(entities);

        for (Entity e : entities) {
            if (e instanceof IMovable) {
                affected.remove(e);
            }
        }

        return affected;
    }

    @Override
    public void draw() {
        GamePanel panel = game.getGamePanel();

        panel.drawImage(tile, position.getPoint());
    }
    
}
