/**
 *
 */
package edu.hm.cs.fh.dominion.database.full;

import edu.hm.cs.fh.dominion.database.ReadonlyPlayer;

import java.util.Random;

/**
 * A layer to read and write the player.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 29.03.2014
 */
public interface WriteablePlayer extends ReadonlyPlayer {
    /**
     * Poll the next amount of cards from the pull-carddeck.
     *
     * @param count  of cards to poll.
     * @param random for shuffle.
     */
    void pollCards(final int count, final Random random);

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
