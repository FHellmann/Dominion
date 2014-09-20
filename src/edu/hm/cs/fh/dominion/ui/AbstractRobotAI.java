/**
 *
 */
package edu.hm.cs.fh.dominion.ui;

import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import edu.hm.cs.fh.dominion.database.ReadonlyGame;
import edu.hm.cs.fh.dominion.database.cards.TreasuryCard;
import edu.hm.cs.fh.dominion.database.cards.VictoryCard;
import edu.hm.cs.fh.dominion.logic.Logic;
import edu.hm.cs.fh.dominion.logic.moves.BuyCard;
import edu.hm.cs.fh.dominion.logic.moves.Move;
import edu.hm.cs.fh.dominion.logic.moves.PlayAllTreasuryCards;
import edu.hm.cs.fh.dominion.logic.moves.PlayTreasuryCard;

/**
 * A robot player which act randomly.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 21.04.2014
 */
public abstract class AbstractRobotAI extends AbstractRegisteredPlayer {
	/**
	 * Creates a new sorcerer.
	 *
	 * @param game
	 *            of datastoreage.
	 * @param logic
	 *            for every logical check.
	 * @param name
	 *            of the robot.
	 */
	public AbstractRobotAI(final ReadonlyGame game, final Logic logic, final String name) {
		super(game, logic, name);
	}

	@Override
	public Move selectMove(final List<Move> moves) {
		// Has money to play?
		final Optional<Move> playAllMoney = moves.parallelStream().filter(move -> move instanceof PlayAllTreasuryCards)
				.findFirst();
		Move selectedMove = getMove(null, playAllMoney);

		final Optional<Move> moneyToPlay = moves.parallelStream().filter(move -> move instanceof PlayTreasuryCard)
				.findFirst();
		selectedMove = getMove(selectedMove, moneyToPlay);

		// Can buy a card?
		final List<Move> buyMoves = moves.parallelStream().filter(move -> move instanceof BuyCard)
				.collect(Collectors.toList());

		// Can buy a prefered card?
		final Optional<Move> prefCardToBuy = onPreferedBuyCard(buyMoves);
		selectedMove = getMove(selectedMove, prefCardToBuy);

		// Can buy a victory card?
		final Optional<Move> victoryToBuy = buyMoves
				.parallelStream()
				.filter(move -> ((BuyCard) move).getCard().get() instanceof VictoryCard)
				.filter(move -> ((VictoryCard) ((BuyCard) move).getCard().get()).getPoints() > VictoryCard.ESTATE
						.getPoints()).findFirst();
		selectedMove = getMove(selectedMove, victoryToBuy);

		// Can buy a treasury card?
		final Optional<Move> treasuryToBuy = buyMoves
				.parallelStream()
				.filter(move -> ((BuyCard) move).getCard().get() instanceof TreasuryCard)
				.filter(move -> ((TreasuryCard) ((BuyCard) move).getCard().get()).getCoints() > TreasuryCard.COPPER
						.getCoints()).findFirst();
		selectedMove = getMove(selectedMove, treasuryToBuy);

		final Optional<Move> preferedMove = onPreferedMove(moves);
		selectedMove = getMove(selectedMove, preferedMove);

		if (selectedMove == null) {
			// Filter curse cards
			final List<Move> filter = moves
					.parallelStream()
					.filter(move -> move instanceof BuyCard ? ((BuyCard) move).getCard().get() != VictoryCard.CURSE
							: true).collect(Collectors.toList());
			// Randomly selection
			final Random random = new Random();
			// generate a random int value between 0 and moves.size
			final int randomInt = random.nextInt(filter.size());
			// find first random item
			selectedMove = filter.stream().skip(randomInt).findFirst().get();
		}
		return selectedMove;
	}

	/**
	 * Let the next class decide which card to buy.
	 *
	 * @param buyMoves
	 *            to buy a card.
	 * @return the move or null in an optional.
	 */
	protected abstract Optional<Move> onPreferedBuyCard(List<Move> buyMoves);

	/**
	 * Let the next class decide which move to play.
	 *
	 * @param allMoves
	 *            to be possible to play.
	 * @return the move or null in an optional.
	 */
	protected abstract Optional<Move> onPreferedMove(List<Move> allMoves);

	@Override
	public void update(final Observable observable, final Object object) { // NOPMD
		// Ignores every update call --> Robots don't need to see the game state
	}

	/**
	 * Get the optional move or the before hand selected move.
	 *
	 * @param selectedMove
	 *            which was may selected before.
	 * @param optMove
	 *            which will be selected if the other one is empty.
	 * @return the move.
	 */
	private static Move getMove(final Move selectedMove, final Optional<Move> optMove) {
		Move result;
		if (optMove.isPresent() && selectedMove == null) {
			result = optMove.get();
		} else {
			result = selectedMove;
		}
		return result;
	}
}
