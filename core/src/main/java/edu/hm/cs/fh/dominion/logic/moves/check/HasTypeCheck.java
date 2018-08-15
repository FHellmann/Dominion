package edu.hm.cs.fh.dominion.logic.moves.check;

import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;

public class HasTypeCheck implements Check {
    private final Card.Type type;

    public HasTypeCheck(final Card.Type type) {
        this.type = type;
    }

    @Override
    public MoveResult isCorrect(WriteableGame game, WriteablePlayer player, Card card) {
        return new MoveResult(card.getMetaData().hasType(type), "The card does not have the type " + type + ".");
    }
}
