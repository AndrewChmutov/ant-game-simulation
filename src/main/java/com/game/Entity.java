package com.game;

import java.awt.Graphics2D;

public abstract class Entity {
    protected int x;
    protected int y;

    public abstract void draw(Graphics2D g2);

    Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
