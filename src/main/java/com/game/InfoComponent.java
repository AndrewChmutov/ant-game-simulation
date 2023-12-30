package com.game;

import java.awt.Component;

public abstract class InfoComponent {
    Component component;

    InfoComponent() {}

    InfoComponent(Component component) {
        setComponent(component);
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    abstract void updateComponent();

    void draw() {
        if (component != null)
            component.repaint();
    }

    public Component getComponent() {
        return component;
    }
}
