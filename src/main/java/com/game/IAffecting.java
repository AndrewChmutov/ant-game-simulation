package com.game;

import java.util.ArrayList;


public interface IAffecting {
    /**
     * Affect the view of entities
     * 
     * @param entities - entitities to affect
     * @return affected view
     */
    public ArrayList<Entity> affect(ArrayList<Entity> entities);
}
