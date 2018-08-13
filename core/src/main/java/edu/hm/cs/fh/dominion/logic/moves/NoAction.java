/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves;

import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;
import edu.hm.cs.fh.dominion.logic.moves.check.IsCurrentPlayerCheck;
import edu.hm.cs.fh.dominion.logic.moves.check.IsCurrentStateCheck;

/**
 * A move to go to the next phase.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 24.04.2014
 */
public class NoAction extends BaseMove {
    /**
     * Creates a new no action move.
     *
     * @param game   to reference.
     * @param player who want to act.
     */
    public NoAction(final WriteableGame game, final WriteablePlayer player) {
        super(game, player);
        addCheck(new IsCurrentStateCheck(State.ACTION));
        addCheck(new IsCurrentPlayerCheck());
    }

    @Override
    public void onFire() {
        getGame().setState(State.PURCHASE);
    }
}
