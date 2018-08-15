/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves;

import edu.hm.cs.fh.dominion.database.Settings;
import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.database.cards.VictoryCard;
import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;
import edu.hm.cs.fh.dominion.logic.moves.check.*;

/**
 * A move to buy a card.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 12.04.2014
 */
public class BuyCard extends BaseMove {
    /**
     * Creates a new buy card move.
     *
     * @param game   to reference.
     * @param player who want to act.
     * @param card   to play.
     */
    public BuyCard(final WriteableGame game, final WriteablePlayer player, final Card card) {
        super(game, player, card);
        addCheck(new CurrentStateCheck(State.PURCHASE));
        addCheck(new CurrentPlayerCheck());
        addCheck(new PurchaseLeftCheck());
        addCheck(CheckFactory.isCardInSupply());
        addCheck(CheckFactory.isCardAvailable());
        addCheck(new MoneyToBuyCardCheck());
    }

    @Override
    public void onFire() {
        final WriteablePlayer player = getPlayer().orElseThrow(() -> new IllegalStateException("No player found"));
        final Card card = getCard().orElseThrow(() -> new IllegalStateException("No card found"));

        player.getPurchases().decrement();
        player.getMoney().add(-card.getMetaData().getCost());
        getGame().getCardFromSupply(card, player.getCardDeckStacker());

        final long emptyCardCount = getGame().getSupplyCardSet()
                .filter(supplyCard -> getGame().getSupplyCardCount(supplyCard) == 0)
                .count();

        final long emptyProvinceCount = getGame().getSupplyCardSet()
                .filter(supplyCard -> VictoryCard.PROVINCE == supplyCard)
                .filter(supplyCard -> getGame().getSupplyCardCount(supplyCard) == 0)
                .count();

        if (emptyCardCount >= Settings.EMPTY_SUPPLY_CARDS || emptyProvinceCount == Settings.EMPTY_PROVINCE_CARD) {
            getGame().setOver();
        }
    }
}
