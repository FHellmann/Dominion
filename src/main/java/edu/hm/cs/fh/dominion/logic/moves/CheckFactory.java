/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves;

import java.util.Optional;
import java.util.stream.Stream;

import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.database.cards.TreasuryCard;
import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;
import edu.hm.cs.fh.dominion.logic.Settings;

/**
 * A factory class for generating checks.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 24.04.2014
 */
public final class CheckFactory {
	/**
	 * No one sould see me from outside!
	 */
	private CheckFactory() {
	}

	/**
	 * Generates a check for equal state.
	 *
	 * @param state
	 *            to check.
	 *
	 * @return a check object for reviewing.
	 */
	public static Check isCurrentState(final State state) {
		return (game, player, card) -> new MoveResult(state == game.getState(), "The current phase is not "
				+ state.toString().toLowerCase() + ".");
	}

	/**
	 * Generates a check for equal player.
	 *
	 * @return a check object for reviewing.
	 */
	public static Check isCurrentPlayer() {
		return (game, player, card) -> new MoveResult(game.getCurrentPlayer().equals(player), "It's not your turn.");
	}

	/**
	 * Generates a check for the right amount of players.
	 *
	 * @return a check object for reviewing.
	 */
	public static Check isInPlayerRange() {
		return (game, player, card) -> new MoveResult(2 <= game.getPlayerCount() && game.getPlayerCount() <= 4,
				"The playercount is to low or to high (min. 2 and max. 4 players are allowed).");
	}

	/**
	 * Generates a check for equal card classes.
	 *
	 * @param cardClass
	 *            to check.
	 *
	 * @return a check object for reviewing.
	 */
	public static Check isCardType(final Class<? extends Card> cardClass) {
		return (game, player, card) -> new MoveResult(cardClass.isAssignableFrom(card.getClass()),
				"The card is not a child from the super class " + cardClass.getSimpleName() + ".");
	}

	/**
	 * Generates a check for proving if the card has the type.
	 *
	 * @param type
	 *            of the card.
	 * @return a check object for reviewing.
	 */
	public static Check hasType(final Card.Type type) {
		return (game, player, card) -> new MoveResult(card.hasType(type), "The card does not have the type " + type
				+ ".");
	}

	/**
	 * Generates a check for non zero purchases.
	 *
	 * @return a check object for reviewing.
	 */
	public static Check hasPurchaseLeft() {
		return (game, player, card) -> new MoveResult(player.getPurchases().getCount() > 0,
				"You have no purchases left.");
	}

	/**
	 * Generates a check for enough money to buy the card.
	 *
	 * @return a check object for reviewing.
	 */
	public static Check hasMoneyToBuyCard() {
		return (game, player, card) -> new MoveResult(player.getMoney().getCount() >= card.getCost(),
				"The card is to expensive.");
	}

	/**
	 * Generates a check for non zero actions.
	 *
	 * @return a check object for reviewing.
	 */
	public static Check hasActionLeft() {
		return (game, player, card) -> new MoveResult(player.getActions().getCount() > 0, "You have no actions left.");
	}

	/**
	 * Generates a check for equal cards.
	 *
	 * @param playedCard
	 *            to check if it is equal with the move card.
	 * @return a check object for reviewing.
	 */
	public static Check isEqualCard(final Card playedCard) {
		return (game, player, card) -> new MoveResult(card.equals(playedCard), "The move card (" + card.getName()
				+ ") is not " + playedCard.getName());
	}

	/**
	 * Generates a check for proving if the card does not exists in the supply.
	 *
	 * @return a check object for reviewing.
	 */
	public static Check isCardNotInSupply() {
		return (game, player, card) -> new MoveResult(game.getSupplyCardSet()
				.filter(supplyCard -> supplyCard.equals(card)).count() == 0, "The supply already contains this card.");
	}

	/**
	 * Generates a check for proving if the card does exists in the supply.
	 *
	 * @return a check object for reviewing.
	 */
	public static Check isCardInSupply() {
		return (game, player, card) -> new MoveResult(game.getSupplyCardSet()
				.filter(supplyCard -> supplyCard.equals(card)).count() > 0, "The supply does not contains this card.");
	}

	/**
	 * Generates a check for availability of the card.
	 *
	 * @return a check object for reviewing.
	 */
	public static Check isCardAvailable() {
		return (game, player, card) -> new MoveResult(game.getSupplyCardCount(card) > 0,
				"The card is no longer available.");
	}

	/**
	 * Generates a check for proving if the supply contains less then ten kingdom cards.
	 *
	 * @return a check object for reviewing.
	 */
	public static Check isSupplyNotFull() {
		return (game, player, card) -> new MoveResult(game.getSupplySize() < Settings.KINGDOMCARDDECKS,
				"The maximum of supply cards is not reached.");
	}

	/**
	 * Generates a check for proving if the supply contains ten kingdom cards.
	 *
	 * @return a check object for reviewing.
	 */
	public static Check isSupplyFull() {
		return (game, player, card) -> new MoveResult(game.getSupplySize() == Settings.KINGDOMCARDDECKS,
				"The maximum of supply cards is reached.");
	}

	/**
	 * Generates a check for proving if the card is in the players hand carddeck.
	 *
	 * @return a check object for reviewing.
	 */
	public static Check isHandcard() {
		return (game, player, card) -> new MoveResult(player.getCardDeckHand().contains(card),
				"The card is not in your hand.");
	}

