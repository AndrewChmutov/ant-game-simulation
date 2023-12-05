package com.game;

public abstract class Entity {
    protected Node position;
    protected Game game;
    int drawPriority;

    public abstract void draw();

    public Entity(Game game, Node node) {
        this.game = game;
        this.position = node;
        drawPriority = 0;
    }

    public void setDrawPriority(int drawPriority) {
        this.drawPriority = drawPriority;
    }

    public int getDrawPriority() {
        return drawPriority;
    }
}
