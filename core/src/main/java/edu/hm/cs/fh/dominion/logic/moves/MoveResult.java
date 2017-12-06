/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves;

/**
 * Contains the result of a move.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 12.04.2014
 */
public class MoveResult {
	/** Is the move possible? */
	private final boolean possible;
	/** If the move is not possible, there is a message. */
	private final String msg;

	/**
	 * Creates a new move result.
	 *
	 * @param possible
	 *            (<code>true</code>) or inpossible (<code>false</code>) move.
	 * @param msg
	 *            if the move is inpossible otherwise <code>null</code>.
	 */
	public MoveResult(final boolean possible, final String msg) {
		this.possible = possible;
		this.msg = possible ? "" : msg;
	}

	/**
	 * If the move is possible (<code>true</code>) otherwise it is inpossible (<code>false</code>).
	 *
	 * @return the isPossible.
	 */
	public boolean isPossible() {
		return possible;
	}

	/**
	 * Is null if the move is possible.
	 *
	 * @return the msg.
	 */
	public String getMsg() {
		return msg;
	}
}
