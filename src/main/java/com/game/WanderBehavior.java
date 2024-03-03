package com.game;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class WanderBehavior extends Behavior {
    public WanderBehavior(Game game, Behaving behaving) {
        super(game, behaving);
    }

    @Override
    public String activate(String status) {
        if (status.equals("collecting") ||
                status.equals("retreating") ||
                status.equals("retreated")) {
            return status;
        }

        Node position = behaving.getPosition();
        ArrayList<Node> neighbors = position.getNeighbors();

        int nextMove = ThreadLocalRandom.current().nextInt(neighbors.size());
        Node nextPosition = neighbors.get(nextMove);

        synchronized (game) {
            game.move(behaving, position, nextPosition);
            behaving.setPosition(nextPosition);
        }
        return status;
    }

}
