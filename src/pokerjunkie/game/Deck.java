package pokerjunkie.game;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    //
    List<Card> cards;

    Deck() {
        cards = new ArrayList();
        for(int i = 0; i < Card.numSuits() * Card.numRanks(); i++) {
            cards.add(new Card(i));
        }
    }


    Card drawCard() {
        int card = (int) (Math.random() * cards.size());
        return cards.remove(card);
    }
}
