package com.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
    final int originalTileSize = 16;
    final int scale = 3;
    
    final int tileSize = originalTileSize * scale;

    final int maxX = 10;
    final int maxY = 10;
    final int sidePanelSize = 120;
    final int screenWidth = tileSize * maxX + sidePanelSize * 2;
    final int screenHeight = tileSize * maxY;

    final int fps = 60;
    Graphics2D g2;

    ArrayList<Node> nodes;
    Game game;

    public GamePanel(Game game, ArrayList<Node> nodes) {
        this.setPreferredSize(new Dimension(720, 480));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        // this.addKeyListener(keyHandler);
        this.setFocusable(true);
        this.nodes = nodes;
        this.game = game;
    }

    public int getFps() {
        return fps;
    };

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g2 = (Graphics2D)g;
        drawGrid();
        drawNodes();
    }

    void fillRect(int x, int y, Color color) {
        Point original = getOriginalPoint(new Point(x, y));
        x = (int)original.getX();
        y = (int)original.getY();

        g2.setColor(color);
        g2.fillRect(x, y, tileSize, tileSize);
    }

    void fillRect(Point position, Color color) {
        Point original = getOriginalPoint(position);
        int x = (int)original.getX();
        int y = (int)original.getY();

        g2.setColor(color);
        g2.fillRect(x, y, tileSize, tileSize);
    }

    void fillOval(int x, int y, Color color) {
        Point original = getOriginalPoint(new Point(x, y));
        x = (int)original.getX();
        y = (int)original.getY();

        g2.setColor(color);
        g2.fillOval(x, y, tileSize, tileSize);
    }

    void fillOval(Point position, Color color) {
        Point original = getOriginalPoint(position);
        int x = (int)original.getX();
        int y = (int)original.getY();

        g2.setColor(color);
        g2.fillOval(x, y, tileSize, tileSize);
    }

    void drawGrid() {
        g2.setColor(Color.white);
        for (int i = 0; i <= maxX; i++) {
            g2.drawLine(sidePanelSize + i * tileSize, 0, sidePanelSize + i * tileSize, screenHeight);
        }

        for (int i = 0; i < maxY; i++) {
            g2.drawLine(sidePanelSize, i * tileSize, screenWidth - sidePanelSize, i * tileSize);
        }
    }

    void drawNodes() {
        synchronized (game) {
            for (Node node : nodes) {
                node.draw();
            }
        }
    }

    public Point getOriginalPoint(Point p) {
        return new Point(sidePanelSize + (int)p.getX() * tileSize, (int)p.getY() * tileSize);
    }

    public int getTileSize() {
        return tileSize;
    }
}
