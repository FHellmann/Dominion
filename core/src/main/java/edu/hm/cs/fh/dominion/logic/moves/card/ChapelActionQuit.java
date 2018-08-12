package edu.hm.cs.fh.dominion.logic.moves.card;

import edu.hm.cs.fh.dominion.database.cards.KingdomCard;
import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;
import edu.hm.cs.fh.dominion.logic.moves.BaseMove;
import edu.hm.cs.fh.dominion.logic.moves.CheckFactory;

public class ChapelActionQuit extends BaseMove {

    /**
     * Creates a new chapel action move.
     *
     * @param game   to reference.
     * @param player who want to act.
     */
    public ChapelActionQuit(final WriteableGame game, final WriteablePlayer player) {
        super(game, player);
        addCheck(CheckFactory.isCurrentState(State.ACTION_RESOLVE));
        addCheck(CheckFactory.isCurrentPlayer());
        addCheck(CheckFactory.isResolveCard(KingdomCard.CHAPEL));
    }

    @Override
    public void onFire() {
        ChapelAction.resetCallCounter();
        getGame().setState(State.ACTION);
        getGame().popToResolveActionCard();
    }
}
