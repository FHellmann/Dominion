package edu.hm.cs.fh.dominion.ui.ai.dql.nn;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface Layer {

    Type getType();

    Stream<Node> getNodes();

    void calcLayer();

    void calcNodeOutput(Node node);

    void activateNode(Node node);

    double getActFctDerivative(double outValue);

    default Node getMaxNode() {
        return getNodes()
                .max(Comparator.comparingDouble(Node::getOutput))
                .orElseThrow(IllegalStateException::new);
    }

    static Optional<Layer> getPrevLayer(final List<Layer> layers, final Layer root) {
        return Optional.ofNullable(layers.indexOf(root) > 0 ? layers.get(layers.indexOf(root)) : null);
    }

    enum Type {
        INPUT, HIDDEN, OUTPUT
    }
}
