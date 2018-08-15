package edu.hm.cs.fh.dominion.logic.moves.check;

import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;

/**
 * Generates a check for enough money to buy the card.
 */
public class MoneyToBuyCardCheck implements Check {
    @Override
    public MoveResult isCorrect(WriteableGame game, WriteablePlayer player, Card card) {
        return new MoveResult(
                player.getMoney().getCount() >= card.getMetaData().getCost(),
                "The card is to expensive.");
    }
}
