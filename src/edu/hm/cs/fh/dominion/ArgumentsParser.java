/**
 *
 */
package edu.hm.cs.fh.dominion;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.hm.cs.fh.dominion.ui.ConsolePlayer;
import edu.hm.cs.fh.dominion.ui.JavaFxPlayer;
import edu.hm.cs.fh.dominion.ui.NetPlayer;
import edu.hm.cs.fh.dominion.ui.Robot1;
import edu.hm.cs.fh.dominion.ui.RobotDefender;
import edu.hm.cs.fh.dominion.ui.RobotMilitia;
import edu.hm.cs.fh.dominion.ui.RobotSorcerer;
import edu.hm.cs.fh.dominion.ui.RobotX;

/**
 * A tool to parse the arguments given from the main.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 05.06.2014
 */
public class ArgumentsParser {
	/** The platform dependend new line. */
	protected static final String NEW_LINE = System.getProperty("line.separator");
	/** The constant for help as sign. */
	private static final String ARG_HELP = "-?";
	/** The constant for help as abc. */
	private static final String ARG_HELP_ABC = "-h";
	/** The constant for the replayer. */
	private static final String ARG_REPLAYER = "-p";
	/** The constant for the recorder. */
	private static final String ARG_RECORDER = "-r";
	/** The constant for the public viewer. */
	private static final String ARG_PUBLICVIEWER = "-v";
	/** The constants saved in a list. */
	private final List<String> constants;
	/** The players saved in a map with SimpleClassName and PlayerName. */
	private final Map<String, String> players;

	/**
	 * Creates a new command line argument parser.
	 *
	 * @param args
	 *            to parse.
	 */
	public ArgumentsParser(final String[] args) {
		this(Arrays.asList(args));
	}

	/**
	 * Creates a new command line argument parser.
	 *
	 * @param args
	 *            to parse.
	 */
	public ArgumentsParser(final List<String> args) {
		constants = args.parallelStream().filter(arg -> !arg.contains("=")).map(arg -> arg)
				.collect(Collectors.toList());
		players = args.parallelStream().filter(arg -> arg.contains("=")).map(arg -> arg.split("="))
				.collect(Collectors.toMap(arg -> arg[1], arg -> arg[0]));
	}

	/**
	 * Get the players in a stream with entries. The key is the SimpleClassName and the value is the
	 * PlayerName.
	 *
	 * @return the players.
	 */
	public Stream<Entry<String, String>> getPlayers() {
		return players.entrySet().stream();
	}

	/**
	 * Exists a JavaFxPlayer.
	 *
	 * @return <code>true</code> if a JavaFxPlayer exists.
	 */
	public boolean isJavaFxApplication() {
		return players.values().parallelStream().filter(key -> key.equals("JavaFxPlayer")).findFirst().isPresent();
	}

	/**
	 * Exists a Recorder.
	 *
	 * @return <code>true</code> if a Recorder exists.
	 */
	public boolean isRecorderActivated() {
		return constants.contains(ARG_RECORDER);
	}

	/**
	 * Get the filepath the recorder has to write to.
	 *
	 * @return the filepath.
	 */
	public String getRecordFilePath() {
		return constants.get(constants.indexOf(ARG_RECORDER) + 1);
	}

	/**
	 * Exists a Recorder.
	 *
	 * @return <code>true</code> if a Recorder exists.
	 */
	public boolean isReplayerActivated() {
		return constants.contains(ARG_REPLAYER);
	}

	/**
	 * Get the filepath the replayer has to read the commands from.
	 *
	 * @return the filepath.
	 */
	public String getReplayFilePath() {
		return constants.get(constants.indexOf(ARG_REPLAYER) + 1);
	}

	/**
	 * Exists a PublicViewer.
	 *
	 * @return <code>true</code> if a PublicViewer exists.
	 */
	public boolean isPublicViewerActivated() {
		return constants.contains(ARG_PUBLICVIEWER);
	}

	/**
	 * Exists a Recorder.
	 *
	 * @return <code>true</code> if a Recorder exists.
	 */
	public boolean isHelpRequired() {
		return constants.contains(ARG_HELP_ABC) | constants.contains(ARG_HELP);
	}

	/**
	 * Display all options.
	 */
	public void showArguments() {
		final StringBuilder strBuilder = new StringBuilder(529);
		strBuilder
				.append("########### DOMINION ###########")
				.append(NEW_LINE)
				.append("You have the following options:")
				.append(NEW_LINE)
				.append("\t" + ARG_HELP_ABC + " or " + ARG_HELP + "\t will show you the help.")
				.append(NEW_LINE)
				.append("\t"
						+ ARG_RECORDER
						+ " <filename> \t allows you to record the game to a file. This file will be stored in the temp-directory/Dominion/.")
				.append(NEW_LINE)
				.append("\t"
						+ ARG_REPLAYER
						+ " <filepath> \t allows you to automaticly replay the game from a recorded game file.allows you to automaticly replay the game from a recorded game file. This will grab the file from the temp-directory/Dominion/<filename>.")
				.append(NEW_LINE)
				.append("\t"
						+ ARG_PUBLICVIEWER
						+ " \t allows you to see the running game and it's players. Possible to use if only roboters play.")
				.append(NEW_LINE);
		appendPlayer(strBuilder, ConsolePlayer.class, "You will play in the command line.");
		appendPlayer(strBuilder, JavaFxPlayer.class, "You will play in an graphical user interface made with Java-FX.");
		appendPlayer(strBuilder, NetPlayer.class, "Someone else can play with you throw the network. (Port=2014)");
		final String robotDescription = "A roboter played by the cpu.";
		appendPlayer(strBuilder, RobotDefender.class, robotDescription);
		appendPlayer(strBuilder, RobotMilitia.class, robotDescription);
		appendPlayer(strBuilder, RobotSorcerer.class, robotDescription);
		appendPlayer(strBuilder, RobotX.class, robotDescription);
		appendPlayer(strBuilder, Robot1.class, robotDescription);
		System.out.println(strBuilder.toString());
		System.out.println("All players have to get different names.");
	}

	/**
	 * Appends the SimpleClassName of the player.
	 *
	 * @param string
	 *            to append to.
	 * @param playerClass
	 *            to get the SimpleClassName from.
	 */
	private static void appendPlayer(final StringBuilder string, final Class<?> playerClass, final String description) {
		string.append("\t" + playerClass.getSimpleName() + "=<name>\t " + description + NEW_LINE);
	}
}
