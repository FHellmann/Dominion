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
 * A choice for the user to keep the last polled card in library action resolving.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 12.06.2014
 */
public class LibraryActionKeep extends BaseMove {
	/**
	 * Creates a new library action to keep the last polled card.
	 *
	 * @param game
	 *            to reference.
	 * @param player
	 *            who want to act.
	 */
	public LibraryActionKeep(final WriteableGame game, final WriteablePlayer player) {
		super(game, player);
		addCheck(CheckFactory.isCurrentState(State.ACTION_RESOLVE));
		addCheck(CheckFactory.isCurrentPlayer());
		addCheck(CheckFactory.isHandcardSizeLower(Settings.LIBRARY_CARDS_TO_HOLD));
		addCheck(CheckFactory.isResolveCard(KingdomCard.LIBRARY));
	}

	@Override
	public void onFire() {
		getPlayer().get().pollCards(1, Settings.getRandom());
	}
}
