package main.java.it.polimi.ingsw.Model.GameStatus;

import main.java.it.polimi.ingsw.Model.Cards.Card;

import java.util.List;

public class Deck {
    private List<Card> cards;

    public Deck(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void shuffle() {
        //TO DO
    }

    public Card pickCard() {
        //TO DO
        return null;
    }
}