package main.java.it.polimi.ingsw.Model.GameStatus;

import main.java.it.polimi.ingsw.Model.Cards.Card;

import java.util.*;

public class Deck {
    private List<Card> cards;

    public Deck(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() { return cards; }

    public void shuffle() { Collections.shuffle(cards); }

    public Card pickCard() {
        Card c = cards.remove(cards.size() - 1);
        return c;
    }

}