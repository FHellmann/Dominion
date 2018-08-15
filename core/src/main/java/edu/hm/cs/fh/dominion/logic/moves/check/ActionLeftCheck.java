package edu.hm.cs.fh.dominion.logic.moves.check;

import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;

/**
 * Generates a check for non zero actions.
 */
public class ActionLeftCheck implements Check {
    @Override
    public MoveResult isCorrect(WriteableGame game, WriteablePlayer player, Card card) {
        return new MoveResult(player.getActions().getCount() > 0, "You have no actions left.");
    }
}
