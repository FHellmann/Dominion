/**
 *
 */
package edu.hm.cs.fh.dominion.logic;

import edu.hm.cs.fh.dominion.database.ReadonlyPlayer;
import edu.hm.cs.fh.dominion.database.cards.KingdomCard;
import edu.hm.cs.fh.dominion.database.cards.TreasuryCard;
import edu.hm.cs.fh.dominion.database.cards.VictoryCard;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;
import edu.hm.cs.fh.dominion.logic.moves.*;
import edu.hm.cs.fh.dominion.logic.moves.card.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * The game controler handles the game events and checks if the events are correct or not.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 01.04.2014
 */
@SuppressWarnings("PMD.ExcessiveImports")
public class Logic {
    /**
     * The game object to modify the gamecontent.
     */
    private final WriteableGame game;

    /**
     * Creates a new controler.
     *
     * @param game to control.
     */
    public Logic(final WriteableGame game) {
        this.game = game;
    }

    /**
     * Generates a list with all moves, afterwords the list is filtered for all possible moves only.
     *
     * @param player who want to have his possible moves.
     * @return a list with all possible moves.
     */
    public List<Move> findApplicableMoves(final ReadonlyPlayer player) {
        final WriteablePlayer rwPlayer = (WriteablePlayer) player;
        // Create all moves
        final List<Move> allMoves = new ArrayList<>();

        // Initialize
        allMoves.add(new OpenGame(game));

        // Setup
        Stream.of(KingdomCard.values()).forEach(card -> allMoves.add(new SelectKingdomCard(game, rwPlayer, card)));
        allMoves.add(new RunGame(game));

        // Action
        Stream.of(KingdomCard.values()).forEach(card -> allMoves.add(new PlayActionCard(game, rwPlayer, card)));
        allMoves.add(new NoAction(game, rwPlayer));

        // Actions to interact
        player.getCardDeckHand().stream().forEach(card -> allMoves.add(new BasementAction(game, rwPlayer, card)));
        allMoves.add(new BasementActionQuit(game, rwPlayer));
        player.getCardDeckHand().stream().forEach(card -> allMoves.add(new ChapelAction(game, rwPlayer, card)));
        allMoves.add(new ChapelActionQuit(game, rwPlayer));
        Stream.of(KingdomCard.values()).forEach(card -> allMoves.add(new WorkshopAction(game, rwPlayer, card)));
        Stream.of(TreasuryCard.values()).forEach(card -> allMoves.add(new WorkshopAction(game, rwPlayer, card)));
        Stream.of(VictoryCard.values()).forEach(card -> allMoves.add(new WorkshopAction(game, rwPlayer, card)));
        Stream.of(KingdomCard.values()).forEach(card -> allMoves.add(new FeastAction(game, rwPlayer, card)));
        Stream.of(TreasuryCard.values()).forEach(card -> allMoves.add(new FeastAction(game, rwPlayer, card)));
        Stream.of(VictoryCard.values()).forEach(card -> allMoves.add(new FeastAction(game, rwPlayer, card)));
        allMoves.add(new ChancellorAction(game, rwPlayer));
        allMoves.add(new ChancellorActionQuit(game, rwPlayer));
        Stream.of(TreasuryCard.values()).forEach(card -> allMoves.add(new MoneylenderAction(game, rwPlayer, card)));
        Stream.of(KingdomCard.values()).forEach(card -> allMoves.add(new LibraryActionDiscard(game, rwPlayer, card)));
        Stream.of(TreasuryCard.values()).forEach(card -> allMoves.add(new LibraryActionDiscard(game, rwPlayer, card)));
        Stream.of(VictoryCard.values()).forEach(card -> allMoves.add(new LibraryActionDiscard(game, rwPlayer, card)));
        allMoves.add(new LibraryActionKeep(game, rwPlayer));
        allMoves.add(new LibraryActionQuit(game, rwPlayer));

        // Attack
        allMoves.add(new AttackYield(game, rwPlayer));
        allMoves.add(new AttackOver(game, rwPlayer));
        allMoves.add(new MoatAttackDefend(game, rwPlayer, KingdomCard.MOAT));
        allMoves.add(new WitchAttack(game, rwPlayer));
        player.getCardDeckHand().stream().forEach(card -> allMoves.add(new MilitiaAttack(game, rwPlayer, card)));
        allMoves.add(new MilitiaAttackOver(game, rwPlayer));
        player.getCardDeckHand().stream()
                .forEach(card -> allMoves.add(new BureaucratAttackDefend(game, rwPlayer, card)));
        allMoves.add(new BureaucratAttackYield(game, rwPlayer));

        // Purchase
        allMoves.add(new PlayAllTreasuryCards(game, rwPlayer));
        Stream.of(TreasuryCard.values()).forEach(card -> allMoves.add(new PlayTreasuryCard(game, rwPlayer, card)));
        Stream.of(TreasuryCard.values()).forEach(card -> allMoves.add(new BuyCard(game, rwPlayer, card)));
        Stream.of(KingdomCard.values()).forEach(card -> allMoves.add(new BuyCard(game, rwPlayer, card)));
        Stream.of(VictoryCard.values()).forEach(card -> allMoves.add(new BuyCard(game, rwPlayer, card)));
        allMoves.add(new NoPurchase(game, rwPlayer));

        // Cleanup
        allMoves.add(new CleanupTurn(game, rwPlayer));

        // Game Over
        allMoves.add(new CloseGame(game));

        // Exit
        allMoves.add(new ExitGame(game));
        allMoves.add(new ViewGameResult(game));

        // filter possible moves
        List<Move> resultMoves = Move.filterPossibleMoves(allMoves);

        if (resultMoves.size() == 1) {
            fireMove(resultMoves.get(0));
            resultMoves = new ArrayList<>();
        }

        return resultMoves;
    }

    /**
     * Executes a move.
     *
     * @param move to execute.
     * @return a result of the execution.
     */
    public MoveResult fireMove(final Move move) {
        final MoveResult result = move.fire();
        game.notifyObservers(move);
        return result;
    }

    /**
     * Registers a new player by name to the game.
     *
     * @param name of the player.
     * @return a read-only-player to communicate.
     */
    public ReadonlyPlayer registerNewPlayer(final String name) {
        return game.registerPlayer(name);
    }
}
