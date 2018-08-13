/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves.card;

import edu.hm.cs.fh.dominion.database.Settings;
import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.database.cards.KingdomCard;
import edu.hm.cs.fh.dominion.database.cards.TreasuryCard;
import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;
import edu.hm.cs.fh.dominion.logic.moves.BaseMove;
import edu.hm.cs.fh.dominion.logic.moves.check.CheckFactory;
import edu.hm.cs.fh.dominion.logic.moves.check.IsCurrentPlayerCheck;
import edu.hm.cs.fh.dominion.logic.moves.check.IsCurrentStateCheck;
import edu.hm.cs.fh.dominion.logic.moves.check.IsResolveCardCheck;

/**
 * A defend of an attack with the {@link KingdomCard#MOAT}.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 06.05.2014
 */
public class MoneylenderAction extends BaseMove {
    /**
     * Creates a new attack reaction move.
     *
     * @param game   to reference.
     * @param player who want to act.
     * @param card   to play.
     */
    public MoneylenderAction(final WriteableGame game, final WriteablePlayer player, final Card card) {
        super(game, player, card);
        addCheck(new IsCurrentStateCheck(State.ACTION_RESOLVE));
        addCheck(new IsCurrentPlayerCheck());
        addCheck(CheckFactory.isHandcard());
        addCheck(new IsResolveCardCheck(KingdomCard.MONEYLENDER));
        addCheck(CheckFactory.isEqualCard(TreasuryCard.COPPER));
    }

    @Override
    public void onFire() {
        final WriteablePlayer player = getPlayer().get();
        getGame().addCardToWaste(player.getCardDeckHand(), TreasuryCard.COPPER);
        player.getMoney().add(Settings.MONEYLENDER_ADD_COINS);
        getGame().setState(State.ACTION);
        getGame().popToResolveActionCard();
    }
}
