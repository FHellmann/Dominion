/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves.card;

import edu.hm.cs.fh.dominion.database.Settings;
import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.database.cards.KingdomCard;
import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;
import edu.hm.cs.fh.dominion.logic.moves.BaseMove;
import edu.hm.cs.fh.dominion.logic.moves.CheckFactory;

/**
 * A defend of an attack with the {@link KingdomCard#MOAT}.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 06.05.2014
 */
public class FeastAction extends BaseMove {
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
	public FeastAction(final WriteableGame game, final WriteablePlayer player, final Card card) {
		super(game, player, card);
		addCheck(CheckFactory.isCurrentState(State.ACTION_RESOLVE));
		addCheck(CheckFactory.isCurrentPlayer());
		addCheck(CheckFactory.isCardInSupply());
		addCheck(CheckFactory.isCardAvailable());
		addCheck(CheckFactory.isInCardPrice(Settings.FEAST_MAX_CARD_COST));
		addCheck(CheckFactory.isResolveCard(KingdomCard.FEAST));
	}

	@Override
	public void onFire() {
		final WriteablePlayer player = getPlayer().get();
		getGame().addCardToWaste(player.getCardDeckPlayed(), KingdomCard.FEAST);
		getGame().getCardFromSupply(getCard().get(), player.getCardDeckStacker());
		getGame().setState(State.ACTION);
		getGame().popToResolveActionCard();
	}
}
