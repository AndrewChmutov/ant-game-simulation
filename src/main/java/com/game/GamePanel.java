package com.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

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

    final int fps = 60;

    Thread gameThread;

    ArrayList<Node> nodes;

    public GamePanel() {
        this.setPreferredSize(new Dimension(720, 480));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        // this.addKeyListener(keyHandler);
        this.setFocusable(true);
        nodes = new ArrayList<>();
        for (int j = 0; j < screenCol; j++) {
            for (int i = 0; i < screenRow; i++) {
                nodes.add(new Node(this, i, j));
            }
        }
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        long desiredDelta = 1_000_000_000 / fps;
        long currentDelta;

        while (gameThread != null) {
            long startTime = System.nanoTime();

            update();
            repaint();

            currentDelta = desiredDelta - (System.nanoTime() - startTime);

            if (currentDelta > 0) {
                try {
                    Thread.sleep((int)currentDelta / 1_000_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void update() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        drawGrid(g2);
        drawNodes(g2);
    }

    void drawGrid(Graphics2D g2) {
        g2.setColor(Color.white);
        for (int i = 0; i <= screenCol; i++) {
            g2.drawLine(sidePanelSize + i * tileSize, 0, sidePanelSize + i * tileSize, screenHeight);
        }

        for (int i = 0; i < screenRow; i++) {
            g2.drawLine(sidePanelSize, i * tileSize, screenWidth - sidePanelSize, i * tileSize);
        }
    }

    void drawNodes(Graphics2D g2) {
        for (Node node : nodes) {
            node.draw(g2);
        }
    }
}
