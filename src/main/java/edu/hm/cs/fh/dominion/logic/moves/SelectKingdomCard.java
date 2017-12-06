/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves;

import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;
import edu.hm.cs.fh.dominion.logic.Settings;
import edu.hm.cs.fh.dominion.logic.cards.KingdomCard;

/**
 * A move to select a kingdom card.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 15.04.2014
 */
public class SelectKingdomCard extends BaseMove {
	/**
	 * Creates a new select kingdomcard move.
	 *
	 * @param game
	 *            to modify.
	 * @param player
	 *            who want's to play the move.
	 * @param card
	 *            to play.
	 */
	public SelectKingdomCard(final WriteableGame game, final WriteablePlayer player, final Card card) {
		super(game, player, card);
		addCheck(CheckFactory.isCurrentState(State.SETUP));
		addCheck(CheckFactory.isCardNotInSupply());
		addCheck(CheckFactory.isSupplyNotFull());
		addCheck(CheckFactory.isCardType(KingdomCard.class));
	}

	@Override
	public void onFire() {
		getGame().addSupplyCard(getCard().get(), Settings.SUPPLY_CARDS);
		getGame().nextPlayer();
	}
}