	/**
	 * Generates a check for proving if the players hand has more then the specified amount of
	 * cards.
	 *
	 * @param amount
	 *            of cards.
	 * @return a check object for reviewing.
	 */
	public static Check isHandcardSizeBigger(final int amount) {
		return (game, player, card) -> new MoveResult(player.getCardDeckHand().size() > amount,
				"The players hand card size isn't > " + amount + ".");
	}

	/**
	 * Generates a check for proving if the players hand has more or equal then the specified amount of
	 * cards.
	 *
	 * @param amount
	 *            of cards.
	 * @return a check object for reviewing.
	 */
	public static Check isHandcardSizeBiggerOrEqual(final int amount) {
		return (game, player, card) -> new MoveResult(player.getCardDeckHand().size() >= amount,
				"The players hand card size isn't >= " + amount + ".");
	}

	/**
	 * Generates a check for proving if the players hand has equal amount of cards then the
	 * specified amount of cards.
	 *
	 * @param amount
	 *            of cards.
	 * @return a check object for reviewing.
	 */
	public static Check isHandcardSizeEqual(final int amount) {
		return (game, player, card) -> new MoveResult(player.getCardDeckHand().size() == amount,
				"The players hand card size isn't == " + amount + ".");
	}

	/**
	 * Generates a check for proving if the players hand has lower amount of cards then the
	 * specified amount of cards.
	 *
	 * @param amount
	 *            of cards.
	 * @return a check object for reviewing.
	 */
	public static Check isHandcardSizeLower(final int amount) {
		return (game, player, card) -> new MoveResult(player.getCardDeckHand().size() < amount,
				"The players hand card size isn't < " + amount + ".");
	}

	/**
	 * Generates a check for proving if the players hand has lower or equal amount of cards then the
	 * specified amount of cards.
	 *
	 * @param amount
	 *            of cards.
	 * @return a check object for reviewing.
	 */
	public static Check isHandcardSizeLowerOrEqual(final int amount) {
		return (game, player, card) -> new MoveResult(player.getCardDeckHand().size() <= amount,
				"The players hand card size isn't <= " + amount + ".");
	}

	/**
	 * Generates a check for proving if the player has more then one treasury card.
	 *
	 * @return a check object for reviewing.
	 */
	public static Check hasMoreMoneyCards() {
		return (game, player, card) -> new MoveResult(player.getCardDeckHand().stream().parallel()
				.filter(cardHand -> cardHand instanceof TreasuryCard).count() >= 1, "You have no 1 or less money.");
	}

	/**
	 * Generates a check for proving if one of the cards is the attack card.
	 *
	 * @param cards
	 *            to check.
	 * @return a check object for reviewing.
	 */
	public static Check isAttackCard(final Card... cards) {
		return (game, player, card) -> new MoveResult(Stream.of(cards).parallel()
				.filter(possibleCard -> possibleCard == game.getAttackCard().get()).count() > 0,
				"This card is not one of the possible ones.");
	}

	/**
	 * Generates a check for proving if the player is NOT the attacker.
	 *
	 * @return a check object for reviewing.
	 */
	public static Check isNotAttacker() {
		return (game, player, card) -> new MoveResult(!player.equals(game.getAttacker().get()),
				"This player is the attacker.");
	}

	/**
	 * Generates a check for proving if the player IS the attacker.
	 *
	 * @return a check object for reviewing.
	 */
	public static Check isAttacker() {
		return (game, player, card) -> new MoveResult(player.equals(game.getAttacker().get()),
				"This player is not the attacker.");
	}

	/**
	 * Generates a check for proving if the last played card is the card which has to be resolved.
	 *
	 * @param lastPlayed
	 *            to check.
	 * @return a check object for reviewing.
	 */
	public static Check isResolveCard(final Card lastPlayed) {
		return (game, player, card) -> new MoveResult(game.getToResolveActionCard().get() == lastPlayed,
				"This card is not the played one.");
	}

	/**
	 * Generates a check for proving if the last played card is NOT the card which has to be
	 * resolved.
	 *
	 * @param lastPlayed
	 *            to check.
	 * @return a check object for reviewing.
	 */
	public static Check isNotPreviousPlayedCard(final Card lastPlayed) {
		return (game, player, card) -> new MoveResult(game.getToResolveActionCard().get() != lastPlayed,
				"This card is the played one.");
	}

	/**
	 * Generates a check for proving if the card is in the range of max price.
	 *
	 * @param maxPrice
	 *            the card could cost.
	 * @return a check object for reviewing.
	 */
	public static Check isInCardPrice(final int maxPrice) {
		return (game, player, card) -> new MoveResult(card.getCost() <= maxPrice, "The card is to expensive.");
	}

	/**
	 * Generates a check for proving if the the card is the last polled one.
	 *
	 * @return a check object for reviewing.
	 */
	public static Check isLastPolledCard() {
		return (game, player, card) -> {
			final Optional<Card> lastPolledCard = player.getCardDeckHand().stream().findFirst();
			return new MoveResult(lastPolledCard.isPresent() && card == lastPolledCard.get(),
					"This card is not the last polled one.");
		};
	}

	/**
	 * A simple check.
	 *
	 * @author Fabio Hellmann, fhellman@hm.edu
	 * @version 24.04.2014
	 */
	@FunctionalInterface
	public interface Check {
		/**
		 * Checks something.
		 *
		 * @param game
		 *            to modify.
		 * @param player
		 *            who want's to play the move.
		 * @param card
		 *            to play.
		 * @return a {@link MoveResult} for more informations.
		 */
		MoveResult isCorrect(final WriteableGame game, final WriteablePlayer player, final Card card);
	}
}
