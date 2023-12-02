package com.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 16;
    final int scale = 3;
    
    public final int tileSize = originalTileSize * scale;

    final int screenCol = 10;
    final int screenRow = 10;
    final int sidePanelSize = 120;
    final int screenWidth = tileSize * screenCol + sidePanelSize * 2;
    final int screenHeight = tileSize * screenRow;

    Thread gameThread;

    public GamePanel() {
        this.setPreferredSize(new Dimension(720, 480));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        // this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (gameThread != null) {
            update();
            repaint();
        }
    }

    public void update() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        printGrid(g2);
    }

    void printGrid(Graphics2D g2) {
        for (int i = 0; i <= screenCol; i++) {
            g2.drawLine(sidePanelSize + i * tileSize, 0, sidePanelSize + i * tileSize, screenHeight);
        }

        for (int i = 0; i < screenRow; i++) {
            g2.drawLine(sidePanelSize, i * tileSize, screenWidth - sidePanelSize, i * tileSize);
        }
    }
}
