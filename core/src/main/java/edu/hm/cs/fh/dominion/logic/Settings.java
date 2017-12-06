/**
 *
 */
package edu.hm.cs.fh.dominion.logic;

import java.util.Random;

/**
 * All constants of the game.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 29.04.2014
 */
public final class Settings {
    /**
     * The amount of supply cards.
     */
    public static final int SUPPLY_CARDS = 10; // XXX Original 10
    /**
     * The amount of kingdomcarddecks to be added to the supply.
     */
    public static final int KINGDOMCARDDECKS = 10; // XXX Orignial 10
    /**
     * The amount of carddecks to be empty to win.
     */
    public static final int EMPTY_SUPPLY_CARDS = 3; // XXX Original 3
    /**
     * The amount of province carddeck to be empty to win.
     */
    public static final int EMPTY_PROVINCE_CARD = 1;
    /**
     * The amount of estate cards for a every player at game start.
     */
    public static final int ESTATE_EVERY_PLAYER = 3;
    /**
     * The amount of copper cards for a every player at game start.
     */
    public static final int COPPERS_EVERY_PLAYER = 7;
    /**
     * The amount of curse cards for a three player game.
     */
    public static final int CURSES_THREE_PLAYERS = 20;
    /**
     * The amount of curse cards for a two player game.
     */
    public static final int CURSES_TWO_PLAYERS = 10;
    /**
     * The amount of victory cards for a two player game.
     */
    public static final int VICTORIES_TWO_PLAYERS = 8;
    /**
     * The amount of gold cards.
     */
    public static final int AMOUNT_OF_GOLD = 30;
    /**
     * The amount of silver cards.
     */
    public static final int AMOUNT_OF_SILVER = 40;
    /**
     * The amount of copper cards.
     */
    public static final int AMOUNT_OF_COPPER = 60;
    /**
     * The amount of duchy cards.
     */
    public static final int AMOUNT_VICTORIES = 12;
    /**
     * The amount of curse cards.
     */
    public static final int AMOUNT_OF_CURSE = 30;
    /**
     * The default amount of the players hand cards.
     */
    public static final int AMOUNT_HAND_CARDS = 5;
    /**
     * The amount of cards the player is alowed to hold in his hand.
     */
    public static final int MILITIA_CARDS_TO_HOLD = 3;
    /**
     * The maximum price a card could cost.
     */
    public static final int FEAST_MAX_CARD_COST = 5;
    /**
     * The maximum price a card could cost.
     */
    public static final int WORKSHOP_MAX_CARD_COST = 4;
    /**
     * The amount of cards needed for one victory point by the gardens card.
     */
    public static final int GARDENS_CARD_AMOUNT = 10;
    /**
     * The maximum amount of cards the player has in his hands after the action ends.
     */
    public static final int LIBRARY_CARDS_TO_HOLD = 7;
    /**
     * The amount of coins the player get with the moneylender action.
     */
    public static final int MONEYLENDER_ADD_COINS = 3;
    /**
     * The amount of card to throw to the trash.
     */
    public static final int CHAPEL_REMOVE_CARDS = 4;
    /**
     * The constant seed for equal random.
     */
    private static final long RANDOM_SEED = 42;
    /**
     * Let the game replay if it is true.
     */
    private static boolean replay;

    /**
     * A Utility class with constants.
     */
    private Settings() {
    }

    /**
     * Get a random. If the {@link #setReplayable()} was called before, then the game can be
     * recorded or replayed (if it was recorded before).
     *
     * @return a random.
     */
    public static Random getRandom() {
        Random random;
        if (replay) {
            random = new Random(RANDOM_SEED);
        } else {
            random = new Random();
        }
        return random;
    }

    /**
     * Sets the game in stoppage mode. This has to be callen from the Recorder and
     * Replayer before starting a game.
     */
    public static void setReplayable() {
        replay = true;
    }
}
