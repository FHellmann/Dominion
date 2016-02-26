package edu.hm.cs.fh.dominion.logic.moves.card;

import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;
import edu.hm.cs.fh.dominion.logic.Settings;
import edu.hm.cs.fh.dominion.logic.cards.KingdomCard;
import edu.hm.cs.fh.dominion.logic.moves.BaseMove;
import edu.hm.cs.fh.dominion.logic.moves.CheckFactory;

public class ChapelAction extends BaseMove {
    private static int handCardSize;

    /**
     * Creates a new chapel action move.
     *
     * @param game   to reference.
     * @param player who want to act.
     * @param card   to play.
     */
    public ChapelAction(final WriteableGame game, final WriteablePlayer player, final Card card) {
        super(game, player, card);
        if (handCardSize == 0) {
            handCardSize = player.getCardDeckHand().size();
        }
        addCheck(CheckFactory.isCurrentState(State.ACTION_RESOLVE));
        addCheck(CheckFactory.isCurrentPlayer());
        addCheck(CheckFactory.isResolveCard(KingdomCard.CHAPEL));
        addCheck(CheckFactory.isHandcardSizeBiggerOrEqual(handCardSize - Settings.CHAPEL_REMOVE_CARDS));
    }

    @Override
    public void onFire() {
        final WriteablePlayer player = getPlayer().get();
        // Remove card from players hand & Throw card to trash
        getGame().addCardToWaste(player.getCardDeckHand(), getCard().get());
    }

    /**
     * Resets the call counter.
     */
    public static void resetCallCounter() {
        handCardSize = 0;
    }
}
