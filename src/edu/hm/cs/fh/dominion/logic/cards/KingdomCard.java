/**
 *
 */
package edu.hm.cs.fh.dominion.logic.cards;

import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.database.cards.TreasuryCard;
import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;
import edu.hm.cs.fh.dominion.logic.Settings;

/**
 * Kingdom cards as enums.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 09.04.2014
 */
public enum KingdomCard implements Card {
    /**
     * Moat card
     */
    MOAT(game -> {
        // +2 Karten
        final WriteablePlayer player = game.getCurrentPlayer();
        player.pollCards(2);
    }),
    /**
     * Basement card
     */
    BASEMENT(game -> {
        // +1 Aktion
        final WriteablePlayer player = game.getCurrentPlayer();
        player.getActions().increment();

        game.setState(State.ACTION_RESOLVE);
    }),
    /**
     * Chapel card
     */
    CHAPEL(game -> game.setState(State.ACTION_RESOLVE)),
    /**
     * Village card
     */
    VILLAGE(game -> {
        // +1 Karte, +2 Aktionen
        final WriteablePlayer player = game.getCurrentPlayer();
        player.pollCards(1);
        player.getActions().add(2);
    }),
    /**
     * Workshop card
     */
    WORKSHOP(game -> game.setState(State.ACTION_RESOLVE)),
    /**
     * Lumberjack card
     */
    LUMBERJACK(game -> {
        // +1 Kauf, +2 Geld
        final WriteablePlayer player = game.getCurrentPlayer();
        player.getPurchases().add(1);
        player.getMoney().add(2);
    }),
    /**
     * Chancellor card
     */
    CHANCELLOR(game -> {
        // +2 Geld
        final WriteablePlayer player = game.getCurrentPlayer();
        player.getMoney().add(2);
        game.setState(State.ACTION_RESOLVE);
    }),
    /**
     * Bureaucrat card
     */
    BUREAUCRAT(game -> {
        // Nimm dir ein Silber und lege es verdeckt auf deinen Nachziehstapel.
        final WriteablePlayer player = game.getCurrentPlayer();
        if (game.getSupplyCardCount(TreasuryCard.SILVER) > 0) {
            game.getCardFromSupply(TreasuryCard.SILVER, player.getCardDeckPull());
        }
    }),
    // /** Thief card */
    // THIEF(game -> {
    // // Muss vorerst nicht implementiert werden
    // }, (game, player) -> {
    // // Jeder Mitspieler muss die obersten beiden Karten von seinem Nachziehstapel aufdecken.
    // // Haben die Mitspieler eine oder mehrere Geldkarten aufgedeckt, muss jeder eine davon
    // // (nach deiner Wahl) entsorgen. Du darfst eine beliebige Zahl der entsorgten Karten bei
    // // dir ablegen. Die übrigen aufgedeckten Karten legen die Spieler bei sich ab.
    // final WriteableCardDeck cardDeckPull = player.getCardDeckPull();
    // Stream.iterate(0, index -> index++).limit(2).forEach(ignore -> {
    // if (cardDeckPull.isEmpty()) {
    // cardDeckPull.add(player.getCardDeckStacker());
    // cardDeckPull.shuffle();
    // }
    // final Optional<Card> card = cardDeckPull.poll();
    // if (card.isPresent()) {
    // player.getCardDeckPlayed().add(card.get());
    // }
    // });
    // }),
    /**
     * Feast card
     */
    FEAST(game -> game.setState(State.ACTION_RESOLVE)),
    /**
     * Moneylender card
     */
    MONEYLENDER(game -> {
        if (game.getCurrentPlayer().getCardDeckHand().contains(TreasuryCard.COPPER)) {
            game.setState(State.ACTION_RESOLVE);
        }
    }),
    /**
     * Militia card
     */
    MILITIA(game -> {
        // +2 Geld
        final WriteablePlayer player = game.getCurrentPlayer();
        player.getMoney().add(2);
    }),
    /**
     * Forge card
     */
    FORGE(game -> {
        // +3 Karten
        final WriteablePlayer player = game.getCurrentPlayer();
        player.pollCards(3);
    }),
    // /** Spy card */
    /*
    SPY(game -> {
        // Muss vorerst nicht implementiert werden
        // +1 Karte +1 Aktion
        final WriteablePlayer player = game.getCurrentPlayer();
        player.pollCards(1);
        player.getActions().increment();
    }),
    */
    // /** Throne room card */
    // THRONEROOM(game -> {
    // // Muss vorerst nicht implementiert werden
    // game.setState(State.ACTION_RESOLVE);
    // }),
    // /** Rebuilding card */
    // REBUILDING(game -> {
    // game.setState(State.ACTION_RESOLVE);
    // }),
    /**
     * Library card
     */
    LIBRARY(game -> {
        game.getCurrentPlayer().pollCards(1);
        game.setState(State.ACTION_RESOLVE);
    }),
    /**
     * Witch card
     */
    WITCH(game -> {
        // +2 Karten
        final WriteablePlayer currPlayer = game.getCurrentPlayer();
        currPlayer.pollCards(2);
    }),
    /**
     * Funfair card
     */
    FUNFAIR(game -> {
        // +2 Aktionen, +1 Kauf, +2 Geld
        final WriteablePlayer currPlayer = game.getCurrentPlayer();
        currPlayer.getActions().add(2);
        currPlayer.getPurchases().add(1);
        currPlayer.getMoney().add(2);
    }),
    /**
     * Laboratory card
     */
    LABORATORY(game -> {
        // +2 Karten, +1 Aktion
        final WriteablePlayer currentPlayer = game.getCurrentPlayer();
        currentPlayer.pollCards(2);
        currentPlayer.getActions().add(1);
    }),
    /**
     * Market card
     */
    MARKET(game -> {
        // +1 Karte, +1 Aktion, +1 Kauf, +1 Geld
        final WriteablePlayer currentPlayer = game.getCurrentPlayer();
        currentPlayer.pollCards(1);
        currentPlayer.getActions().add(1);
        currentPlayer.getPurchases().add(1);
        currentPlayer.getMoney().add(1);
    }),
    // /** Mine card */
    // MINE(game -> {
    // game.setState(State.ACTION_RESOLVE);
    // }),
    /**
     * Council meeting card
     */
    COUNCILMEETING(game -> {
        // +4 Karten, +1 Kauf, Jeder Mitspieler zieht sofort eine Karte nach.
        final WriteablePlayer currPlayer = game.getCurrentPlayer();
        currPlayer.pollCards(3);
        currPlayer.getPurchases().add(1);
        game.getRwPlayers().parallel().forEach(player -> player.pollCards(1));
    }),
    // /** Adventure card */
    // ADVENTURE(game -> {
    // // Das aufdecken der Karten ist hier noch nicht mit inbegriffen
    // final WriteablePlayer currPlayer = game.getCurrentPlayer();
    // Stream.iterate(0, index -> index++).map(ignore -> {
    // if (currPlayer.getCardDeckPull().isEmpty()) {
    // currPlayer.getCardDeckPull().move(currPlayer.getCardDeckStacker());
    // currPlayer.getCardDeckPull().shuffle();
    // }
    // return currPlayer.getCardDeckPull().poll();
    // }).filter(optCard -> optCard.isPresent()).map(optCard -> optCard.get())
    // .peek(card -> currPlayer.getCardDeckStacker().add(card))
    // .filter(card -> card instanceof TreasuryCard).limit(2)
    // .forEach(card -> currPlayer.getCardDeckStacker().move(currPlayer.getCardDeckHand(), card));
    // }),
    /**
     * Gardens card
     */
    GARDENS(game -> {
        final WriteablePlayer currPlayer = game.getCurrentPlayer();
        // Count all cards from every carddeck
        long fullCardCount = currPlayer.getCardDeckHand().stream().parallel().count();
        fullCardCount += currPlayer.getCardDeckPull().stream().parallel().count();
        fullCardCount += currPlayer.getCardDeckPlayed().stream().parallel().count();
        fullCardCount += currPlayer.getCardDeckStacker().stream().parallel().count();
        fullCardCount--; // Substract one gardens card!
        // Do an Integer-Devision for cutting the end off
        final int victoryPoints = (int) (fullCardCount / Settings.GARDENS_CARD_AMOUNT);
        currPlayer.getVictoryPoints().add(victoryPoints);
    });

    /**
     * The resolve-method content.
     */
    private final Resolvable resolvable;

    /**
     * Creates a new kingdom card.
     *
     * @param resolvable method for the current player.
     */
    KingdomCard(final Resolvable resolvable) {
        this.resolvable = resolvable;
    }

    /**
     * The text of this card.
     *
     * @return the text of this card.
     */
    public String getText() {
        return getMetaData().getText();
    }

    /**
     * Resolve the action(s) of this card.
     *
     * @param game to modify data.
     */
    public void resolve(final WriteableGame game) {
        resolvable.resolve(game);
    }

    /**
     * Interface for interaction.
     *
     * @author Fabio Hellmann, fhellman@hm.edu
     * @version 09.04.2014
     */
    @FunctionalInterface
    private interface Resolvable {
        /**
         * Solve the logical part of this card.
         *
         * @param game to acess manipulating functions.
         */
        void resolve(WriteableGame game);
    }
}
