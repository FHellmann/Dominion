package edu.hm.cs.fh.dominion.logic.moves.check;

import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;

public class CurrentStateCheck implements Check {
    private final State state;

    public CurrentStateCheck(final State state) {
        this.state = state;
    }

    @Override
    public MoveResult isCorrect(WriteableGame game, WriteablePlayer player, Card card) {
        return new MoveResult(state == game.getState(), "The current phase is not "
                + state.toString().toLowerCase() + ".");
    }
}
