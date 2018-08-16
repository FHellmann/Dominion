package edu.hm.cs.fh.dominion.ui.javafx;

import edu.hm.cs.fh.dominion.database.ReadonlyGame;
import edu.hm.cs.fh.dominion.database.ReadonlyPlayer;
import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.database.cards.KingdomCard;
import edu.hm.cs.fh.dominion.database.cards.TreasuryCard;
import edu.hm.cs.fh.dominion.database.cards.VictoryCard;
import edu.hm.cs.fh.dominion.logic.moves.CleanupTurn;
import edu.hm.cs.fh.dominion.logic.moves.ExitGame;
import edu.hm.cs.fh.dominion.logic.moves.Move;
import edu.hm.cs.fh.dominion.logic.moves.ViewGameResult;
import edu.hm.cs.fh.dominion.logic.moves.card.ShowCards;
import edu.hm.cs.fh.dominion.ui.UserInterface;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;

import java.net.URL;
import java.util.*;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

/**
 * The GUI-Controller handles the communication between the user and the programm.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 28.05.2014
 */
@SuppressWarnings({"PMD.ExcessiveImports", "PMD.TooManyFields"})
public class GuiController implements Initializable, Observer {
    /**
     * The card width of a non JavaFX player.
     */
    private static final int CARD_SIZE_PLAYERS_OTHER = 25;
    /**
     * The card width of a JavaFX player.
     */
    private static final int CARD_SIZE_PLAYER_ME = 200;
    /**
     * The offset of a card to the next one.
     */
    private static final int CARD_OFFSET = 5;
    /**
     * A comparator for sorting the cards by their costs.
     */
    private static final Comparator<? super Card> CARD_COMPARATOR = Comparator.comparingInt(card -> card.getMetaData().getCost());
    /**
     * The ImageLoader handles the loading of the card image.
     */
    private final ImageLoader imageLoader = new ImageLoader();

    private ReadonlyGame game;

    // JavaFX - FXML Elements

    // For every player
    @FXML
    private Label actions;
    @FXML
    private Label buys;
    @FXML
    private Label coins;
    @FXML
    private ImageView attackCard;
    @FXML
    private Pane supplyCardPane;
    @FXML
    private Pane playedCardPane;
    @FXML
    private Pane wastePane;
    @FXML
    private Pane showCardPane;

    // Result pane
    @FXML
    private Pane resultPane;
    @FXML
    private Label labelResultPlayer1;
    @FXML
    private Label labelResultPlayer1Points;
    @FXML
    private Label labelResultPlayer2;
    @FXML
    private Label labelResultPlayer2Points;
    @FXML
    private Label labelResultPlayer3;
    @FXML
    private Label labelResultPlayer3Points;
    @FXML
    private Label labelResultPlayer4;
    @FXML
    private Label labelResultPlayer4Points;

    // All the other players
    @FXML
    private Label labelPlayer1;
    @FXML
    private Pane handCardPanePlayer1;
    @FXML
    private ImageView cardDeckStackerPlayer1;
    @FXML
    private Label labelPlayer2;
    @FXML
    private Pane handCardPanePlayer2;
    @FXML
    private ImageView cardDeckStackerPlayer2;
    @FXML
    private Label labelPlayer3;
    @FXML
    private Pane handCardPanePlayer3;
    @FXML
    private ImageView cardDeckStackerPlayer3;

    // Only the human player
    @FXML
    private Label labelPlayerGui;
    @FXML
    private Pane handCardPane;
    @FXML
    private ImageView cardDeckStacker;
    private UserInterface fxPlayer;
    //@FXML
    //private ListView moveSelectionMenu;

    /**
     * Set the name of a player and coloring the background in red if the player is the current
     * player.
     *
     * @param labelName to set the name in.
     * @param game      with all data.
     * @param player    who belongs to this data.
     */
    private static void setPlayerData(final Label labelName,
                                      final ReadonlyGame game,
                                      final ReadonlyPlayer player) {
        labelName.setVisible(true);

        labelName.setText(player.getName());
        if (game.getCurrentPlayer().equals(player)) {
            labelName.setBackground(new Background(new BackgroundFill(Color.web("#dd0000"), new CornerRadii(100),
                    null)));
        } else {
            labelName.setBackground(new Background(new BackgroundFill(Color.web("#DDD6C5"), new CornerRadii(100),
                    null)));
        }
    }

