package edu.hm.cs.fh.dominion.ui.javafx;

import edu.hm.cs.fh.dominion.ArgumentsParser;
import edu.hm.cs.fh.dominion.database.ReadonlyGame;
import edu.hm.cs.fh.dominion.database.full.Game;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.logic.Logic;
import edu.hm.cs.fh.dominion.logic.Settings;
import edu.hm.cs.fh.dominion.ui.Player;
import edu.hm.cs.fh.dominion.ui.PublicViewer;
import edu.hm.cs.fh.dominion.ui.Recorder;
import edu.hm.cs.fh.dominion.ui.UserInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JavaFxApp extends Application {
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
        // Intialize players
        @SuppressWarnings("unchecked")
        final List<UserInterface> players = argsParser
                .getPlayers()
                .map(entry -> {
                    final Optional<String> playerByName = Player.findPlayerClassByName(entry.getValue());
                    if (playerByName.isPresent()) {
                        try {
                            final Class<UserInterface> uiType = (Class<UserInterface>) Class.forName(playerByName.get());
                            final Constructor<UserInterface> ctor = uiType.getDeclaredConstructor(ReadonlyGame.class,
                                    Logic.class, String.class);
                            return ctor.newInstance(game, logic, entry.getKey());
                        } catch (final Exception e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        throw new RuntimeException("No player found for " + entry.getValue());
                    }
                })
                .peek(System.out::println)
                .collect(Collectors.toList());
        uis.addAll(players);
        return uis;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Dominion");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setFullScreen(true);
        primaryStage.setMaximized(true);
        primaryStage.setIconified(true);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("images/dominion_icon.png")));

        // create Loader
        final FXMLLoader myLoader = new FXMLLoader(getClass().getResource("application.fxml"));

        // load the fxml
        final Parent loadScreen = myLoader.load();

        // Normal-Launcher
        final ArgumentsParser argsParser = new ArgumentsParser(getParameters().getRaw());
        final WriteableGame game = new Game();
        final Logic logic = new Logic(game);
        final List<UserInterface> uis = new ArrayList<>();

        if (argsParser.isRecorderActivated()) {
            // Initialize and add the Recorder
            Settings.setReplayable();
            uis.add(new Recorder(game, logic, argsParser.getRecordFilePath()));
        }

        if (argsParser.isPublicViewerActivated()) {
            // Initialize and add the PublicViewer
            uis.add(new PublicViewer(game, logic));
        }

        // Create and add the players
        uis.addAll(createPlayers(argsParser, game, logic));

        // get the controller from fxml
        final GuiController controller = myLoader.getController();
        controller.setGame(game);
        game.addObserver(controller);

        // Add mainContainer to layout
        final AnchorPane root = new AnchorPane();
        root.setManaged(true);
        root.getChildren().addAll(loadScreen);
        root.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        // Fit mainContainer to layout
        AnchorPane.setTopAnchor(loadScreen, 0.0);
        AnchorPane.setBottomAnchor(loadScreen, 0.0);
        AnchorPane.setLeftAnchor(loadScreen, 0.0);
        AnchorPane.setRightAnchor(loadScreen, 0.0);

        // Create scene and show all
        final Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.toFront();
        primaryStage.show();

        // Let's play the game!
        UserInterface.loopAsync(game, logic, uis);
    }
}
