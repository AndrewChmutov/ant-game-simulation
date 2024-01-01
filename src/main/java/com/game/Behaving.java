package com.game;

import java.util.ArrayList;

public abstract class Behaving extends Entity {
    protected ArrayList<Behavior> behaviors;
    protected String status;

    Behaving(Game game, Node position) {
        super(game, position);

        behaviors = new ArrayList<>();
    }

    void addBehavior(Behavior behavior) {
        behaviors.add(behavior);
    }

    public abstract void behave();
}
