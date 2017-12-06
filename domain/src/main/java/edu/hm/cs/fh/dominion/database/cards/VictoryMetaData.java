package edu.hm.cs.fh.dominion.database.cards;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public interface VictoryMetaData extends Card.MetaData {
    int getPoints();

    static VictoryMetaData create(final int points, final int cost, final Card.Type... types) {
        return create(points, cost, Arrays.asList(types));
    }

    static VictoryMetaData create(final int points, final int cost, final List<Card.Type> types) {
        return new VictoryMetaData() {
            @Override
            public int getPoints() {
                return points;
            }

            @Override
            public int getCost() {
                return cost;
            }

            @Override
            public Stream<Card.Type> getTypes() {
                return types.stream();
            }
        };
    }
}
