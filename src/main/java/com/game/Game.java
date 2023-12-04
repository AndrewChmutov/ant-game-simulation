package com.game;

import java.util.ArrayList;

public class Game extends Thread {
    GameFrame frame;
    GamePanel panel;

    final int maxX, maxY;
    
    ArrayList<Node> nodes;
    ArrayList<Anthill> anthills;

    public Game(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;

        setupField();
        setupScreen();
    }
    
    public void setupField() {
        anthills = new ArrayList<>();
        nodes = new ArrayList<>();
        
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                nodes.add(new Node(this, j, i));
            }
        }
    }

    public GamePanel getGamePanel() {
        return panel;
    }

    void setupScreen() {
        frame = new GameFrame();
        panel = new GamePanel(this, nodes, maxX, maxY, 120);

        frame.add(panel);
        frame.pack();
    }

    public Anthill deployAnthill(int x, int y, Ant.Team team) {
        Node n = getNode(x, y);
        Anthill a = new Anthill(this, n, team);
        n.insertEntity(a);
        anthills.add(a);

        return a;
    }

    void initAnts() {
        for (int i = 0; i < anthills.size(); i++) {
            for (int j = 0; j < 3; j++) {
                anthills.get(i).produceAnt();
            }
        }
    }

    @Override
    public void run() {
        deployAnthill(maxX - 1, 0, Ant.Team.BLUE);
        deployAnthill(0, maxY - 1, Ant.Team.RED);
        initAnts();

        long desiredDelta = 1_000_000_000 / panel.getFps();
        long currentDelta;

        while (true) {
            long startTime = System.nanoTime();

            panel.repaint();

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

    public Node getNode(int x, int y) {
        if (x < maxX && y < maxY && x >= 0 && y >= 0)
            return nodes.get(y * maxX + x);

        return null;
    }

    public synchronized void move(Entity e, Node n1, Node n2) {
        n1.deleteEntity(e);
        n2.insertEntity(e);
    }
}
