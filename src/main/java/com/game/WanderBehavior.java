package com.game;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class WanderBehavior extends Behavior {
    WanderBehavior(Game game, Behaving behaving) {
        super(game, behaving);
    }

    @Override
    public void activate(String status) {
        Node position = behaving.getPosition();
        ArrayList<Node> neighbors = position.getNeighbors();

        int nextMove = ThreadLocalRandom.current().nextInt(neighbors.size());
        Node nextPosition = neighbors.get(nextMove);

        game.move(behaving, position, nextPosition);
        behaving.setPosition(nextPosition);
    }

}
