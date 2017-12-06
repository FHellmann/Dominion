/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves;

import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;

/**
 * A move to view the results of the ending game.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 24.04.2014
 */
public class ViewGameResult extends BaseMove {
    /**
     * Creates a new view game result move.
     *
     * @param game to reference.
     */
    public ViewGameResult(final WriteableGame game) {
        super(game);
        addCheck(CheckFactory.isCurrentState(State.RESULTS));
    }

    @Override
    public void onFire() {
        // Do nothing! Only notify the observers to show the game result.
    }
}
