package com.game;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JButton;

/**
 * The {@code Ant} class represents an ant in the game world. Ants can belong to different teams (BLUE or RED),
 * have a name, strength, health, and can perform various behaviors in the game.
 *
 * <p>This class extends the {@code Behaving} class and implements the {@code Runnable} interface for concurrent
 * behavior in the game.
 */
public class Ant extends Behaving implements Runnable {
    /**
     * Enumeration representing the possible teams an ant can belong to: BLUE or RED.
     */
    enum Team {
        BLUE,
        RED
    }

    private String name;
    private int strength;
    private AtomicInteger health;
    private Team color;
    private String label;

    /**
     * Array containing possible names for ants.
     */
    private static String names[] = {
        "Gosling",
        "Bateman",
        "Gigachad",
        "Rock",
        "Vim user",
        "Tyler",
        "Goggins"
    };

    /**
     * Constructs an ant with default behaviors, given the game, initial position, team, and a label.
     *
     * @param game     The game in which the ant exists.
     * @param position The initial position of the ant.
     * @param team     The team to which the ant belongs (BLUE or RED).
     * @param label    The label associated with the ant.
     */
    public Ant(Game game, Node position, Team team, String label) {
        super(game, position);
        init(team, label);
        addBehavior(new WanderBehavior(game, this));
    }

    /**
     * Constructs an ant with specified behaviors, given the game, initial position, team, behaviors, and a label.
     *
     * @param game      The game in which the ant exists.
     * @param position  The initial position of the ant.
     * @param team      The team to which the ant belongs (BLUE or RED).
     * @param behaviors The list of behaviors associated with the ant.
     * @param label     The label associated with the ant.
     */
    public Ant(Game game, Node position, Team team, ArrayList<Behavior> behaviors, String label) {
        super(game, position);
        init(team, label);

        this.behaviors = behaviors;
    }

    private static String getRandomName() {
        int roll = ThreadLocalRandom
                        .current()
                        .nextInt(names.length);

        return names[roll];
    }

    private void init(Team team, String label) {
        super.setDrawPriority(1);
        status = "wandering";

        fitTile(label.trim());
        name = getRandomName();
        color = team;
        strength = 20 + ThreadLocalRandom.current().nextInt(30);
        health = new AtomicInteger(30 + ThreadLocalRandom.current().nextInt(20));
        this.label = label;

        setupInfo();
    }

    @Override
    public void run() {
        while (health.get() > 0) {
            entityInfo.updateComponents();
            behave();

            ArrayList<IInteractive> toInterract = new ArrayList<>();

            synchronized (game) {
                ArrayList<Entity> inSameNode = position.getEntities();
                for (Entity e : inSameNode) {
                    if (e instanceof IInteractive)
                        toInterract.add(((IInteractive)e));
                }
            }

            for (IInteractive obj : toInterract)
                obj.interact(this);
        }

        entityInfo.updateComponents();
        game.kill(this);
    }

    @Override
    public void draw() {
        if (System.getProperty("os.name").contains("Linux")) 
            Toolkit.getDefaultToolkit().sync();
        GamePanel panel = game.getGamePanel();

        panel.drawImage(tile, position.getPoint());
    }

    @Override
    protected void setupInfo() {
        entityInfo.addComponent(new InfoLabel("Ant " + label, tile));
        entityInfo.addComponent(new InfoLabel("Name: " + name));
        entityInfo.addComponent(new InfoLabel("Strength: " + strength));
        entityInfo.addComponent(new InfoLabel("Health: ", health));
        entityInfo.addComponent(new InfoLabel("Team: " + color));

        JButton button = new JButton("Kill");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                health.set(0);
            }});
        entityInfo.addComponent(new InfoComponent(button));
    }

    @Override
    public void behave() {
        for (Behavior behavior : behaviors)
            status = behavior.activate(status);
    }


    public int getAndEmpty() {
        int answer = 0;
        for (Behavior behavior : behaviors) {
            if (behavior instanceof CollectBehavior) {
                answer += ((CollectBehavior)behavior).getAndSetZero();
            }
        }

        return answer;
    }

     /**
     * Returns the strength value of the ant.
     *
     * @return The strength value of the ant.
     */
    public int getStrength() {
        return strength;
    }

    public Team getColor() {
        return color;
    }

    /**
     * Inflicts damage on the ant, reducing its health.
     *
     * @param damage The amount of damage to be inflicted on the ant.
     */
    public void getDamage(int damage) {
        health.set(health.get() - damage);
    }

    /**
     * Drops larvae at the ant's current Node.
     */
    public void dropLarvae() {
        getPosition().dropLarvae(getAndEmpty());
    }

    /**
     * Sets the label of the ant AKA its class.
     */
    public void setLabel(String label) {
        this.label = label;
    }


}
