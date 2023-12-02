package com.main;

import com.game.GameFrame;
import com.game.GamePanel;

public class App 
{
    public static void main(String[] args) {
        GameFrame frame = new GameFrame();
        frame.add(new GamePanel());
        frame.pack();
    }
}
