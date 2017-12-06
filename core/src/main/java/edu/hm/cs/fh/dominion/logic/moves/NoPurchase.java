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
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 24.04.2014
 */
public class NoPurchase extends BaseMove {
	/**
	 * Creates a new no purchase move.
	 *
	 * @param game
	 *            to reference.
	 * @param player
	 *            who want to act.
	 */
	public NoPurchase(final WriteableGame game, final WriteablePlayer player) {
		super(game, player);
		addCheck(CheckFactory.isCurrentState(State.PURCHASE));
		addCheck(CheckFactory.isCurrentPlayer());
	}

	@Override
	public void onFire() {
		getGame().setState(State.CLEANUP);
	}
}
