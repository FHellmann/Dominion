/**
 *
 */
package edu.hm.cs.fh.dominion.database.full;

import edu.hm.cs.fh.dominion.database.ReadonlyCardDeck;
import edu.hm.cs.fh.dominion.database.cards.Card;

import java.util.Collections;
import java.util.Optional;
import java.util.Random;

/**
 * A layer to read and write the carddeck.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 29.03.2014
 */
public interface WriteableCardDeck extends ReadonlyCardDeck {
    /**
     * Add a card to the carddeck.
     *
     * @param card to add.
     */
    void add(final Card card);

    /**
     * Randomly shuffles the carddeck.
     *
     * @see {@link Collections#shuffle(java.util.List)}
     */
    void shuffle(final Random random);

    /**
     * Remove the card from the cardlist.
     *
     * @param card to remove.
     */
    void remove(final Card card);

    /**
     * Retrieve the card of the top from the carddeck. Afterwards it will be deleted.
     *
     * @return the card from the top.
     */
    Optional<Card> poll();

    /**
     * Remove all cards.
     */
    void clear();

    /**
     * Moves and removes all of the cards from the carddeck to the other one.
     *
     * @param cardDeckFrom to remove the cards and move them.
     * @param cardDeckTo   to move the cards to.
     */
    static void move(final WriteableCardDeck cardDeckFrom, final WriteableCardDeck cardDeckTo) {
        if (cardDeckFrom == null || cardDeckTo == null) {
            throw new IllegalArgumentException("The carddeck can not be null");
        }

        cardDeckFrom.stream().forEach(cardDeckTo::add);
        cardDeckFrom.clear();
    }

    /**
     * Moves the card from the carddeck to another. The card will be removed from the carddeck and
     * will be added to the other one.
     *
     * @param cardDeckFrom to remove the card from.
     * @param cardDeckTo   to move the card to.
     * @param card         to move.
     */
    static void move(final WriteableCardDeck cardDeckFrom, final WriteableCardDeck cardDeckTo, final Card card) {
        if (cardDeckFrom.contains(card)) {
            cardDeckFrom.remove(card);
            cardDeckTo.add(card);
        }
    }
}
