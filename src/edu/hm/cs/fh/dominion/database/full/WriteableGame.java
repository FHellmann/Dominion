/**
 *
 */
package edu.hm.cs.fh.dominion.database.full;

import java.util.Optional;
import java.util.stream.Stream;

import edu.hm.cs.fh.dominion.database.ReadonlyGame;
import edu.hm.cs.fh.dominion.database.cards.Card;

/**
 * A layer to read and write the game.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 25.03.2014
 */
public abstract class WriteableGame extends ReadonlyGame {
    /**
     * Sets the current state of the game.
     *
     * @param currentState the currentState to set
     */
    public abstract void setState(final State currentState);

    /**
     * Let's the next player do his turn.
     */
    public abstract void nextPlayer();

    /**
     * Sets the current player of the game.
     *
     * @param player to set.
     */
    public abstract void setCurrentPlayer(WriteablePlayer player);

    /**
     * Get a list of all players as read-write-version.
     *
     * @return an unmutable list with read-write-players.
     */
    public abstract Stream<WriteablePlayer> getRwPlayers();

    /**
     * Decrease the card counter by one or receive an exception if there is no more card or the card
     * does not exists.
     *
     * @param card     to poll.
     * @param cardDeck to add the card to.
     */
    public abstract void getCardFromSupply(final Card card, final WriteableCardDeck cardDeck);

    /**
     * Adds a card to the supply.
     *
     * @param card   to add.
     * @param amount of cards to add.
     */
    public abstract void addSupplyCard(final Card card, final int amount);

    /**
     * Registers a player with his name.
     *
     * @param name of the player.
     * @return the players object.
     */
    public abstract WriteablePlayer registerPlayer(final String name);

    /**
     * Sets a note, that the game is over after the current player finished his turn.
     */
    public abstract void setOver();

    @Override
    public abstract WriteablePlayer getCurrentPlayer();

    /**
     * Sets the player who is the attacker.
     *
     * @param player the player to set as attacker.
     */
    public abstract void setAttacker(WriteablePlayer player);

    /**
     * Sets the players attack card.
     *
     * @param previousCard the previousCard to set.
     */
    public abstract void setAttackCard(Card previousCard);

    /**
     * Set the last played card.
     *
     * @param previousPlayedCard the previousPlayedCard to set.
     */
    public abstract void addToResolveActionCard(final Card previousPlayedCard);

    /**
     * Returns and removes the last action card to resolve.
     *
     * @return the card which was resolved.
     */
    public abstract Optional<Card> popToResolveActionCard();

    /**
     * Throws a card from the specified carddeck to the waste.
     *
     * @param cardDeck to remove the card from.
     * @param card     to throw away.
     */
    public abstract void addCardToWaste(final WriteableCardDeck cardDeck, final Card card);
}
