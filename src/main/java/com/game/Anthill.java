package com.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JLabel;

import com.game.Ant.Team;

public class Anthill extends Entity {
    Integer collectedLavrae;
    Team team;
    ArrayList<Ant> ants;

    
    BufferedImage icon;

    public Anthill(Game game, Node position, Team team) {
        super(game, position);

        collectedLavrae = 0;
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

        icon = TileLoader.loadTile(picName);
        icon = TileScaler.fit(
            icon,
            settings.getTileSize() * 2,
            settings.getTileSize() * 2);

        ants = new ArrayList<>();
    }


    public void produceAnt() {
        Ant ant = new Ant(game, position, team);
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

    public JLabel getDefaultlLabel() {
        JLabel label = new JLabel();
        label.setForeground(Color.white);
        label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));

        return label;
    }

    @Override
    public void setupInfo(InfoBundler bundler) {
        entityInfo.addComponent(new InfoComponent(getDefaultlLabel()) {
            @Override
            void updateComponent() {
                synchronized (this) {
                    ((JLabel)component).setText("Collected lavrae: " + collectedLavrae);
                }
            }
        });

        entityInfo.addComponent(new InfoComponent(getDefaultlLabel()) {
            @Override
            void updateComponent() {
                synchronized (this) {
                    ((JLabel)component).setText("Alive ants: " + getAntCount());
                }
            }
        });

        bundler.bundle(entityInfo.getComponents());
    }

    public synchronized int getAntCount() {
        return ants.size();
    }
}