    /**
     * Set the result of the player with name and earned victory points.
     *
     * @param labelName   where the players name comes in.
     * @param labelPoints where the players victory points come in.
     * @param player      who belongs to this data.
     */
    private static void setPlayerResult(final Label labelName, final Label labelPoints, final ReadonlyPlayer player) {
        if (player == null) {
            labelName.setVisible(false);
            labelPoints.setVisible(false);
        } else {
            labelName.setText(player.getName());
            labelPoints.setText(Integer.toString(player.getVictoryPoints().getCount()));
        }
    }

    @Override
    public void initialize(final URL url, final ResourceBundle resource) {
        // Do nothing
    }

    @Override
    public void update(Observable observable, Object object) {
        if (game == null || object == null) {
            return;
        }
        Platform.runLater(() -> updateGame((Move) object));
    }

    private void updateGame(Move move) {
        showCardPane.getChildren().clear();

        // Handle the different moves
        if (move instanceof CleanupTurn) {
            // Clean for the next player
            playedCardPane.getChildren().clear();
        } else if (move instanceof ViewGameResult) {
            getGame().getPlayers().forEach(player -> updateResultPane(game, player));
            resultPane.setVisible(true);
        } else if (move instanceof ShowCards) {
            final ShowCards showCards = (ShowCards) move;
            updateDisplayedCards(showCardPane, showCards.getCards().collect(Collectors.toList()),
                    CARD_SIZE_PLAYER_ME / 2.0, true);
        } else if (move instanceof ExitGame) {
            System.exit(0);
        }
        updateContentData(game);
    }

    /**
     * Updates the data of the result view which can be shown at the end of a game.
     *
     * @param game         with all data.
     * @param javaFxPlayer which playes this gui.
     */
    private void updateResultPane(final ReadonlyGame game, final ReadonlyPlayer javaFxPlayer) {
        setPlayerResult(labelResultPlayer1, labelResultPlayer1Points, javaFxPlayer);
        game.getPlayers().filter(player -> !player.equals(javaFxPlayer)).findFirst()
                .ifPresent(player -> setPlayerResult(labelResultPlayer2, labelResultPlayer2Points, player));
        game.getPlayers().filter(player -> !player.equals(javaFxPlayer)).skip(1).findFirst()
                .ifPresent(player -> setPlayerResult(labelResultPlayer3, labelResultPlayer3Points, player));
        game.getPlayers().filter(player -> !player.equals(javaFxPlayer)).skip(2).findFirst()
                .ifPresent(player -> setPlayerResult(labelResultPlayer4, labelResultPlayer4Points, player));
    }

