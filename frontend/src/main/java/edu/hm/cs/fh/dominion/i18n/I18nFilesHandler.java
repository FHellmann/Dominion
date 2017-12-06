/**
 *
 */
package edu.hm.cs.fh.dominion.i18n;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The handler for loading the content from resource files.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 11.04.2014
 */
public class I18nFilesHandler implements I18N {
	/** A seperator associated by the OS. */
	private static final String ESCAPED_FILE_SEPERATOR = Character.toString(File.separatorChar)
			+ Character.toString(File.separatorChar);
	/** The fileprefix for languages. */
	private static final String DOMINION_LANGUAGE = "Dominion_Language";
	/** The supported languages. */
	private static final List<Locale> SUPPORTED_LOCALES = Arrays.asList(Locale.GERMAN, Locale.ENGLISH);
	/** The default language. */
	private static final Locale DEFAULT_LOCALE = Locale.GERMAN;
	/** The resourcebundle for the i18n. */
	private final ResourceBundle bundleI18N;

	/**
	 * Create to get the card settings and language.
	 *
	 * @throws MissingResourceException
	 */
	I18nFilesHandler() throws MissingResourceException {
		Locale locale = Locale.getDefault();
		if (!SUPPORTED_LOCALES.contains(locale)) {
			locale = DEFAULT_LOCALE;
		}

		// generate the path to the *.properties files
		final String path = getClass().getPackage().getName().replaceAll("\\.", ESCAPED_FILE_SEPERATOR);

		// create the ResourceBundle
		bundleI18N = ResourceBundle.getBundle(path + ESCAPED_FILE_SEPERATOR + DOMINION_LANGUAGE, locale);
	}

	@Override
	public Optional<String> getTranslation(final String key) {
		if (bundleI18N.containsKey(key)) {
			return Optional.of(bundleI18N.getString(key));
		}
		return Optional.empty();
	}
}
