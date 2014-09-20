/**
 *
 */
package edu.hm.cs.fh.dominion.ui.javafx;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import edu.hm.cs.fh.dominion.logic.moves.Move;

/**
 * The move selector will be displayed as an overlay over the primarystage.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 28.05.2014
 */
public class MoveSelectorOverlay {
	/** The possible moves which will be displayed in a list. */
	private final List<Move> moves;
	/** The players choise for his move. */
	private Move chosenMove;

	/**
	 * Creates a new overlay which shows all the possible moves as clickable objects.
	 *
	 * @param possibleMoves
	 *            to display.
	 */
	public MoveSelectorOverlay(final List<Move> possibleMoves) {
		moves = possibleMoves;
	}

	/**
	 * Shows the overlay and waits for a click on a possible move before the overlay disapears.
	 *
	 * @return the selected move.
	 */
	public Move showAndWait() {
		final Stage stage = new Stage();
		// let the main stage shine through
		stage.initStyle(StageStyle.TRANSPARENT);
		// keep the overlay in the front
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setMaximized(true);
		stage.setResizable(false);

		// Display all possible moves in a listview on the right bottom
		final ListView<Move> moveList = new ListView<>(FXCollections.observableArrayList(moves));
		moveList.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		moveList.setLayoutX(1500.0);
		moveList.setLayoutY(659.0);
		moveList.setPrefWidth(406.0);
		moveList.setPrefHeight(407.0);
		moveList.setOnMouseClicked(event -> {
			if (!moveList.getSelectionModel().getSelectedItems().isEmpty()) {
				chosenMove = moveList.getSelectionModel().getSelectedItem();
				stage.close();
			}
		});

		final AnchorPane anchorPane = new AnchorPane(moveList);

		// make every background transparent to shine the main stage through
		anchorPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
		final Scene scene = new Scene(anchorPane, Color.TRANSPARENT);

		stage.setScene(scene);
		stage.toFront();
		stage.showAndWait();

		// Bring the stage to the back to prevent flashing at the screen sides
		stage.toBack();

		return chosenMove;
	}
}
