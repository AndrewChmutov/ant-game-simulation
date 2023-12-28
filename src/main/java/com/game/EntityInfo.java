package com.game;

import java.util.ArrayList;

import javax.swing.JPanel;

public abstract class EntityInfo {
    protected ArrayList<InfoComponent> infoComponents;

    EntityInfo() {
        infoComponents = new ArrayList<>();
    }

    public void addComponent(InfoComponent component) {
        infoComponents.add(component);
    }

    public abstract void pushToPanel(JPanel panel);
}
