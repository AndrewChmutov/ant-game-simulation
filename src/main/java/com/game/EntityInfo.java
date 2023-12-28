package com.game;

import java.util.ArrayList;


public class EntityInfo {
    protected ArrayList<InfoComponent> infoComponents;

    EntityInfo() {
        infoComponents = new ArrayList<>();
    }

    public void addComponent(InfoComponent component) {
        infoComponents.add(component);
    }

    public void updateComponents() {
        for (InfoComponent infoComponent : infoComponents)
            infoComponent.updateComponent();
    }

    public ArrayList<InfoComponent> getComponents() {
        return infoComponents;
    }
}
