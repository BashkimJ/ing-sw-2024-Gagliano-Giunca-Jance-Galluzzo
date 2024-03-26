package main.java.it.polimi.ingsw.Cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> Cards = new ArrayList<Card>();

    public List<Card> getCards() { return Cards; }

    public void shuffle() { Collections.shuffle(Cards); }

    public Card pickCard() {
        Card pick = Cards.get(Cards.size() - 1 );
        Cards.remove(Cards.size() - 1 );
        return pick;
    }
}
