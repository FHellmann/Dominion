/**
 *
 */
package edu.hm.cs.fh.dominion.ui;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Optional;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import edu.hm.cs.fh.dominion.ArgumentsParser;
import edu.hm.cs.fh.dominion.ThreadedGameLoop;
import edu.hm.cs.fh.dominion.database.ReadonlyGame;
import edu.hm.cs.fh.dominion.logic.Logic;
import edu.hm.cs.fh.dominion.logic.Settings;
import edu.hm.cs.fh.dominion.logic.moves.Move;
import edu.hm.cs.fh.dominion.ui.javafx.GuiController;
import edu.hm.cs.fh.dominion.ui.javafx.MoveSelectorOverlay;

/**
 * A JavaFX GUI.<br>
 * This GUI is only viewable at a monitor with a full-hd screen resolution.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 18.05.2014
 */
public class JavaFxPlayer extends Application {
	@Override
	public void start(final Stage primaryStage) {
		primaryStage.setTitle("Dominion");
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setWidth(1920);
		primaryStage.setHeight(1080);
		primaryStage.setMaximized(true);
		primaryStage.setIconified(true);
		primaryStage.setResizable(false);
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("javafx/images/dominion_icon.png")));

		// create Loader
		final FXMLLoader myLoader = new FXMLLoader(getClass().getResource("javafx/application.fxml"));

		// load the fxml
		try {
			final Parent loadScreen = myLoader.load();

			// get the controller from fxml
			final GuiController controller = myLoader.getController();

			// Add mainContainer to layout
			final AnchorPane root = new AnchorPane();
			root.setManaged(true);
			root.getChildren().addAll(loadScreen);
			root.getStylesheets().add(getClass().getResource("javafx/application.css").toExternalForm());

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

			startGame(controller);
		} catch (final IOException e) {
			System.err.println("Unable to start game");
			e.printStackTrace();
		}
	}

	/**
	 * Initialize all the game objects and run the game loop in an own thread.
	 *
	 * @param controller
	 *            of the Java-FX gui.
	 */
	private void startGame(final GuiController controller) {
		// Initialize game
		final ThreadedGameLoop gameLoop = new ThreadedGameLoop();

		final ArgumentsParser argsParser = new ArgumentsParser(getParameters().getRaw());
		final List<UserInterface> uis = new ArrayList<>();
		if (argsParser.isRecorderActivated()) {
			// Initialize and add the Recorder
			Settings.setReplayable();
			try {
				uis.add(new Recorder(gameLoop.getGame(), gameLoop.getLogic(), argsParser.getRecordFilePath()));
			} catch (final IOException e) {
				// Only show the user a error message... the programm can run at all
				System.err.println("Failed to start Recorder...");
				e.printStackTrace();
			}
		}

		// Intialize players
		final List<UserInterface> players = argsParser.getPlayers()
				.filter(entry -> !entry.getValue().equals(getClass().getSimpleName()))
				.map(entry -> loadClass(gameLoop, entry)).collect(Collectors.toList());
		uis.addAll(players);

		// filter the name of the Java-FX-Player from the programm arguments
		final String javaFxPlayerName = getParameters().getRaw().stream().map(arg -> arg.split("="))
				.filter(typeAndName -> typeAndName[0].equals("JavaFxPlayer")).map(typeAndName -> typeAndName[1])
				.findFirst().get();
		uis.add(new RealPlayer(gameLoop.getGame(), gameLoop.getLogic(), javaFxPlayerName, controller));

		gameLoop.start(uis);
	}

	/**
	 * Loads the players classes.
	 *
	 * @param gameLoop
	 *            to initialize the players.
	 * @param entry
	 *            with key=SimpleClassName and value=Playersname
	 * @return the player object.
	 */
	@SuppressWarnings("unchecked")
	private static UserInterface loadClass(final ThreadedGameLoop gameLoop, final Entry<String, String> entry) {
		try {
			final Class<UserInterface> uiType = (Class<UserInterface>) Class.forName("edu.hm.cs.fh.dominion.ui."
					+ entry.getValue());
			final Constructor<UserInterface> ctor = uiType.getDeclaredConstructor(ReadonlyGame.class, Logic.class,
					String.class);
			return ctor.newInstance(gameLoop.getGame(), gameLoop.getLogic(), entry.getKey());
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * This class represents the real JavaFxPlayer.
	 *
	 * @author Fabio Hellmann, fhellman@hm.edu
	 * @version 18.05.2014
	 */
	private final class RealPlayer extends AbstractRegisteredPlayer implements UserInterface {
		/** The move which was selected from the user. */
		private Move selectedMove;
		/** The controller is the interface between the gui and the backend. */
		private final GuiController controller;

		/**
		 * Creates a new JavaFx player.
		 *
		 * @param game
		 *            of datastoreage.
		 * @param logic
		 *            for every logical check.
		 * @param name
		 *            of the player.
		 * @param controller
		 *            of the java fx gui.
		 */
		public RealPlayer(final ReadonlyGame game, final Logic logic, final String name, final GuiController controller) {
			super(game, logic, name);
			this.controller = controller;
		}

		@Override
		public void update(final Observable observable, final Object object) {
			// inform the controller to update the gui
			Platform.runLater(() -> controller.update(getGame(), getPlayer().get(), Optional.ofNullable((Move) object)));
		}

		@Override
		public Move selectMove(final List<Move> moves) {
			// Create a "Dialog"-Overlay over the primarystage to wait for user interaction
			try {
				runAndWait(() ->
				// This can't be executed in the normal thread -> must be a JavaFX-Thread
				selectedMove = new MoveSelectorOverlay(moves).showAndWait());
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
			return selectedMove;
		}

		/**
		 * Invokes a Runnable in JFX Thread and waits while it's finished.
		 *
		 * @param run
		 *            that has to be called on JFX thread.
		 * @throws InterruptedException
		 *             is thrown if execution is interrupted.
		 */
		public void runAndWait(final Runnable run) throws InterruptedException {
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
				} finally {
					lock.unlock();
				}
			}
		}
	}
}
