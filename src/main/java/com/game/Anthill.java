package com.game;

import java.awt.Color;

import com.game.Ant.Team;

public class Anthill extends Entity {
    int collectedLavrae;
    Team team;
    
    public Anthill(Game game, Node position, Team team) {
        super(game, position);

        collectedLavrae = 0;
        this.team = team;
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

        GamePanel panel = game.getGamePanel();
        panel.fillRect(position.getPoint(), color);
    }
}
