package edu.hm.cs.fh.dominion.ui.ai.dql.nn;

public interface Node {
    double getBias();

    double getOutput();

    enum ActFctType {
        SIGMOID, TANH, NONE
    }
}
