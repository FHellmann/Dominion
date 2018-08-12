/**
 *
 */
package edu.hm.cs.fh.dominion.ui.ai;

import edu.hm.cs.fh.dominion.database.ReadonlyGame;
import edu.hm.cs.fh.dominion.database.cards.KingdomCard;
import edu.hm.cs.fh.dominion.logic.Logic;
import edu.hm.cs.fh.dominion.logic.moves.BuyCard;
import edu.hm.cs.fh.dominion.logic.moves.Move;
import edu.hm.cs.fh.dominion.logic.moves.card.MoatAttackDefend;

import java.util.List;
import java.util.Optional;

/**
 * A robot player which act randomly.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 21.04.2014
 */
public class RobotDefender extends AbstractRobotAI {
	/**
	 * Creates a new defender.
	 *
	 * @param game
	 *            of datastoreage.
	 * @param logic
	 *            for every logical check.
	 * @param name
	 *            of the robot.
	 */
	public RobotDefender(final ReadonlyGame game, final Logic logic, final String name) {
		super(game, logic, name);
	}

	@Override
	protected Optional<Move> onPreferedBuyCard(final List<Move> buyMoves) {
		return buyMoves.parallelStream().filter(move -> ((BuyCard) move).getCard().get() == KingdomCard.MOAT)
				.findFirst();
	}

	@Override
	protected Optional<Move> onPreferedMove(final List<Move> allMoves) {
		return allMoves.parallelStream().filter(move -> move instanceof MoatAttackDefend).findFirst();
	}
}
