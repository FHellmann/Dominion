/**
 *
 */
package edu.hm.cs.fh.dominion.database.full;

/**
 * Something can be changed.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 29.03.2014
 */
interface Changeable {
    /**
     * Notify the changeable that something changed.
     */
    void setChanged();
}
