package com.game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JButton;

import com.game.Ant.Team;

/**
 * The {@code Anthill} class represents an anthill in the game world. Anthills are entities that produce ants,
 * collect larvae, and have specific behaviors in the game. Anthills can belong to different teams (BLUE or RED).
 *
 * <p>Anthills can produce ants with specific behaviors and labels. They keep track of the number of collected larvae,
 * the number of alive ants, and display information on the game screen. Anthills extend the {@code Entity} class.
 *
 */
public class Anthill extends Entity {
    private AtomicInteger collectedLarvae;
    private AtomicInteger antCount;

    private Team team;
    private ArrayList<Ant> ants;
    private ArrayList<ArrayList<Behavior>> antTypes;
    private ArrayList<String> antLabels;


    /**
     * Constructs an anthill with default behaviors, given the game, initial position, and team.
     *
     * @param game    The game in which the anthill exists.
     * @param position The initial position of the anthill.
     * @param team     The team to which the anthill belongs (BLUE or RED).
     */
    public Anthill(Game game, Node position, Team team) {
        super(game, position);

        collectedLarvae = new AtomicInteger(0);
        antCount = new AtomicInteger(0);
        this.team = team;

        Settings settings = game.getSettings();

        String picName;
        switch (team) {
            case BLUE:
                picName = "blue";
                break;
            case RED:
                picName = "red";
                break;
            default:
                picName = "";
                break;
        }

        tile = TileLoader.loadTile(picName);
        tile = TileScaler.fit(
            tile,
            settings.getTileSize() * 2,
            settings.getTileSize() * 2);

        ants = new ArrayList<>();

        ArrayList<Behavior> defaultAnt = new ArrayList<>();
        defaultAnt.add(new WanderBehavior(game, null));
        defaultAnt.add(new ChillBehavior(game, null));

        antTypes = new ArrayList<>();
        antLabels = new ArrayList<>();
        antTypes.add(defaultAnt);
        antLabels.add("Default");

        setupInfo();
    }

    /**
     * Constructs an anthill with specified behaviors, given the game, initial position, team, ant types, and ant labels.
     *
     * @param game      The game in which the anthill exists.
     * @param position  The initial position of the anthill.
     * @param team      The team to which the anthill belongs (BLUE or RED).
     * @param antTypes  The list of ant behaviors for different ant types.
     * @param antLabels The list of labels associated with each ant type.
     */
    public Anthill(Game game, Node position, Team team,
            ArrayList<ArrayList<Behavior>> antTypes,
            ArrayList<String> antLabels) {
        this(game, position, team);
        this.antTypes = antTypes;
        this.antLabels = antLabels;

        entityInfo = new EntityInfo();
        setupInfo();
    }


     /**
     * Produces an ant of a specific type based on the provided index.
     *
     * @param i The index representing the ant type.
     * 
     */
    public void produceAnt(int i) {
        ArrayList<Behavior> toInsert = new ArrayList<>();
        for (Behavior behavior : antTypes.get(i)) {
            toInsert.add(behavior.clone());
        }

        Ant ant = new Ant(game, position, team, toInsert, antLabels.get(i));

        for (Behavior behavior : toInsert) {
            behavior.setSubject(ant);
        }

        antCount.incrementAndGet();
        ants.add(ant);
        position.insertEntity(ant);

        Thread t = new Thread(ant);
        t.start();
    }

    @Override
    public void draw() {
        Color color = Color.white;
        switch (team) {
            case BLUE:
                color = new Color(0, 0, 200, 100);
                break;
            case RED:
                color = new Color(200, 0, 0, 100);
                break;
        }

        entityInfo.updateComponents();

        GamePanel panel = game.getGamePanel();
        panel.fillRect(position.getPoint(), color);
    }

    @Override
    protected void setupInfo() {
        Settings settings = game.getSettings();

        InfoComponent infoComponent = new InfoLabel(
            "Collected larvae: ",
            collectedLarvae
        );

        entityInfo.addComponent(infoComponent);

        infoComponent = new InfoLabel(
            "Alive ants: ",
            antCount
        );

        entityInfo.addComponent(infoComponent);

        BufferedImage tile;
        int increasedTileSize = settings.getTileSize() * 2;
        switch (team) {
            case BLUE:
                tile = TileLoader.loadTile("blue");
                tile = TileScaler.fit(
                    tile,
                    increasedTileSize,
                    increasedTileSize
                );
                infoComponent = new InfoLabel("Average Drone enjoyer", tile);
                break;

            default:
                tile = TileLoader.loadTile("red");
                tile = TileScaler.fit(
                    tile,
                    increasedTileSize,
                    increasedTileSize
                );
                infoComponent = new InfoLabel("Average Soldier fan", tile);
                break;
        }

        entityInfo.addComponent(infoComponent);

        for (int i = 0; i < antTypes.size(); i++) {
            JButton button = new JButton();
            button.setText(antLabels.get(i));
            final int tempValue = i;

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    produceAnt(tempValue);
                }
            });
            entityInfo.addComponent(new InfoComponent(button));
        }
    }

     /**
     * Kills the specified ant, updating collected larvae and ant count if applicable.
     *
     * @param ant The ant to be killed.
     */
    public void kill(Ant ant) {
        if (ants.contains(ant)) {
            ants.remove(ant);

            if (this.getPosition() == ant.getPosition()) {
                collectedLarvae.set(collectedLarvae.get() + ant.getAndEmpty());
            }
            antCount.decrementAndGet();
        }
    }

    /**
     * Adds larvae to the collected larvae count.
     *
     * @param l The number of larvae to be added.
     */
    public void putLarvae(int l) {
        collectedLarvae.set(collectedLarvae.get() + l);
    }
}
