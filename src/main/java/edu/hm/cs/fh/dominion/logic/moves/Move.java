/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A possible action of a player.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 12.04.2014
 */
public interface Move {
	/**
	 * Tests if the move is possible or not.
	 *
	 * @return <code>true</code> if the move is possible to execute.
	 */
	MoveResult test();

	/**
	 * The move is going to be executed.
	 *
	 * @return the result.
	 */
	MoveResult fire();

	/**
	 * Filters from all moves of the list the possible ones.
	 *
	 * @param moves
	 *            to filter.
	 * @return a list with all possible moves.
	 */
	static List<Move> filterPossibleMoves(final List<Move> moves) {
		return moves.parallelStream().filter(move -> move.test().isPossible()).collect(Collectors.toList());
	}
}
