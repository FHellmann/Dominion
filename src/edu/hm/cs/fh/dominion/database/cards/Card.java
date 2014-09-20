package edu.hm.cs.fh.dominion.database.cards;

import java.util.List;
import java.util.stream.Stream;

import edu.hm.cs.fh.dominion.resources.CardMetaData;
import edu.hm.cs.fh.dominion.resources.ResourceDelegator;

/**
 * A definition of every card.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 25.03.2014
 */
public interface Card {
	/**
	 * Get the meta data of this card from the {@link ResourceDelegator}.
	 *
	 * @return the meta data.
	 */
	default CardMetaData getMetaData() {
		return ResourceDelegator.getCardMetaData(toString().toLowerCase());
	}

	/**
	 * Get the name of this card.
	 *
	 * @return the card name.
	 */
	default String getName() {
		return getMetaData().getName();
	}

	/**
	 * Get the costs of this card.
	 *
	 * @return the card costs.
	 */
	default int getCost() {
		return getMetaData().getCost();
	}

	/**
	 * Get the main type of this card.
	 *
	 * @return the main type.
	 */
	default Type getType() {
		return getMetaData().getTypes().stream().findFirst().get();
	}

	/**
	 * Has the the card the type.
	 *
	 * @param type
	 *            to check.
	 *
	 * @return <code>true</code> if this card contains the type.
	 */
	default boolean hasType(final Type type) {
		return getMetaData().getTypes().contains(type);
	}

	/**
	 * Find all cards matching the type.
	 *
	 * @param cards
	 *            to search in.
	 * @param type
	 *            to match.
	 * @return a list with matching cards.
	 */
	static Stream<Card> findAll(final List<Card> cards, final Type type) {
		return cards.parallelStream().filter(card -> card.hasType(type));
	}

	/**
	 * Different types of the cards.
	 *
	 * @author Fabio Hellmann, fhellman@hm.edu
	 * @version 25.03.2014
	 */
	enum Type {
		/** Points */
		VICTORY,
		/** Money */
		TREASURY,
		/** Action */
		ACTION,
		/** Reaction */
		REACTION,
		/** Attack */
		ATTACK,
		/** Curse */
		CURSE;

		/** The name of the type. */
		private final String name;

		/**
		 * Creates a new type.
		 */
		private Type() {
			name = ResourceDelegator.getI18N(toString().toLowerCase());
		}

		/**
		 * Get the type name.
		 *
		 * @return the name.
		 */
		public String getName() {
			return name;
		}
	}
}
