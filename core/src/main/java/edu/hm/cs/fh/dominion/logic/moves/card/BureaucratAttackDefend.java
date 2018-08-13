/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves.card;

import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.database.cards.KingdomCard;
import edu.hm.cs.fh.dominion.database.cards.VictoryCard;
import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableCardDeck;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;
import edu.hm.cs.fh.dominion.logic.moves.BaseMove;
import edu.hm.cs.fh.dominion.logic.moves.check.CheckFactory;
import edu.hm.cs.fh.dominion.logic.moves.check.IsCurrentPlayerCheck;
import edu.hm.cs.fh.dominion.logic.moves.check.IsCurrentStateCheck;

import java.util.stream.Stream;

/**
 * A defend of an attack with the {@link KingdomCard#MOAT}.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 06.05.2014
 */
public class BureaucratAttackDefend extends BaseMove implements ShowCards {
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
	public BureaucratAttackDefend(final WriteableGame game, final WriteablePlayer player, final Card card) {
		super(game, player, card);
		addCheck(new IsCurrentStateCheck(State.ATTACK));
		addCheck(new IsCurrentPlayerCheck());
		addCheck(CheckFactory.isNotAttacker());
		addCheck(CheckFactory.isHandcard());
		addCheck(CheckFactory.isCardType(VictoryCard.class));
		addCheck(CheckFactory.isAttackCard(KingdomCard.BUREAUCRAT));
	}

	@Override
	public void onFire() {
		final WriteablePlayer player = getPlayer().get();
		WriteableCardDeck.move(player.getCardDeckHand(), player.getCardDeckPull(), getCard().get());
		getGame().nextPlayer();
	}

	@Override
	public Stream<Card> getCards() {
		return Stream.of(getCard().orElseThrow(IllegalStateException::new));
	}
}
