package com.game;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
    public GameFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setTitle("title");
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
