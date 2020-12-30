package edu.hm.cs.fh.dominion.ui.ai.dql.nn;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface NeuralNetwork {
    void feedInput(); // TODO Input format

    double train();

    default int getNetworkClassification() {
        return getOutputLayer().getNodes()
                .collect(Collectors.toList())
                .indexOf(getOutputLayer().getMaxNode());
    }

    default void feedForward() {
        getInputLayer().calcLayer();
        getOutputLayer().calcLayer();
    }

    default void backPropagate(int classification) {

    }

    Stream<Layer> getLayers();

    default Layer getInputLayer() {
        return getLayers()
                .filter(layer -> layer.getType() == Layer.Type.INPUT)
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    default Layer getOutputLayer() {
        return getLayers()
                .filter(layer -> layer.getType() == Layer.Type.OUTPUT)
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }
}
