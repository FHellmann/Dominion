/**
 *
 */
package edu.hm.cs.fh.dominion.database.full;

/**
 * A counter to handle the decrement level of a value.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 29.03.2014
 */
public class Counter implements WriteableCounter {
	/** The parents changeable. */
	private final Changeable changeable;
	/** The lower limit of the counter. */
	private final int lowerLimit;
	/** The current count of the counter. */
	private int count;

	/**
	 * Creates a new counter with the default lower limit and start value 0.
	 *
	 * @param changeable
	 *            of the parent.
	 */
	Counter(final Changeable changeable) {
		this(changeable, 0, 0);
	}

	/**
	 * Creates a new counter with the default lower limit 0.
	 *
	 * @param changeable
	 *            of the parent.
	 * @param startValue
	 *            of the counter.
	 */
	Counter(final Changeable changeable, final int startValue) {
		this(changeable, 0, startValue);
	}

	/**
	 * Creates a new counter.
	 *
	 * @param changeable
	 *            of the parent.
	 * @param lowerLimit
	 *            of the counter.
	 * @param startValue
	 *            of the counter.
	 */
	Counter(final Changeable changeable, final int lowerLimit, final int startValue) {
		this.changeable = changeable;
		this.lowerLimit = lowerLimit;
		count = startValue;
	}

	@Override
	public int decrement() {
		add(-1);
		return count;
	}

	@Override
	public int increment() {
		return add(1);
	}

	@Override
	public int add(final int number) {
		set(count + number);
		changeable.setChanged();
		return count;
	}

	@Override
	public int set(final int number) {
		if (number < lowerLimit) {
			throw new IllegalStateException("The counter can not be lower then the lowerlimit(=" + lowerLimit + ")");
		}
		count = number;
		changeable.setChanged();
		return count;
	}

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public int getLowerLimit() {
		return lowerLimit;
	}

	/**
	 * The different types of Counters.
	 *
	 * @author Fabio Hellmann, fhellman@hm.edu
	 * @version 29.03.2014
	 */
	enum Type {
		/** The rest of actions the player has. */
		REMAIN_ACTIONS,
		/** The rest of purchases the player has. */
		REMAIN_PURCHASES,
		/** The rest of money the player has. */
		REMAIN_MONEY,
		/** The rest of points the player has. */
		SUM_POINTS
	}
}
