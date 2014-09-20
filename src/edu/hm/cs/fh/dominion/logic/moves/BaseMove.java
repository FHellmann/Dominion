/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingFormatArgumentException;
import java.util.Optional;

import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;
import edu.hm.cs.fh.dominion.logic.moves.CheckFactory.Check;
import edu.hm.cs.fh.dominion.resources.ResourceDelegator;

/**
 * A basic move which every move extends.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 17.04.2014
 */
public abstract class BaseMove implements Move {
	/** The game reference. */
	private final WriteableGame game;
	/** The player who called the move object. */
	private final WriteablePlayer player;
	/** The card to play. */
	private final Card card;
	/** Every check the move has to pass. */
	private final List<Check> checks = new ArrayList<>();

	/**
	 * Creates a new basic move.
	 *
	 * @param game
	 *            to modify.
	 */
	public BaseMove(final WriteableGame game) {
		this(game, null, null);
	}

	/**
	 * Creates a new basic move.
	 *
	 * @param game
	 *            to modify.
	 * @param player
	 *            who want's to play the move.
	 */
	public BaseMove(final WriteableGame game, final WriteablePlayer player) {
		this(game, player, null);
	}

	/**
	 * Creates a new basic move.
	 *
	 * @param game
	 *            to modify.
	 * @param player
	 *            who want's to play the move.
	 * @param card
	 *            to play.
	 */
	public BaseMove(final WriteableGame game, final WriteablePlayer player, final Card card) {
		this.game = game;
		this.player = player;
		this.card = card;
	}

	/**
	 * The read-write-game in return.
	 *
	 * @return the game.
	 */
	public WriteableGame getGame() {
		return game;
	}

	/**
	 * The player who called this move.
	 *
	 * @return the player.
	 */
	public Optional<WriteablePlayer> getPlayer() {
		return Optional.ofNullable(player);
	}

	/**
	 * The card the player wants to play.
	 *
	 * @return the card.
	 */
	public Optional<Card> getCard() {
		return Optional.ofNullable(card);
	}

	@Override
	public final MoveResult fire() {
		final MoveResult result = test();
		if (result.isPossible()) {
			onFire();
		}
		return result;
	}

	/**
	 * Will be called if the {@link MoveResult} from the {@link #test()} was possible.
	 */
	public abstract void onFire();

	@Override
	public final MoveResult test() {
		// Filer every impossible checks and get the first
		final Optional<MoveResult> moveResult = checks.stream().map(check -> check.isCorrect(game, player, card))
				.filter(result -> !result.isPossible()).findFirst();

		final MoveResult result;
		// check if MoveResult is present
		if (moveResult.isPresent()) {
			// if so return it
			result = moveResult.get();
		} else {
			// else don't return null -> create new possible MoveResult
			result = new MoveResult(true, "");
		}
		return result;
	}

	/**
	 * Add a check to the move it has to pass before execution.
	 *
	 * @param check
	 *            to pass.
	 */
	protected void addCheck(final Check check) {
		checks.add(check);
	}

	@Override
	public String toString() {
		// Try to get the localized text from the move-classname
		String moveName;
		try {
			// the text without placeholder
			moveName = ResourceDelegator.getI18N(getClass().getSimpleName().toLowerCase());
		} catch (final MissingFormatArgumentException e) {
			// the text with placeholder
			moveName = ResourceDelegator.getI18N(getClass().getSimpleName().toLowerCase(), getCard().get().getName());
		}
		return moveName;
	}
}