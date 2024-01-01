package com.game;

import java.util.concurrent.ThreadLocalRandom;

public class ChillBehavior extends Behavior {
    ChillBehavior(Game game, Behaving behaving) {
        super(game, behaving);
    }

    @Override
    public void activate() {
        try {
            Thread.sleep(300 + ThreadLocalRandom.current().nextInt(200));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
