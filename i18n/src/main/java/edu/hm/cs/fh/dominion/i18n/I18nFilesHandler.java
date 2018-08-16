/**
 *
 */
package edu.hm.cs.fh.dominion.i18n;

import java.util.*;

/**
 * The handler for loading the content from resource files.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 11.04.2014
 */
public class I18nFilesHandler implements I18N {
    /**
     * The fileprefix for languages.
     */
    private static final String DOMINION_LANGUAGE = "Dominion_Language";
    /**
     * The supported languages.
     */
    private static final List<Locale> SUPPORTED_LOCALES = Arrays.asList(Locale.GERMAN, Locale.ENGLISH);
    /**
     * The default language.
     */
    private static final Locale DEFAULT_LOCALE = Locale.GERMAN;
    /**
     * The resourcebundle for the i18n.
     */
    private final ResourceBundle bundleI18N;

    /**
     * Create to get the card settings and language.
     *
     * @throws MissingResourceException is thrown when the resource file could not be found.
     */
    I18nFilesHandler() throws MissingResourceException {
        Locale locale = Locale.getDefault();
        if (!SUPPORTED_LOCALES.contains(locale)) {
            locale = DEFAULT_LOCALE;
        }

        // create the ResourceBundle
        bundleI18N = ResourceBundle.getBundle(DOMINION_LANGUAGE, locale);
    }

    @Override
    public Optional<String> getTranslation(final String key) {
        if (bundleI18N.containsKey(key)) {
            return Optional.of(bundleI18N.getString(key));
        }
        return Optional.empty();
    }
}
