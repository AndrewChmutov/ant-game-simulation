package com.game;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Settings {
    private int originalTileSize = 16;
    private double scale = 3;

    private int tileSize = (int)(originalTileSize * scale);
    
    private int maxX = 10;
    private int maxY = 10;

    private int sidePanelSize = 160;
    private int screenWidth = tileSize * maxX + 2 * sidePanelSize;
    private int screenHeight = tileSize * maxY;

    private int fps = 60;
    private double speed = 0.25;

    public void recalculate() {
        tileSize = (int)(originalTileSize * scale);
        screenWidth = tileSize * maxX + 2 * sidePanelSize;
        screenHeight = tileSize * maxY;

        sidePanelSize = Math.max(160, 2 * tileSize);
    }

    public int getOriginalTileSize() {
        return originalTileSize;
    }

    public double getScale() {
        return scale;
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getSidePanelSize() {
        return sidePanelSize;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getFps() {
        return fps;
    }

    public synchronized double getSpeed() {
        return speed;
    }

    public synchronized void setOriginalTileSize(int originalTileSize) {
        this.originalTileSize = originalTileSize;
    }

    public synchronized void setScale(double scale) {
        this.scale = scale;
    }

    public synchronized void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    public synchronized void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public synchronized void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public synchronized void setSidePanelSize(int sidePanelSize) {
        this.sidePanelSize = sidePanelSize;
    }

    public synchronized void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public synchronized void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public synchronized void setFps(int fps) {
        this.fps = fps;
    }

    public synchronized void setSpeed(double speed) {
        this.speed = speed;
    }
}
