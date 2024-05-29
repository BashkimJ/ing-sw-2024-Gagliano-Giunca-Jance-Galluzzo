package test.java.it.polimi.ingsw.Model.Player;

import main.java.it.polimi.ingsw.Exceptions.SchemeCardExc.GoldCardPlacementException;
import main.java.it.polimi.ingsw.Exceptions.SchemeCardExc.InvalidPositionException;
import main.java.it.polimi.ingsw.Exceptions.SchemeCardExc.InvalidSideException;
import main.java.it.polimi.ingsw.Exceptions.SchemeCardExc.OutOfBoundsException;
import main.java.it.polimi.ingsw.Model.Cards.*;
import main.java.it.polimi.ingsw.Model.Enumerations.Colour;
import main.java.it.polimi.ingsw.Model.Enumerations.Resource;
import main.java.it.polimi.ingsw.Model.GameStatus.Game;
import main.java.it.polimi.ingsw.Model.Player.CardScheme;
import main.java.it.polimi.ingsw.Model.Player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static main.java.it.polimi.ingsw.Model.Enumerations.Items.*;
import static main.java.it.polimi.ingsw.Model.Enumerations.Resource.*;
import static org.junit.jupiter.api.Assertions.*;

class CardSchemeTest {
    Game game;
    Player p1 = new Player("Simone", Colour.red);
    Corner UL,DL,UR,DR;
    Side front,retro;
    InitialCard initial;
    List<Resource> goldRsc,initRsc;
    CardScheme scheme;
    boolean OK;
    int points;
    @BeforeEach
    public void setup(){
        game = new Game(p1, 3);

        scheme = new CardScheme();

        initial = ((InitialCard) game.getInitialDeck().getCards().get(3));

    }

    @Test
    void placeInitialCardTest() {
        try {
            OK = scheme.placeInitialCard(initial,"front");
        } catch (InvalidSideException e) {
            throw new RuntimeException(e);
        }
        assertTrue(OK);
        assertEquals(1,scheme.getResourceNum(Fungi));
        assertEquals(2,scheme.getResourceNum(Animal));
        assertEquals(1,scheme.getResourceNum(Plant));
        assertEquals(2,scheme.getResourceNum(Insects));
        scheme = new CardScheme();
        try {
            OK = scheme.placeInitialCard(initial,"retro");
        } catch (InvalidSideException e) {
            throw new RuntimeException(e);
        }
        assertTrue(OK);
        assertEquals(0,scheme.getResourceNum(Fungi));
        assertEquals(0,scheme.getResourceNum(Animal));
        assertEquals(0,scheme.getResourceNum(Plant));
        assertEquals(0,scheme.getResourceNum(Insects));
    }

