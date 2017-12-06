/**
 *
 */
package edu.hm.cs.fh.dominion.database.full;

import edu.hm.cs.fh.dominion.database.ReadonlyPlayer;

/**
 * A layer to read and write the player.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 29.03.2014
 */
public interface WriteablePlayer extends ReadonlyPlayer {
	/**
	 * Poll the next amount of cards from the pull-carddeck.
	 *
	 * @param count
	 *            of cards to poll.
	 */
	void pollCards(final int count);

	/**
	 * Clear the counters for: Actions, Purchases, Money. The victory-points will not be reset!
	 */
	void clean();

	@Override
	WriteableCardDeck getCardDeckPull();

	@Override
	WriteableCardDeck getCardDeckHand();

	@Override
	WriteableCardDeck getCardDeckPlayed();

	@Override
	WriteableCardDeck getCardDeckStacker();

	@Override
	WriteableCounter getVictoryPoints();

	@Override
	WriteableCounter getMoney();

	@Override
	WriteableCounter getPurchases();

	@Override
	WriteableCounter getActions();
}
