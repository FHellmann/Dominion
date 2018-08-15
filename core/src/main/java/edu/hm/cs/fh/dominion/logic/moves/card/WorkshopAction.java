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
import edu.hm.cs.fh.dominion.logic.moves.check.CurrentPlayerCheck;
import edu.hm.cs.fh.dominion.logic.moves.check.CurrentStateCheck;
import edu.hm.cs.fh.dominion.logic.moves.check.ResolveCardCheck;

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
        addCheck(new CurrentStateCheck(State.ACTION_RESOLVE));
        addCheck(new CurrentPlayerCheck());
        addCheck(CheckFactory.isCardInSupply());
        addCheck(CheckFactory.isCardAvailable());
        addCheck(CheckFactory.isInCardPrice(Settings.WORKSHOP_MAX_CARD_COST));
        addCheck(new ResolveCardCheck(KingdomCard.WORKSHOP));
    }

    @Override
    public void onFire() {
        final Card card = getCard().orElseThrow(IllegalStateException::new);
        final WriteablePlayer player = getPlayer().orElseThrow(IllegalStateException::new);
        getGame().getCardFromSupply(card, player.getCardDeckStacker());
        getGame().setState(State.ACTION);
        getGame().popToResolveActionCard();
    }
}
