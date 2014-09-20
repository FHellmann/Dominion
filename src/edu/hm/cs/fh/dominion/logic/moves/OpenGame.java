/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves;

import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;

/**
 * A move to open the game.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 15.04.2014
 */
public class OpenGame extends BaseMove {
	/**
	 * Creates a new open game move.
	 *
	 * @param game
	 *            to reference.
	 */
	public OpenGame(final WriteableGame game) {
		super(game);
		addCheck(CheckFactory.isCurrentState(State.INITIALIZE));
		addCheck(CheckFactory.isInPlayerRange());
	}

	@Override
	public void onFire() {
		getGame().setCurrentPlayer(getGame().getRwPlayers().findFirst().get());
		getGame().setState(State.SETUP);
	}
}
