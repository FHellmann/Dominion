/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves.card;

import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;
import edu.hm.cs.fh.dominion.logic.Settings;
import edu.hm.cs.fh.dominion.logic.cards.KingdomCard;
import edu.hm.cs.fh.dominion.logic.moves.BaseMove;
import edu.hm.cs.fh.dominion.logic.moves.CheckFactory;

/**
 * The attack by the militia.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 06.05.2014
 */
public class MilitiaAttack extends BaseMove {
	/**
	 * Creates a new attack militia move.
	 *
	 * @param game
	 *            to reference.
	 * @param player
	 *            who want to act.
	 * @param card
	 *            to play.
	 */
	public MilitiaAttack(final WriteableGame game, final WriteablePlayer player, final Card card) {
		super(game, player, card);
		addCheck(CheckFactory.isCurrentState(State.ATTACK_YIELD));
		addCheck(CheckFactory.isCurrentPlayer());
		addCheck(CheckFactory.isNotAttacker());
		addCheck(CheckFactory.isHandcard());
		addCheck(CheckFactory.isAttackCard(KingdomCard.MILITIA));
		addCheck(CheckFactory.isHandcardSizeBigger(Settings.MILITIA_CARDS_TO_HOLD));
	}

	@Override
	public void onFire() {
		final WriteablePlayer player = getPlayer().get();
		final Card card = getCard().get();
		player.getCardDeckHand().remove(card);
		player.getCardDeckStacker().add(card);
	}
}
