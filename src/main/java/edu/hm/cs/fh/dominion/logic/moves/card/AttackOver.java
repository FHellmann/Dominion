/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves.card;

import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;
import edu.hm.cs.fh.dominion.logic.moves.BaseMove;
import edu.hm.cs.fh.dominion.logic.moves.CheckFactory;

/**
 * A action to turn the State to {@link State#ACTION}.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 09.05.2014
 */
public class AttackOver extends BaseMove {
	/**
	 * Creates a new attack reaction move.
	 *
	 * @param game
	 *            to reference.
	 * @param player
	 *            who want to act.
	 */
	public AttackOver(final WriteableGame game, final WriteablePlayer player) {
		super(game, player);
		addCheck(CheckFactory.isCurrentState(State.ATTACK));
		addCheck(CheckFactory.isCurrentPlayer());
		addCheck(CheckFactory.isAttacker());
	}

	@Override
	public void onFire() {
		// Let the attacker do his turn again
		getGame().setState(State.ACTION);
		getGame().setAttackCard(null);
		getGame().setAttacker(null);
	}
}
