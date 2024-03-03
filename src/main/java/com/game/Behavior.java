package com.game;


/**
 * The {@code Behavior} class is an abstract class representing specific behaviors that can be associated with
 * behaving entities in the game world.
 *
 * <p>This class implements the {@code Cloneable} interface, allowing behaviors to be cloned for multiple entities.
 * Subclasses of this class define the actual behavior through the {@code activate} method.
 *
 */
public abstract class Behavior implements Cloneable {
    /**
     * The game in which the behavior operates.
     */
    protected Game game;

    /**
     * The behaving entity to which the behavior is associated.
     */
    protected Behaving behaving;

    /**
     * Constructs a behavior with the specified game and behaving entity.
     *
     * @param game     The game in which the behavior operates.
     * @param behaving The behaving entity to which the behavior is associated.
     */
    public Behavior(Game game, Behaving behaving) {
        this.game = game;
        this.behaving = behaving;
    }

    /**
     * Sets the behaving entity for the behavior.
     *
     * @param behaving The behaving entity to be associated with the behavior.
     */
    public void setSubject(Behaving behaving) {
        this.behaving = behaving;
    }

    @Override
    public Behavior clone(){
        Object obj = null;

        try {
            obj = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return (Behavior)obj;
    }

    /**
     * Abstract method to activate the behavior, specifying the change in status.
     *
     * @param status The current status of the behaving entity.
     * @return The new status after the behavior is activated.
     */
    public abstract String activate(String status);
}
