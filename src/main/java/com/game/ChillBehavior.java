package com.game;

import java.util.concurrent.ThreadLocalRandom;

public class ChillBehavior extends Behavior {
    public ChillBehavior(Game game, Behaving behaving) {
        super(game, behaving);
    }

    @Override
    public String activate(String status) {
        double speed = game
            .getSettings()
            .getSpeed();

        int toSleep = (int)(150 * speed);
        if (!status.equals("retreating"))
            toSleep += 100;
            toSleep += ThreadLocalRandom.current().nextInt((int)(200 * speed));

        try {
            Thread.sleep(toSleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return status;
    }

}
