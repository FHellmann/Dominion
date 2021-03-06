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
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 06.05.2014
 */
public class BureaucratAttackYield extends BaseMove implements ShowCards {
	/**
	 * Creates a new attack reaction move.
	 *
	 * @param game
	 *            to reference.
	 * @param player
	 *            who want to act.
	 */
	public BureaucratAttackYield(final WriteableGame game, final WriteablePlayer player) {
		super(game, player);
		addCheck(CheckFactory.isCurrentState(State.ATTACK_YIELD));
		addCheck(CheckFactory.isCurrentPlayer());
		addCheck(CheckFactory.isNotAttacker());
		addCheck(CheckFactory.isAttackCard(KingdomCard.BUREAUCRAT));
	}

	@Override
	public void onFire() {
		getGame().nextPlayer();
		getGame().setState(State.ATTACK);
	}

	@Override
	public Stream<Card> getCards() {
		return getPlayer().get().getCardDeckHand().stream();
	}
}
