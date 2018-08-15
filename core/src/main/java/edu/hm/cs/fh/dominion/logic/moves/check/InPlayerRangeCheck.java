package edu.hm.cs.fh.dominion.logic.moves.check;

import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;

public class InPlayerRangeCheck implements Check {
    @Override
    public MoveResult isCorrect(WriteableGame game, WriteablePlayer player, Card card) {
        return new MoveResult(2 <= game.getPlayerCount() && game.getPlayerCount() <= 4,
                "The playercount is to low or to high (min. 2 and max. 4 players are allowed).");
    }
}
