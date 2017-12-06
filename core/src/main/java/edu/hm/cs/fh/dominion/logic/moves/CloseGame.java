/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves;

import edu.hm.cs.fh.dominion.database.cards.VictoryCard;
import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableCardDeck;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.logic.cards.KingdomCard;

/**
 * A move to close the game.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 15.04.2014
 */
public class CloseGame extends BaseMove {

	/**
	 * Creates a new close game move.
	 *
	 * @param game
	 *            to reference.
	 */
	public CloseGame(final WriteableGame game) {
		super(game);
		addCheck(CheckFactory.isCurrentState(State.OVER));
	}

	@Override
	public void onFire() {
		getGame().getRwPlayers().forEach(player -> {
			// Add all carddecks together
				final WriteableCardDeck cardDeckStacker = player.getCardDeckStacker();
				WriteableCardDeck.move(player.getCardDeckPull(), cardDeckStacker);
				WriteableCardDeck.move(player.getCardDeckHand(), cardDeckStacker);
				WriteableCardDeck.move(player.getCardDeckPlayed(), cardDeckStacker);

				// Search for all victory cards and add the points
				final int points = cardDeckStacker.stream().parallel().filter(card -> card instanceof VictoryCard)
						.mapToInt(card -> ((VictoryCard) card).getMetaData().getPoints()).sum();
				player.getVictoryPoints().add(points);

				// don't forget the gardens!!!
				cardDeckStacker.stream().filter(card -> card == KingdomCard.GARDENS)
						.forEach(card -> ((KingdomCard) card).resolve(getGame()));
			});

		getGame().setState(State.RESULTS);
	}
}
