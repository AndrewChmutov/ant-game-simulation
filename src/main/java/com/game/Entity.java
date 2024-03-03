package com.game;

import java.awt.image.BufferedImage;

/**
 * The {@code Entity} class is an abstract class representing entities in the game world.
 *
 * <p>This class serves as a base class for various entities, providing common functionality such as positioning,
 * drawing, and information management. Subclasses of this class define the specific behavior and appearance of
 * individual entities in the game.
 *
 * @author Your Name
 * @version 1.0
 */
public abstract class Entity {
    
    /**
     * The position of the entity in the game world.
     */
    protected Node position;

    /**
     * The game in which the entity exists.
     */
    protected Game game;

    /**
     * The image tile representing the appearance of the entity.
     */
    protected BufferedImage tile;

    /**
     * The priority for drawing the entity on the game screen.
     */
    protected int drawPriority;

    /**
     * Information associated with the entity for display purposes.
     */
    protected EntityInfo entityInfo;

    /**
     * Abstract method to draw the entity on the game screen.
     */
    public abstract void draw();

    /**
     * Sets the tile for the entity with the specified name and scale.
     *
     * @param name  The name of the tile.
     * @param scale The scale factor for the tile.
     */
    public void setTile(String name, double scale) {
        tile = TileLoader.loadTile(name);
        tile = TileScaler.scale(tile, scale, scale);
    }

    /**
     * Fits the tile for the entity with the specified name to the game settings.
     *
     * @param name The name of the tile.
     */
    public void fitTile(String name) {
        Settings settings = game.getSettings();

        tile = TileLoader.loadTile(name);
        tile = TileScaler.fit(tile, settings.getTileSize(), settings.getTileSize());
    }

    /**
     * Draws the entity's tile on the game screen at its current position.
     */
    public void drawTile() {
        GamePanel panel = game.getGamePanel();
        panel.drawImage(tile, position.getPoint());
    }

    /**
     * Constructs an entity with the specified game and initial position.
     *
     * @param game The game in which the entity exists.
     * @param node The initial position of the entity.
     */
    public Entity(Game game, Node node) {
        this.game = game;
        this.position = node;
        drawPriority = 0;
        entityInfo = new EntityInfo();
    }

    /**
     * Sets the drawing priority for the entity.
     *
     * @param drawPriority The drawing priority to set.
     */
    public void setDrawPriority(int drawPriority) {
        this.drawPriority = drawPriority;
    }

    /**
     * Retrieves the drawing priority of the entity.
     *
     * @return The drawing priority of the entity.
     */
    public int getDrawPriority() {
        return drawPriority;
    }

    public Node getPosition() {
        return position;
    }

    public void setPosition(Node position) {
        this.position = position;
    }

    protected abstract void setupInfo();

    public EntityInfo getEntityInfo() {
        return entityInfo;
    }

}
