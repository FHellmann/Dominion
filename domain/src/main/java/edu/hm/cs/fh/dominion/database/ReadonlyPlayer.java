/**
 *
 */
package edu.hm.cs.fh.dominion.database;

/**
 * A layer to read the player.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 29.03.2014
 */
public interface ReadonlyPlayer {

	/**
	 * Get the name.
	 *
	 * @return the name
	 */
	String getName();

	/**
	 * Get the carddeck pull.
	 *
	 * @return the pull carddeck.
	 */
	ReadonlyCardDeck getCardDeckPull();

	/**
	 * Get the carddeck hand.
	 *
	 * @return the hand carddeck.
	 */
	ReadonlyCardDeck getCardDeckHand();

	/**
	 * Get the carddeck played.
	 *
	 * @return the played cards carddeck.
	 */
	ReadonlyCardDeck getCardDeckPlayed();

	/**
	 * Get the carddeck stacker.
	 *
	 * @return the stacker carddeck.
	 */
	ReadonlyCardDeck getCardDeckStacker();

	/**
	 * Get the victory points sum.
	 *
	 * @return the amount of victory-points.
	 */
	ReadonlyCounter getVictoryPoints();

	/**
	 * Get the money of this turn.
	 *
	 * @return the amount of remaining money.
	 */
	ReadonlyCounter getMoney();

	/**
	 * Get the purchases of this turn.
	 *
	 * @return the amount of remaining purchases.
	 */
	ReadonlyCounter getPurchases();

	/**
	 * Get the actions of this turn.
	 *
	 * @return the amount of remaining actions.
	 */
	ReadonlyCounter getActions();

}