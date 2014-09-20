/**
 *
 */
package edu.hm.cs.fh.dominion.database;

import java.util.stream.Stream;

import edu.hm.cs.fh.dominion.database.cards.Card;

/**
 * A layer to read on the carddeck.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 29.03.2014
 */
public interface ReadonlyCardDeck {
	/**
	 * Checks weather the carddeck is empty or not.
	 *
	 * @return <code>true</code> if the carddeck is empty.
	 */
	boolean isEmpty();
	
	/**
	 * Get the size of this carddeck.
	 *
	 * @return the size of the carddeck.
	 */
	int size();

	/**
	 * Checks weather the card exists in this carddeck.
	 *
	 * @param card
	 *            to check.
	 * @return <code>true</code> if the card exists, <code>false</code> otherwise.
	 */
	boolean contains(final Card card);

	/**
	 * Builds a stream.
	 *
	 * @return a stream of the carddeck.
	 */
	Stream<Card> stream();
}
