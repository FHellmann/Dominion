/**
 *
 */
package edu.hm.cs.fh.dominion.database.full;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import edu.hm.cs.fh.dominion.database.cards.Card;

/**
 * A player holds every information data about his own game process. How many victory-points he got,
 * how much money he can pay this round, etc.. And which cards he has in his carddeck.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 29.03.2014
 */
public class Player implements WriteablePlayer {
	/** The name of the player. */
	private final String name;
	/** The different carddecks mapped by their types. */
	private final Map<CardDeck.Type, WriteableCardDeck> cardDecks = new HashMap<>();
	/** The different counters mapped by their types. */
	private final Map<Counter.Type, WriteableCounter> counters = new HashMap<>();

	/**
	 * Create a new player.
	 *
	 * @param changeable
	 *            of the parent.
	 * @param name
	 *            of the player.
	 */
	Player(final Changeable changeable, final String name) {
		this.name = name;

		// initialize all Carddecks
		cardDecks.put(CardDeck.Type.PULL, new CardDeck(changeable));
		cardDecks.put(CardDeck.Type.HAND, new CardDeck(changeable));
		cardDecks.put(CardDeck.Type.PLAYED, new CardDeck(changeable));
		cardDecks.put(CardDeck.Type.STACKER, new CardDeck(changeable));

		// initialize all Counters
		counters.put(Counter.Type.REMAIN_ACTIONS, new Counter(changeable));
		counters.put(Counter.Type.REMAIN_PURCHASES, new Counter(changeable));
		counters.put(Counter.Type.REMAIN_MONEY, new Counter(changeable));
		counters.put(Counter.Type.SUM_POINTS, new Counter(changeable, Integer.MIN_VALUE, 0));
	}

	@Override
	public WriteableCardDeck getCardDeckPull() {
		return cardDecks.get(CardDeck.Type.PULL);
	}

	@Override
	public WriteableCardDeck getCardDeckHand() {
		return cardDecks.get(CardDeck.Type.HAND);
	}

	@Override
	public WriteableCardDeck getCardDeckPlayed() {
		return cardDecks.get(CardDeck.Type.PLAYED);
	}

	@Override
	public WriteableCardDeck getCardDeckStacker() {
		return cardDecks.get(CardDeck.Type.STACKER);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void clean() {
		getActions().set(0);
		getPurchases().set(0);
		getMoney().set(0);

		WriteableCardDeck.move(getCardDeckHand(), getCardDeckStacker());
		WriteableCardDeck.move(getCardDeckPlayed(), getCardDeckStacker());
	}

	@Override
	public WriteableCounter getActions() {
		return counters.get(Counter.Type.REMAIN_ACTIONS);
	}

	@Override
	public WriteableCounter getPurchases() {
		return counters.get(Counter.Type.REMAIN_PURCHASES);
	}

	@Override
	public WriteableCounter getMoney() {
		return counters.get(Counter.Type.REMAIN_MONEY);
	}

	@Override
	public WriteableCounter getVictoryPoints() {
		return counters.get(Counter.Type.SUM_POINTS);
	}

	@Override
	public void pollCards(final int count) {
		Stream.iterate(0, index -> index++).limit(count).forEach(ignore -> {
			if (getCardDeckPull().isEmpty()) {
				// Move the stacker carddeck to pull and shuffle it up
				WriteableCardDeck.move(getCardDeckStacker(), getCardDeckPull());
				getCardDeckPull().shuffle();
			}
			// Get the next card from the pull carddeck
			final Optional<Card> card = getCardDeckPull().poll();
			if (card.isPresent()) {
				getCardDeckHand().add(card.get());
			}
		});
	}
}
