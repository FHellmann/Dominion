/**
 *
 */
package edu.hm.cs.fh.dominion.ui;

import java.util.Observable;
import java.util.stream.Collectors;

import edu.hm.cs.fh.dominion.database.ReadonlyGame;
import edu.hm.cs.fh.dominion.logic.Logic;
import edu.hm.cs.fh.dominion.logic.moves.ViewGameResult;
import edu.hm.cs.fh.dominion.logic.moves.card.ShowCards;
import edu.hm.cs.fh.dominion.ui.io.ConsoleIO;
import edu.hm.cs.fh.dominion.ui.io.ContentIO;

/**
 * A logger writes every update to a log.txt file.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 21.04.2014
 */
public class PublicViewer extends AbstractPlayer {
	/** The output writer. */
	private final ContentIO inOut;

	/**
	 * Creates a new logger.
	 *
	 * @param game
	 *            of datastoreage.
	 * @param logic
	 *            for every logical check.
	 */
	public PublicViewer(final ReadonlyGame game, final Logic logic) {
		super(game, logic);
		inOut = new ConsoleIO();
	}

	@Override
	public void update(final Observable observable, final Object object) {
		// only show the moves not the datastore updates
		if (object != null) {
			inOut.sendOutput(getGameState());
			inOut.sendOutput("\t~ MOVE: " + object);

			if (object instanceof ViewGameResult) {
				final StringBuilder strBuilder = new StringBuilder(" ~ Game Result:\n");
				getGame().getPlayers().forEach(
						player -> strBuilder.append("\t").append(player.getName()).append("\t")
								.append(player.getVictoryPoints().getCount()).append("\n"));

				inOut.sendOutput(strBuilder.toString());
			} else if (object instanceof ShowCards) {
				final ShowCards show = (ShowCards) object;
				final StringBuilder strBuilder = new StringBuilder("\t+ Karten: ");
				strBuilder.append(show.getCards().map(card -> card.getName()).collect(Collectors.joining(", ")));
				inOut.sendOutput(strBuilder.toString());
			}
		}
	}
}
