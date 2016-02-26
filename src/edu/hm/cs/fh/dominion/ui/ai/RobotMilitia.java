/**
 *
 */
package edu.hm.cs.fh.dominion.ui.ai;

import java.util.List;
import java.util.Optional;

import edu.hm.cs.fh.dominion.database.ReadonlyGame;
import edu.hm.cs.fh.dominion.logic.Logic;
import edu.hm.cs.fh.dominion.logic.cards.KingdomCard;
import edu.hm.cs.fh.dominion.logic.moves.BuyCard;
import edu.hm.cs.fh.dominion.logic.moves.Move;
import edu.hm.cs.fh.dominion.logic.moves.card.MilitiaAttack;

/**
 * A robot player which act randomly.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 21.04.2014
 */
public class RobotMilitia extends AbstractRobotAI {
	/**
	 * Creates a new militia.
	 *
	 * @param game
	 *            of datastoreage.
	 * @param logic
	 *            for every logical check.
	 * @param name
	 *            of the robot.
	 */
	public RobotMilitia(final ReadonlyGame game, final Logic logic, final String name) {
		super(game, logic, name);
	}

	@Override
	protected Optional<Move> onPreferedBuyCard(final List<Move> buyMoves) {
		return buyMoves.parallelStream().filter(move -> ((BuyCard) move).getCard().get() == KingdomCard.MILITIA)
				.findFirst();
	}

	@Override
	protected Optional<Move> onPreferedMove(final List<Move> allMoves) {
		return allMoves.parallelStream().filter(move -> move instanceof MilitiaAttack).findFirst();
	}
}
