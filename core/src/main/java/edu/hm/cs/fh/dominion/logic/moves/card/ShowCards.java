/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves.card;

import java.util.stream.Stream;

import edu.hm.cs.fh.dominion.database.cards.Card;

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
