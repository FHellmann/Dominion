/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves;

import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.database.cards.TreasuryCard;
import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableCardDeck;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;

/**
 * A move to play a treasury card.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 24.04.2014
 */
public class PlayTreasuryCard extends BaseMove {
	/**
	 * Creates a new play treasury card move.
	 *
	 * @param game
	 *            to reference.
	 * @param player
	 *            who want to act.
	 * @param card
	 *            to play.
	 */
	public PlayTreasuryCard(final WriteableGame game, final WriteablePlayer player, final Card card) {
		super(game, player, card);
		addCheck(CheckFactory.isCurrentState(State.PURCHASE));
		addCheck(CheckFactory.isCurrentPlayer());
		addCheck(CheckFactory.isCardType(TreasuryCard.class));
		addCheck(CheckFactory.isHandcard());
	}

	@Override
	public void onFire() {
		final WriteablePlayer player = getPlayer().get();
		final TreasuryCard card = (TreasuryCard) getCard().get();
		WriteableCardDeck.move(player.getCardDeckHand(), player.getCardDeckPlayed(), card);
		player.getMoney().add(card.getCoints());
	}
}
