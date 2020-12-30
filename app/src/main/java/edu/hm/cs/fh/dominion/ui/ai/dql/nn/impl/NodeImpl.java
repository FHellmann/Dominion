package edu.hm.cs.fh.dominion.ui.ai.dql.nn.impl;

import edu.hm.cs.fh.dominion.ui.ai.dql.nn.Node;

public class NodeImpl implements Node {
    private double bias;
    private double output;

    @Override
    public double getBias() {
        return bias;
    }

    @Override
    public double getOutput() {
        return output;
    }
}
