/**
 *
 */
package edu.hm.cs.fh.dominion.i18n;

import java.util.Optional;

/**
 * The resource of the game cards.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 11.04.2014
 */
interface I18N {
    /**
     * Localize the text of the key.
     *
     * @param key of the text.
     * @return the localized text.
     */
    Optional<String> getTranslation(String key);
}
