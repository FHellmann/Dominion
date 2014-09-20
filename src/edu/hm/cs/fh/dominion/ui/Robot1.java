/**
 *
 */
package edu.hm.cs.fh.dominion.ui;

import java.util.List;
import java.util.Observable;

import edu.hm.cs.fh.dominion.database.ReadonlyGame;
import edu.hm.cs.fh.dominion.logic.Logic;
import edu.hm.cs.fh.dominion.logic.moves.Move;

/**
 * A robot player which choose every time the first move.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 21.04.2014
 */
public class Robot1 extends AbstractRegisteredPlayer {

	/**
	 * Creates a new robot1.
	 *
	 * @param game
	 *            of datastoreage.
	 * @param logic
	 *            for every logical check.
	 * @param name
	 *            of the robot.
	 */
	public Robot1(final ReadonlyGame game, final Logic logic, final String name) {
		super(game, logic, name);
	}

	@Override
	public Move selectMove(final List<Move> moves) {
		return moves.stream().findFirst().get();
	}

	@Override
	public void update(final Observable observable, final Object object) {
		// Ignores every update call
	}
}
