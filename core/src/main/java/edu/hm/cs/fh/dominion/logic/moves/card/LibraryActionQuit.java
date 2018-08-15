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
 * The last action of the resolving the library card.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 12.06.2014
 */
public class LibraryActionQuit extends BaseMove {

    /**
     * Creates a new library action to quit this action.
     *
     * @param game   to reference.
     * @param player who want to act.
     */
    public LibraryActionQuit(final WriteableGame game, final WriteablePlayer player) {
        super(game, player);
        addCheck(new CurrentStateCheck(State.ACTION_RESOLVE));
        addCheck(new CurrentPlayerCheck());
        addCheck(CheckFactory.isHandcardSizeEqual(Settings.LIBRARY_CARDS_TO_HOLD));
        addCheck(new ResolveCardCheck(KingdomCard.LIBRARY));
    }

    @Override
    public void onFire() {
        getGame().popToResolveActionCard();
        getGame().setState(State.ACTION);
    }
}
