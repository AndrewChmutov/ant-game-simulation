package com.game;

import java.util.concurrent.ThreadLocalRandom;

public class DefaultNodeFiller implements INodeFiller {
    private double stoneProbability;
    private double leafProbability;
    private Game game;
    
    public DefaultNodeFiller(double stoneProbability, double leafProbability, Game game) {
        assert stoneProbability >= 0 && stoneProbability <= 1;
        assert leafProbability >= 0 && leafProbability <= 1;
        assert stoneProbability + leafProbability <= 1;

        this.stoneProbability = stoneProbability;
        this.leafProbability = leafProbability;
        this.game = game;
    }

    @Override
    public void fill(Node node) {
        double roll = ThreadLocalRandom.current().nextDouble(1);

        if (!node.isEmpty())
            return;

        if (roll < stoneProbability) {
            node.insertEntity(new Stone(game, node));
            return;
        }

        roll -= stoneProbability;

        if (roll < leafProbability) {
            node.insertEntity(new Leaf(game, node));
            return;
        }

        node.setRandomLarvae();

        return;
    }
    
}
