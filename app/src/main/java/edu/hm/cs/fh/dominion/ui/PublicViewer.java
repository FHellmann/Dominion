/**
 *
 */
package edu.hm.cs.fh.dominion.ui;

import edu.hm.cs.fh.dominion.database.ReadonlyGame;
import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.logic.Logic;
import edu.hm.cs.fh.dominion.logic.moves.Move;
import edu.hm.cs.fh.dominion.logic.moves.ViewGameResult;
import edu.hm.cs.fh.dominion.logic.moves.card.ShowCards;
import edu.hm.cs.fh.dominion.ui.io.ConsoleIO;
import edu.hm.cs.fh.dominion.ui.io.ContentIO;

import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;

/**
 * A logger writes every update to a log.txt file.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 21.04.2014
 */
public class PublicViewer extends AbstractPlayer {
    /**
     * The output writer.
     */
    private final ContentIO inOut;

    /**
     * Creates a new logger.
     *
     * @param game  of datastoreage.
     * @param logic for every logical check.
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
                inOut.sendOutput("\t+ Karten: " + show.getCards().map(Card::getName).collect(Collectors.joining(", ")));
            }
        }
    }

    @Override
    public Move selectMove(List<Move> moves) {
        throw new RuntimeException("This method should not be called");
    }
}
