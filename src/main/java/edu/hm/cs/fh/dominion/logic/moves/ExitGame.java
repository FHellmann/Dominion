/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves;

import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;

/**
 * A move to exit the game.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 24.04.2014
 */
public class ExitGame extends BaseMove {
	/**
	 * Creates a new exit game move.
	 *
	 * @param game
	 *            to reference.
	 */
	public ExitGame(final WriteableGame game) {
		super(game);
		addCheck(CheckFactory.isCurrentState(State.RESULTS));
	}

	@Override
	public void onFire() {
		getGame().setState(State.QUIT);
	}
}
