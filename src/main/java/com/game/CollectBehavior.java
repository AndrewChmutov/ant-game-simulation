package com.game;

import java.util.concurrent.atomic.AtomicInteger;

public class CollectBehavior extends Behavior {
    private AtomicInteger larvae;

    public CollectBehavior(Game game, Behaving behaving) {
        super(game, behaving);
        larvae = new AtomicInteger(0);
    }


    @Override
    public String activate(String status) {
        synchronized(game) {
            if (status.equals("collecting") || status.equals("wandering")) {
                int capacity = 0;
                if (behaving instanceof Ant) {
                    capacity = ((Ant)behaving).getStrength();
                }
                int tempLarvae = behaving.getPosition().collectLarvae(capacity - larvae.get());
                if (tempLarvae <= 0) {
                    if (capacity <= larvae.get())
                        return "retreating";
                    else {
                        return "wandering";
                    }
                } else {
                    larvae.set(larvae.get() + tempLarvae);
                    return "collecting";
                }
            } else if (status.equals("retreated")) {
                Node pos = behaving.getPosition();
                for (Entity entity : pos.getEntities()) {
                    if (entity instanceof Anthill) {
                        Anthill anthill = (Anthill)entity;
                        anthill.putLarvae(larvae.get());
                        larvae.set(0);
                        break;
                    }
                }
                status = "wandering";
            }
        }
        return status;
    }


    public int getAndSetZero() {
        int tempLarvae = larvae.get();
        larvae.set(0);
        return tempLarvae;
    }


    public AtomicInteger getLarvaeView() {
        return larvae;
    }
}
