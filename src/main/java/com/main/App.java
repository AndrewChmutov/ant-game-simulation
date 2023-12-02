package com.main;

import com.game.GameFrame;
import com.game.GamePanel;

public class App 
{
    public static void main(String[] args) {
        GameFrame frame = new GameFrame();
        GamePanel panel = new GamePanel();

        frame.add(panel);
        frame.pack();

        panel.startGameThread();
    }
}
