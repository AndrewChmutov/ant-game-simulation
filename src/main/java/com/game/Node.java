package com.game;

import java.util.ArrayList;

public class Node {
    int lavrae;
    ArrayList<Entity> entities;
    
    public int getLavraeAmount() {
        return lavrae;
    }

    public int collectLavrae(int capacity) {
        int toCollect = Math.min(lavrae, capacity);
        lavrae -= toCollect;

        return toCollect;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }
}
