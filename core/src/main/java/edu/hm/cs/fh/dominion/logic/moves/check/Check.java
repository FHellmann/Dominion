package edu.hm.cs.fh.dominion.logic.moves.check;

import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;

/**
 * A simple check.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 24.04.2014
 */
@FunctionalInterface
public interface Check {
    /**
     * Checks something.
     *
     * @param game   to modify.
     * @param player who want's to play the move.
     * @param card   to play.
     * @return a {@link MoveResult} for more informations.
     */
    MoveResult isCorrect(final WriteableGame game, final WriteablePlayer player, final Card card);
}
