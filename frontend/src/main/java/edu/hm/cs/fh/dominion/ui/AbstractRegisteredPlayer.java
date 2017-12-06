/**
 *
 */
package edu.hm.cs.fh.dominion.ui;

import java.util.Optional;

import edu.hm.cs.fh.dominion.database.ReadonlyGame;
import edu.hm.cs.fh.dominion.database.ReadonlyPlayer;
import edu.hm.cs.fh.dominion.logic.Logic;

/**
 * An abstract player with the baseinformation about the game and himself.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 21.04.2014
 */
public abstract class AbstractRegisteredPlayer extends AbstractPlayer {
	private final ReadonlyPlayer player;

	/**
	 * Creates a new player.
	 *
	 * @param game
	 *            of datastoreage.
	 * @param logic
	 *            for every logical check.
	 * @param name
	 *            of the player.
	 */
	public AbstractRegisteredPlayer(final ReadonlyGame game, final Logic logic, final String name) {
		super(game, logic);
		player = logic.registerNewPlayer(name);
	}

	@Override
	public Optional<ReadonlyPlayer> getPlayer() {
		return Optional.of(player);
	}
}