package com.game;

import java.util.ArrayList;

/**
 * The {@code Behaving} class is an abstract class representing entities in the game world that exhibit behaviors.
 *
 * <p>This class extends the {@code Entity} class and provides a framework for entities that have a collection of
 * behaviors. Behaving entities can have various behaviors, and their behavior can be influenced by the game world.
 *
 */
public abstract class Behaving extends Entity {
    /**
     * The list of behaviors associated with the behaving entity.
     */
    protected ArrayList<Behavior> behaviors;

    /**
     * The current status or state of the behaving entity.
     */
    protected String status;

    /**
     * Constructs a behaving entity with the specified game and position.
     *
     * @param game     The game in which the behaving entity exists.
     * @param position The initial position of the behaving entity.
     */
    public Behaving(Game game, Node position) {
        super(game, position);

        behaviors = new ArrayList<>();
    }

    /**
     * Adds a behavior to the list of behaviors associated with the behaving entity.
     *
     * @param behavior The behavior to be added.
     */
    public void addBehavior(Behavior behavior) {
        behaviors.add(behavior);
    }

    /**
     * Retrieves the current status or state of the behaving entity.
     *
     * @return The current status of the behaving entity.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Abstract method for defining the behavior of the behaving entity.
     * Subclasses must implement this method to specify the entity's behavior.
     */
    public abstract void behave();

    /**
     * Retrieves the list of behaviors associated with the behaving entity.
     *
     * @return The list of behaviors associated with the behaving entity.
     */
    public ArrayList<Behavior> getBehaviors() {
        return behaviors;
    }
}
