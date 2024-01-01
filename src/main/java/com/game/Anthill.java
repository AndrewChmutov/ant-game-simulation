package com.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.game.Ant.Team;


public class Anthill extends Entity {
    AtomicInteger collectedLavrae;
    AtomicInteger antCount;

    Team team;
    ArrayList<Ant> ants;
    ArrayList<ArrayList<Behavior>> antTypes;
    ArrayList<String> antLabels;


    public Anthill(Game game, Node position, Team team) {
        super(game, position);

        collectedLavrae = new AtomicInteger(0);
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

        antTypes = new ArrayList<>();
        antLabels = new ArrayList<>();
        antTypes.add(defaultAnt);
        antLabels.add("Default");
    }

    public Anthill(Game game, Node position, Team team,
            ArrayList<ArrayList<Behavior>> antTypes,
            ArrayList<String> antLabels) {
        this(game, position, team);
        this.antTypes = antTypes;
        this.antLabels = antLabels;
    }


    public void produceAnt(int i) {
        ArrayList<Behavior> toInsert = new ArrayList<>();
        for (Behavior behavior : antTypes.get(i)) {
            toInsert.add(behavior.clone());
        }

        Ant ant = new Ant(game, position, team, toInsert);

        for (Behavior behavior : toInsert) {
            behavior.setSubject(ant);
        }

        antCount.incrementAndGet();
        ants.add(ant);
        position.insertEntity(ant);

        Thread t = new Thread(ant);
        t.start();
    }

    synchronized void deleteAnd(Ant ant) {
        ants.remove(ant);
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
    public void setupInfo(InfoBundler bundler) {
        Settings settings = game.getSettings();

        InfoComponent infoComponent = new InfoLabel(
            "Collected lavrae: ",
            collectedLavrae
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

        bundler.bundle(entityInfo.getComponents());
    }
}
