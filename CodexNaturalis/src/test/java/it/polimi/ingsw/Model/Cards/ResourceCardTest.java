package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.Cards.ResourceCard;
import it.polimi.ingsw.Model.Enumerations.Colour;
import it.polimi.ingsw.Model.Enumerations.Items;
import it.polimi.ingsw.Model.Enumerations.Resource;
import it.polimi.ingsw.Model.GameStatus.Game;
import it.polimi.ingsw.Model.Player.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceCardTest {
    Game game;
    Player p1 = new Player("Simone", Colour.red);
    ResourceCard card;
    int points;

    @BeforeEach
    void setUp() {
        game = new Game(p1, 3);

        card = ((ResourceCard) game.getResourceDeck().getCards().get(24));
    }

    @Test
    void getPoints() {
        assertEquals(points,card.getPoints());
    }
    @Test
    void getResourceType(){
        assertEquals(Resource.Animal, card.getResourceType());
    }
    @Test
    void general(){
        assertEquals(25,card.getCardId());
        assertEquals(Resource.Insects, card.getFront().getUpRight().getResource());
        assertEquals(Items.Manuscript, card.getFront().getDownRight().getItems());
        assertTrue(card.getRetro().getUpRight().isVisible());
    }
}