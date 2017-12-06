/**
 *
 */
package edu.hm.cs.fh.dominion.ui;

import edu.hm.cs.fh.dominion.database.ReadonlyGame;
import edu.hm.cs.fh.dominion.logic.Logic;
import edu.hm.cs.fh.dominion.ui.io.ConsoleIO;

/**
 * A console player is played by a real person.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 21.04.2014
 */
public class ConsolePlayer extends AbstractHumanPlayer {
	/**
	 * Creates a new console-player.
	 *
	 * @param game
	 *            of datastoreage.
	 * @param logic
	 *            for every logical check.
	 * @param name
	 *            of the player.
	 */
	public ConsolePlayer(final ReadonlyGame game, final Logic logic, final String name) {
		super(game, logic, name, new ConsoleIO());
	}
}
