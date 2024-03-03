package com.game;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoBundler {
    JPanel panel;

    InfoBundler(JPanel panel) {
        this.panel = panel;
    }

    public void bundle(ArrayList<InfoComponent> infoComponents) {
        synchronized (panel) {
            panel.removeAll();
            for (InfoComponent infoComponent : infoComponents) {
                panel.add(infoComponent.getComponent());
                panel.setVisible(true);
                panel.repaint();
            }
        }
    }
}
