/**
 *
 */
package edu.hm.cs.fh.dominion.database.cards;

import edu.hm.cs.fh.dominion.database.Settings;
import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;

import java.util.Arrays;
import java.util.Locale;

/**
 * Kingdom cards as enums.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 09.04.2014
 */
public enum KingdomCard implements Card<KingdomMetaData> {
    /**
     * Moat card
     */
    MOAT(game -> {
        // +2 Karten
        final WriteablePlayer player = game.getCurrentPlayer();
        player.pollCards(2, Settings.getRandom());
    }, 2, Type.ACTION, Type.REACTION),
    /**
     * Basement card
     */
    BASEMENT(game -> {
        // +1 Aktion
        final WriteablePlayer player = game.getCurrentPlayer();
        player.getActions().increment();

        game.setState(State.ACTION_RESOLVE);
    }, 2, Type.ACTION),
    /**
     * Chapel card
     */
    CHAPEL(game -> game.setState(State.ACTION_RESOLVE), 2, Type.ACTION),
    /**
     * Village card
     */
    VILLAGE(game -> {
        // +1 Karte, +2 Aktionen
        final WriteablePlayer player = game.getCurrentPlayer();
        player.pollCards(1, Settings.getRandom());
        player.getActions().add(2);
    }, 3, Type.ACTION),
    /**
     * Workshop card
     */
    WORKSHOP(game -> game.setState(State.ACTION_RESOLVE), 3, Type.ACTION),
    /**
     * Lumberjack card
     */
    LUMBERJACK(game -> {
        // +1 Kauf, +2 Geld
        final WriteablePlayer player = game.getCurrentPlayer();
        player.getPurchases().add(1);
        player.getMoney().add(2);
    }, 3, Type.ACTION),
    /**
     * Chancellor card
     */
    CHANCELLOR(game -> {
        // +2 Geld
        final WriteablePlayer player = game.getCurrentPlayer();
        player.getMoney().add(2);
        game.setState(State.ACTION_RESOLVE);
    }, 3, Type.ACTION),
    /**
     * Bureaucrat card
     */
    BUREAUCRAT(game -> {
        // Nimm dir ein Silber und lege es verdeckt auf deinen Nachziehstapel.
        final WriteablePlayer player = game.getCurrentPlayer();
        if (game.getSupplyCardCount(TreasuryCard.SILVER) > 0) {
            game.getCardFromSupply(TreasuryCard.SILVER, player.getCardDeckPull());
        }
    }, 4, Type.ACTION, Type.ATTACK),
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
    // }, 4, Type.ACTION, Type.ATTACK),
    /**
     * Feast card
     */
    FEAST(game -> game.setState(State.ACTION_RESOLVE), 4, Type.ACTION),
    /**
     * Moneylender card
     */
    MONEYLENDER(game -> {
        if (game.getCurrentPlayer().getCardDeckHand().contains(TreasuryCard.COPPER)) {
            game.setState(State.ACTION_RESOLVE);
        }
    }, 4, Type.ACTION),
    /**
     * Militia card
     */
    MILITIA(game -> {
        // +2 Geld
        final WriteablePlayer player = game.getCurrentPlayer();
        player.getMoney().add(2);
    }, 4, Type.ACTION, Type.ATTACK),
    /**
     * Forge card
     */
    FORGE(game -> {
        // +3 Karten
        final WriteablePlayer player = game.getCurrentPlayer();
        player.pollCards(3, Settings.getRandom());
    }, 4, Type.ACTION),
    // /** Spy card */
    /*
    SPY(game -> {
        // Muss vorerst nicht implementiert werden
        // +1 Karte +1 Aktion
        final WriteablePlayer player = game.getCurrentPlayer();
        player.pollCards(1);
        player.getActions().increment();
    }, 4, Type.ACTION, Type.ATTACK),
    */
    /**
     * Throne room card
     */
    THRONEROOM(game -> game.setState(State.ACTION_RESOLVE), 4, Type.ACTION),
    // /** Rebuilding card */
    // REBUILDING(game -> {
    // game.setState(State.ACTION_RESOLVE);
    // }, 4, Type.ACTION),
    /**
     * Library card
     */
    LIBRARY(game -> {
        game.getCurrentPlayer().pollCards(1, Settings.getRandom());
        game.setState(State.ACTION_RESOLVE);
    }, 5, Type.ACTION),
    /**
     * Witch card
     */
    WITCH(game -> {
        // +2 Karten
        final WriteablePlayer currPlayer = game.getCurrentPlayer();
        currPlayer.pollCards(2, Settings.getRandom());
    }, 5, Type.ACTION, Type.ATTACK),
    /**
     * Funfair card
     */
    FUNFAIR(game -> {
        // +2 Aktionen, +1 Kauf, +2 Geld
        final WriteablePlayer currPlayer = game.getCurrentPlayer();
        currPlayer.getActions().add(2);
        currPlayer.getPurchases().add(1);
        currPlayer.getMoney().add(2);
    }, 5, Type.ACTION),
    /**
     * Laboratory card
     */
    LABORATORY(game -> {
        // +2 Karten, +1 Aktion
        final WriteablePlayer currentPlayer = game.getCurrentPlayer();
        currentPlayer.pollCards(2, Settings.getRandom());
        currentPlayer.getActions().add(1);
    }, 5, Type.ACTION),
    /**
     * Market card
     */
    MARKET(game -> {
        // +1 Karte, +1 Aktion, +1 Kauf, +1 Geld
        final WriteablePlayer currentPlayer = game.getCurrentPlayer();
        currentPlayer.pollCards(1, Settings.getRandom());
        currentPlayer.getActions().add(1);
        currentPlayer.getPurchases().add(1);
        currentPlayer.getMoney().add(1);
    }, 5, Type.ACTION),
    // /** Mine card */
    // MINE(game -> {
    // game.setState(State.ACTION_RESOLVE);
    // }, 5, Type.ACTION),
    /**
     * Council meeting card
     */
    COUNCILMEETING(game -> {
        // +4 Karten, +1 Kauf, Jeder Mitspieler zieht sofort eine Karte nach.
        final WriteablePlayer currPlayer = game.getCurrentPlayer();
        currPlayer.pollCards(3, Settings.getRandom());
        currPlayer.getPurchases().add(1);
        game.getRwPlayers().parallel().forEach(player -> player.pollCards(1, Settings.getRandom()));
    }, 5, Type.ACTION),
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
    // }, 6, Type.ACTION),
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
    }, 4, Type.VICTORY);

    /**
     * The resolve-method content.
     */
    private final Resolvable resolvable;
    private final KingdomMetaData kingdomMetaData;

    /**
     * Creates a new kingdom card.
     *
     * @param resolvable method for the current player.
     */
    KingdomCard(final Resolvable resolvable, final int cost, final Type... types) {
        this.resolvable = resolvable;
        kingdomMetaData = KingdomMetaData.create(cost, Arrays.asList(types));
    }

    /**
     * Resolve the action(s) of this card.
     *
     * @param game to modify data.
     */
    public void resolve(final WriteableGame game) {
        resolvable.resolve(game);
    }

    @Override
    public KingdomMetaData getMetaData() {
        return kingdomMetaData;
    }

    @Override
    public String getName() {
        return name().toLowerCase(Locale.getDefault());
    }

    /**
     * Interface for interaction.
     *
     * @author Fabio Hellmann, info@fabio-hellmann.de
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
