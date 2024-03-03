package com.game;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The {@code Node} class represents a node in the game world. Nodes are locations where entities can be positioned,
 * and they may contain larvae. Nodes can have neighboring nodes and entities associated with them.
 *
 * <p>Nodes are used to organize the spatial layout of entities in the game. They can be populated with entities
 * and provide information about neighboring nodes. Nodes are part of the overall game structure.
 */
public class Node {
    private Point position;
    private int larvae;

    private ArrayList<Entity> entities;
    private Game game;

    /**
     * Constructs a node with the specified game, x-coordinate, and y-coordinate.
     *
     * @param game The game in which the node exists.
     * @param x    The x-coordinate of the node.
     * @param y    The y-coordinate of the node.
     */
    public Node(Game game, int x, int y) {
        position = new Point(x, y);
        entities = new ArrayList<>();
        this.game = game;
    }

    /**
     * Constructs a node with the specified game, x-coordinate, y-coordinate, and initial larvae amount.
     *
     * @param game   The game in which the node exists.
     * @param x      The x-coordinate of the node.
     * @param y      The y-coordinate of the node.
     * @param larvae The initial amount of larvae in the node.
     */
    public Node(Game game, int x, int y, int larvae) {
        position = new Point(x, y);
        entities = new ArrayList<>();
        this.game = game;
        this.larvae = larvae;
    }

    /**
     * Sets a random amount of larvae for the node.
     */
    public void setRandomLarvae() {
        larvae = ThreadLocalRandom.current().nextInt(0, 100);
    }

    /**
     * Sets the amount of larvae for the node.
     *
     * @param larvae The amount of larvae to set.
     */
    public void setLarvae(int larvae) {
        this.larvae = larvae;
    }

    /**
     * Adds larvae to the node.
     *
     * @param larvae The amount of larvae to add.
     */
    public void dropLarvae(int larvae) {
        this.larvae += larvae;
    }

    /**
     * Retrieves the current amount of larvae in the node.
     *
     * @return The amount of larvae in the node.
     */
    public int getLarvaeAmount() {
        return larvae;
    }

    /**
     * Collects larvae from the node, up to a specified capacity.
     *
     * @param capacity The maximum capacity for collecting larvae.
     * @return The amount of larvae collected.
     */
    public int collectLarvae(int capacity) {
        int toCollect;
        synchronized (game) {
            toCollect = Math.min(larvae, capacity);
            larvae -= toCollect;
        }

        return toCollect;
    }

    /**
     * Inserts an entity into the node.
     *
     * @param e The entity to insert.
     */
    public synchronized void insertEntity(Entity e) {
        entities.add(e);
    }

    /**
     * Deletes an entity from the node.
     *
     * @param e The entity to delete.
     */
    public synchronized void deleteEntity(Entity e) {
        entities.remove(entities.indexOf(e));
    }

    /**
     * Retrieves the list of entities associated with the node, considering affecting entities.
     *
     * @return The list of entities after applying affecting entities' effects.
     */
    public ArrayList<Entity> getEntities() {
        ArrayList<IAffecting> affecting = new ArrayList<>();

        for (Entity e : entities) {
            if (e instanceof IAffecting) {
                affecting.add((IAffecting)e);
            }
        }

        ArrayList<Entity> tempEntities = new ArrayList<>(entities);
        for (IAffecting a : affecting) {
            tempEntities = a.affect(tempEntities);
        }

        return entities;
    }

    /**
     * Retrieves neighboring nodes of the current node.
     *
     * @return The list of neighboring nodes.
     */
    public ArrayList<Node> getNeighbors() {
        ArrayList<Node> neighbors = new ArrayList<>();

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == j || (i != 0 && j != 0))
                    continue;
                
                Node temp = game.getNode((int)position.getX() + i, (int)position.getY() + j);
                if (temp != null)
                    neighbors.add(temp);
            }
        }

        return neighbors;
    }

    /**
     * Retrieves the position of the node.
     *
     * @return The position of the node as a {@code Point}.
     */
    public Point getPoint() {
        return position;
    }

    /**
     * Draws the node on the game screen, including a representation of larvae amount and associated entities.
     */
    public void draw() {
        int chroma = Math.min((int)(1.0 * larvae / 100 * 128), 128);

        GamePanel panel = game.getGamePanel();

        panel.fillRect(position, new Color(chroma, chroma, chroma, 128));

        entities.sort((Entity e1, Entity e2) -> {
            return - e1.getDrawPriority() + e2.getDrawPriority();
        });

        for (Entity entity : entities) {
            entity.draw();
        }
    }


    /**
     * Checks if the node is empty (contains no entities).
     *
     * @return {@code true} if the node is empty, {@code false} otherwise.
     */
    public synchronized boolean isEmpty() {
        return entities.isEmpty();
    }
}
