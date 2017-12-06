/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves.card;

import java.util.stream.Stream;

import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;
import edu.hm.cs.fh.dominion.logic.cards.KingdomCard;
import edu.hm.cs.fh.dominion.logic.moves.BaseMove;
import edu.hm.cs.fh.dominion.logic.moves.CheckFactory;

/**
 * A defend of an attack with the {@link KingdomCard#MOAT}.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 06.05.2014
 */
public class MoatAttackDefend extends BaseMove implements ShowCards {
	/**
	 * Creates a new attack reaction move.
	 *
	 * @param game
	 *            to reference.
	 * @param player
	 *            who want to act.
	 * @param card
	 *            to play.
	 */
	public MoatAttackDefend(final WriteableGame game, final WriteablePlayer player, final Card card) {
		super(game, player, card);
		addCheck(CheckFactory.isCurrentState(State.ATTACK));
		addCheck(CheckFactory.isCurrentPlayer());
		addCheck(CheckFactory.isNotAttacker());
		addCheck(CheckFactory.isHandcard());
		addCheck(CheckFactory.isAttackCard(KingdomCard.WITCH, KingdomCard.MILITIA));
	}

	@Override
	public void onFire() {
		// This player is save. Let the next one catch it.
		getGame().nextPlayer();
	}

	@Override
	public Stream<Card> getCards() {
		return Stream.of(getCard().get());
	}
}
