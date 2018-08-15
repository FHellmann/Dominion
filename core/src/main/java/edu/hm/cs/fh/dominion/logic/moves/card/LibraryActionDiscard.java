/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves.card;

import edu.hm.cs.fh.dominion.database.Settings;
import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.database.cards.KingdomCard;
import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableCardDeck;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;
import edu.hm.cs.fh.dominion.logic.moves.BaseMove;
import edu.hm.cs.fh.dominion.logic.moves.check.*;

/**
 * A choice for the user to discard the last polled card and poll a new one in library action
 * resolving.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 12.06.2014
 */
public class LibraryActionDiscard extends BaseMove {
    /**
     * Creates a new library action to discard the card.
     *
     * @param game   to reference.
     * @param player who want to act.
     * @param card   to play.
     */
    public LibraryActionDiscard(final WriteableGame game, final WriteablePlayer player, final Card card) {
        super(game, player, card);
        addCheck(new CurrentStateCheck(State.ACTION_RESOLVE));
        addCheck(new CurrentPlayerCheck());
        addCheck(CheckFactory.isHandcard());
        addCheck(CheckFactory.isHandcardSizeLower(Settings.LIBRARY_CARDS_TO_HOLD));
        addCheck(CheckFactory.isLastPolledCard());
        addCheck(new CardTypeCheck(KingdomCard.class));
        addCheck(new ResolveCardCheck(KingdomCard.LIBRARY));
    }

    @Override
    public void onFire() {
        final WriteablePlayer player = getPlayer().get();
        // get last added card to handcarddeck
        final Card lastAddedCard = player.getCardDeckHand().stream().findFirst().get();
        // discard this card to the stacker
        WriteableCardDeck.move(player.getCardDeckHand(), player.getCardDeckStacker(), lastAddedCard);
        // get a new card
        player.pollCards(1, Settings.getRandom());
    }
}
