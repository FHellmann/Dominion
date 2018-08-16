package edu.hm.cs.fh.dominion.json;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.database.cards.KingdomCard;
import edu.hm.cs.fh.dominion.database.cards.TreasuryCard;
import edu.hm.cs.fh.dominion.database.cards.VictoryCard;

import java.io.IOException;
import java.util.stream.Stream;

public class CardTypeAdapter extends TypeAdapter<Card> {
    @Override
    public void write(JsonWriter out, Card value) throws IOException {
        out.name("card").jsonValue(value.getName()).flush();
    }

    @Override
    public Card read(JsonReader in) throws IOException {
        final String cardName = in.nextString();
        if (Stream.of(KingdomCard.values()).anyMatch(card -> card.getName().equalsIgnoreCase(cardName))) {
            return KingdomCard.valueOf(cardName);
        } else if (Stream.of(VictoryCard.values()).anyMatch(card -> card.getName().equalsIgnoreCase(cardName))) {
            return VictoryCard.valueOf(cardName);
        } else {
            return TreasuryCard.valueOf(cardName);
        }
    }
}
