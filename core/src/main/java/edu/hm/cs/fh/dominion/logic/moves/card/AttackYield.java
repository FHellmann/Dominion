/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves.card;

import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;
import edu.hm.cs.fh.dominion.logic.moves.BaseMove;
import edu.hm.cs.fh.dominion.logic.moves.check.CheckFactory;
import edu.hm.cs.fh.dominion.logic.moves.check.IsCurrentPlayerCheck;
import edu.hm.cs.fh.dominion.logic.moves.check.IsCurrentStateCheck;

/**
 * A action to turn the State to {@link State#ATTACK_YIELD}.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 09.05.2014
 */
public class AttackYield extends BaseMove {
	/**
	 * Creates a new attack reaction move.
	 *
	 * @param game
	 *            to reference.
	 * @param player
	 *            who want to act.
	 */
	public AttackYield(final WriteableGame game, final WriteablePlayer player) {
		super(game, player);
		addCheck(new IsCurrentStateCheck(State.ATTACK));
		addCheck(new IsCurrentPlayerCheck());
		addCheck(CheckFactory.isNotAttacker());
	}

	@Override
	public void onFire() {
		getGame().setState(State.ATTACK_YIELD);
	}
}
