package com.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

import com.game.Ant.Team;

public class Anthill extends Entity {
    Integer collectedLavrae;
    Team team;
    
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
    }


    public void produceAnt() {
        Ant ant = new Ant(game, position, team);
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
    public void setupInfo(InfoBundler bundler) {
        JLabel label = new JLabel();
        label.setForeground(Color.white);
        label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        entityInfo.addComponent(new InfoComponent(label) {
            @Override
            void updateComponent() {
                ((JLabel)component).setText("Collected lavrae: " + collectedLavrae);
            }
        });
        bundler.bundle(entityInfo.getComponents());
    }
}
