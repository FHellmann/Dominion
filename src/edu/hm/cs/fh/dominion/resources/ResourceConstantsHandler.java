/**
 *
 */
package edu.hm.cs.fh.dominion.resources;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.database.cards.Card.Type;
import edu.hm.cs.fh.dominion.database.cards.TreasuryCard;
import edu.hm.cs.fh.dominion.database.cards.VictoryCard;
import edu.hm.cs.fh.dominion.logic.cards.KingdomCard;
import edu.hm.cs.fh.dominion.logic.moves.BuyCard;
import edu.hm.cs.fh.dominion.logic.moves.CleanupTurn;
import edu.hm.cs.fh.dominion.logic.moves.CloseGame;
import edu.hm.cs.fh.dominion.logic.moves.ExitGame;
import edu.hm.cs.fh.dominion.logic.moves.Move;
import edu.hm.cs.fh.dominion.logic.moves.NoAction;
import edu.hm.cs.fh.dominion.logic.moves.NoPurchase;
import edu.hm.cs.fh.dominion.logic.moves.OpenGame;
import edu.hm.cs.fh.dominion.logic.moves.PlayActionCard;
import edu.hm.cs.fh.dominion.logic.moves.PlayAllTreasuryCards;
import edu.hm.cs.fh.dominion.logic.moves.PlayTreasuryCard;
import edu.hm.cs.fh.dominion.logic.moves.RunGame;
import edu.hm.cs.fh.dominion.logic.moves.SelectKingdomCard;
import edu.hm.cs.fh.dominion.logic.moves.ViewGameResult;
import edu.hm.cs.fh.dominion.logic.moves.card.AttackOver;
import edu.hm.cs.fh.dominion.logic.moves.card.AttackYield;
import edu.hm.cs.fh.dominion.logic.moves.card.BasementAction;
import edu.hm.cs.fh.dominion.logic.moves.card.BasementActionQuit;
import edu.hm.cs.fh.dominion.logic.moves.card.BureaucratAttackDefend;
import edu.hm.cs.fh.dominion.logic.moves.card.BureaucratAttackYield;
import edu.hm.cs.fh.dominion.logic.moves.card.ChancellorAction;
import edu.hm.cs.fh.dominion.logic.moves.card.ChancellorActionQuit;
import edu.hm.cs.fh.dominion.logic.moves.card.FeastAction;
import edu.hm.cs.fh.dominion.logic.moves.card.LibraryActionDiscard;
import edu.hm.cs.fh.dominion.logic.moves.card.LibraryActionKeep;
import edu.hm.cs.fh.dominion.logic.moves.card.LibraryActionQuit;
import edu.hm.cs.fh.dominion.logic.moves.card.MilitiaAttack;
import edu.hm.cs.fh.dominion.logic.moves.card.MilitiaAttackOver;
import edu.hm.cs.fh.dominion.logic.moves.card.MoatAttackDefend;
import edu.hm.cs.fh.dominion.logic.moves.card.MoneylenderAction;
import edu.hm.cs.fh.dominion.logic.moves.card.WitchAttack;
import edu.hm.cs.fh.dominion.logic.moves.card.WorkshopAction;

/**
 * The handler for loading the constant content.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 11.04.2014
 */
@SuppressWarnings({ "PMD.ExcessiveImports", "PMD.CouplingBetweenObjects" })
public class ResourceConstantsHandler implements Resources {
	/** The mapping for <b>name <-> metadata</b> of a card. */
	private final Map<String, CardMetaData> metaDataMap = new HashMap<>();
	/** The mapping for <b>key <-> localized text</b>. */
	private final Map<String, String> i18n = new HashMap<>();

