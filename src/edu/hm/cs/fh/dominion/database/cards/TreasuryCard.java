/**
 *
 */
package edu.hm.cs.fh.dominion.database.cards;

/**
 * Treasury cards as enums.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 09.04.2014
 */
public enum TreasuryCard implements Card {
	/** Copper card. */
	COPPER,
	/** Silver card. */
	SILVER,
	/** Gold card. */
	GOLD;

	/**
	 * The card attribute coints.
	 *
	 * @return the coints.
	 */
	public int getCoints() {
		return getMetaData().getCointsOrPoints();
	}
}
