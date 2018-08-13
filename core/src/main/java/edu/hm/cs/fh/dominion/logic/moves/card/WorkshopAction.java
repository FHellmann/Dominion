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
public class WorkshopAction extends BaseMove {
    /**
     * Creates a new attack reaction move.
     *
     * @param game   to reference.
     * @param player who want to act.
     * @param card   to play.
     */
    public WorkshopAction(final WriteableGame game, final WriteablePlayer player, final Card card) {
        super(game, player, card);
        addCheck(new IsCurrentStateCheck(State.ACTION_RESOLVE));
        addCheck(new IsCurrentPlayerCheck());
        addCheck(CheckFactory.isCardInSupply());
        addCheck(CheckFactory.isCardAvailable());
        addCheck(CheckFactory.isInCardPrice(Settings.WORKSHOP_MAX_CARD_COST));
        addCheck(new IsResolveCardCheck(KingdomCard.WORKSHOP));
    }

    @Override
    public void onFire() {
        getGame().getCardFromSupply(getCard().get(), getPlayer().get().getCardDeckStacker());
        getGame().setState(State.ACTION);
        getGame().popToResolveActionCard();
    }
}
