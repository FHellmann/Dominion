package edu.hm.cs.fh.dominion.logic.moves.check;

import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;

public class IsResolveCardCheck implements Check {
    private final Card lastPlayed;

    public IsResolveCardCheck(final Card lastPlayed) {
        this.lastPlayed = lastPlayed;
    }

    @Override
    public MoveResult isCorrect(WriteableGame game, WriteablePlayer player, Card card) {
        return new MoveResult(
                game.getToResolveActionCard().orElseThrow(IllegalStateException::new) == lastPlayed,
                "This card is not the played one."
        );
    }
}
