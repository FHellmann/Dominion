/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves.card;

import edu.hm.cs.fh.dominion.database.cards.Card;

import java.util.stream.Stream;

/**
 * This interface has to be implemented by classes which would need to show some cards.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 14.06.2014
 */
public interface ShowCards {
    /**
     * Get the cards to show.
     *
     * @return cards to show.
     */
    Stream<Card> getCards();
}
