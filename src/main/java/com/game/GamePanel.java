package com.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
    Graphics2D g2;
    ArrayList<Node> nodes;
    Settings settings;
    Game game;

    public GamePanel(Game game, ArrayList<Node> nodes) {
        this.game = game;
        this.nodes = nodes;
        settings = game.getSettings();

        this.setPreferredSize(
            new Dimension(
                settings.getScreenWidth(), 
                settings.getScreenHeight()
            )
        );

        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        // this.addKeyListener(keyHandler);
        this.setFocusable(true);
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
        fillRect(new Point(x, y), color);
    }

    void fillRect(Point position, Color color) {
        if (System.getProperty("os.name").contains("Linux")) 
            Toolkit.getDefaultToolkit().sync();
        
        Point original = getOriginalPoint(position);
        int x = (int)original.getX();
        int y = (int)original.getY();

        g2.setColor(color);
        g2.fillRect(x, y, settings.getTileSize(), settings.getTileSize());
    }

    void fillOval(int x, int y, Color color) {
        fillOval(new Point(x, y), color);
    }

    void fillOval(Point position, Color color) {
        if (System.getProperty("os.name").contains("Linux")) 
            Toolkit.getDefaultToolkit().sync();
        
        Point original = getOriginalPoint(position);
        int x = (int)original.getX();
        int y = (int)original.getY();

        g2.setColor(color);
        g2.fillOval(x, y, settings.getTileSize(), settings.getTileSize());
    }

    void drawImage(BufferedImage image, Point position) {
        if (System.getProperty("os.name").contains("Linux")) 
            Toolkit.getDefaultToolkit().sync();
        
        Point original = getOriginalPoint(position);
        int x = (int)original.getX();
        int y = (int)original.getY();
        // System.out.println(image.getWidth());

        g2.drawImage(image, x, y, null);
    }

    void drawGrid() {
        g2.setColor(Color.white);
        for (int i = 0; i <= settings.getMaxX(); i++) {
            g2.drawLine(settings.getSidePanelSize() + i * settings.getTileSize(), 0, settings.getSidePanelSize() + i * settings.getTileSize(), settings.getScreenHeight());
        }

        for (int i = 0; i < settings.getMaxY(); i++) {
            g2.drawLine(settings.getSidePanelSize(), i * settings.getTileSize(), settings.getScreenWidth() - settings.getSidePanelSize(), i * settings.getTileSize());
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
        return new Point(settings.getSidePanelSize() + (int)p.getX() * settings.getTileSize(), (int)p.getY() * settings.getTileSize());
    }
}
