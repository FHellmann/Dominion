/**
 *
 */
package edu.hm.cs.fh.dominion.resources;

import java.util.Arrays;
import java.util.List;

import edu.hm.cs.fh.dominion.database.cards.Card.Type;

/**
 * The metadata of a card contains all their attributes content.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 11.04.2014
 */
public class CardMetaData {
	/** Name of the card. (can not be null) */
	private final String name;
	/** Text of the card. (can be null for victory and treasury cards not for kingdom cards) */
	private final String text;
	/** Cost of the card. (>= 0) */
	private final int cost;
	/** Points (for victory cards) or coints (for treasury) of the card. (for kingdom cards... = 0) */
	private final int cointsOrPoints;
	/** Types of the card. (Min. size = 1) */
	private final List<Type> types;

	/**
	 * Creates a new cardmetadata. (Kingdom)
	 *
	 * @param name
	 *            of the card.
	 * @param text
	 *            of the card.
	 * @param cost
	 *            of the card.
	 * @param types
	 *            of the card.
	 */
	public CardMetaData(final String name, final int cost, final String text, final Type... types) {
		this(name, text, cost, 0, Arrays.asList(types));
	}

	/**
	 * Creates a new cardmetadata. (Kingdom)
	 *
	 * @param name
	 *            of the card.
	 * @param text
	 *            of the card.
	 * @param cost
	 *            of the card.
	 * @param types
	 *            of the card.
	 */
	public CardMetaData(final String name, final int cost, final String text, final List<Type> types) {
		this(name, text, cost, 0, types);
	}

	/**
	 * Creates a new cardmetadata. (Victory, Treasury)
	 *
	 * @param name
	 *            of the card.
	 * @param cost
	 *            of the card.
	 * @param cointsOrPoints
	 *            of the card.
	 * @param types
	 *            of the card.
	 */
	public CardMetaData(final String name, final int cost, final int cointsOrPoints, final Type... types) {
		this(name, null, cost, cointsOrPoints, Arrays.asList(types));
	}

	/**
	 * Creates a new cardmetadata. (Victory, Treasury)
	 *
	 * @param name
	 *            of the card.
	 * @param cost
	 *            of the card.
	 * @param cointsOrPoints
	 *            of the card.
	 * @param types
	 *            of the card.
	 */
	public CardMetaData(final String name, final int cost, final int cointsOrPoints, final List<Type> types) {
		this(name, null, cost, cointsOrPoints, types);
	}

	/**
	 * Creates a new cardmetadata.
	 *
	 * @param name
	 *            of the card.
	 * @param text
	 *            of the card.
	 * @param cost
	 *            of the card.
	 * @param cointsOrPoints
	 *            of the card.
	 * @param types
	 *            of the card.
	 */
	public CardMetaData(final String name, final String text, final int cost, final int cointsOrPoints,
			final List<Type> types) {
		this.name = name;
		this.text = text;
		this.cost = cost;
		this.cointsOrPoints = cointsOrPoints;
		this.types = types;
	}

	/**
	 * The card attribute name.
	 *
	 * @return the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * The card attribute text. (Kingdom only)
	 *
	 * @return the text.
	 */
	public String getText() {
		return text;
	}

	/**
	 * The card attribute cost.
	 *
	 * @return the cost.
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * The card attribute coints (Treasury) or points (Victory).
	 *
	 * @return the coints or points.
	 */
	public int getCointsOrPoints() {
		return cointsOrPoints;
	}

	/**
	 * The card attribute types.
	 *
	 * @return the types.
	 */
	public List<Type> getTypes() {
		return types;
	}
}
