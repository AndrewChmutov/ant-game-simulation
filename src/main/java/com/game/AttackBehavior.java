package com.game;

import java.util.ArrayList;

public class AttackBehavior extends Behavior {
    public AttackBehavior(Game game, Behaving behaving) {
        super(game, behaving);
    }

    @Override
    public String activate(String status) {
        synchronized(game) {
            if (status.equals("retreated")) {
                status = "wandering";
            }
            ArrayList<Entity> neighbors = behaving.getPosition().getEntities();

            for (Entity entity : neighbors) {
                if (behaving instanceof Ant && entity instanceof Ant) {
                    Ant ant = (Ant)behaving;
                    Ant other = (Ant)entity;

                    if (ant.getColor() != other.getColor()) {
                        other.getDamage(ant.getStrength());
                        for (Behavior b : behaving.getBehaviors()) {
                            if (b instanceof RetreatBehavior) {
                                status = "retreating";
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        }
        return status;
    }
}
