/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves.card;

import edu.hm.cs.fh.dominion.database.cards.KingdomCard;
import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;
import edu.hm.cs.fh.dominion.logic.moves.BaseMove;
import edu.hm.cs.fh.dominion.logic.moves.check.IsCurrentPlayerCheck;
import edu.hm.cs.fh.dominion.logic.moves.check.IsCurrentStateCheck;
import edu.hm.cs.fh.dominion.logic.moves.check.IsResolveCardCheck;

/**
 * A defend of an attack with the {@link KingdomCard#MOAT}.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 06.05.2014
 */
public class ChancellorActionQuit extends BaseMove {
    /**
     * Creates a new attack reaction move.
     *
     * @param game   to reference.
     * @param player who want to act.
     */
    public ChancellorActionQuit(final WriteableGame game, final WriteablePlayer player) {
        super(game, player);
        addCheck(new IsCurrentStateCheck(State.ACTION_RESOLVE));
        addCheck(new IsResolveCardCheck(KingdomCard.CHANCELLOR));
        addCheck(new IsCurrentPlayerCheck());
    }

    @Override
    public void onFire() {
        getGame().setState(State.ACTION);
        getGame().popToResolveActionCard();
    }
}
