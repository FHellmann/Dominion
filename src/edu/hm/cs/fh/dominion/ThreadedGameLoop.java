/**
 *
 */
package edu.hm.cs.fh.dominion;

import java.util.Collection;
import java.util.List;

import edu.hm.cs.fh.dominion.database.ReadonlyGame;
import edu.hm.cs.fh.dominion.database.full.Game;
import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.logic.Logic;
import edu.hm.cs.fh.dominion.logic.moves.Move;
import edu.hm.cs.fh.dominion.ui.UserInterface;

/**
 * A threaded game loop for JavaFX-Users.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 03.06.2014
 */
public class ThreadedGameLoop {
	/** The datastore of the game. */
	private final WriteableGame game;
	/** The logic of the game. */
	private final Logic logic;

	/**
	 * Creates a new threaded game loop.
	 */
	public ThreadedGameLoop() {
		game = new Game();
		logic = new Logic(game);
	}

	/**
	 * Start the game loop in a new thread.
	 *
	 * @param uis
	 *            to start the game with.
	 */
	public void start(final Collection<UserInterface> uis) {
		// Start Game-Loop in an extra thread
		final Thread gameLoopThread = new Thread(() -> {
			while (game.getState() != State.QUIT) {
				uis.stream().filter(user -> user.getPlayer().isPresent()).forEach(user -> {
					final List<Move> moves = logic.findApplicableMoves(user.getPlayer().get());

					try {
						// This is essential for JavaFx
						// Without the sleep, the GUI wouldn't be able to update
						// and the user wouldn't see some changing at his enemies
						Thread.sleep(250);
					} catch (final Exception e) {
						// Can be ignored in normal case
						// e.printStackTrace();
					}

					if (!moves.isEmpty()) {
						final Move move = user.selectMove(moves);
						logic.fireMove(move);
					}
				});
			}
		});
		gameLoopThread.setDaemon(true);
		gameLoopThread.start();
	}

	/**
	 * Get the readonly game.
	 *
	 * @return the game.
	 */
	public ReadonlyGame getGame() {
		return game;
	}

	/**
	 * Get the logic.
	 *
	 * @return the logic.
	 */
	public Logic getLogic() {
		return logic;
	}
}
