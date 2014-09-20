/**
 *
 */
package edu.hm.cs.fh.dominion.ui;

import java.util.Collection;
import java.util.List;
import java.util.Observer;
import java.util.Optional;

import edu.hm.cs.fh.dominion.database.ReadonlyGame;
import edu.hm.cs.fh.dominion.database.ReadonlyPlayer;
import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.logic.Logic;
import edu.hm.cs.fh.dominion.logic.moves.Move;

/**
 * The base methods for every ui.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 21.04.2014
 */
public interface UserInterface extends Observer {
	/**
	 * Get the read-only-game.
	 *
	 * @return the game.
	 */
	ReadonlyGame getGame();

	/**
	 * Get the logic module for logical checks.
	 *
	 * @return the logic.
	 */
	Logic getLogic();

	/**
	 * Get the read-only-player.
	 *
	 * @return the player.
	 */
	Optional<ReadonlyPlayer> getPlayer();

	/**
	 * Select a move out of a list and return it.
	 *
	 * @param moves
	 *            to choose.
	 * @return the selected move.
	 */
	Move selectMove(List<Move> moves);

	/**
	 * The main game loop runs while the game is not in {@link State#RESULTS}.
	 *
	 * @param game
	 *            to get the state.
	 * @param logic
	 *            to check all moves.
	 * @param uis
	 *            to play.
	 */
	static void loop(final ReadonlyGame game, final Logic logic, final Collection<UserInterface> uis) {
		while (game.getState() != State.QUIT) {
			uis.stream().filter(user -> user.getPlayer().isPresent()).forEach(user -> {
				final List<Move> moves = logic.findApplicableMoves(user.getPlayer().get());

				if (!moves.isEmpty()) {
					final Move move = user.selectMove(moves);
					logic.fireMove(move);
				}
			});
		}
	}
}
