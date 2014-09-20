/**
 *
 */
package edu.hm.cs.fh.dominion.database;

import java.util.Observable;
import java.util.Optional;
import java.util.stream.Stream;

import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.database.full.State;

/**
 * A layer to read the game.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 25.03.2014
 */
public abstract class ReadonlyGame extends Observable {
	/**
	 * Get the current state.
	 *
	 * @return the currentState
	 */
	public abstract State getState();

	/**
	 * Get the current player.
	 *
	 * @return the currentPlayer
	 */
	public abstract ReadonlyPlayer getCurrentPlayer();

	/**
	 * Get a list of all players as read-only-version.
	 *
	 * @return an unmutable list with read-only-players.
	 */
	public abstract Stream<ReadonlyPlayer> getPlayers();

	/**
	 * The amount of players who play the game.
	 *
	 * @return the player count.
	 */
	public abstract int getPlayerCount();

	/**
	 * The count of already added supply cards.
	 *
	 * @return the count of supply cards.
	 */
	public abstract int getSupplySize();

	/**
	 * Get the amount of cards from a carddeck.
	 *
	 * @param card
	 *            to get the number of.
	 * @return the number of a carddeck.
	 */
	public abstract int getSupplyCardCount(final Card card);

	/**
	 * Get the supply cards.
	 *
	 * @return a set of cards.
	 */
	public abstract Stream<Card> getSupplyCardSet();

	/**
	 * Checks weather the game is over or not.
	 *
	 * @return <code>true</code> if the game is over.
	 */
	public abstract boolean isOver();

	/**
	 * Gets the player who is the attacker.
	 *
	 * @return the attacker.
	 */
	public abstract Optional<ReadonlyPlayer> getAttacker();

	/**
	 * Gets the players attack card.
	 *
	 * @return the attackCard.
	 */
	public abstract Optional<Card> getAttackCard();

	/**
	 * Get the last played card.
	 *
	 * @return the previousPlayedCard.
	 */
	public abstract Optional<Card> getToResolveActionCard();

	/**
	 * Get the waste carddeck.
	 *
	 * @return the waste carddeck.
	 */
	public abstract ReadonlyCardDeck getWaste();
}
