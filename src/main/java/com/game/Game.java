package com.game;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Game extends Thread {
    private GameFrame frame, inspectFrame;
    private GamePanel panel;
    private JPanel mainPanel, leftPanel, rightPanel, inspectPanel;
    private Settings settings;

    private ArrayList<Node> nodes;
    private ArrayList<Anthill> anthills;

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
        INodeFiller filler = new DefaultNodeFiller(0.1, 0.2, this);
        
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

    private void setupScreen() {
        assert nodes != null : "Nodes have to be initialized. Call setupField() first";

        frame = new GameFrame();
        frame.setTitle("Ant Colony Simulation");

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

        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                synchronized(this) {
                    int x, y;
                    x = e.getX() / settings.getTileSize();
                    y = e.getY() / settings.getTileSize();

                    if (x >= 0 && x < settings.getMaxX() &&
                            y >= 0 && y < settings.getMaxY()) {
                        ArrayList<Entity> entities = getNode(x, y)
                                                        .getEntities();

                        ArrayList<Entity> entitiesCopy = (ArrayList<Entity>)entities.clone();
                        entitiesCopy.sort((Entity e1, Entity e2) -> {
                            return e1.getDrawPriority() - e2.getDrawPriority();
                        });

                        for (Entity entity : entitiesCopy) {
                            if (!(entity instanceof Anthill)) {
                                new InfoBundler(inspectPanel).bundle(
                                    entity
                                        .getEntityInfo()
                                        .getComponents()
                                );
                                inspectFrame.pack();
                                break;
                            }
                        }
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}
        });

        mainPanel.add(leftPanel);
        mainPanel.add(panel);
        mainPanel.add(rightPanel);

        inspectFrame = new GameFrame();
        inspectFrame.setTitle("Ant Inspector");

        inspectPanel = new JPanel();
        inspectPanel.setLayout(new BoxLayout(inspectPanel, BoxLayout.Y_AXIS));
        inspectPanel.setDoubleBuffered(true);
        inspectPanel.setBackground(Color.black);
        inspectPanel.setPreferredSize(
            new Dimension(
                300, 300
            )
        );

        inspectFrame.add(inspectPanel);
        inspectFrame.pack();
        inspectFrame.setVisible(true);

        frame.add(mainPanel);
        frame.pack();
        frame.setVisible(true);
    }

    private Anthill deployAnthill(int x, int y, Ant.Team team) {
        Node n = getNode(x, y);

        assert n != null : "Invalid node"; 
        assert n.isEmpty() : "Node is not empty";

        Anthill a = new Anthill(this, n, team);
        n.insertEntity(a);
        anthills.add(a);

        return a;
    }

    private Anthill deployAnthill(int x, int y, Ant.Team team,
            ArrayList<ArrayList<Behavior>> antTypes,
            ArrayList<String> antLabels) {
        Node n = getNode(x, y);

        assert n != null : "Invalid node"; 
        assert n.isEmpty() : "Node is not empty";

        Anthill a = new Anthill(this, n, team, antTypes, antLabels);
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
            inspectPanel.repaint();
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

        ArrayList<ArrayList<Behavior>> antTypes = new ArrayList<>();
        ArrayList<String> antLabels = new ArrayList<>();
        ArrayList<Behavior> type = new ArrayList<>();

        type.add(new CollectBehavior(this, null));
        type.add(new AttackBehavior(this, null));
        type.add(new RetreatBehavior(this, null));
        type.add(new WanderBehavior(this, null));
        type.add(new ChillBehavior(this, null));
        antLabels.add("Worker");
        antTypes.add(type);

        type = new ArrayList<>();

        type.add(new AttackBehavior(this, null));
        type.add(new WanderBehavior(this, null));
        type.add(new ChillBehavior(this, null));
        antLabels.add("Drone  ");
        antTypes.add(type);

        deployAnthill(settings.getMaxY() - 1, 0, Ant.Team.BLUE, antTypes, antLabels);

        antTypes = new ArrayList<>();
        antLabels = new ArrayList<>();
        type = new ArrayList<>();

        type.add(new CollectBehavior(this, null));
        type.add(new RetreatBehavior(this, null));
        type.add(new WanderBehavior(this, null));
        type.add(new ChillBehavior(this, null));
        antLabels.add("Collector");
        antTypes.add(type);

        type = new ArrayList<>();

        CollectBehavior collectBehavior = new CollectBehavior(this, null);
        type.add(collectBehavior);
        type.add(new BlundererBehavior(this, null, collectBehavior.getLarvaeView(), 0.1));
        type.add(new RetreatBehavior(this, null));
        type.add(new WanderBehavior(this, null));
        type.add(new ChillBehavior(this, null));
        antLabels.add("Blunderer");
        antTypes.add(type);

        type = new ArrayList<>();
        type.add(new AttackBehavior(this, null));
        type.add(new RetreatBehavior(this, null));
        type.add(new WanderBehavior(this, null));
        type.add(new ChillBehavior(this, null));
        antLabels.add("Soldier");
        antTypes.add(type);

        deployAnthill(0, settings.getMaxY() - 1, Ant.Team.RED, antTypes, antLabels);

        InfoBundler infoBundler;
        infoBundler = new InfoBundler(rightPanel);
        infoBundler.bundle(anthills.get(0).getEntityInfo().getComponents());

        infoBundler = new InfoBundler(leftPanel);
        infoBundler.bundle(anthills.get(1).getEntityInfo().getComponents());
        fillNodes();
        loop();
    }

    /**
     * Get the node from the grid
     * 
     * @param x the column number.
     * @param y the row number.
     * 
     * @return the node itself.
     */
    public Node getNode(int x, int y) {
        if (x < settings.getMaxX() && y < settings.getMaxY() && x >= 0 && y >= 0)
            return nodes.get(y * settings.getMaxX() + x);

        return null;
    }

    /**
     * Moves entity from one node to another.
     * 
     * @param e - entity that is being moved
     * @param n1 - source node
     * @param n2 - target node
     */
    public void move(Entity e, Node n1, Node n2) {
        n1.deleteEntity(e);
        n2.insertEntity(e);
    }


    /**
     * Kill the entity from the game
     * 
     * @param entity - entity to kill
     */
    public synchronized void kill(Entity entity) {
        for (Node node : nodes) {
            node.getEntities().remove(entity);
        }

        if (!(entity instanceof Ant)) {
            return;
        }

        for (Anthill anthill : anthills) {
            anthill.kill((Ant)entity);
        }

        ((Ant)entity).dropLarvae();
    }
}
