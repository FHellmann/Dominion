/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves;

import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.database.cards.Card.Type;
import edu.hm.cs.fh.dominion.database.cards.KingdomCard;
import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableCardDeck;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;
import edu.hm.cs.fh.dominion.logic.moves.check.*;

/**
 * A move to play a action card.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 24.04.2014
 */
public class PlayActionCard extends BaseMove {
    /**
     * Creates a new play action card move.
     *
     * @param game   to reference.
     * @param player who want to act.
     * @param card   to play.
     */
    public PlayActionCard(final WriteableGame game, final WriteablePlayer player, final Card card) {
        super(game, player, card);
        addCheck(new CurrentStateCheck(State.ACTION));
        addCheck(new CurrentPlayerCheck());
        addCheck(new CardTypeCheck(KingdomCard.class));
        addCheck(new ActionLeftCheck());
        addCheck(CheckFactory.isHandcard());
        addCheck(new HasTypeCheck(Card.Type.ACTION));
    }

    @Override
    public void onFire() {
        final WriteablePlayer player = getPlayer().orElseThrow(() -> new IllegalStateException("No player found"));
        final Card card = getCard().orElseThrow(() -> new IllegalStateException("No card found"));

        ((KingdomCard) card).resolve(getGame());

        WriteableCardDeck.move(player.getCardDeckHand(), player.getCardDeckPlayed(), card);
        player.getActions().decrement();

        if (getGame().getState() == State.ACTION_RESOLVE) {
            getGame().addToResolveActionCard(card);
        }
        if (card.getMetaData().hasType(Type.ATTACK)) {
            getGame().setState(State.ATTACK);
            getGame().setAttacker(player);
            getGame().setAttackCard(card);
            getGame().nextPlayer();
        }
    }
}
