/**
 *
 */
package edu.hm.cs.fh.dominion.database.full;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.hm.cs.fh.dominion.database.cards.Card;

/**
 * A handler for a list of cards.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
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

	@Override
	public void shuffle(final Random random) {
		Collections.shuffle(cards, random);
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
		return cards.stream().map(Card::getName).collect(Collectors.joining(", "));
	}

	/**
	 * The different types of CardDecks.
	 *
	 * @author Fabio Hellmann, info@fabio-hellmann.de
	 * @version 29.03.2014
	 */
	enum Type {
		/** The cards to get next. */
		PULL,
		/** The cards you have played the last round. */
		STACKER,
		/** The cards in your hand. */
		HAND,
		/** The cards you have now. */
		PLAYED
	}
}
