/**
 *
 */
package edu.hm.cs.fh.dominion.database.full;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.logic.Settings;

/**
 * A handler for a list of cards.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 29.03.2014
 */
public class CardDeck implements WriteableCardDeck {
	/** card of the carddeck */
	private final List<Card> cards = new LinkedList<>();
	/** the parent changeable */
	private final Changeable changeable;

	/**
	 * Create a new and empty carddeck.
	 *
	 * @param changeable
	 *            to notify.
	 */
	CardDeck(final Changeable changeable) {
		this.changeable = changeable;
	}

	@Override
	public void add(final Card card) {
		if (card == null) {
			throw new IllegalArgumentException("The card can not be null");
		}
		cards.add(0, card);
		changeable.setChanged();
	}

	// @Override
	// public void move(final WriteableCardDeck cardDeck) {
	// if (cardDeck == null) {
	// throw new IllegalArgumentException("The carddeck can not be null");
	// }
	//
	// cards.addAll(cardDeck.stream().collect(Collectors.toList()));
	// cardDeck.clear();
	// changeable.setChanged();
	// }
	//
	// @Override
	// public void move(final WriteableCardDeck cardDeck, final Card card) {
	// if (contains(card)) {
	// remove(card);
	// cardDeck.add(card);
	// changeable.setChanged();
	// }
	// }

	@Override
	public void shuffle() {
		Collections.shuffle(cards, Settings.getRandom());
	}

	@Override
	public void remove(final Card card) {
		if (card == null) {
			throw new IllegalArgumentException("The card can not be null");
		}
		cards.remove(card);
		changeable.setChanged();
	}

	@Override
	public void clear() {
		cards.clear();
		changeable.setChanged();
	}

	@Override
	public Optional<Card> poll() {
		changeable.setChanged();
		// find the last card to poll
		final Optional<Card> optional = cards.stream().skip(cards.stream().skip(1).count()).findFirst();
		if (optional.isPresent()) {
			cards.remove(optional.get());
			changeable.setChanged();
		}
		return optional;
	}

	@Override
	public boolean isEmpty() {
		return cards.isEmpty();
	}

	@Override
	public int size() {
		return cards.size();
	}

	@Override
	public Stream<Card> stream() {
		return cards.stream();
	}

	@Override
	public boolean contains(final Card card) {
		return cards.contains(card);
	}

	@Override
	public String toString() {
		return cards.stream().map(card -> card.getName()).collect(Collectors.joining(", "));
	}

	/**
	 * The different types of CardDecks.
	 *
	 * @author Fabio Hellmann, fhellman@hm.edu
	 * @version 29.03.2014
	 */
	enum Type {
		/** The cards to get next. */
		PULL,
		/** The cards you have played the last round. */
		STACKER,
		/** The cards in your hand. */
		HAND,
		/** The cards you currently palyed. */
		PLAYED
	}
}
