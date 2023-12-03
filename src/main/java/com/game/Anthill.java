package com.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import com.game.Ant.Team;

public class Anthill extends Entity {
    int collectedLavrae;
    Team team;
    
    public Anthill(Node position, Team team) {
        super(position, Entity.Type.STATIC);

        collectedLavrae = 0;
        this.team = team;
    }

    @Override
    public void draw(Graphics2D g2) {
        switch (team) {
            case BLUE:
                g2.setColor(new Color(0, 0, 200, 100));
                break;
            case RED:
                g2.setColor(new Color(200, 0, 0, 100));
                break;
        }

        Point originalPosition = position.getWindowCoords();

        g2.fillRect(originalPosition.x, originalPosition.y, position.getTileSize(), position.getTileSize());
    }
}
