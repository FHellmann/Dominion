/**
 *
 */
package edu.hm.cs.fh.dominion.resources;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import edu.hm.cs.fh.dominion.database.cards.Card.Type;

/**
 * The handler for loading the content from resource files.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 11.04.2014
 */
public class ResourceFilesHandler implements Resources {
	/** A seperator for seperating the name and key. */
	private static final String SEPERATOR = "_";
	/** The key for text. */
	private static final String TEXT = "text";
	/** The key for coints. */
	private static final String COINTS = "coints";
	/** The key for types. */
	private static final String TYPES = "types";
	/** The key for costs. */
	private static final String COST = "cost";
	/** The key for points. */
	private static final String POINTS = "points";
	/** A seperator associated by the OS. */
	private static final String ESCAPED_FILE_SEPERATOR = Character.toString(File.separatorChar)
			+ Character.toString(File.separatorChar);
	/** The fileprefix for card attributes. */
	private static final String DOMINION_CARD = "Dominion_Card";
	/** The fileprefix for languages. */
	private static final String DOMINION_LANGUAGE = "Dominion_Language";
	/** The supported languages. */
	private static final List<Locale> SUPPORTED_LOCALES = Arrays.asList(Locale.GERMAN, Locale.ENGLISH);
	/** The default language. */
	private static final Locale DEFAULT_LOCALE = Locale.GERMAN;
	/** The resourcebundle for the card attributes. */
	private final ResourceBundle bundleConstants;
	/** The resourcebundle for the i18n. */
	private final ResourceBundle bundleI18N;

	/**
	 * Create to get the card settings and language.
	 *
	 * @throws MissingResourceException
	 */
	ResourceFilesHandler() throws MissingResourceException {
		Locale locale = Locale.getDefault();
		if (!SUPPORTED_LOCALES.contains(locale)) {
			locale = DEFAULT_LOCALE;
		}

		// generate the path to the *.properties files
		final String path = getClass().getPackage().getName().replaceAll("\\.", ESCAPED_FILE_SEPERATOR);

		// create the ResourceBundle
		bundleConstants = ResourceBundle.getBundle(path + ESCAPED_FILE_SEPERATOR + DOMINION_CARD);
		bundleI18N = ResourceBundle.getBundle(path + ESCAPED_FILE_SEPERATOR + DOMINION_LANGUAGE, locale);
	}

	@Override
	public Optional<String> getTranslation(final String key) {
		return Optional.ofNullable(bundleI18N.getString(key));
	}

	@Override
	public Optional<CardMetaData> getMetaData(final String name) {
		final CardMetaData metaData;
		final List<Type> typeList = getTypes(name);
		final String nameI18n = getName(name);
		final int cost = getCost(name);
		if (hasValue(name, POINTS)) {
			metaData = new CardMetaData(nameI18n, cost, getPoints(name), typeList);
		} else if (hasValue(name, COINTS)) {
			metaData = new CardMetaData(nameI18n, cost, getCoints(name), typeList);
		} else {
			metaData = new CardMetaData(nameI18n, cost, getText(name), typeList);
		}
		return Optional.of(metaData);
	}

	/**
	 * Lookup the internationalisation of the cards name.
	 *
	 * @param name
	 *            of the card (lowercase).
	 * @return the name of the card.
	 */
	private String getName(final String name) {
		return bundleI18N.getString(name);
	}

	/**
	 * Lookup the internationalisation of the cards text.
	 *
	 * @param name
	 *            of the card (lowercase).
	 * @return the text of the card.
	 */
	private String getText(final String name) {
		return bundleI18N.getString(name + SEPERATOR + TEXT);
	}

	/**
	 * Lookup the cost of a card in the properties file.
	 *
	 * @param name
	 *            of the card (lowercase).
	 * @return the cost of the card.
	 */
	private int getCost(final String name) {
		return getValue(name, COST);
	}

	/**
	 * Lookup the points of a card in the properties file.
	 *
	 * @param name
	 *            of the card (lowercase).
	 * @return the points of the card.
	 */
	private int getPoints(final String name) {
		return getValue(name, POINTS);
	}

	/**
	 * Lookup the coints of a card in the properties file.
	 *
	 * @param name
	 *            of the card (lowercase).
	 * @return the coints of the card.
	 */
	private int getCoints(final String name) {
		return getValue(name, COINTS);
	}

	/**
	 * Lookup the types of a card in the properties file.
	 *
	 * @param name
	 *            of the card (lowercase).
	 * @return the types of the card.
	 */
	private List<Type> getTypes(final String name) {
		final List<String> baseTypes = new ArrayList<>();
		final String typeOrTypes = bundleConstants.getString(name + SEPERATOR + TYPES);
		if (typeOrTypes.contains(" ")) {
			// has more then one type!
			baseTypes.addAll(Arrays.asList(typeOrTypes.split(" ")));
		} else {
			// has only one type
			baseTypes.add(typeOrTypes);
		}

		// Convert type as String to type as Type
		return baseTypes.stream().map(typeName -> Type.valueOf(typeName.toUpperCase())).collect(Collectors.toList());
	}

	/**
	 * Lookup if the constant value by the name-key-pair exits.
	 *
	 * @param name
	 *            of the card.
	 * @param key
	 *            to map.
	 * @return <code>true</code> if the value exits.
	 */
	private boolean hasValue(final String name, final String key) {
		return bundleConstants.containsKey(name + SEPERATOR + key);
	}

	/**
	 * Lookup the constant value by the name-key-pair.
	 *
	 * @param name
	 *            of the card.
	 * @param key
	 *            to map.
	 * @return the constant value.
	 */
	private int getValue(final String name, final String key) {
		return Integer.parseInt(bundleConstants.getString(name + SEPERATOR + key));
	}
}
