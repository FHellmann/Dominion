/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves.card;

import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;
import edu.hm.cs.fh.dominion.logic.Settings;
import edu.hm.cs.fh.dominion.logic.cards.KingdomCard;
import edu.hm.cs.fh.dominion.logic.moves.BaseMove;
import edu.hm.cs.fh.dominion.logic.moves.CheckFactory;

/**
 * The last action of the resolving the library card.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 12.06.2014
 */
public class LibraryActionQuit extends BaseMove {

	/**
	 * Creates a new library action to quit this action.
	 *
	 * @param game
	 *            to reference.
	 * @param player
	 *            who want to act.
	 */
	public LibraryActionQuit(final WriteableGame game, final WriteablePlayer player) {
		super(game, player);
		addCheck(CheckFactory.isCurrentState(State.ACTION_RESOLVE));
		addCheck(CheckFactory.isCurrentPlayer());
		addCheck(CheckFactory.isHandcardSizeEqual(Settings.LIBRARY_CARDS_TO_HOLD));
		addCheck(CheckFactory.isResolveCard(KingdomCard.LIBRARY));
	}

	@Override
	public void onFire() {
		getGame().popToResolveActionCard();
		getGame().setState(State.ACTION);
	}
}
