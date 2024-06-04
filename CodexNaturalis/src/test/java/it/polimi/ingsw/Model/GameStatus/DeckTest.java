package test.java.it.polimi.ingsw.Model.GameStatus;

import main.java.it.polimi.ingsw.Model.Cards.*;
import main.java.it.polimi.ingsw.Model.Enumerations.Colour;
import main.java.it.polimi.ingsw.Model.GameStatus.Deck;

import java.util.ArrayList;
import java.util.List;

import main.java.it.polimi.ingsw.Model.GameStatus.Game;
import main.java.it.polimi.ingsw.Model.Player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {
    Game game;
    Player p1 = new Player("Simone", Colour.red);
    Deck deck;
    List<Card> cards = new ArrayList<>();
    Card lastcard;

    @BeforeEach
    void setUp() {
        game = new Game(p1, 3);

        for (int i = 1; i < 41; i++) {
            cards.add(game.getResourceDeck().getCards().get(i-1));
        }

        for (int i = 41; i < 81; i++) {
            cards.add(game.getGoldDeck().getCards().get(i-41));
        }

        for (int i = 81; i < 87; i++) {
            cards.add(game.getInitialDeck().getCards().get(i-81));
        }

        deck = new Deck(cards);
    }

    @Test
    void shuffle(){
        //Test that the order of the cards is different
        List<Card> originalDeck = new ArrayList<>(deck.getCards());
        deck.shuffle();
        assertNotEquals(originalDeck, deck.getCards());
    }

    @Test
    void pickCard() {
        //Test that the function returns the last card of the deck and that it gets removed from it
        for(int i = 85; i>=0; i--){
            assertEquals(cards.getLast(), lastcard = deck.pickCard());
            assertEquals(i, deck.getCards().size());
        }
    }
}
