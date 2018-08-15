/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves.card;

import edu.hm.cs.fh.dominion.database.Settings;
import edu.hm.cs.fh.dominion.database.cards.KingdomCard;
import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;
import edu.hm.cs.fh.dominion.logic.moves.BaseMove;
import edu.hm.cs.fh.dominion.logic.moves.check.CheckFactory;
import edu.hm.cs.fh.dominion.logic.moves.check.CurrentPlayerCheck;
import edu.hm.cs.fh.dominion.logic.moves.check.CurrentStateCheck;
import edu.hm.cs.fh.dominion.logic.moves.check.ResolveCardCheck;

/**
 * A choice for the user to keep the last polled card in library action resolving.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 12.06.2014
 */
public class LibraryActionKeep extends BaseMove {
    /**
     * Creates a new library action to keep the last polled card.
     *
     * @param game   to reference.
     * @param player who want to act.
     */
    public LibraryActionKeep(final WriteableGame game, final WriteablePlayer player) {
        super(game, player);
        addCheck(new CurrentStateCheck(State.ACTION_RESOLVE));
        addCheck(new CurrentPlayerCheck());
        addCheck(CheckFactory.isHandcardSizeLower(Settings.LIBRARY_CARDS_TO_HOLD));
        addCheck(new ResolveCardCheck(KingdomCard.LIBRARY));
    }

    @Override
    public void onFire() {
        getPlayer().get().pollCards(1, Settings.getRandom());
    }
}
