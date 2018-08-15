/**
 *
 */
package edu.hm.cs.fh.dominion.ui;

import edu.hm.cs.fh.dominion.database.ReadonlyGame;
import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.logic.Logic;
import edu.hm.cs.fh.dominion.logic.moves.ExitGame;
import edu.hm.cs.fh.dominion.logic.moves.Move;
import edu.hm.cs.fh.dominion.logic.moves.ViewGameResult;
import edu.hm.cs.fh.dominion.logic.moves.card.ShowCards;
import edu.hm.cs.fh.dominion.ui.io.ContentIO;

import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A human player has to play this content.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 24.04.2014
 */
public abstract class AbstractHumanPlayer extends AbstractRegisteredPlayer {
    /**
     * The in- and output handler.
     */
    private final ContentIO inOut;

    /**
     * Creates a new net-player.
     *
     * @param game  of datastoreage.
     * @param logic for every logical check.
     * @param name  of the player.
     * @param inOut is an IO-Interface.
     */
    AbstractHumanPlayer(final ReadonlyGame game, final Logic logic, final String name, final ContentIO inOut) {
        super(game, logic, name);
        this.inOut = inOut;
    }

    @Override
    public Move selectMove(final List<Move> moves) {
        getIO().sendOutput(getGameState());

        // The players possible moves
        getIO().sendOutput("***************************");
        Stream.iterate(0, count -> count + 1).limit(moves.size())
                .forEach(count -> getIO().sendOutput(Integer.toString(count) + "\t" + moves.get(count)));
        int choice;
        do {
            choice = getIO().getInput();
        } while (choice < 0 || choice > moves.size());
        getIO().sendOutput("***************************");

        return moves.get(choice);
    }

    @Override
    public void update(final Observable observable, final Object object) {
        if (object instanceof ShowCards) {
            final ShowCards show = (ShowCards) object;

            getIO().sendOutput(" ~ Show Cards: " + show.getCards().map(Card::getName).collect(Collectors.joining(", ")));
        } else if (object instanceof ViewGameResult) {
            final StringBuilder strBuilder = new StringBuilder();
            strBuilder.append(" ~ Game Result:").append(NEW_LINE);
            getGame().getPlayers().forEach(
                    player -> strBuilder.append("\t").append(player.getName()).append("\t")
                            .append(player.getVictoryPoints().getCount()).append(NEW_LINE));
            getIO().sendOutput(strBuilder.toString());
        } else if (object instanceof ExitGame) {
            try {
                // Close connections/writers
                getIO().close();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        } else if (object != null) {
            getIO().sendOutput(" ~ " + getGame().getCurrentPlayer().getName() + ": " + object);
        }
    }

    /**
     * Get the IO-Interface.
     *
     * @return the inOut.
     */
    private ContentIO getIO() {
        return inOut;
    }
}
