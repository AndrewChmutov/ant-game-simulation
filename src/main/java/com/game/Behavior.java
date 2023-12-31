package com.game;

public abstract class Behavior implements Cloneable {
    Game game;
    Behaving behaving;

    public Behavior(Game game, Behaving behaving) {
        this.game = game;
        this.behaving = behaving;
    }

    public void setSubject(Behaving behaving) {
        this.behaving = behaving;
    }

    @Override
    public Behavior clone(){
        Object obj = null;

        try {
            obj = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return (Behavior)obj;
    }

    public abstract void activate(String status);
}
