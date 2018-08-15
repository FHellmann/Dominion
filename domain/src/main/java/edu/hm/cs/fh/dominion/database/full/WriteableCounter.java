/**
 *
 */
package edu.hm.cs.fh.dominion.database.full;

import edu.hm.cs.fh.dominion.database.ReadonlyCounter;

/**
 * A layer to read and write the counter.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 28.04.2014
 */
public interface WriteableCounter extends ReadonlyCounter {

    /**
     * Reduce the counter by one.
     *
     * @return the current counter after the reduction.
     */
    int decrement();

    /**
     * Increment the counter by one.
     *
     * @return the current counter after the incrementation.
     */
    int increment();

    /**
     * Add a number (could also be a negative number).
     *
     * @param number to add.
     * @return the current counter after the incrementation.
     */
    int add(int number);

    /**
     * Set a number.
     *
     * @param number to set.
     * @return the current counter value.
     */
    int set(int number);

}