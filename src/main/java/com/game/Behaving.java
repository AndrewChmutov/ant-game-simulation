package com.game;

import java.util.ArrayList;

public abstract class Behaving extends Entity {
    protected ArrayList<Behavior> behaviors;
    protected String status;

    Behaving(Game game, Node node) {
        super(game, node);

        behaviors = new ArrayList<>();
    }

    void addBehavior(Behavior behavior) {
        behaviors.add(behavior);
    }

    public abstract void behave();
}
