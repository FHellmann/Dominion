package edu.hm.cs.fh.dominion.database.cards;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public interface KingdomMetaData extends Card.MetaData {

    static KingdomMetaData create(final int cost, final Card.Type... types) {
        return create(cost, Arrays.asList(types));
    }

    static KingdomMetaData create(final int cost, final List<Card.Type> types) {
        return new KingdomMetaData() {
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
