/**
 *
 */
package edu.hm.cs.fh.dominion.database.full;

/**
 * Something can be changed.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 29.03.2014
 */
public interface Changeable {
	/**
	 * Notify the changeable that something changed.
	 */
	void setChanged();
}
