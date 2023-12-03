package com.game;

import java.awt.Graphics2D;

public abstract class Entity {
    enum Type {
        MOVABLE,
        STATIC
    }

    protected Node position;
    Type t;

    public abstract void draw(Graphics2D g2);

    public Entity(Node position, Type t) {
        this.position = position;
        this.t = t;
    }

    public Type getType() {
        return t;
    }
}
