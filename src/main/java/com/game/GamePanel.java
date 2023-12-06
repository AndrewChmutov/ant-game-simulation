package com.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
    final int originalTileSize;
    final int scale;
    final int tileSize;;
    
    final int maxX;
    final int maxY;

    final int sidePanelSize;
    final int screenWidth;
    final int screenHeight;

    final int fps = 60;

    Graphics2D g2;
    ArrayList<Node> nodes;
    Game game;

    public GamePanel(Game game, ArrayList<Node> nodes, int maxX, int maxY, int sidePanelSize) {
        originalTileSize = 16;
        scale = 3;
        tileSize = originalTileSize * scale;

        this.maxX = maxX;
        this.maxY = maxY;

        this.sidePanelSize = sidePanelSize;
        screenWidth = tileSize * maxX + sidePanelSize * 2;
        screenHeight = tileSize * maxY;

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
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
        if (System.getProperty("os.name").contains("Linux")) 
            Toolkit.getDefaultToolkit().sync();
        
        super.paintComponent(g);

        g2 = (Graphics2D)g;
        drawGrid();
        drawNodes();
    }

    void fillRect(int x, int y, Color color) {
        if (System.getProperty("os.name").contains("Linux")) 
            Toolkit.getDefaultToolkit().sync();
        
        Point original = getOriginalPoint(new Point(x, y));
        x = (int)original.getX();
        y = (int)original.getY();

        g2.setColor(color);
        g2.fillRect(x, y, tileSize, tileSize);
    }

    void fillRect(Point position, Color color) {
        if (System.getProperty("os.name").contains("Linux")) 
            Toolkit.getDefaultToolkit().sync();
        
        Point original = getOriginalPoint(position);
        int x = (int)original.getX();
        int y = (int)original.getY();

        g2.setColor(color);
        g2.fillRect(x, y, tileSize, tileSize);
    }

    void fillOval(int x, int y, Color color) {
        if (System.getProperty("os.name").contains("Linux")) 
            Toolkit.getDefaultToolkit().sync();
        
        Point original = getOriginalPoint(new Point(x, y));
        x = (int)original.getX();
        y = (int)original.getY();

        g2.setColor(color);
        g2.fillOval(x, y, tileSize, tileSize);
    }

    void fillOval(Point position, Color color) {
        if (System.getProperty("os.name").contains("Linux")) 
            Toolkit.getDefaultToolkit().sync();
        
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
