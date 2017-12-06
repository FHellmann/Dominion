package edu.hm.cs.fh.dominion.database.cards;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * The metadata of a treasury card contains all their attributes content.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 06.12.2017
 */
public interface TreasuryMetaData extends Card.MetaData {
    /**
     * Get the amount of coints for this treasury card.
     *
     * @return the amount of coints.
     */
    int getCoints();

    static TreasuryMetaData create(final int coints, final int cost, final Card.Type... types) {
        return create(coints, cost, Arrays.asList(types));
    }

    static TreasuryMetaData create(final int coints, final int cost, final List<Card.Type> types) {
        return new TreasuryMetaData() {
            @Override
            public int getCoints() {
                return coints;
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
