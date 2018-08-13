package edu.hm.cs.fh.dominion.logic.moves.check;

import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;

public class IsCardTypeCheck implements Check {
    private final Class<? extends Card> cardClass;

    public IsCardTypeCheck(final Class<? extends Card> cardClass) {
        this.cardClass = cardClass;
    }

    @Override
    public MoveResult isCorrect(WriteableGame game, WriteablePlayer player, Card card) {
        return new MoveResult(cardClass.isAssignableFrom(card.getClass()),
                "The card is not a child from the super class " + cardClass.getSimpleName() + ".");
    }
}
