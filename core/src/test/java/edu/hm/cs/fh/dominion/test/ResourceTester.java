/**
 *
 */
package edu.hm.cs.fh.dominion.test;

import java.util.Arrays;

import edu.hm.cs.fh.dominion.database.cards.TreasuryCard;
import edu.hm.cs.fh.dominion.database.cards.VictoryCard;
import edu.hm.cs.fh.dominion.logic.cards.KingdomCard;

/**
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 11.04.2014
 */
public class ResourceTester {
	@SuppressWarnings("javadoc")
	public static void main(final String[] args) {
		Arrays.asList(VictoryCard.values()).forEach(card -> System.out.println(card.getName()));
		Arrays.asList(TreasuryCard.values()).forEach(card -> System.out.println(card.getName()));
		Arrays.asList(KingdomCard.values()).forEach(card -> {
			System.out.println(card.getName());
			// final StringBuilder strBuilder = new StringBuilder();
			// strBuilder.append("metaDataMap.put(");
			// strBuilder.append("\"");
			// strBuilder.append(card.toString());
			// strBuilder.append("\"");
			// strBuilder.append(", ");
			// strBuilder.append("new CardMetaData(");
			// strBuilder.append("\"");
			// strBuilder.append(card.getName());
			// strBuilder.append("\"");
			// strBuilder.append(", ");
			// strBuilder.append(card.getCost());
			// strBuilder.append(", ");
			// strBuilder.append("\"");
			// strBuilder.append(card.getText().replaceAll("\\n", "\\\\n"));
			// strBuilder.append("\"");
			// for (final Type type : Type.values()) {
			// if (card.hasType(type)) {
			// strBuilder.append(", ");
			// strBuilder.append("Type." + type.toString().toUpperCase());
			// }
			// }
			// strBuilder.append(")");
			// strBuilder.append(");");
			// System.out.println(strBuilder.toString());
			});
	}
}
