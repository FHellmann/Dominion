/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves;

import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;
import edu.hm.cs.fh.dominion.logic.moves.check.CurrentStateCheck;
import edu.hm.cs.fh.dominion.logic.moves.check.InPlayerRangeCheck;

/**
 * A move to open the game.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 15.04.2014
 */
public class OpenGame extends BaseMove {
    /**
     * Creates a new open game move.
     *
     * @param game to reference.
     */
    public OpenGame(final WriteableGame game) {
        super(game);
        addCheck(new CurrentStateCheck(State.INITIALIZE));
        addCheck(new InPlayerRangeCheck());
    }

    @Override
    public void onFire() {
        final WriteablePlayer player = getGame().getRwPlayers().findFirst()
                .orElseThrow(() -> new IllegalStateException("No player was found"));
        getGame().setCurrentPlayer(player);
        getGame().setState(State.SETUP);
    }
}
