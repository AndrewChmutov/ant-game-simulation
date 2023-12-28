package com.game;

import java.awt.Component;

public abstract class InfoComponent {
    Component component;

    InfoComponent(Component component) {
        this.component = component;
    }

    abstract void updateComponent();

    void draw() {
        component.repaint();
    }

    public Component getComponent() {
        return component;
    }
}
