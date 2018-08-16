/**
 *
 */
package edu.hm.cs.fh.dominion;

import edu.hm.cs.fh.dominion.database.ReadonlyGame;
import edu.hm.cs.fh.dominion.database.Settings;
import edu.hm.cs.fh.dominion.database.full.Game;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.logic.Logic;
import edu.hm.cs.fh.dominion.ui.*;
import edu.hm.cs.fh.dominion.ui.javafx.JavaFxApp;
import javafx.application.Application;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The programm entry point.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 12.04.2014
 */
public class DominionMain {
    /**
     * The main method to start the program.
     *
     * @param args to pass commands to the application.
     * @throws IOException is thrown by the logger.
     */
    public static void main(final String[] args) throws Exception {
        final ArgumentsParser argsParser = new ArgumentsParser(args);

        if (argsParser.isHelpRequired()) {
            argsParser.showArguments();
        } else if (argsParser.isJavaFxApplication()) {
            Application.launch(JavaFxApp.class, args);
        } else {
            // Normal-Launcher
            final WriteableGame game = new Game();
            final Logic logic = new Logic(game);
            final List<UserInterface> uis = new ArrayList<>();

            if (argsParser.isRecorderActivated()) {
                // Initialize and add the Recorder
                Settings.setReplayable();
                uis.add(new Recorder(game, logic, argsParser.getRecordFilename()));
            }

            if (argsParser.isPublicViewerActivated()) {
                // Initialize and add the PublicViewer
                uis.add(new PublicViewer(game, logic));
            }

            // Create and add the players
            uis.addAll(createPlayers(argsParser, game, logic));

            // Let's play the game!
            UserInterface.loop(game, logic, uis);
        }
    }

    /**
     * Initializes all the players or replayers.
     *
     * @param argsParser to check if the players should be initialized or the replayers.
     * @param game       to create the objects.
     * @param logic      to create the objects.
     * @return a list with the players.
     * @throws IOException is possible thrown by the file parser while extracting the replayers count.
     */
    private static List<UserInterface> createPlayers(final ArgumentsParser argsParser, final WriteableGame game,
                                                     final Logic logic) throws IOException {
        final List<UserInterface> uis = new ArrayList<>();
        if (argsParser.isReplayerActivated()) {
            // Intialize replayers
            Settings.setReplayable();
            final int replayerCount = Replayer.extractPlayerNames(argsParser.getReplayFilename()).size();
            Stream.iterate(0, count -> count + 1).limit(replayerCount).forEach(ignore -> {
                try {
                    uis.add(new Replayer(game, logic, argsParser.getReplayFilename()));
                } catch (final Exception e) {
                    throw new RuntimeException("The Replayer could not be initialized.", e);
                }
            });
        } else {
            // Intialize players
            @SuppressWarnings("unchecked") final List<UserInterface> players = argsParser
                    .getPlayers()
                    .map(entry -> {
                        final String playerByName = Player.findPlayerClassByName(entry.getValue())
                                .orElseThrow(() -> new RuntimeException("No player found for " + entry.getValue()));
                        try {
                            final Class<UserInterface> uiType = (Class<UserInterface>) Class.forName(playerByName);
                            final Constructor<UserInterface> ctor = uiType.getDeclaredConstructor(ReadonlyGame.class,
                                    Logic.class, String.class);
                            return ctor.newInstance(game, logic, entry.getKey());
                        } catch (final Exception e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toList());
            uis.addAll(players);
        }
        return uis;
    }
}