    @Test
    void placeCardTest() {
        //Test the placement of an initial card;
        ResourceCard rsc1 = ((ResourceCard) game.getResourceDeck().getCards().get(12));
        try {
            OK = scheme.placeInitialCard(initial,"retro");
        } catch (InvalidSideException e) {
            OK = false;

        }
        assertTrue(OK);

        //Test a OutOfBoundsException
        int[] pos = new int[]{161,160};
        try {
            points = scheme.placeCard(rsc1,pos,"front");
        } catch (GoldCardPlacementException e) {
           points = -1;
        } catch (OutOfBoundsException e) {
            points = -2;
        } catch (InvalidPositionException e) {
            points = -3;
        } catch (InvalidSideException e) {
            points = -4;
        }
        assertEquals(-2, points);

        //Tests an InvalidPositionException
        pos = new int[]{82,80};
        try {
            points = scheme.placeCard(rsc1,pos,"front");
        } catch (GoldCardPlacementException e) {
            points = -1;
        } catch (OutOfBoundsException e) {
            points = -2;
        } catch (InvalidPositionException e) {
            points = -3;
        } catch (InvalidSideException e) {
            points = -4;
        }
        assertEquals(-3, points);

        //Tests a valid placement
        pos = new int[]{78,82};
        try {
            points = scheme.placeCard(rsc1,pos,"front");
        } catch (GoldCardPlacementException e) {
            points = -1;
        } catch (OutOfBoundsException e) {
            points = -2;
        } catch (InvalidPositionException e) {
            points = -3;
        } catch (InvalidSideException e) {
            points = -4;
        }
        assertEquals(0, points);
        assertEquals(2, scheme.getResourceNum(Plant));

        //Test the fact that the matrix is correctly updated
        ResourceCard rsc2 = ((ResourceCard) game.getResourceDeck().getCards().get(13));

        pos[0] = 82;
        pos[1] = 82;
        try {
            points = scheme.placeCard(rsc2,pos,"retro");
        } catch (GoldCardPlacementException e) {
            throw new RuntimeException(e);
        } catch (OutOfBoundsException e) {
            throw new RuntimeException(e);
        } catch (InvalidPositionException e) {
            throw new RuntimeException(e);
        } catch (InvalidSideException e) {
            throw new RuntimeException(e);
        }
        //assertEquals(3, scheme.show()[83][83]);
        assertEquals(0, points);

        //Test the fact that the number of resources and items is correctly updated
        assertEquals(0, scheme.getResourceNum(Animal));
        assertEquals(0, scheme.getResourceNum(Insects));
        assertEquals(3, scheme.getResourceNum(Plant));
        assertEquals(0, scheme.getResourceNum(Fungi));
        assertEquals(0, scheme.getItemNum(Inkwell));
        assertEquals(0, scheme.getItemNum(Quill));
        assertEquals(0, scheme.getItemNum(Manuscript));


        //Test the GoldCardPlacementException
        GoldCard gold1 = ((GoldCard) game.getGoldDeck().getCards().get(22));

        pos[0]=84;
        pos[1]=84;
        try {
            points = scheme.placeCard(gold1,pos,"front");
        } catch (GoldCardPlacementException e) {
            points = -1;
        } catch (OutOfBoundsException e) {
            points = -2;
        } catch (InvalidPositionException e) {
            points = -3;
        } catch (InvalidSideException e) {
            points = -4;
        }
        assertEquals(-1, points);

        //Test the right placement of a gold card
        GoldCard gold2 = ((GoldCard) game.getGoldDeck().getCards().get(16));

        pos[0] = 84;
        pos[1] = 84;
        try {
            points = scheme.placeCard(gold2,pos,"front");
        } catch (GoldCardPlacementException e) {
            points = -1;
        } catch (OutOfBoundsException e) {
            points = -2;
        } catch (InvalidPositionException e) {
            points = -3;
        } catch (InvalidSideException e) {
            points = -4;
        }
        assertEquals(3, points);

    }

    @Test
    void controlObjectiveTest(){
        ObjectiveCard objPattern = game.getObjectiveCards().get(1);
        ObjectiveCard objRsc = game.getObjectiveCards().get(9);
        ObjectiveCard objItems = game.getObjectiveCards().get(14);

        InitialCard initialCard = ((InitialCard) game.getInitialDeck().getCards().get(4));

        ResourceCard rsc_plant1 = ((ResourceCard) game.getResourceDeck().getCards().get(10)); //retro
        ResourceCard rsc_plant2 = ((ResourceCard) game.getResourceDeck().getCards().get(11)); //front
        ResourceCard rsc_plant3 = ((ResourceCard) game.getResourceDeck().getCards().get(12)); //front
        ResourceCard rsc_Inkwell1 = ((ResourceCard) game.getResourceDeck().getCards().get(5)); //front
        ResourceCard rsc_Inkwell2 = ((ResourceCard) game.getResourceDeck().getCards().get(36)); //front

        int[] pos = new int[]{161,160};

        pos[0]=80;
        pos[1]=80;
        try {
            points = scheme.placeCard(rsc_plant1,pos,"front");
        } catch (GoldCardPlacementException e) {
            points = -1;
        } catch (OutOfBoundsException e) {
            points = -2;
        } catch (InvalidPositionException e) {
            points = -3;
        } catch (InvalidSideException e) {
            points = -4;
        }
        assertEquals(0, scheme.ControlObjective(objItems));
    }


}