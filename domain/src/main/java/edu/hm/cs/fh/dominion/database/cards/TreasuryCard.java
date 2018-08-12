/**
 *
 */
package edu.hm.cs.fh.dominion.database.cards;

import java.util.Locale;

/**
 * Treasury cards as enums.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 09.04.2014
 */
public enum TreasuryCard implements Card<TreasuryMetaData> {
	/** Copper card. */
	COPPER(0, 1),
	/** Silver card. */
	SILVER(3, 2),
	/** Gold card. */
	GOLD(6, 3);

	private final TreasuryMetaData treasuryMetaData;

	TreasuryCard(final int coints, final int cost) {
		treasuryMetaData = TreasuryMetaData.create(coints, cost, Type.TREASURY);
	}

	@Override
	public TreasuryMetaData getMetaData() {
		return treasuryMetaData;
	}

	@Override
	public String getName() {
		return name().toLowerCase(Locale.getDefault());
	}
}
