package com.game;

import java.util.ArrayList;
import java.awt.Toolkit;

public class Game extends Thread {
    GameFrame frame;
    GamePanel panel;
    Settings settings;

    ArrayList<Node> nodes;
    ArrayList<Anthill> anthills;

    public Game() {
        System.out.println("kek1");
        SettingsLoader settingsLoader;
        System.out.println("kek2");
        settingsLoader = new SettingsLoader();

        System.out.println("kek3");
        settings = new Settings();
        settingsLoader.dumpSettings("settings", settings);
        settings = settingsLoader.loadSettings("settings"); 

        setupField();
        setupScreen();
    }
    
    public void setupField() {
        anthills = new ArrayList<>();
        nodes = new ArrayList<>();
        INodeFiller filler = new DefaultNodeFiller(0.05, 0.05);
        
        for (int i = 0; i < settings.getMaxY(); i++) {
            for (int j = 0; j < settings.getMaxX(); j++) {
                Node node = new Node(this, j, i);
                filler.fill(node);
                nodes.add(node);
            }
        }
    }

    public GamePanel getGamePanel() {
        return panel;
    }

    void setupScreen() {
        assert nodes != null : "Nodes have to be initialized. Call setupField() first";

        frame = new GameFrame();
        panel = new GamePanel(this, nodes, settings.getMaxX(), settings.getMaxY(), 120);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public Anthill deployAnthill(int x, int y, Ant.Team team) {
        Node n = getNode(x, y);

        assert n != null : "Invalid node"; 
        assert n.isEmpty() : "Node is not empty";

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
        deployAnthill(settings.getMaxY() - 1, 0, Ant.Team.BLUE);
        deployAnthill(0, settings.getMaxY() - 1, Ant.Team.RED);
        initAnts();

        long desiredDelta = 1_000_000_000 / panel.getFps();
        long currentDelta;

        while (true) {
            long startTime = System.nanoTime();
            Toolkit.getDefaultToolkit().sync();
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
        if (x < settings.getMaxX() && y < settings.getMaxY() && x >= 0 && y >= 0)
            return nodes.get(y * settings.getMaxX() + x);

        return null;
    }

    public synchronized void move(Entity e, Node n1, Node n2) {
        n1.deleteEntity(e);
        n2.insertEntity(e);
    }
}
