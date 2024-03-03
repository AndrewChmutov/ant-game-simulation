package com.game;

import java.util.ArrayList;

public class RetreatBehavior extends Behavior {
    private ArrayList<Node> savedPositions;

    public RetreatBehavior(Game game, Behaving behaving) {
        super(game, behaving);
    }

    @Override
    public String activate(String status) {
        if (savedPositions == null) {
            savedPositions = new ArrayList<>();
        }
        if (status.equals("retreating")) {
            if (savedPositions.size() > 0) {
                Node pos = savedPositions.get(savedPositions.size() - 1);

                synchronized(game) {
                    game.move(behaving, behaving.getPosition(), pos);
                    behaving.setPosition(pos);
                }
                savedPositions.remove(savedPositions.size() - 1);
            } else {
                status = "retreated";
            }
        } else {
            savedPositions.add(behaving.getPosition());
        }

        return status;
    }
}
