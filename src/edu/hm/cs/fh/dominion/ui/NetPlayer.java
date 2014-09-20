/**
 *
 */
package edu.hm.cs.fh.dominion.ui;

import java.io.IOException;

import edu.hm.cs.fh.dominion.database.ReadonlyGame;
import edu.hm.cs.fh.dominion.logic.Logic;
import edu.hm.cs.fh.dominion.ui.io.NetIO;

/**
 * A net player is played by a person across the network.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 21.04.2014
 */
public class NetPlayer extends AbstractHumanPlayer {
	/**
	 * Creates a new net-player.
	 *
	 * @param game
	 *            of datastoreage.
	 * @param logic
	 *            for every logical check.
	 * @param name
	 *            of the player.
	 * @throws IOException
	 *             is thrown by the network io handler.
	 */
	public NetPlayer(final ReadonlyGame game, final Logic logic, final String name) throws IOException {
		super(game, logic, name, new NetIO());
	}
}
