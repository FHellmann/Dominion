/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves;

import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.database.cards.Card.Type;
import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableCardDeck;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;
import edu.hm.cs.fh.dominion.logic.cards.KingdomCard;

/**
 * A move to play a action card.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 24.04.2014
 */
public class PlayActionCard extends BaseMove {
	/**
	 * Creates a new play action card move.
	 *
	 * @param game
	 *            to reference.
	 * @param player
	 *            who want to act.
	 * @param card
	 *            to play.
	 */
	public PlayActionCard(final WriteableGame game, final WriteablePlayer player, final Card card) {
		super(game, player, card);
		addCheck(CheckFactory.isCurrentState(State.ACTION));
		addCheck(CheckFactory.isCurrentPlayer());
		addCheck(CheckFactory.isCardType(KingdomCard.class));
		addCheck(CheckFactory.hasActionLeft());
		addCheck(CheckFactory.isHandcard());
		addCheck(CheckFactory.hasType(Card.Type.ACTION));
	}

	@Override
	public void onFire() {
		final WriteablePlayer player = getPlayer().get();
		final Card card = getCard().get();

		((KingdomCard) card).resolve(getGame());

		WriteableCardDeck.move(player.getCardDeckHand(), player.getCardDeckPlayed(), card);
		player.getActions().decrement();

		if (getGame().getState() == State.ACTION_RESOLVE) {
			getGame().addToResolveActionCard(card);
		}
		if (card.hasType(Type.ATTACK)) {
			getGame().setState(State.ATTACK);
			getGame().setAttacker(player);
			getGame().setAttackCard(card);
			getGame().nextPlayer();
		}
	}
}
