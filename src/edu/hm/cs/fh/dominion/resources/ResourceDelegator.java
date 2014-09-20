/**
 *
 */
package edu.hm.cs.fh.dominion.resources;

import java.util.MissingResourceException;
import java.util.Optional;

/**
 * Delegation handler to manage the resources need for the constants and i18n.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 11.04.2014
 */
public final class ResourceDelegator implements Resources {
	/** The resource client. */
	private Resources resource;

	/**
	 * Creates a new resource delegation handler.
	 */
	private ResourceDelegator() {
		try {
			resource = new ResourceFilesHandler();
		} catch (final MissingResourceException e) {
			resource = new ResourceConstantsHandler();
		}
	}

	@Override
	public Optional<CardMetaData> getMetaData(final String name) {
		return resource.getMetaData(name);
	}

	@Override
	public Optional<String> getTranslation(final String key) {
		return resource.getTranslation(key);
	}

	/**
	 * Generates a instance of the {@link ResourceDelegator}. Get the metadata of a card by name.
	 *
	 * @param name
	 *            of the card.
	 * @return the metadata of the card.
	 */
	public static CardMetaData getCardMetaData(final String name) {
		return new ResourceDelegator().getMetaData(name).get();
	}

	/**
	 * Localize the text of the key.
	 *
	 * @param key
	 *            of the text.
	 * @param args
	 *            for placeholder in the text. (optional)
	 * @return the localized text.
	 */
	public static String getI18N(final String key, final Object... args) {
		return String.format(new ResourceDelegator().getTranslation(key).orElse("Undefined key >" + key + "<"), args);
	}
}
