package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.Cards.Card;
import it.polimi.ingsw.Model.Enumerations.Colour;
import it.polimi.ingsw.Model.Enumerations.Items;
import it.polimi.ingsw.Model.Enumerations.Resource;
import it.polimi.ingsw.Model.GameStatus.Game;
import it.polimi.ingsw.Model.Player.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class SideTest {
    Game game;
    Player p1 = new Player("Simone", Colour.red);
    Card card;

    File image;

    @BeforeEach
    void setUp() {
        game = new Game(p1, 3);

        image = new File("github-recovery-codes.txt");

        card = game.getResourceDeck().getCards().get(4);

        }

    @Test
    void getUpLeft() {
        assertFalse(card.getFront().getUpLeft().isFree());
        assertFalse(card.getFront().getUpLeft().isVisible());
        assertNull(card.getFront().getUpLeft().getResource());
    }

    @Test
    void getDownLeft() {
        assertEquals(Resource.Plant, card.getFront().getDownLeft().getResource());
        assertTrue(card.getFront().getDownLeft().isVisible());
        assertTrue(card.getFront().getDownLeft().isFree());
    }

    @Test
    void getUpRight() {
        assertEquals(Items.Quill, card.getFront().getUpRight().getItems());
        assertTrue(card.getFront().getUpRight().isVisible());
        assertTrue(card.getFront().getUpRight().isFree());
    }

    @Test
    void getDownRight() {
        assertEquals(Resource.Fungi, card.getFront().getDownRight().getResource());
        assertTrue(card.getFront().getDownRight().isVisible());
        assertTrue(card.getFront().getDownRight().isFree());
    }
}