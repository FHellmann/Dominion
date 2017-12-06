/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves;

import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;

/**
 * A move to go to the next phase.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 24.04.2014
 */
public class NoAction extends BaseMove {
	/**
	 * Creates a new no action move.
	 *
	 * @param game
	 *            to reference.
	 * @param player
	 *            who want to act.
	 */
	public NoAction(final WriteableGame game, final WriteablePlayer player) {
		super(game, player);
		addCheck(CheckFactory.isCurrentState(State.ACTION));
		addCheck(CheckFactory.isCurrentPlayer());
	}

	@Override
	public void onFire() {
		getGame().setState(State.PURCHASE);
	}
}
