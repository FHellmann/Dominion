/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves.card;

import edu.hm.cs.fh.dominion.database.cards.KingdomCard;
import edu.hm.cs.fh.dominion.database.cards.VictoryCard;
import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;
import edu.hm.cs.fh.dominion.logic.moves.BaseMove;
import edu.hm.cs.fh.dominion.logic.moves.CheckFactory;

/**
 * The attack by the witch.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 06.05.2014
 */
public class WitchAttack extends BaseMove {
	/**
	 * Creates a new attack witch move.
	 *
	 * @param game
	 *            to reference.
	 * @param player
	 *            who want to act.
	 */
	public WitchAttack(final WriteableGame game, final WriteablePlayer player) {
		super(game, player);
		addCheck(CheckFactory.isCurrentState(State.ATTACK_YIELD));
		addCheck(CheckFactory.isCurrentPlayer());
		addCheck(CheckFactory.isNotAttacker());
		addCheck(CheckFactory.isAttackCard(KingdomCard.WITCH));
	}

	@Override
	public void onFire() {
		if (getGame().getSupplyCardCount(VictoryCard.CURSE) > 0) {
			getGame().getCardFromSupply(VictoryCard.CURSE, getPlayer().get().getCardDeckStacker());
		}
		getGame().setState(State.ATTACK);
		getGame().nextPlayer();
	}
}
