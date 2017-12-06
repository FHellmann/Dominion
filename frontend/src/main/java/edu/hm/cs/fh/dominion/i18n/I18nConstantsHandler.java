/**
 *
 */
package edu.hm.cs.fh.dominion.i18n;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import edu.hm.cs.fh.dominion.database.cards.Card;
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
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 11.04.2014
 */
@SuppressWarnings({"PMD.ExcessiveImports", "PMD.CouplingBetweenObjects"})
public class I18nConstantsHandler implements I18N {
    /**
     * The mapping for <b>key <-> localized text</b>.
     */
    private final Map<String, String> i18n = new HashMap<>();

    /**
     * Create to get the constant card attributes. (Only in german)
     */
    I18nConstantsHandler() {
        addI18nCard(KingdomCard.MOAT, "Burggraben", "+2 Karten\nWenn ein Mitspieler eine "
                + "Angriffskarte ausspielt, darfst du diese Karte aus deiner Hand aufdecken. Der Angriff "
                + "hat dann keine Wirkung auf dich.");
        addI18nCard(KingdomCard.CHAPEL, "Kapelle", "Entsorge bis zu 4 Karten aus deiner Hand.");
        addI18nCard(KingdomCard.BASEMENT, "Keller", "+1 Aktion\nLege eine beliebige Anzahl "
                + "Karten aus deiner Hand ab. Ziehe fuer jede abgelegte Karte eine Karte nach.");
        addI18nCard(KingdomCard.VILLAGE, "Dorf", "+1 Karte\n+2 Aktionen");
        addI18nCard(KingdomCard.LUMBERJACK, "Holzfaeller", "+1 Kauf\n+2 Aktionen");
        addI18nCard(KingdomCard.CHANCELLOR, "Kanzler", "+2 Geld\nDu darfst sofort deinen kompletten Nachziehstapel ablegen.");
        addI18nCard(KingdomCard.WORKSHOP, "Werkstatt", "Nimm dir eine Karte, die bis zu 4 kostet.");
        addI18nCard(KingdomCard.BUREAUCRAT, "Buerokrat", "Nimm dir ein Silber und lege es "
                + "verdeckt auf deinen Nachziehstapel. Jeder Mitspieler muss eine Punktekarte aus seiner "
                + "Hand aufdecken und sie verdeckt auf seinen Nachziehstapel liegen. Hat ein Spieler "
                + "keine Punktekarte auf der Hand, muss er seine Kartenhand vorzeigen.");
//        addI18nCard(KingdomCard.THIEF, "Dieb", "Jeder Mitspieler muss die obersten beiden "
//                + "Karten von seinem Nachziehstapel aufdecken. Haben die Mitspieler eine oder mehrere "
//                + "Geldkarten aufgedeckt, muss jeder eine davon (nach deiner Wahl) entsorgen. Du darfst "
//                + "eine beliebige Zahl der entsorgten Karten bei dir ablegen. Die uebrigen aufgedeckten "
//                + "Karten legen die Spieler bei sich ab.");
        addI18nCard(KingdomCard.FEAST, "Festmahl", "Entsorge diese Karte. Nimm dir eine "
                + "Karte, die bis zu 5 kostet.");
        addI18nCard(KingdomCard.MONEYLENDER, "Geldverleiher", "Entsorge eine Kupfer aus "
                + "deiner Hand. Wenn du das machst: +3 Geld");
        addI18nCard(KingdomCard.MILITIA, "Miliz", "+2 Geld\nJeder Mitspieler muss Karten "
                + "ablegen, bis er nur noch 3 Karten auf der Hand hat.");
        addI18nCard(KingdomCard.FORGE, "Schmiede", "+3 Karten");
//        addI18nCard(KingdomCard.SPY, "Spion", "+1 Karte\n+1 Aktion\nJeder Spieler (auch " +
//                "du selbst) muss die oberste Karte von seinem Nachziehstapel aufdecken. Du entscheidest, " +
//                "ob er sie ablegen oder zurueck auf seinen Nachziehstapel legen muss.");
        addI18nCard(KingdomCard.THRONEROOM, "Thronsaal", "Waehle eine Aktionskarte aus "
                + "deiner Hand. Spiele diese Aktionskarte zweimal aus.");
//        addI18nCard(KingdomCard.REBUILDING, "Umbau", "Entsorge eine Karte aus deiner "
//                + "Hand. Nimm dir eine Karte, die bis zu 2 mehr kostet als die entsorgte Karte.");
        addI18nCard(KingdomCard.LIBRARY, "Bibliothek", "Ziehe solange Karten nach, bis "
                + "du 7 Karten auf der Hand hast. Aktionskarten darfst du zur Seite legen, sobald du "
                + "sie ziehst. Die zur Seite gelegten Karten legst du ab, sobald du 7 Karten auf der Hand hast.");
        addI18nCard(KingdomCard.WITCH, "Hexe", "+2 Karten\nJeder Mitspieler muss sich "
                + "eine Fluchkarte nehmen.");
        addI18nCard(KingdomCard.FUNFAIR, "Jahrmarkt", "+2 Aktionen\n+1 Kauf\n+2 Geld");
        addI18nCard(KingdomCard.LABORATORY, "Laboratorium", "+2 Karten\n+1 Aktion");
        addI18nCard(KingdomCard.MARKET, "Markt", "+1 Karte\n+1 Aktion\n+1 Kauf\n+1 Geld");
//        addI18nCard(KingdomCard.MINE, "Mine", "Entsorge eine Geldkarte aus deiner Hand. "
//                + "Nimm dir eine Geldkarte, die bis zu 3 mehr kostet. Nimm diese Geldkarte sofort auf " +
//                "die Hand.");
        addI18nCard(KingdomCard.COUNCILMEETING, "Ratsversammlung", "+4 Karten\n+1 Kauf\n"
                + "Jeder Mitspieler zieht sofort eine Karte nach.");
//        addI18nCard(KingdomCard.ADVENTURE, "Abenteuer", "Decke solange Karten vom "
//                + "Nachziehstapel auf, bis 2 Geldkarten offen liegen. Nimm die Geldkarten auf die Hand, "
//                + "lege die uebrigen aufgedeckten Karten ab.");
        addI18nCard(KingdomCard.GARDENS, "Gaerten", "Wert 1 (Punkte) fuer je 10 Karten im eigenen " +
                "Kartensatz (abgerundet).");

        addI18nMove(OpenGame.class, "Das Spiel ist eroeffnet.");
        addI18nMove(SelectKingdomCard.class, "Fuege die Karte %s zum Kartensatz hinzu.");
        addI18nMove(RunGame.class, "Das Spiel ist gestartet.");
        addI18nMove(PlayActionCard.class, "Ausspielen: %s");
        addI18nMove(NoAction.class, "Keine weitere Aktion taetigen.");
        addI18nMove(PlayTreasuryCard.class, "Auslegen: %s");
        addI18nMove(PlayAllTreasuryCards.class, "Alle Geldkarten auslegen.");
        addI18nMove(BuyCard.class, "Kaufen: %s");
        addI18nMove(NoPurchase.class, "Keinen weiteren Kauf taetigen.");
        addI18nMove(CleanupTurn.class, "Naechster Spieler.");
        addI18nMove(CloseGame.class, "Das Spiel ist zu Ende.");
        addI18nMove(ViewGameResult.class, "Den Spielstand anzeigen.");
        addI18nMove(ExitGame.class, "Das Spiel verlassen.");
        addI18nMove(MoatAttackDefend.class, "Mit dem Burggraben verteidigen.");
        addI18nMove(AttackYield.class, "Dem Angriff nachgeben.");
        addI18nMove(MilitiaAttack.class, "%s ablegen.");
        addI18nMove(MilitiaAttackOver.class, "Der Angriff der Miliz ist vorueber.");
        addI18nMove(WitchAttack.class, "Ziehe einen Fluch.");
        addI18nMove(AttackOver.class, "Der Angriff ist vorueber.");
        addI18nMove(FeastAction.class, "%s nehmen.");
        addI18nMove(BasementAction.class, "%s ablegen.");
        addI18nMove(BasementActionQuit.class, "Ziehe die Anzahl an abgelegten Karten nach.");
        addI18nMove(ChancellorAction.class, "Sofort den kompletten Nachziehstapel ablegen.");
        addI18nMove(ChancellorActionQuit.class, "Kanzler Aktion beenden.");
        addI18nMove(MoneylenderAction.class, "%s ablegen und +3 Geld erhalten.");
        addI18nMove(BureaucratAttackYield.class, "Angriff nachgeben und Handkarten vorzeigen.");
        addI18nMove(BureaucratAttackDefend.class, "Mit dem Vorzeigen der %s-Karte und dem anschließenden Ablegen "
                + "dieser auf den Nachziehstapel, wird der Angriff abgewehrt.");
        addI18nMove(WorkshopAction.class, "Nimm dir die Karte %s.");
        addI18nMove(LibraryActionKeep.class, "Karte behalten.");
        addI18nMove(LibraryActionDiscard.class, "Karte %s ablegen und eine neue ziehen.");
        addI18nMove(LibraryActionQuit.class, "Bibliotheksaktion beenden.");
    }

    @Override
    public Optional<String> getTranslation(final String key) {
        return Optional.ofNullable(i18n.get(key));
    }

    /**
     * Adds the extracted simple class name in to lower case as key and the move description as
     * value to the i18n-map.
     *
     * @param moveClass   to get the simple class name in to lower case from.
     * @param description of the move.
     */
    private void addI18nMove(final Class<? extends Move> moveClass, final String description) {
        i18n.put(moveClass.getSimpleName().toLowerCase() + "_move", description);
    }

    /**
     * Adds the name and description text of a card to the i18n-map.
     *
     * @param card to add the i18n text.
     * @param name of the card.
     * @param text of the card.
     */
    private void addI18nCard(final Card card, final String name, String text) {
        i18n.put(card.getClass().getSimpleName().toLowerCase(), name);
        i18n.put(card.getClass().getSimpleName().toLowerCase() + "_text", text);
    }
}
