/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves.card;

import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.database.cards.KingdomCard;
import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableCardDeck;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;
import edu.hm.cs.fh.dominion.logic.moves.BaseMove;
import edu.hm.cs.fh.dominion.logic.moves.CheckFactory;

/**
 * A defend of an attack with the {@link KingdomCard#MOAT}.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 06.05.2014
 */
public class ThroneroomAction extends BaseMove {
    /**
     * Creates a new attack reaction move.
     *
     * @param game   to reference.
     * @param player who want to act.
     * @param card   to play.
     */
    public ThroneroomAction(final WriteableGame game, final WriteablePlayer player, final Card card) {
        super(game, player, card);
        addCheck(CheckFactory.isCurrentState(State.ACTION_RESOLVE));
        addCheck(CheckFactory.isCurrentPlayer());
        addCheck(CheckFactory.isHandcard());
        addCheck(CheckFactory.isCardType(KingdomCard.class));
        addCheck(CheckFactory.isResolveCard(KingdomCard.THRONEROOM));
    }

    @Override
    public void onFire() {
        getGame().popToResolveActionCard();

        final Card baseCard = getCard().get();
        // Wähle eine Aktionskarte aus deiner Hand. Spiele diese Aktionskarte zweimal aus.
        final KingdomCard card = (KingdomCard) baseCard;
        getGame().addToResolveActionCard(card);
        getGame().addToResolveActionCard(card);
        final WriteablePlayer player = getPlayer().get();
        WriteableCardDeck.move(player.getCardDeckHand(), player.getCardDeckPlayed(), baseCard);
    }
}
