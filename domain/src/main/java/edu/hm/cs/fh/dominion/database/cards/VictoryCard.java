/**
 *
 */
package edu.hm.cs.fh.dominion.database.cards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Victory cards as enums.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 09.04.2014
 */
public enum VictoryCard implements Card<VictoryMetaData> {
    /**
     * Curse card.
     */
    CURSE(0, -1, Type.CURSE),
    /**
     * Estate card.
     */
    ESTATE(2, 1),
    /**
     * Duchy card.
     */
    DUCHY(5, 3),
    /**
     * province card.
     */
    PROVINCE(8, 6);

    private final VictoryMetaData treasuryMetaData;

    VictoryCard(final int cost, final int points, final Type... types) {
        final List<Type> typeList = new ArrayList<>();
        typeList.add(Type.VICTORY);
        typeList.addAll(Arrays.asList(types));
        treasuryMetaData = VictoryMetaData.create(points, cost, typeList);
    }

    @Override
    public VictoryMetaData getMetaData() {
        return treasuryMetaData;
    }

    @Override
    public String getName() {
        return name().toLowerCase(Locale.getDefault());
    }
}
