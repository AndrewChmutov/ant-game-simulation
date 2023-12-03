package com.game;

import java.awt.Graphics2D;

public abstract class Entity {
    protected Node position;

    public abstract void draw(Graphics2D g2);

    public Entity(Node position) {
        this.position = position;
    }
}
