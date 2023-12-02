package com.game;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
    public GamePanel() {
        this.setPreferredSize(new Dimension(720, 480));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        // this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }
}
