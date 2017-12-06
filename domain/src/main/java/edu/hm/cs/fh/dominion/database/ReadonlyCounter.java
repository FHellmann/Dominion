/**
 *
 */
package edu.hm.cs.fh.dominion.database;


/**
 * A layer to read the counter.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 28.04.2014
 */
public interface ReadonlyCounter {

	/**
	 * The current counter value.
	 *
	 * @return the count.
	 */
	int getCount();

	/**
	 * The lower limit the counter will not undercut.
	 *
	 * @return the lower limit.
	 */
	int getLowerLimit();

}