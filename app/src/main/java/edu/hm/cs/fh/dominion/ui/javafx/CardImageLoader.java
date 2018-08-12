/**
 *
 */
package edu.hm.cs.fh.dominion.ui.javafx;

import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.database.cards.TreasuryCard;
import edu.hm.cs.fh.dominion.database.cards.VictoryCard;
import edu.hm.cs.fh.dominion.logic.cards.KingdomCard;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * The CardImageLoader handles the loading of all the cards. And keeps the memory low as possible.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 18.05.2014
 */
class CardImageLoader {
	/** Map the card to a path where it's image is. */
	private final Map<Card, String> cardImageMap = new HashMap<>();
	/**
	 * Cache the already displayed card images. Do not reload them! Reuse them for lower memory
	 * usage.
	 */
	private final Map<Card, Image> cache = new HashMap<>();

	/**
	 * Creates a new image manager for cards.
	 */
	CardImageLoader() {
		Stream.of(TreasuryCard.values()).forEach(card -> cardImageMap.put(card, findImage(card)));
		Stream.of(VictoryCard.values()).forEach(card -> cardImageMap.put(card, findImage(card)));
		Stream.of(KingdomCard.values()).forEach(card -> cardImageMap.put(card, findImage(card)));
	}

	/**
	 * Get the image of the card.
	 *
	 * @param card
	 *            to get the image of.
	 * @param prefWidth
	 *            of the image.
	 * @return the loaded image.
	 */
	Image getCardImage(final Card card, final double prefWidth) {
		if (!cache.containsKey(card) || cache.get(card).getWidth() != prefWidth) {
			cache.put(card, new Image(cardImageMap.get(card), prefWidth, 0, true, true));
		}
		return cache.get(card);
	}

	/**
	 * Find the image in the package 'images'. It is a sub-package of this one.
	 *
	 * @param card
	 *            to find the matching image.
	 * @return the image path.
	 */
	private String findImage(final Card card) {
		return Optional.ofNullable(getClass().getClassLoader().getResource("images/" + card.toString() + ".jpg"))
				.orElseThrow(RuntimeException::new)
				.toString();
	}
}
