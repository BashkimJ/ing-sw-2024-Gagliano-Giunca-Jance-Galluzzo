package test.java.it.polimi.ingsw.Model.Cards;

import main.java.it.polimi.ingsw.Model.Cards.InitialCard;
import main.java.it.polimi.ingsw.Model.Enumerations.Colour;
import main.java.it.polimi.ingsw.Model.Enumerations.Resource;
import main.java.it.polimi.ingsw.Model.GameStatus.Game;
import main.java.it.polimi.ingsw.Model.Player.Player;

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
        //Front: upLeft
        assertEquals(Resource.Insects, card.getFront().getUpLeft().getResource() );
        assertNull(card.getFront().getUpLeft().getItems());
        assertTrue(card.getFront().getUpLeft().isVisible());
        assertTrue(card.getFront().getUpLeft().isFree());
        //Front: downleft
        assertEquals(Resource.Plant, card.getFront().getDownLeft().getResource() );
        assertNull(card.getFront().getDownLeft().getItems());
        assertTrue(card.getFront().getDownLeft().isVisible());
        assertTrue(card.getFront().getDownLeft().isFree());
        //Front: upRight
        assertEquals(Resource.Fungi, card.getFront().getUpRight().getResource() );
        assertNull(card.getFront().getUpRight().getItems());
        assertTrue(card.getFront().getUpRight().isVisible());
        assertTrue(card.getFront().getUpRight().isFree());
        //Front: downRight
        assertEquals(Resource.Animal, card.getFront().getDownRight().getResource() );
        assertNull(card.getFront().getDownRight().getItems());
        assertTrue(card.getFront().getDownRight().isVisible());
        assertTrue(card.getFront().getDownRight().isFree());
        //Retro: upLeft
        assertNull(card.getRetro().getUpLeft().getResource() );
        assertNull(card.getRetro().getUpLeft().getItems());
        assertTrue(card.getRetro().getUpLeft().isVisible());
        assertTrue(card.getRetro().getUpLeft().isFree());
        //Retro: downleft
        assertNull(card.getRetro().getDownLeft().getResource() );
        assertNull(card.getRetro().getDownLeft().getItems());
        assertFalse(card.getRetro().getDownLeft().isVisible());
        assertFalse(card.getRetro().getDownLeft().isFree());
        //Retro: upRight
        assertNull(card.getRetro().getUpRight().getResource() );
        assertNull(card.getRetro().getUpRight().getItems());
        assertTrue(card.getRetro().getUpRight().isVisible());
        assertTrue(card.getRetro().getUpRight().isFree());
        //Retro: downRight
        assertNull(card.getRetro().getDownRight().getResource() );
        assertNull(card.getRetro().getDownRight().getItems());
        assertFalse(card.getRetro().getDownRight().isVisible());
        assertFalse(card.getRetro().getDownRight().isFree());
    }
}

