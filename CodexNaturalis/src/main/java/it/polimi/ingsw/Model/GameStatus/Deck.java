package it.polimi.ingsw.Model.GameStatus;

import it.polimi.ingsw.Model.Cards.Card;

import java.io.Serializable;
import java.util.*;

/**
 * Class that represents a deck of cards.
 */
public class Deck implements Serializable {
    private List<Card> cards;

    /**
     * Constructs a deck of cards.
     * @param cards The list of the cards the deck is going to contain.
     */
    public Deck(List<Card> cards) {
        this.cards = cards;
    }

    /**
     * Getter method to obtain the deck.
     * @return The list of the cards in the deck.
     */
    public List<Card> getCards() { return cards; }

    /**
     * Mixes(shuffles) all the cards in the deck.
     */
    public void shuffle() { Collections.shuffle(cards); }

    /**
     * Returns the last card of the deck.
     * @return A card.
     */
    public Card pickCard() {
        Card c = cards.remove(cards.size() - 1);
        return c;
    }

}