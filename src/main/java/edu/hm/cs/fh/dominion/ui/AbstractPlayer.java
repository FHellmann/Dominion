/**
 *
 */
package edu.hm.cs.fh.dominion.ui;

import java.util.List;
import java.util.Optional;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;

import edu.hm.cs.fh.dominion.database.ReadonlyGame;
import edu.hm.cs.fh.dominion.database.ReadonlyPlayer;
import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.database.cards.TreasuryCard;
import edu.hm.cs.fh.dominion.database.cards.VictoryCard;
import edu.hm.cs.fh.dominion.logic.Logic;
import edu.hm.cs.fh.dominion.logic.cards.KingdomCard;
import edu.hm.cs.fh.dominion.logic.moves.Move;

/**
 * A nameless ui player. (For example a logger)
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 21.04.2014
 */
public abstract class AbstractPlayer implements UserInterface {
	/** The tabulator. */
	private static final String TAB = "\t";
	/** The platform dependend new line. */
	protected static final String NEW_LINE = System.getProperty("line.separator");
	/** The game instance. */
	private final ReadonlyGame game;
	/** The logic module. */
	private final Logic logic;

	/**
	 * Creates a new player.
	 *
	 * @param game
	 *            of datastoreage.
	 * @param logic
	 *            for every logical check.
	 */
	public AbstractPlayer(final ReadonlyGame game, final Logic logic) {
		this.game = game;
		this.logic = logic;
		game.addObserver(this);
	}

	@Override
	public ReadonlyGame getGame() {
		return game;
	}

	@Override
	public Logic getLogic() {
		return logic;
	}

	@Override
	public Optional<ReadonlyPlayer> getPlayer() {
		return Optional.empty();
	}

	@Override
	public Move selectMove(final List<Move> moves) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	/**
	 * Get all the game states as string.
	 *
	 * @return the game states.
	 */
	public String getGameState() {
		final StringBuilder strBuilder = new StringBuilder("Current player = ");
		// Current player
		strBuilder.append(getGame().getCurrentPlayer().getName()).append(NEW_LINE)

		// Supply
				.append("Supply =").append(NEW_LINE);
		appendCard(strBuilder, TreasuryCard.COPPER);
		appendCard(strBuilder, TreasuryCard.SILVER);
		appendCard(strBuilder, TreasuryCard.GOLD);
		appendCard(strBuilder, VictoryCard.ESTATE);
		appendCard(strBuilder, VictoryCard.DUCHY);
		appendCard(strBuilder, VictoryCard.PROVINCE);
		appendCard(strBuilder, VictoryCard.CURSE);
		strBuilder.append(getGame().getSupplyCardSet().filter(card -> card instanceof KingdomCard)
				.map(card -> TAB + getGame().getSupplyCardCount(card) + TAB + card.getName())
				.collect(Collectors.joining(NEW_LINE)));

		// Players
		final IntSupplier incrementer = new IntSupplier() {
			private int number = 1;

			@Override
			public int getAsInt() {
				return number++;
			}
		};
		getGame().getPlayers().forEach(player -> appendPlayer(strBuilder, player, incrementer.getAsInt()));

		return strBuilder.toString();
	}

	/**
	 * Append a card to the stringbuilder.
	 *
	 * @param strBuilder
	 *            to append to.
	 * @param card
	 *            to append.
	 */
	private void appendCard(final StringBuilder strBuilder, final Card card) {
		if (getGame().getSupplyCardSet().filter(supplyCard -> supplyCard.equals(card)).findFirst().isPresent()) {
			strBuilder.append(TAB).append(getGame().getSupplyCardCount(card)).append(TAB).append(card.getName())
					.append(NEW_LINE);
		}
	}

	/**
	 * Append a player to the stringbuilder.
	 *
	 * @param strBuilder
	 *            to append to.
	 * @param player
	 *            to append.
	 * @param number
	 *            of the player to mark and append.
	 */
	private void appendPlayer(final StringBuilder strBuilder, final ReadonlyPlayer player, final int number) {
		strBuilder.append(NEW_LINE).append("Player#").append(number).append(NEW_LINE).append(TAB).append("Name = ")
				.append(player.getName()).append(NEW_LINE).append(TAB).append("Deck = ")
				.append(player.getCardDeckPull().size()).append(NEW_LINE).append(TAB).append("Hand = ");
		if (getPlayer().isPresent() && player.equals(getPlayer().get())) {
			strBuilder.append(player.getCardDeckHand().toString());
		} else {
			strBuilder.append(player.getCardDeckHand().size());
		}
		strBuilder.append(NEW_LINE);
		if (getPlayer().isPresent() && player.equals(getPlayer().get())) {
			strBuilder.append(TAB).append("Played = ").append(player.getCardDeckPlayed().toString()).append(NEW_LINE);
		}
		strBuilder.append(TAB).append("Pile = ").append(player.getCardDeckStacker().size()).append(NEW_LINE);
		if (getPlayer().isPresent() && player.equals(getPlayer().get())) {
			strBuilder.append(TAB).append("Actions = ").append(player.getActions().getCount()).append(NEW_LINE)
					.append(TAB).append("Buys = ").append(player.getPurchases().getCount()).append(NEW_LINE)
					.append(TAB).append("Coins = ").append(player.getMoney().getCount()).append(NEW_LINE);
			if (getGame().getToResolveActionCard().isPresent()) {
				strBuilder.append(TAB).append("Resolving = ")
						.append(getGame().getToResolveActionCard().get().getName()).append(NEW_LINE);
			}
		}
	}
}
