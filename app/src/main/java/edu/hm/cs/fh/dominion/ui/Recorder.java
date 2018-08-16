/**
 *
 */
package edu.hm.cs.fh.dominion.ui;

import edu.hm.cs.fh.dominion.database.ReadonlyCardDeck;
import edu.hm.cs.fh.dominion.database.ReadonlyGame;
import edu.hm.cs.fh.dominion.database.ReadonlyPlayer;
import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.logic.Logic;
import edu.hm.cs.fh.dominion.logic.moves.Move;
import edu.hm.cs.fh.dominion.ui.io.ContentIO;
import edu.hm.cs.fh.dominion.ui.io.FileIO;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The Recorder writes every Move-Object and the Game-State to a file.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 22.05.2014
 */
public class Recorder extends AbstractPlayer {
    /**
     * The system temp directory.
     */
    private static final String TEMP_DIRECTORY = System.getProperty("java.io.tmpdir");
    /**
     * The temp directory for the dominion files.
     */
    static final File TEMP_DIRECTORY_DOMINION = new File(TEMP_DIRECTORY + File.separatorChar + "Dominion");
    /**
     * The output writer.
     */
    private final ContentIO inOut;

    static {
        if (!TEMP_DIRECTORY_DOMINION.exists()) {
            TEMP_DIRECTORY_DOMINION.mkdir();
        }
    }

    /**
     * Creates a new recorder.
     *
     * @param game  of datastoreage.
     * @param logic for every logical check.
     * @param name  of the logger.
     * @throws IOException is thrown by the file-io.
     */
    public Recorder(final ReadonlyGame game, final Logic logic, final String name) throws IOException {
        super(game, logic);
        inOut = new FileIO(new File(TEMP_DIRECTORY_DOMINION, name));
    }

    @Override
    public void update(final Observable observable, final Object object) {
        if (object != null) {
            // keep the locale default save
            final Locale realLocale = Locale.getDefault();
            // just set the default locale for the record file output
            Locale.setDefault(Locale.ENGLISH);

            // write the output
            inOut.sendOutput("#" + object.toString());
            inOut.sendOutput("[" + getGameState(getGame()) + "]");

            // reset the locale to saved default
            Locale.setDefault(realLocale);
        }
    }

    /**
     * Get the game data as string.
     *
     * @param game to convert.
     * @return the data string.
     */
    private static String getGameState(final ReadonlyGame game) {
        final State state = game.getState();
        final int playerCount = game.getPlayerCount();
        final ReadonlyPlayer currentPlayer = game.getCurrentPlayer();
        final Stream<ReadonlyPlayer> players = game.getPlayers();
        final Optional<Card> attackCard = game.getAttackCard();
        final Optional<ReadonlyPlayer> attacker = game.getAttacker();
        final int supplyCardCount = game.getSupplySize();
        // Just put the cards around to get them every time in the same order
        final Set<String> sortedSupplyCardSet = game.getSupplyCardSet()
                .map(Object::toString).collect(Collectors.toCollection(TreeSet::new));
        final Optional<Card> resolveActionCard = game.getToResolveActionCard();
        final ReadonlyCardDeck waste = game.getWaste();

        return "{ State: " +
                state +
                " }" +
                "," +
                "{ PlayerCount: " +
                playerCount +
                " }" +
                "," +
                "{ CurrentPlayer: " +
                getPlayerData(currentPlayer) +
                " }" +
                "," +
                "{ Players: " +
                players.map(player -> "{ Player: " + getPlayerData(player) + " }").collect(
                        Collectors.joining(", ")) +
                " }" +
                "," +
                "{ AttackCard: " +
                (attackCard.isPresent() ? attackCard.get() : "null") +
                " }" +
                "," +
                "{ Attacker: " +
                (attacker.isPresent() ? attacker.get().getName() : "null") +
                " }" +
                "," +
                "{ SupplyCardCount: " +
                supplyCardCount +
                " }" +
                "," +
                "{ SupplyCards: " +
                sortedSupplyCardSet
                        .stream()
                        .map(cardName -> cardName
                                + "("
                                + game.getSupplyCardCount(game.getSupplyCardSet()
                                .filter(card -> card.toString().equals(cardName)).findFirst().get()) + ")")
                        .collect(Collectors.joining(", ")) +
                " }" + "," + "{ ResolveActionCard: " +
                (resolveActionCard.isPresent() ? resolveActionCard.get() : "null") + " }" + "," +
                "{ Waste: " + getCarddeckData(waste) + " }";
    }

    /**
     * Get the player data as string.
     *
     * @param player to convert.
     * @return the data string.
     */
    private static String getPlayerData(final ReadonlyPlayer player) {
        return "{ " + "Name: " + player.getName() +
                ", " + "Actions: " + player.getActions().getCount() + ", " + "Coins: " +
                player.getMoney().getCount() + ", " + "Buys: " +
                player.getPurchases().getCount() + ", " + "Points: " +
                player.getVictoryPoints().getCount() + ", " + "Hand: " +
                getCarddeckData(player.getCardDeckHand()) + ", " + "Pull: " +
                getCarddeckData(player.getCardDeckPull()) + ", " + "Played: " +
                getCarddeckData(player.getCardDeckPlayed()) + ", " + "Stacker: " +
                getCarddeckData(player.getCardDeckStacker()) + " }";
    }

    /**
     * Get the carddeck data as string.
     *
     * @param carddeck to convert.
     * @return the data string.
     */
    private static String getCarddeckData(final ReadonlyCardDeck carddeck) {
        return "{ " + carddeck.toString() + " }";
    }

    @Override
    public Move selectMove(List<Move> moves) {
        throw new RuntimeException("This method should not be called");
    }
}
