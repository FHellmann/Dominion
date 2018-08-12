package edu.hm.cs.fh.dominion.database.cards;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * A definition of every card.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 25.03.2014
 */
public interface Card<M extends Card.MetaData> {
    /**
     * Get the meta data of this card.
     *
     * @return the meta data.
     */
    M getMetaData();

    /**
     * Get the name of the cards class.
     *
     * @return the name.
     */
    String getName();

    /**
     * Find all cards matching the type.
     *
     * @param cards to search in.
     * @param type  to match.
     * @return a list with matching cards.
     */
    static Stream<Card> findAll(final List<Card> cards, final Type type) {
        return cards.parallelStream().filter(card -> card.getMetaData().hasType(type));
    }

    /**
     * Different types of the cards.
     *
     * @author Fabio Hellmann, info@fabio-hellmann.de
     * @version 25.03.2014
     */
    enum Type {
        /**
         * Points
         */
        VICTORY,
        /**
         * Money
         */
        TREASURY,
        /**
         * Action
         */
        ACTION,
        /**
         * Reaction
         */
        REACTION,
        /**
         * Attack
         */
        ATTACK,
        /**
         * Curse
         */
        CURSE
    }

    /**
     * The meta data of each card.
     *
     * @author Fabio Hellmann, info@fabio-hellmann.de
     * @version 06.12.2017
     */
    interface MetaData {
        /**
         * Get the cost of this card.
         *
         * @return the cost.
         */
        int getCost();

        /**
         * Get the main type of this card.
         *
         * @return the main type.
         */
        default Type getMainType() {
            return getTypes().findFirst().orElseThrow(IllegalStateException::new);
        }

        /**
         * Has the the card the type.
         *
         * @param type to check.
         * @return <code>true</code> if this card contains the type.
         */
        default boolean hasType(final Type type) {
            return getTypes().anyMatch(t -> Objects.equals(t, type));
        }

        /**
         * Get all types of this card.
         *
         * @return all types.
         */
        Stream<Type> getTypes();
    }
}
