package com.game;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Game extends Thread {
    GameFrame frame;
    GamePanel panel;
    JPanel mainPanel, leftPanel, rightPanel;
    Settings settings;

    ArrayList<Node> nodes;
    ArrayList<Anthill> anthills;

    public Game() {
        loadSettings();

        nodes = new ArrayList<>();
        anthills = new ArrayList<>();

    }

    public void loadSettings() {
        SettingsLoader settingsLoader;
        settingsLoader = new SettingsLoader();
        settings = settingsLoader.loadSettings("settings"); 

    }
    
    public void createNodes() {
        for (int i = 0; i < settings.getMaxY(); i++) {
            for (int j = 0; j < settings.getMaxX(); j++) {
                Node node = new Node(this, j, i);
                nodes.add(node);
            }
        }
    }

    public void fillNodes() {
        INodeFiller filler = new DefaultNodeFiller(0.1, 0.2);
        
        for (Node node : nodes) {
            filler.fill(node);
        }

    }

    public GamePanel getGamePanel() {
        return panel;
    }

    public Settings getSettings() {
        return settings;
    }

    void setupScreen() {
        assert nodes != null : "Nodes have to be initialized. Call setupField() first";

        frame = new GameFrame();

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

        panel = new GamePanel(this, nodes);

        leftPanel = new JPanel();
        leftPanel.setDoubleBuffered(true);
        leftPanel.setBackground(Color.black);
        leftPanel.setPreferredSize(
            new Dimension(
                settings.getSidePanelSize(),
                settings.getScreenHeight()
            )
        );
        
        rightPanel = new JPanel();
        rightPanel.setDoubleBuffered(true);
        rightPanel.setBackground(Color.black);
        rightPanel.setPreferredSize(
            new Dimension(
                settings.getSidePanelSize(),
                settings.getScreenHeight()
            )
        );

        mainPanel.add(leftPanel);
        mainPanel.add(panel);
        mainPanel.add(rightPanel);

        frame.add(mainPanel);
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

    public Anthill deployAnthill(int x, int y, Ant.Team team,
            ArrayList<ArrayList<Behavior>> antTypes,
            ArrayList<String> antLabels) {
        Node n = getNode(x, y);

        assert n != null : "Invalid node"; 
        assert n.isEmpty() : "Node is not empty";

        Anthill a = new Anthill(this, n, team);
        n.insertEntity(a);
        anthills.add(a);

        return a;
    }


    private void loop() {
        long desiredDelta = 1_000_000_000 / settings.getFps();
        long currentDelta;

        while (true) {
            long startTime = System.nanoTime();
            Toolkit.getDefaultToolkit().sync();
            panel.repaint();
            leftPanel.repaint();
            rightPanel.repaint();

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

    @Override
    public void run() {
        setupScreen();
        createNodes();
        deployAnthill(settings.getMaxY() - 1, 0, Ant.Team.BLUE);
        deployAnthill(0, settings.getMaxY() - 1, Ant.Team.RED);

        anthills.get(0).setupInfo(new InfoBundler(rightPanel));
        anthills.get(1).setupInfo(new InfoBundler(leftPanel)); 
        fillNodes();
        loop();
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
