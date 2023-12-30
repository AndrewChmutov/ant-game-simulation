package com.game;

public abstract class Behavior {
    Game game;
    Behaving behaving;

    public Behavior(Game game, Behaving behaving) {
        this.game = game;
        this.behaving = behaving;
    }

    public abstract void activate(String status);
}
