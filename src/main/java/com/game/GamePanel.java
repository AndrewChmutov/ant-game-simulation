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
    private Graphics2D g2;
    private ArrayList<Node> nodes;
    private Game game;

    public GamePanel(Game game, ArrayList<Node> nodes) {
        this.game = game;
        this.nodes = nodes;
        Settings settings = game.getSettings();

        this.setPreferredSize(
            new Dimension(
                settings.getMaxX() * settings.getTileSize(),
                settings.getMaxY() * settings.getTileSize()
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
        Settings settings = new Settings();
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
        Settings settings = new Settings();
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

    void drawImageTruePosition(BufferedImage image, Point truePosition) {
        int x = (int)truePosition.getX();
        int y = (int)truePosition.getY();
        g2.drawImage(image, x, y, null);
    }

    void drawGrid() {
        Settings settings = new Settings();
        g2.setColor(Color.white);
        for (int i = 0; i <= settings.getMaxX(); i++) {
            g2.drawLine(i * settings.getTileSize(), 0, i * settings.getTileSize(), settings.getScreenHeight());
        }

        for (int i = 0; i <= settings.getMaxY(); i++) {
            g2.drawLine(0, i * settings.getTileSize(), settings.getMaxX() * settings.getTileSize(), i * settings.getTileSize());
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
        Settings settings = new Settings();
        return new Point((int)p.getX() * settings.getTileSize(), (int)p.getY() * settings.getTileSize());
    }
}