    /**
     * Update all the labels, panes, etc.
     *
     * @param game with all the data.
     */
    private void updateContentData(final ReadonlyGame game) {
        final ReadonlyPlayer currPlayer = game.getCurrentPlayer();

        actions.setText(Integer.toString(currPlayer.getActions().getCount()));
        buys.setText(Integer.toString(currPlayer.getPurchases().getCount()));
        coins.setText(Integer.toString(currPlayer.getMoney().getCount()));

        if (game.getAttackCard().isPresent()) {
            attackCard.setImage(imageLoader.getCardImage(game.getAttackCard().get(), attackCard.getFitWidth()));
        } else {
            attackCard.setImage(null);
        }

        // Players
        final List<ReadonlyPlayer> players = getGame().getPlayers()
                .filter(player -> player != fxPlayer.getPlayer().orElseThrow(IllegalStateException::new))
                .collect(Collectors.toList());
        final Optional<ReadonlyPlayer> player1 = fxPlayer.getPlayer();
        final Optional<ReadonlyPlayer> player2 = players.stream().skip(0).findFirst();
        final Optional<ReadonlyPlayer> player3 = players.stream().skip(1).findFirst();
        final Optional<ReadonlyPlayer> player4 = players.stream().skip(2).findFirst();

        // JavaFX Player
        if (player1.isPresent()) {
            setPlayerData(labelPlayerGui, game, player1.get());
            updateStacker(cardDeckStacker, player1.get());
            updateDisplayedCards(
                    handCardPane,
                    player1.get().getCardDeckHand().stream().collect(Collectors.toList()),
                    CARD_SIZE_PLAYER_ME,
                    true
            );
        } else {
            labelPlayerGui.setVisible(false);
            cardDeckStacker.setVisible(false);
        }

        // Player 1
        if (player2.isPresent()) {
            setPlayerData(labelPlayer1, game, player2.get());
            updateStacker(cardDeckStackerPlayer1, player2.get());
            updateDisplayedCards(
                    handCardPanePlayer1,
                    player2.get().getCardDeckHand().stream().collect(Collectors.toList()),
                    CARD_SIZE_PLAYERS_OTHER,
                    false
            );
        } else {
            labelPlayer1.setVisible(false);
            cardDeckStackerPlayer1.setVisible(false);
        }

        // Player 2
        if (player3.isPresent()) {
            setPlayerData(labelPlayer2, game, player3.get());
            updateStacker(cardDeckStackerPlayer2, player3.get());
            updateDisplayedCards(
                    handCardPanePlayer2,
                    player3.get().getCardDeckHand().stream().collect(Collectors.toList()),
                    CARD_SIZE_PLAYERS_OTHER,
                    false
            );
        } else {
            labelPlayer2.setVisible(false);
            cardDeckStackerPlayer2.setVisible(false);
        }

        // Player 3
        if (player4.isPresent()) {
            setPlayerData(labelPlayer3, game, player4.get());
            updateStacker(cardDeckStackerPlayer3, player4.get());
            updateDisplayedCards(
                    handCardPanePlayer3,
                    player4.get().getCardDeckHand().stream().collect(Collectors.toList()),
                    CARD_SIZE_PLAYERS_OTHER,
                    false
            );
        } else {
            labelPlayer3.setVisible(false);
            cardDeckStackerPlayer3.setVisible(false);
        }

        // The supply cards
        final double cardWidth = supplyCardPane.getWidth()
                / (4 + VictoryCard.values().length + TreasuryCard.values().length);

        updateSupply(game, cardWidth);

        // All played cards
        addCardsRotatedToPane(playedCardPane,
                game.getCurrentPlayer().getCardDeckPlayed().stream().collect(Collectors.toList()), cardWidth);

        // Waste
        final Node wasteImage = wastePane.getChildren().get(0); // Save card image
        addCardsRotatedToPane(wastePane, game.getWaste().stream().collect(Collectors.toList()), wastePane.getWidth());
        wastePane.getChildren().add(0, wasteImage); // add card image back to the root
    }

    /**
     * Add the cards in rotated form to the pane.
     *
     * @param pane      to add the cards to.
     * @param cards     to add.
     * @param cardWidth the cards have when they will be displayed.
     */
    private void addCardsRotatedToPane(final Pane pane, final List<Card> cards, final double cardWidth) {
        // An angle incrementer adds a value to the angle to rotate the card
        final IntFunction<Integer> angleIncrementer = new IntFunction<Integer>() {
            private static final int MAX_ANGLE = 90;
            private int angle = -MAX_ANGLE;

            @Override
            public Integer apply(final int cardCount) {
                return angle += MAX_ANGLE / (cardCount > 0 ? cardCount : MAX_ANGLE);
            }
        };

        // Reset the data of this pane
        pane.getChildren().clear();

        // Add the data
        cards.stream()
                .filter(Objects::nonNull)
                .forEach(card -> {
                    final Image image = imageLoader.getCardImage(card, cardWidth - CARD_OFFSET);

                    final ImageView imageView = new ImageView(image);
                    imageView.setLayoutX(0);
                    imageView.setLayoutY(0);
                    imageView.setRotate(angleIncrementer.apply(cards.size()));
                    imageView.setPreserveRatio(true);

                    pane.getChildren().add(imageView);
                });
    }

