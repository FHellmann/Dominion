/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves;

import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.database.cards.VictoryCard;
import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;
import edu.hm.cs.fh.dominion.logic.Settings;

/**
 * A move to buy a card.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 12.04.2014
 */
public class BuyCard extends BaseMove {
	/**
	 * Creates a new buy card move.
	 *
	 * @param game
	 *            to reference.
	 * @param player
	 *            who want to act.
	 * @param card
	 *            to play.
	 */
	public BuyCard(final WriteableGame game, final WriteablePlayer player, final Card card) {
		super(game, player, card);
		addCheck(CheckFactory.isCurrentState(State.PURCHASE));
		addCheck(CheckFactory.isCurrentPlayer());
		addCheck(CheckFactory.hasPurchaseLeft());
		addCheck(CheckFactory.isCardInSupply());
		addCheck(CheckFactory.isCardAvailable());
		addCheck(CheckFactory.hasMoneyToBuyCard());
	}

	@Override
	public void onFire() {
		getPlayer().get().getPurchases().decrement();
		getPlayer().get().getMoney().add(-getCard().get().getCost());
		getGame().getCardFromSupply(getCard().get(), getPlayer().get().getCardDeckStacker());

		final long emptyCardCount = getGame().getSupplyCardSet().parallel()
				.filter(card -> getGame().getSupplyCardCount(card) == 0).count();

		final long emptyProvinceCount = getGame().getSupplyCardSet().parallel()
				.filter(card -> VictoryCard.PROVINCE == card).filter(card -> getGame().getSupplyCardCount(card) == 0)
				.count();

		if (emptyCardCount >= Settings.EMPTY_SUPPLY_CARDS || emptyProvinceCount == Settings.EMPTY_PROVINCE_CARD) {
			getGame().setOver();
		}
	}
}
