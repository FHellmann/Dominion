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
    private static final File TEMP_DIRECTORY_DOMINION = new File(TEMP_DIRECTORY + File.separatorChar + "Dominion");
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
            Locale.setDefault(Locale.GERMAN);

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
        final List<String> supplyCardNameList = game.getSupplyCardSet()
                .map(Object::toString)
                .collect(Collectors.toList());
        final Set<String> sortedSupplyCardSet = new TreeSet<>(supplyCardNameList);
        final Optional<Card> resolveActionCard = game.getToResolveActionCard();
        final ReadonlyCardDeck waste = game.getWaste();

        final StringBuilder gameState = new StringBuilder()
                .append("{ State: ")
                .append(state)
                .append(" }")
                .append(",")
                .append("{ PlayerCount: ")
                .append(playerCount)
                .append(" }")
                .append(",")
                .append("{ CurrentPlayer: ")
                .append(getPlayerData(currentPlayer))
                .append(" }")
                .append(",")
                .append("{ Players: ")
                .append(players.map(player -> "{ Player: " + getPlayerData(player) + " }").collect(
                        Collectors.joining(", ")))
                .append(" }")
                .append(",")
                .append("{ AttackCard: ")
                .append(attackCard.isPresent() ? attackCard.get() : "null")
                .append(" }")
                .append(",")
                .append("{ Attacker: ")
                .append(attacker.isPresent() ? attacker.get().getName() : "null")
                .append(" }")
                .append(",")
                .append("{ SupplyCardCount: ")
                .append(supplyCardCount)
                .append(" }")
                .append(",")
                .append("{ SupplyCards: ")
                .append(sortedSupplyCardSet
                        .stream()
                        .map(cardName -> cardName
                                + "("
                                + game.getSupplyCardCount(game.getSupplyCardSet()
                                .filter(card -> card.toString().equals(cardName)).findFirst().get()) + ")")
                        .collect(Collectors.joining(", "))).append(" }").append(",").append("{ ResolveActionCard: ")
                .append(resolveActionCard.isPresent() ? resolveActionCard.get() : "null").append(" }").append(",")
                .append("{ Waste: ").append(getCarddeckData(waste)).append(" }");

        return gameState.toString();
    }

    /**
     * Get the player data as string.
     *
     * @param player to convert.
     * @return the data string.
     */
    private static String getPlayerData(final ReadonlyPlayer player) {
        final StringBuilder playerData = new StringBuilder().append("{ ").append("Name: ").append(player.getName())
                .append(", ").append("Actions: ").append(player.getActions().getCount()).append(", ").append("Coins: ")
                .append(player.getMoney().getCount()).append(", ").append("Buys: ")
                .append(player.getPurchases().getCount()).append(", ").append("Points: ")
                .append(player.getVictoryPoints().getCount()).append(", ").append("Hand: ")
                .append(getCarddeckData(player.getCardDeckHand())).append(", ").append("Pull: ")
                .append(getCarddeckData(player.getCardDeckPull())).append(", ").append("Played: ")
                .append(getCarddeckData(player.getCardDeckPlayed())).append(", ").append("Stacker: ")
                .append(getCarddeckData(player.getCardDeckStacker())).append(" }");
        return playerData.toString();
    }

    /**
     * Get the carddeck data as string.
     *
     * @param carddeck to convert.
     * @return the data string.
     */
    private static String getCarddeckData(final ReadonlyCardDeck carddeck) {
        final StringBuilder carddeckData = new StringBuilder();
        carddeckData.append("{ ").append(carddeck.toString()).append(" }");
        return carddeckData.toString();
    }

    @Override
    public Move selectMove(List<Move> moves) {
        throw new RuntimeException("This method should not be called");
    }
}