    /**
     * Update the supply cards area.
     *
     * @param game      with all data.
     * @param cardWidth the cards have to be displayed.
     */
    private void updateSupply(final ReadonlyGame game, final double cardWidth) {
        // Sort the cards
        final List<Card> supplyCardsTreasury = game.getSupplyCardSet()
                .filter(card -> card instanceof TreasuryCard)
                .sorted(CARD_COMPARATOR)
                .collect(Collectors.toList());
        final List<Card> supplyCardsVictory = game.getSupplyCardSet()
                .filter(card -> card instanceof VictoryCard)
                .sorted(CARD_COMPARATOR)
                .collect(Collectors.toList());
        final List<Card> supplyCardsKingdom = game.getSupplyCardSet().
                filter(card -> card instanceof KingdomCard)
                .sorted(CARD_COMPARATOR)
                .collect(Collectors.toList());

        // Add the cards all together
        final List<Card> completeSupply = new ArrayList<>();
        completeSupply.addAll(supplyCardsTreasury);
        completeSupply.addAll(supplyCardsVictory);
        completeSupply.addAll(supplyCardsKingdom);

        // Clear to reset
        supplyCardPane.getChildren().clear();

        // Rearrange the cards
        int xPosition = 0;
        double layoutY = CARD_OFFSET;
        int index = 0;
        for (final Card card : completeSupply) {
            final Image image = imageLoader.getCardImage(card, cardWidth - CARD_OFFSET);

            // put the cards in 2 lines for more overview
            if (index == completeSupply.size() / 2) {
                xPosition = 0;
                layoutY = image.getHeight() + CARD_OFFSET * 2;
            }

            // Display the card in an ImageView
            final ImageView imageView = new ImageView(image);
            imageView.setLayoutX(xPosition);
            imageView.setLayoutY(layoutY);
            imageView.setPreserveRatio(true);
            supplyCardPane.getChildren().add(imageView);

            // Show how many cards of this the supply contains now
            final int supplyCardCount = game.getSupplyCardCount(card);
            final Label labelCardCounter = new Label(Integer.toString(supplyCardCount));
            labelCardCounter.getStyleClass().add("counter-label");
            labelCardCounter.setPrefHeight(15);
            labelCardCounter.setPrefWidth(25);
            labelCardCounter.setAlignment(Pos.CENTER);
            labelCardCounter.setTextAlignment(TextAlignment.CENTER);
            labelCardCounter.setLayoutX(xPosition + image.getWidth() - labelCardCounter.getPrefWidth());
            labelCardCounter.setLayoutY(layoutY);
            supplyCardPane.getChildren().add(labelCardCounter);

            // next position
            xPosition += cardWidth;
            index++;
        }
    }

    /**
     * Update the stacker view.
     *
     * @param view   of the stacker.
     * @param player where the stacker belongs to.
     */
    private void updateStacker(final ImageView view, final ReadonlyPlayer player) {
        view.setVisible(true);

        final Optional<Card> firstStackerCard = player.getCardDeckStacker().stream().findFirst();
        if (firstStackerCard.isPresent()) {
            view.setImage(imageLoader.getCardImage(firstStackerCard.get(), view.getFitWidth()));
        } else {
            view.setImage(imageLoader.getCardBack(view.getFitWidth()));
        }
    }

    /**
     * Update the hand of a player.
     *
     * @param pane      where the hand of the player is displayed.
     * @param cardWidth to display.
     * @param showCards if <code>true</code> the cards will be shown to the Java-FX player.
     */
    private void updateDisplayedCards(final Pane pane, final List<Card> cardsToDisplay, final double cardWidth,
                                      final boolean showCards) {
        if (cardsToDisplay == null) {
            pane.setVisible(false);
        } else {
            pane.setVisible(true);
            pane.getChildren().clear();

            if (!cardsToDisplay.isEmpty()) {
                final double rotationLocationX = pane.getWidth() / 2;
                final double imageViewLocationY = pane.getLayoutY() - pane.getHeight();

                final double angle = 60.0 / cardsToDisplay.size();
                double startAngle = -angle * (cardsToDisplay.size() / 2.0);

                for (final Card card : cardsToDisplay) {
                    final Image image;
                    if (showCards) {
                        image = imageLoader.getCardImage(card, cardWidth);
                    } else {
                        image = imageLoader.getCardBack(cardWidth);
                    }
                    final ImageView imageView = new ImageView(image);
                    imageView.setPreserveRatio(true);
                    imageView.setLayoutX(rotationLocationX - imageView.getImage().getWidth() / 2);
                    imageView.setLayoutY(imageViewLocationY);

                    imageView.setLayoutY(-imageView.getImage().getHeight() / 2);
                    final Rotate rotate = new Rotate(startAngle, imageView.getImage().getWidth() / 2, cardWidth * 4);
                    imageView.getTransforms().add(rotate);
                    startAngle += angle;

                    final double zLayer = imageView.getTranslateZ();
                    imageView.setOnMouseDragEntered(event -> imageView.setTranslateZ(100));
                    imageView.setOnMouseDragExited(event -> imageView.setTranslateZ(zLayer));

                    pane.getChildren().add(imageView);
                }
            }
        }
    }

    private ReadonlyGame getGame() {
        return game;
    }

    public void setGame(ReadonlyGame game) {
        this.game = game;
    }

    public void setFxPlayer(UserInterface fxPlayer) {
        this.fxPlayer = fxPlayer;
    }
}
