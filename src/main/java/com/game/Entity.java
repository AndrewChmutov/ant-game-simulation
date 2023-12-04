package com.game;

public abstract class Entity {
    protected Node position;
    protected Game game;

    public abstract void draw();

    public Entity(Game game, Node node) {
        this.game = game;
        this.position = node;
    }
}
