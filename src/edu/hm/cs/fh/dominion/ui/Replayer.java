/**
 *
 */
package edu.hm.cs.fh.dominion.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import edu.hm.cs.fh.dominion.database.ReadonlyGame;
import edu.hm.cs.fh.dominion.database.ReadonlyPlayer;
import edu.hm.cs.fh.dominion.logic.Logic;
import edu.hm.cs.fh.dominion.logic.moves.Move;

/**
 * The Replayer reads the record-file out and replay it.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 22.05.2014
 */
public class Replayer extends AbstractPlayer {
    /**
     * The system temp directory.
     */
    private static final String TEMP_DIRECTORY = System.getProperty("java.io.tmpdir");
    /**
     * The temp directory for the dominion files.
     */
    private static final File TEMP_DIRECTORY_DOMINION = new File(TEMP_DIRECTORY + File.separatorChar + "Dominion");
    /**
     * A regex pattern to filter the player names.
     */
    private static final Pattern PATTERN_PLAYER_NAMES = Pattern.compile("Name: ([^,]+)");
    /**
     * The moves to play as string.
     */
    private final List<String> textMoves;
    /**
     * The player this one represents.
     */
    private final ReadonlyPlayer player;

    /**
     * Creates a new player.
     *
     * @param game     of datastoreage.
     * @param logic    for every logical check.
     * @param filename of the record file.
     * @throws IOException is thrown in case of file not found or something like this.
     */
    public Replayer(final ReadonlyGame game, final Logic logic, final String filename) throws IOException {
        super(game, logic);

        try (BufferedReader reader = new BufferedReader(new FileReader(new File(TEMP_DIRECTORY_DOMINION, filename)))) {
            textMoves = reader.lines().filter(line -> line.charAt(0) == '#').map(line -> line.substring(1))
                    .collect(Collectors.toList());
        }

        // Find first free player name and take it
        final String firstFreePlayerName = extractPlayerNames(filename).stream().skip(game.getPlayerCount())
                .findFirst().get();
        player = logic.registerNewPlayer(firstFreePlayerName);
    }

    @Override
    public Optional<ReadonlyPlayer> getPlayer() {
        return Optional.of(player);
    }

    @Override
    public Move selectMove(final List<Move> moves) {
        final String nextMove = textMoves.get(0);
        return moves.parallelStream().filter(move -> nextMove.equals(move.toString())).findFirst().get();
    }

    @Override
    public void update(final Observable observable, final Object object) {
        if (object != null) {
            // keep the locale default save
            final Locale realLocale = Locale.getDefault();
            // just set the default locale for the record file output
            Locale.setDefault(Locale.GERMAN);

            // remove the move from the list
            textMoves.remove(object.toString());

            // reset the locale to saved default
            Locale.setDefault(realLocale);
        }
    }

    /**
     * Extracts the names of all players from the recorded file.
     *
     * @param filename of the file to extract from.
     * @return the extracted names.
     * @throws IOException is possible thrown by the reader.
     */
    public static List<String> extractPlayerNames(final String filename) throws IOException {
        final List<String> playerNames = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(TEMP_DIRECTORY_DOMINION, filename)))) {
            final String line = reader.lines().skip(1).findFirst().get();
            if (playerNames.isEmpty()) {
                // extract player names
                final Matcher matcherAllPlayers = PATTERN_PLAYER_NAMES.matcher(line);
                while (matcherAllPlayers.find()) {
                    final String playerName = matcherAllPlayers.group(1);
                    if (!playerNames.contains(playerName)) {
                        // If player is not in the list yet --> add the name at the last
                        // position
                        playerNames.add(playerNames.size(), playerName);
                    }
                }
            }
        }
        return playerNames;
    }
}
