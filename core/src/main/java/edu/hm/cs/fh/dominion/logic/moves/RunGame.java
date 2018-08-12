/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves;

import edu.hm.cs.fh.dominion.database.Settings;
import edu.hm.cs.fh.dominion.database.cards.TreasuryCard;
import edu.hm.cs.fh.dominion.database.cards.VictoryCard;
import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableCardDeck;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;

import java.util.stream.Stream;

/**
 * A move to run the game.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 24.04.2014
 */
public class RunGame extends BaseMove {
    /**
     * Creates a new run game move.
     *
     * @param game to reference.
     */
    public RunGame(final WriteableGame game) {
        super(game);
        addCheck(CheckFactory.isCurrentState(State.SETUP));
        addCheck(CheckFactory.isSupplyFull());
    }

    @Override
    public void onFire() {
        // Define amount of start cards
        final int amountOfCurseCards;
        final int amountOfVictories;

        switch (getGame().getPlayerCount()) {
            case 2:
                amountOfCurseCards = Settings.CURSES_TWO_PLAYERS;
                amountOfVictories = Settings.VICTORIES_TWO_PLAYERS;
                break;
            case 3:
                amountOfCurseCards = Settings.CURSES_THREE_PLAYERS;
                amountOfVictories = Settings.AMOUNT_VICTORIES;
                break;
            default:
                amountOfCurseCards = Settings.AMOUNT_OF_CURSE;
                amountOfVictories = Settings.AMOUNT_VICTORIES;
                break;
        }

        final int amountOfCopper = Settings.AMOUNT_OF_COPPER - Settings.COPPERS_EVERY_PLAYER
                * getGame().getPlayerCount();

        getGame().addSupplyCard(VictoryCard.CURSE, amountOfCurseCards);
        getGame().addSupplyCard(VictoryCard.ESTATE, amountOfVictories);
        getGame().addSupplyCard(VictoryCard.DUCHY, amountOfVictories);
        getGame().addSupplyCard(VictoryCard.PROVINCE, amountOfVictories);
        getGame().addSupplyCard(TreasuryCard.COPPER, amountOfCopper);
        getGame().addSupplyCard(TreasuryCard.SILVER, Settings.AMOUNT_OF_SILVER);
        getGame().addSupplyCard(TreasuryCard.GOLD, Settings.AMOUNT_OF_GOLD);

        // Default pull carddeck set and startsettings for every player
        getGame().getRwPlayers().forEach(
                player -> {
                    final WriteableCardDeck deckPull = player.getCardDeckPull();
                    Stream.iterate(0, index -> index + 1).limit(Settings.COPPERS_EVERY_PLAYER)
                            .forEach(index -> deckPull.add(TreasuryCard.COPPER));
                    Stream.iterate(0, index -> index + 1).limit(Settings.ESTATE_EVERY_PLAYER)
                            .forEach(index -> deckPull.add(VictoryCard.ESTATE));
                    deckPull.shuffle(Settings.getRandom());

                    player.pollCards(Settings.AMOUNT_HAND_CARDS, Settings.getRandom());
                    player.getActions().increment();
                    player.getPurchases().increment();
                });

        getGame().setState(State.ACTION);
    }
}
