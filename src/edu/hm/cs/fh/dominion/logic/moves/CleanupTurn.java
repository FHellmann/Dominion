/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves;

import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;
import edu.hm.cs.fh.dominion.logic.Settings;

/**
 * A move to clean up players turn and check if game is over or not.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 17.04.2014
 */
public class CleanupTurn extends BaseMove {
	/**
	 * Creates a new cleanup turn move.
	 *
	 * @param game
	 *            to modify.
	 * @param player
	 *            who want's to play the move.
	 */
	public CleanupTurn(final WriteableGame game, final WriteablePlayer player) {
		super(game, player, null);
		addCheck(CheckFactory.isCurrentState(State.CLEANUP));
		addCheck(CheckFactory.isCurrentPlayer());
	}

	@Override
	public void onFire() {
		// check if game is already over
		if (getGame().isOver()) {
			getGame().setState(State.OVER);
		} else {
			getGame().getCurrentPlayer().clean();
			final WriteablePlayer currentPlayer = getGame().getCurrentPlayer();
			currentPlayer.pollCards(Settings.AMOUNT_HAND_CARDS);
			currentPlayer.getActions().increment();
			currentPlayer.getPurchases().increment();
			getGame().setState(State.ACTION);
			getGame().nextPlayer();
		}
	}
}
