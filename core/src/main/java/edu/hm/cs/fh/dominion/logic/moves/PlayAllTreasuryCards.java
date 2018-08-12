/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves;

import edu.hm.cs.fh.dominion.database.cards.TreasuryCard;
import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableCardDeck;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;

/**
 * A move to play all treasury cards.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 24.04.2014
 */
public class PlayAllTreasuryCards extends BaseMove {
    /**
     * Creates a new play all treasury card move.
     *
     * @param game   to reference.
     * @param player who want to act.
     */
    public PlayAllTreasuryCards(final WriteableGame game, final WriteablePlayer player) {
        super(game, player);
        addCheck(CheckFactory.isCurrentState(State.PURCHASE));
        addCheck(CheckFactory.isCurrentPlayer());
        addCheck(CheckFactory.hasMoreMoneyCards());
    }

    @Override
    public void onFire() {
        // filter all money cards from the players hand
        final WriteablePlayer player = getPlayer().orElseThrow(() -> new IllegalStateException("No player found"));
        player.getCardDeckHand().stream()
                .filter(card -> card instanceof TreasuryCard)
                .map(card -> (TreasuryCard) card)
                .peek(card -> WriteableCardDeck.move(player.getCardDeckHand(), player.getCardDeckPlayed(), card))
                .map(card -> card.getMetaData().getCoints())
                .forEach(coints -> player.getMoney().add(coints));
    }
}
