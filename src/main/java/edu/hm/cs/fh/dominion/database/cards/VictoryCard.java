/**
 *
 */
package edu.hm.cs.fh.dominion.database.cards;

/**
 * Victory cards as enums.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 09.04.2014
 */
public enum VictoryCard implements Card {
	/** Curse card. */
	CURSE,
	/** Duchy card. */
	DUCHY,
	/** Estate card. */
	ESTATE,
	/** province card. */
	PROVINCE;

	/**
	 * The card attribute points.
	 *
	 * @return the points.
	 */
	public int getPoints() {
		return getMetaData().getCointsOrPoints();
	}
}
