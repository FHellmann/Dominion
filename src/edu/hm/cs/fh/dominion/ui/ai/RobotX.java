/**
 *
 */
package edu.hm.cs.fh.dominion.ui.ai;

import java.util.List;
import java.util.Observable;
import java.util.Random;

import edu.hm.cs.fh.dominion.database.ReadonlyGame;
import edu.hm.cs.fh.dominion.logic.Logic;
import edu.hm.cs.fh.dominion.logic.moves.Move;
import edu.hm.cs.fh.dominion.ui.AbstractRegisteredPlayer;

/**
 * A robot player which act randomly.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 21.04.2014
 */
public class RobotX extends AbstractRegisteredPlayer {
	/**
	 * Creates a new robotx.
	 *
	 * @param game
	 *            of datastoreage.
	 * @param logic
	 *            for every logical check.
	 * @param name
	 *            of the robot.
	 */
	public RobotX(final ReadonlyGame game, final Logic logic, final String name) {
		super(game, logic, name);
	}

	@Override
	public Move selectMove(final List<Move> moves) {
		// generate a random int value between 0 and moves.size
		final Random random = new Random();
		final int randomInt = random.nextInt(moves.size());
		// find first random item
		return moves.stream().skip(randomInt).findFirst().get();
	}

	@Override
	public void update(final Observable observable, final Object object) {
		// Ignores every update call
	}
}
