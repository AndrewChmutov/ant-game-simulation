package com.game;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class BlundererBehavior extends Behavior {
    private double prob;

    public BlundererBehavior(Game game, Behaving behaving, AtomicInteger larvae, double prob) {
        super(game, behaving);
    }

    @Override
    public String activate(String status) {
        if (status == "retreating" && ThreadLocalRandom.current().nextDouble(1.0) < 0.2) {
            if (behaving instanceof Ant) {
                Ant ant = (Ant)behaving;
                ant.dropLarvae();
            }
            return "wandering";
        }

        return status;
    }

    public void setProbability(double prob) {
        this.prob = prob;
    }

    public double getProbability() {
        return prob;
    }
}
