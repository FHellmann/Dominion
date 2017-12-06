/**
 *
 */
package edu.hm.cs.fh.dominion.database.full;

import java.util.*;
import java.util.stream.Stream;

import edu.hm.cs.fh.dominion.database.ReadonlyCardDeck;
import edu.hm.cs.fh.dominion.database.ReadonlyPlayer;
import edu.hm.cs.fh.dominion.database.cards.Card;

/**
 * The datastore root for the game.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 29.03.2014
 */
public class Game extends WriteableGame implements Changeable {
    /**
     * The map of storing the amount of kingdomcards which are already able to pull.
     */
    private final Map<Card, Counter> supply = new HashMap<>();
    /**
     * The players.
     */
    private final List<WriteablePlayer> players = new ArrayList<>();
    /**
     * The carddeck for all cards which have to be thrown away.
     */
    private final CardDeck cardDeckWaste = new CardDeck(this);
    /**
     * The current state of the game. (Default = INITIALIZE)
     */
    private State state = State.INITIALIZE;
    /**
     * The current player.
     */
    private WriteablePlayer currentPlayer;
    /**
     * Is defaultly set to false. If one player has reached the end of the game, the state turns to
     * true.
     */
    private boolean gameOver;
    /**
     * The last player who played a card with attack type or null if the last move does not contains
     * a attack card.
     */
    private WriteablePlayer attacker;
    /**
     * The last played card with attack type or null if the last move does not contains a attack
     * card.
     */
    private Card attackCard;
    /**
     * The last played kingdom cards to resolve.
     */
    private final Stack<Card> actionCardStack = new Stack<>();

    @Override
    public void setChanged() {
        super.setChanged();
        // We have to inform all observers for some changes!
        // The changes can also come from the CardDeck, Player or Counter...
        notifyObservers();
    }

    @Override
    public void notifyObservers(final Object move) {
        super.setChanged();
        super.notifyObservers(move);
    }

    @Override
    public void setState(final State state) {
        this.state = state;
        setChanged();
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public void nextPlayer() {
        int index = 0;
        if (currentPlayer != null) {
            final int indexOf = players.indexOf(currentPlayer);
            index = indexOf + 1 < players.size() ? indexOf + 1 : 0;
        }
        currentPlayer = players.get(index);
        setChanged();
    }

    @Override
    public void setCurrentPlayer(final WriteablePlayer player) {
        currentPlayer = player;
    }

    @Override
    public WriteablePlayer getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public int getPlayerCount() {
        return players.size();
    }

    @Override
    public Stream<WriteablePlayer> getRwPlayers() {
        return players.stream();
    }

    @Override
    public Stream<ReadonlyPlayer> getPlayers() {
        return players.stream().map(player -> player);
    }

    @Override
    public int getSupplySize() {
        return supply.size();
    }

    @Override
    public int getSupplyCardCount(final Card card) {
        return supply.get(card).getCount();
    }

    @Override
    public Stream<Card> getSupplyCardSet() {
        return supply.keySet().stream();
    }

    @Override
    public void addSupplyCard(final Card card, final int amount) {
        supply.put(card, new Counter(this, amount));
        setChanged();
    }

    @Override
    public void getCardFromSupply(final Card card, final WriteableCardDeck cardDeck) {
        supply.get(card).decrement();
        cardDeck.add(card);
    }

    @Override
    public WriteablePlayer registerPlayer(final String name) {
        final Player player = new Player(this, name);
        players.add(player);
        return player;
    }

    @Override
    public void setOver() {
        gameOver = true;
        setChanged();
    }

    @Override
    public boolean isOver() {
        return gameOver;
    }

    @Override
    public Optional<ReadonlyPlayer> getAttacker() {
        return Optional.ofNullable(attacker);
    }

    @Override
    public void setAttacker(final WriteablePlayer player) {
        attacker = player;
    }

    @Override
    public Optional<Card> getAttackCard() {
        return Optional.ofNullable(attackCard);
    }

    @Override
    public void setAttackCard(final Card previousCard) {
        attackCard = previousCard;
    }

    @Override
    public Optional<Card> getToResolveActionCard() {
        return actionCardStack.isEmpty() ? Optional.empty() : Optional.of(actionCardStack.peek());
    }

    @Override
    public void addToResolveActionCard(final Card toResolveActionCard) {
        actionCardStack.add(toResolveActionCard);
    }

    @Override
    public Optional<Card> popToResolveActionCard() {
        return actionCardStack.isEmpty() ? Optional.empty() : Optional.of(actionCardStack.pop());
    }

    @Override
    public void addCardToWaste(final WriteableCardDeck cardDeck, final Card card) {
        cardDeck.remove(card);
        cardDeckWaste.add(card);
    }

    @Override
    public ReadonlyCardDeck getWaste() {
        return cardDeckWaste;
    }
}
