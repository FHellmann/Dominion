/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves;

import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;
import edu.hm.cs.fh.dominion.logic.moves.check.Check;
import edu.hm.cs.fh.dominion.logic.moves.check.MoveResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A basic move which every move extends.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 17.04.2014
 */
public abstract class BaseMove implements Move {
    /**
     * The game reference.
     */
    private final WriteableGame game;
    /**
     * The player who called the move object.
     */
    private final WriteablePlayer player;
    /**
     * The card to play.
     */
    private final Card card;
    /**
     * Every check the move has to pass.
     */
    private final List<Check> checks = new ArrayList<>();

    /**
     * Creates a new basic move.
     *
     * @param game to modify.
     */
    public BaseMove(final WriteableGame game) {
        this(game, null, null);
    }

    /**
     * Creates a new basic move.
     *
     * @param game   to modify.
     * @param player who want's to play the move.
     */
    public BaseMove(final WriteableGame game, final WriteablePlayer player) {
        this(game, player, null);
    }

    /**
     * Creates a new basic move.
     *
     * @param game   to modify.
     * @param player who want's to play the move.
     * @param card   to play.
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
    public final void fire() {
        if (test().isPossible()) {
            onFire();
        }
    }

    /**
     * Will be called if the {@link MoveResult} from the {@link #test()} was possible.
     */
    public abstract void onFire();

    @Override
    public final MoveResult test() {
        // Filer every impossible checks and get the first
        return checks.stream()
                .map(check -> check.isCorrect(game, player, card))
                .filter(result -> !result.isPossible())
                .findFirst()
                .orElseGet(() -> new MoveResult(true, ""));
    }

    /**
     * Add a check to the move it has to pass before execution.
     *
     * @param check to pass.
     */
    protected void addCheck(final Check check) {
        checks.add(check);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName().toLowerCase();
    }
}