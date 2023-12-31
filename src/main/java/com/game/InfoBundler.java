package com.game;

import java.util.ArrayList;

import javax.swing.JPanel;

public class InfoBundler {
    JPanel panel;

    InfoBundler(Game game, JPanel panel) {
        this.panel = panel;
    }

    public void bundle(ArrayList<InfoComponent> infoComponents) {
        for (InfoComponent infoComponent : infoComponents)
            panel.add(infoComponent.getComponent());
    }
}
