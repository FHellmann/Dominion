/**
 *
 */
package edu.hm.cs.fh.dominion.ui.javafx;

import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.database.cards.KingdomCard;
import edu.hm.cs.fh.dominion.database.cards.TreasuryCard;
import edu.hm.cs.fh.dominion.database.cards.VictoryCard;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * The ImageLoader handles the loading of all the cards. And keeps the memory low as possible.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 18.05.2014
 */
class ImageLoader {
    /**
     * Map the card to a path where it's image is.
     */
    private final Map<String, String> imageMap = new HashMap<>();
    /**
     * Cache the already displayed card images. Do not reload them! Reuse them for lower memory
     * usage.
     */
    private final Map<String, Image> cache = new HashMap<>();

    /**
     * Creates a new image manager for cards.
     */
    ImageLoader() {
        Stream.of(TreasuryCard.values()).forEach(card -> imageMap.put(card.getName(), findImage(card.getName())));
        Stream.of(VictoryCard.values()).forEach(card -> imageMap.put(card.getName(), findImage(card.getName())));
        Stream.of(KingdomCard.values()).forEach(card -> imageMap.put(card.getName(), findImage(card.getName())));
    }

    /**
     * Get the image of the card.
     *
     * @param card      to get the image of.
     * @param prefWidth of the image.
     * @return the loaded image.
     */
    Image getCardImage(final Card card, final double prefWidth) {
        return getImage(card.getName(), prefWidth);
    }

    /**
     * Get the image of the back of a card.
     *
     * @param prefWidth of the image.
     * @return the loaded image.
     */
    Image getCardBack(final double prefWidth) {
        return getImage("cardback", prefWidth);
    }

    private Image getImage(final String imageName, final double prefWidth) {
        if (!cache.containsKey(imageName) || cache.get(imageName).getWidth() != prefWidth) {
            final String imagePath;
            if (imageMap.containsKey(imageName)) {
                imagePath = imageMap.get(imageName);
            } else {
                imagePath = findImage(imageName);
            }
            cache.put(imageName, new Image(imagePath, prefWidth, 0, true, true));
        }
        return cache.get(imageName);
    }

    /**
     * Find the image in the package 'images'. It is a sub-package of this one.
     *
     * @param name to find the matching image.
     * @return the image path.
     */
    private String findImage(final String name) {
        return Optional.ofNullable(getClass().getClassLoader().getResource("images/" + name + ".jpg"))
                .orElseThrow(() -> new RuntimeException("Unable to find image '" + name + "'"))
                .toString();
    }
}
