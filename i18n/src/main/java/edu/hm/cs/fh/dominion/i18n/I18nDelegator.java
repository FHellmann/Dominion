/**
 *
 */
package edu.hm.cs.fh.dominion.i18n;

import java.util.MissingResourceException;
import java.util.Optional;

/**
 * Delegation handler to manage the i18n need for the constants and i18n.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 11.04.2014
 */
public final class I18nDelegator implements I18N {
	/** The resource client. */
	private I18N resource;

	/**
	 * Creates a new resource delegation handler.
	 */
	private I18nDelegator() {
		try {
			resource = new I18nFilesHandler();
		} catch (final MissingResourceException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Optional<String> getTranslation(final String key) {
		return resource.getTranslation(key);
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
		return String.format(new I18nDelegator().getTranslation(key).orElse("Undefined key >" + key + "<"), args);
	}
}
