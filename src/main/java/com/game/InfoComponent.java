package com.game;

import java.awt.Component;

public class InfoComponent {
    protected Component component;

    public InfoComponent() {}

    public InfoComponent(Component component) {
        setComponent(component);
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public void updateComponent() {};

    public void draw() {
        if (component != null)
            component.repaint();
    }

    public Component getComponent() {
        return component;
    }
}
