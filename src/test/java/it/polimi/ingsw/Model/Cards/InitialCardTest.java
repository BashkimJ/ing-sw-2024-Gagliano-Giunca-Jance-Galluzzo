package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.Cards.InitialCard;
import it.polimi.ingsw.Model.Enumerations.Colour;
import it.polimi.ingsw.Model.Enumerations.Resource;
import it.polimi.ingsw.Model.GameStatus.Game;
import it.polimi.ingsw.Model.Player.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InitialCardTest {
    Game game;
    Player p1 = new Player("Simone", Colour.red);
    List<Resource> rscList;
    InitialCard card;

    @BeforeEach
    void setUp() {
        game = new Game(p1, 3);

        rscList = new ArrayList<Resource>();

        rscList.add(Resource.Animal);
        rscList.add(Resource.Insects);
        rscList.add(Resource.Plant);

        card = ((InitialCard) game.getInitialDeck().getCards().get(4)); //cardId = 85
    }

    @Test
    void getMiddleResource() {
        assertEquals(rscList, card.getMiddleResource());
    }
    @Test   //Control the methods from Card which is an abstract class
    void generalTest(){
        assertEquals(85, card.getCardId());
        //Retro: upLeft
        assertEquals(Resource.Insects, card.getRetro().getUpLeft().getResource() );
        assertNull(card.getRetro().getUpLeft().getItems());
        assertTrue(card.getRetro().getUpLeft().isVisible());
        assertTrue(card.getRetro().getUpLeft().isFree());
        //Retro: downleft
        assertEquals(Resource.Plant, card.getRetro().getDownLeft().getResource() );
        assertNull(card.getRetro().getDownLeft().getItems());
        assertTrue(card.getRetro().getDownLeft().isVisible());
        assertTrue(card.getRetro().getDownLeft().isFree());
        //Retro: upRight
        assertEquals(Resource.Fungi, card.getRetro().getUpRight().getResource() );
        assertNull(card.getRetro().getUpRight().getItems());
        assertTrue(card.getRetro().getUpRight().isVisible());
        assertTrue(card.getRetro().getUpRight().isFree());
        //Retro: downRight
        assertEquals(Resource.Animal, card.getRetro().getDownRight().getResource() );
        assertNull(card.getRetro().getDownRight().getItems());
        assertTrue(card.getRetro().getDownRight().isVisible());
        assertTrue(card.getRetro().getDownRight().isFree());
        //Front: upLeft
        assertNull(card.getFront().getUpLeft().getResource() );
        assertNull(card.getFront().getUpLeft().getItems());
        assertTrue(card.getFront().getUpLeft().isVisible());
        assertTrue(card.getFront().getUpLeft().isFree());
        //Front: downleft
        assertNull(card.getFront().getDownLeft().getResource() );
        assertNull(card.getFront().getDownLeft().getItems());
        assertFalse(card.getFront().getDownLeft().isVisible());
        assertFalse(card.getFront().getDownLeft().isFree());
        //Front: upRight
        assertNull(card.getFront().getUpRight().getResource() );
        assertNull(card.getFront().getUpRight().getItems());
        assertTrue(card.getFront().getUpRight().isVisible());
        assertTrue(card.getFront().getUpRight().isFree());
        //Front: downRight
        assertNull(card.getFront().getDownRight().getResource() );
        assertNull(card.getFront().getDownRight().getItems());
        assertFalse(card.getFront().getDownRight().isVisible());
        assertFalse(card.getFront().getDownRight().isFree());
        System.out.println(card.toString());
    }
    @Test
    void initCardTest(){
        InitialCard init = new InitialCard(card.getFront(),card.getRetro(), card.getCardId(),card.getMiddleResource());
    }
}

