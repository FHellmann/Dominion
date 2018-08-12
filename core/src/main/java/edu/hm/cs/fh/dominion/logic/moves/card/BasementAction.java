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
public class BasementAction extends BaseMove {
	/** Counts the times this class fire method was called. */
	private static int callCounter;

	/**
	 * Creates a new attack reaction move.
	 *
	 * @param game
	 *            to reference.
	 * @param player
	 *            who want to act.
	 * @param card
	 *            to play.
	 */
	public BasementAction(final WriteableGame game, final WriteablePlayer player, final Card card) {
		super(game, player, card);
		addCheck(CheckFactory.isCurrentState(State.ACTION_RESOLVE));
		addCheck(CheckFactory.isCurrentPlayer());
		addCheck(CheckFactory.isResolveCard(KingdomCard.BASEMENT));
	}

	@Override
	public void onFire() {
		callCounter++;
		final WriteablePlayer player = getPlayer().get();
		WriteableCardDeck.move(player.getCardDeckHand(), player.getCardDeckStacker(), getCard().get());
	}

	/**
	 * Get and resets the call counter.
	 *
	 * @return the call counter.
	 */
	static int getAndResetCallCounter() {
		final int calls = callCounter;
		callCounter = 0;
		return calls;
	}
}
