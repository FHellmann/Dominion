package edu.hm.cs.fh.dominion.logic.moves.card;

import edu.hm.cs.fh.dominion.database.cards.KingdomCard;
import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;
import edu.hm.cs.fh.dominion.logic.moves.BaseMove;
import edu.hm.cs.fh.dominion.logic.moves.check.IsCurrentPlayerCheck;
import edu.hm.cs.fh.dominion.logic.moves.check.IsCurrentStateCheck;
import edu.hm.cs.fh.dominion.logic.moves.check.IsResolveCardCheck;

public class ChapelActionQuit extends BaseMove {

    /**
     * Creates a new chapel action move.
     *
     * @param game   to reference.
     * @param player who want to act.
     */
    public ChapelActionQuit(final WriteableGame game, final WriteablePlayer player) {
        super(game, player);
        addCheck(new IsCurrentStateCheck(State.ACTION_RESOLVE));
        addCheck(new IsCurrentPlayerCheck());
        addCheck(new IsResolveCardCheck(KingdomCard.CHAPEL));
    }

    @Override
    public void onFire() {
        ChapelAction.resetCallCounter();
        getGame().setState(State.ACTION);
        getGame().popToResolveActionCard();
    }
}
