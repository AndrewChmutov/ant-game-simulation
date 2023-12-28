package com.game;

import java.util.ArrayList;

import javax.swing.JPanel;

public abstract class InfoBundler {
    JPanel panel;

    InfoBundler(JPanel panel) {
        this.panel = panel;
    }

    public void bundle(ArrayList<InfoComponent> infoComponents) {
        for (InfoComponent infoComponent : infoComponents)
            panel.add(infoComponent.getComponent());
    }
}
