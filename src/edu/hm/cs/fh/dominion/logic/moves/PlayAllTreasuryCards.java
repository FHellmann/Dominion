/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves;

import java.util.List;
import java.util.stream.Collectors;

import edu.hm.cs.fh.dominion.database.cards.TreasuryCard;
import edu.hm.cs.fh.dominion.database.full.*;

/**
 * A move to play all treasury cards.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
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
        final WriteablePlayer player = getPlayer().get();
        getPlayer().get().getCardDeckHand().stream()
                .filter(card -> card instanceof TreasuryCard)
                .map(card -> (TreasuryCard) card)
                .forEach(card -> {
                    WriteableCardDeck.move(player.getCardDeckHand(), player.getCardDeckPlayed(), card);
                    player.getMoney().add(card.getCoints());
                });
    }
}