	/**
	 * Create to get the constant card attributes. (Only in german)
	 */
	ResourceConstantsHandler() {
		// Treasury
		addCardMetaData(TreasuryCard.COPPER, new CardMetaData("Kupfer", 0, 1, Type.TREASURY));
		addCardMetaData(TreasuryCard.SILVER, new CardMetaData("Silber", 3, 2, Type.TREASURY));
		addCardMetaData(TreasuryCard.GOLD, new CardMetaData("Gold", 6, 3, Type.TREASURY));
		// Victory
		addCardMetaData(VictoryCard.ESTATE, new CardMetaData("Anwesen", 2, 1, Type.VICTORY));
		addCardMetaData(VictoryCard.DUCHY, new CardMetaData("Herzogtum", 5, 3, Type.VICTORY));
		addCardMetaData(VictoryCard.PROVINCE, new CardMetaData("Provinz", 8, 6, Type.VICTORY));
		addCardMetaData(VictoryCard.CURSE, new CardMetaData("Fluch", 0, -1, Type.VICTORY, Type.CURSE));
		// Kingdom
		addCardMetaData(KingdomCard.MOAT, new CardMetaData("Burggraben", 2, "+2 Karten\nWenn ein Mitspieler eine "
				+ "Angriffskarte ausspielt, darfst du diese Karte aus deiner Hand aufdecken. Der Angriff "
				+ "hat dann keine Wirkung auf dich.", Type.ACTION, Type.REACTION));
		// addCardMetaData(KingdomCard.CHAPEL, new CardMetaData("Kapelle", 2,
		// "Entsorge bis zu 4 Karten aus deiner " + "Hand.",
		// Type.ACTION));
		addCardMetaData(KingdomCard.BASEMENT, new CardMetaData("Keller", 2, "+1 Aktion\nLege eine beliebige Anzahl "
				+ "Karten aus deiner Hand ab. Ziehe fuer jede abgelegte Karte eine Karte nach.", Type.ACTION));
		addCardMetaData(KingdomCard.VILLAGE, new CardMetaData("Dorf", 3, "+1 Karte\n+2 Aktionen", Type.ACTION));
		addCardMetaData(KingdomCard.LUMBERJACK, new CardMetaData("Holzfaeller", 3, "+1 Kauf\n+2 Aktionen", Type.ACTION));
		addCardMetaData(KingdomCard.CHANCELLOR, new CardMetaData("Kanzler", 3,
				"+2 Geld\nDu darfst sofort deinen kompletten " + "Nachziehstapel ablegen.", Type.ACTION));
		addCardMetaData(KingdomCard.WORKSHOP, new CardMetaData("Werkstatt", 3, "Nimm dir eine Karte, die bis zu 4 "
				+ "kostet.", Type.ACTION));
		addCardMetaData(KingdomCard.BUREAUCRAT, new CardMetaData("Buerokrat", 3, "Nimm dir ein Silber und lege es "
				+ "verdeckt auf deinen Nachziehstapel. Jeder Mitspieler muss eine Punktekarte aus seiner "
				+ "Hand aufdecken und sie verdeckt auf seinen Nachziehstapel liegen. Hat ein Spieler "
				+ "keine Punktekarte auf der Hand, muss er seine Kartenhand vorzeigen.", Type.ACTION, Type.ATTACK));
		// addCardMetaData(KingdomCard.THIEF, new CardMetaData("Dieb", 4,
		// "Jeder Mitspieler muss die obersten beiden "
		// + "Karten von seinem Nachziehstapel aufdecken. Haben die Mitspieler eine oder mehrere "
		// + "Geldkarten aufgedeckt, muss jeder eine davon (nach deiner Wahl) entsorgen. Du darfst "
		// + "eine beliebige Zahl der entsorgten Karten bei dir ablegen. Die uebrigen aufgedeckten "
		// + "Karten legen die Spieler bei sich ab.", Type.ACTION, Type.ATTACK));
		addCardMetaData(KingdomCard.FEAST, new CardMetaData("Festmahl", 4, "Entsorge diese Karte. Nimm dir eine "
				+ "Karte, die bis zu 5 kostet.", Type.ACTION));
		addCardMetaData(KingdomCard.MONEYLENDER, new CardMetaData("Geldverleiher", 4, "Entsorge eine Kupfer aus "
				+ "deiner Hand. Wenn du das machst: +3 Geld", Type.ACTION));
		addCardMetaData(KingdomCard.MILITIA, new CardMetaData("Miliz", 4, "+2 Geld\nJeder Mitspieler muss Karten "
				+ "ablegen, bis er nur noch 3 Karten auf der Hand hat.", Type.ACTION, Type.ATTACK));
		addCardMetaData(KingdomCard.FORGE, new CardMetaData("Schmiede", 4, "+3 Karten", Type.ACTION));
		// addCardMetaData(KingdomCard.SPY, new CardMetaData("Spion", 4,
		// "+1 Karte\n+1 Aktion\nJeder Spieler (auch "
		// +
		// "du selbst) muss die oberste Karte von seinem Nachziehstapel aufdecken. Du entscheidest, "
		// + "ob er sie ablegen oder zurueck auf seinen Nachziehstapel legen muss.", Type.ACTION,
		// Type.ATTACK));
		// addCardMetaData(KingdomCard.THRONEROOM, new CardMetaData("Thronsaal", 4,
		// "Waehle eine Aktionskarte aus "
		// + "deiner Hand. Spiele diese Aktionskarte zweimal aus.", Type.ACTION));
		// addCardMetaData(KingdomCard.REBUILDING, new CardMetaData("Umbau", 4,
		// "Entsorge eine Karte aus deiner "
		// + "Hand. Nimm dir eine Karte, die bis zu 2 mehr kostet als die entsorgte Karte.",
		// Type.ACTION));
		addCardMetaData(KingdomCard.LIBRARY, new CardMetaData("Bibliothek", 5, "Ziehe solange Karten nach, bis "
				+ "du 7 Karten auf der Hand hast. Aktionskarten darfst du zur Seite legen, sobald du "
				+ "sie ziehst. Die zur Seite gelegten Karten legst du ab, sobald du 7 Karten auf der " + "Hand hast.",
				Type.ACTION));
		addCardMetaData(KingdomCard.WITCH, new CardMetaData("Hexe", 5, "+2 Karten\nJeder Mitspieler muss sich "
				+ "eine Fluchkarte nehmen.", Type.ACTION, Type.ATTACK));
		addCardMetaData(KingdomCard.FUNFAIR, new CardMetaData("Jahrmarkt", 5, "+2 Aktionen\n+1 Kauf\n+2 Geld",
				Type.ACTION));
		addCardMetaData(KingdomCard.LABORATORY,
				new CardMetaData("Laboratorium", 5, "+2 Karten\n+1 Aktion", Type.ACTION));
		addCardMetaData(KingdomCard.MARKET, new CardMetaData("Markt", 5, "+1 Karte\n+1 Aktion\n+1 Kauf\n+1 Geld",
				Type.ACTION));
		// addCardMetaData(KingdomCard.MINE, new CardMetaData("Mine", 5,
		// "Entsorge eine Geldkarte aus deiner Hand. "
		// + "Nimm dir eine Geldkarte, die bis zu 3 mehr kostet. Nimm diese Geldkarte sofort auf " +
		// "die Hand.",
		// Type.ACTION));
		addCardMetaData(KingdomCard.COUNCILMEETING, new CardMetaData("Ratsversammlung", 5, "+4 Karten\n+1 Kauf\n"
				+ "Jeder Mitspieler zieht sofort eine Karte nach.", Type.ACTION));
		// addCardMetaData(KingdomCard.ADVENTURE, new CardMetaData("Abenteuer", 6,
		// "Decke solange Karten vom "
		// + "Nachziehstapel auf, bis 2 Geldkarten offen liegen. Nimm die Geldkarten auf die Hand, "
		// + "lege die uebrigen aufgedeckten Karten ab.", Type.ACTION));
		addCardMetaData(KingdomCard.GARDENS, new CardMetaData("Gaerten", 4,
				"Wert 1 (Punkte) fuer je 10 Karten im eigenen Kartensatz " + "(abgerundet).", Type.VICTORY));

		addI18n(OpenGame.class, "Das Spiel ist eroeffnet.");
		addI18n(SelectKingdomCard.class, "Fuege die Karte %s zum Kartensatz hinzu.");
		addI18n(RunGame.class, "Das Spiel ist gestartet.");
		addI18n(PlayActionCard.class, "Ausspielen: %s");
		addI18n(NoAction.class, "Keine weitere Aktion taetigen.");
		addI18n(PlayTreasuryCard.class, "Auslegen: %s");
		addI18n(PlayAllTreasuryCards.class, "Alle Geldkarten auslegen.");
		addI18n(BuyCard.class, "Kaufen: %s");
		addI18n(NoPurchase.class, "Keinen weiteren Kauf taetigen.");
		addI18n(CleanupTurn.class, "Naechster Spieler.");
		addI18n(CloseGame.class, "Das Spiel ist zu Ende.");
		addI18n(ViewGameResult.class, "Den Spielstand anzeigen.");
		addI18n(ExitGame.class, "Das Spiel verlassen.");
		addI18n(MoatAttackDefend.class, "Mit dem Burggraben verteidigen.");
		addI18n(AttackYield.class, "Dem Angriff nachgeben.");
		addI18n(MilitiaAttack.class, "%s ablegen.");
		addI18n(MilitiaAttackOver.class, "Der Angriff der Miliz ist vorueber.");
		addI18n(WitchAttack.class, "Ziehe einen Fluch.");
		addI18n(AttackOver.class, "Der Angriff ist vorueber.");
		addI18n(FeastAction.class, "%s nehmen.");
		addI18n(BasementAction.class, "%s ablegen.");
		addI18n(BasementActionQuit.class, "Ziehe die Anzahl an abgelegten Karten nach.");
		addI18n(ChancellorAction.class, "Sofort den kompletten Nachziehstapel ablegen.");
		addI18n(ChancellorActionQuit.class, "Kanzler Aktion beenden.");
		addI18n(MoneylenderAction.class, "%s ablegen und +3 Geld erhalten.");
		addI18n(BureaucratAttackYield.class, "Angriff nachgeben und Handkarten vorzeigen.");
		addI18n(BureaucratAttackDefend.class, "Mit dem Vorzeigen der %s-Karte und dem anschließenden Ablegen "
				+ "dieser auf den Nachziehstapel, wird der Angriff abgewehrt.");
		addI18n(WorkshopAction.class, "Nimm dir die Karte %s.");
		addI18n(LibraryActionKeep.class, "Karte behalten.");
		addI18n(LibraryActionDiscard.class, "Karte %s ablegen und eine neue ziehen.");
		addI18n(LibraryActionQuit.class, "Bibliotheksaktion beenden.");
	}

	@Override
	public Optional<String> getTranslation(final String key) {
		return Optional.ofNullable(i18n.get(key));
	}

	@Override
	public Optional<CardMetaData> getMetaData(final String name) {
		return Optional.ofNullable(metaDataMap.get(name));
	}

	/**
	 * Adds the extracted simple class name in to lower case as key and the move description as
	 * value to the i18n-map.
	 *
	 * @param moveClass
	 *            to get the simple class name in to lower case from.
	 * @param description
	 *            of the move.
	 */
	private void addI18n(final Class<? extends Move> moveClass, final String description) {
		i18n.put(moveClass.getSimpleName().toLowerCase(), description);
	}

	/**
	 * Adds the card with the metadata to the map.
	 *
	 * @param card
	 *            to add.
	 * @param metaData
	 *            to add.
	 */
	private void addCardMetaData(final Card card, final CardMetaData metaData) {
		metaDataMap.put(card.toString().toLowerCase(), metaData);
	}
}
