package edu.hm.cs.fh.dominion.ui;

import edu.hm.cs.fh.dominion.database.ReadonlyGame;
import edu.hm.cs.fh.dominion.logic.Logic;
import edu.hm.cs.fh.dominion.logic.moves.Move;
import edu.hm.cs.fh.dominion.ui.io.ConsoleIO;
import edu.hm.cs.fh.dominion.ui.javafx.MoveSelectorOverlay;
import javafx.application.Platform;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This class represents the JavaFxPlayer.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 18.05.2014
 */
public class JavaFxPlayer extends AbstractHumanPlayer {
    private Move selectedMove;

    /**
     * Creates a new JavaFx player.
     *
     * @param game  of datastoreage.
     * @param logic for every logical check.
     * @param name  of the player.
     * @throws IOException
     */
    public JavaFxPlayer(final ReadonlyGame game, final Logic logic, final String name) throws IOException {
        super(game, logic, name, new ConsoleIO());
    }

    @Override
    public Move selectMove(final List<Move> moves) {
        // Create a "Dialog"-Overlay over the primarystage to wait for user interaction
        run(() -> {
            // This can't be executed in the normal thread -> must be a JavaFX-Thread
            selectedMove = new MoveSelectorOverlay(moves).showAndWait();
        });
        return selectedMove;
    }

    /**
     * Invokes a Runnable in JFX Thread and waits while it's finished.
     *
     * @param run that has to be called on JFX thread.
     */
    public void run(final Runnable run) {
        // normaly we should stay in another thread... so let's do the else branch
        if (Platform.isFxApplicationThread()) {
            // we are already in the FX-Thread
            run.run();
        } else {
            // Create a locker to wait for the finishing of the Runnable
            final Lock lock = new ReentrantLock();
            final Condition condition = lock.newCondition();
            lock.lock();
            try {
                Platform.runLater(() -> {
                    lock.lock();
                    try {
                        run.run();
                    } finally {
                        try {
                            condition.signal();
                        } finally {
                            lock.unlock();
                        }
                    }
                });
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
