/**
 *
 */
package edu.hm.cs.fh.dominion.ui;

import edu.hm.cs.fh.dominion.database.ReadonlyGame;
import edu.hm.cs.fh.dominion.database.ReadonlyPlayer;
import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.logic.Logic;
import edu.hm.cs.fh.dominion.logic.moves.Move;

import java.util.*;

/**
 * The base methods for every ui.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
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
     * @param moves to choose.
     * @return the selected move.
     */
    Move selectMove(List<Move> moves);

    /**
     * The main game loop runs while the game is not in {@link State#RESULTS}.
     *
     * @param game  to get the state.
     * @param logic to check all moves.
     * @param uis   to play.
     */
    static void loop(final ReadonlyGame game, final Logic logic, final Collection<UserInterface> uis) {
        while (game.getState() != State.QUIT) {
            uis.stream().filter(user -> user.getPlayer().isPresent()).forEach(user -> {
                final List<Move> moves = user.getPlayer()
                        .map(logic::findApplicableMoves)
                        .orElse(Collections.emptyList());
                if (!moves.isEmpty()) {
                    final Move move = user.selectMove(moves);
                    logic.fireMove(move);
                }
            });
        }
    }

    /**
     * The main game async loop runs while the game is not in {@link State#RESULTS}.
     *
     * @param game  to get the state.
     * @param logic to check all moves.
     * @param uis   to play.
     */
    static void loopAsync(final ReadonlyGame game, final Logic logic, final Collection<UserInterface> uis) {
        // Start Game-Loop in an extra thread
        final Thread gameLoopThread = new Thread(() -> {
            while (game.getState() != State.QUIT) {
                uis.stream().filter(user -> user.getPlayer().isPresent()).forEach(user -> {
                    final List<Move> moves = user.getPlayer()
                            .map(logic::findApplicableMoves)
                            .orElse(Collections.emptyList());

                    try {
                        // This is essential for JavaFx
                        // Without the sleep, the GUI wouldn't be able to update
                        // and the user wouldn't see some changing at his enemies
                        Thread.sleep(200);
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
        }, "Game-Loop");
        gameLoopThread.setDaemon(true);
        gameLoopThread.start();
    }
}
