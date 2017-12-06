/**
 *
 */
package edu.hm.cs.fh.dominion.resources;

import java.util.Optional;

/**
 * The resource of the game cards.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 11.04.2014
 */
public interface Resources {
	/**
	 * Looks up the metadata for a card.
	 *
	 * @param name
	 *            of the card.
	 * @return the metadata of the cards name.
	 */
	Optional<CardMetaData> getMetaData(String name);

	/**
	 * Localize the text of the key.
	 *
	 * @param key
	 *            of the text.
	 * @return the localized text.
	 */
	Optional<String> getTranslation(String key);
}
