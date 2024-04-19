package test.java.it.polimi.ingsw.Cards;

import main.java.it.polimi.ingsw.Exceptions.SchemeCardExc.GoldCardPlacementException;
import main.java.it.polimi.ingsw.Exceptions.SchemeCardExc.InvalidPositionException;
import main.java.it.polimi.ingsw.Exceptions.SchemeCardExc.InvalidSideException;
import main.java.it.polimi.ingsw.Exceptions.SchemeCardExc.OutOfBoundsException;
import main.java.it.polimi.ingsw.Model.Cards.*;
import main.java.it.polimi.ingsw.Model.Enumerations.Resource;
import main.java.it.polimi.ingsw.Model.Player.CardScheme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static main.java.it.polimi.ingsw.Model.Enumerations.Items.*;
import static main.java.it.polimi.ingsw.Model.Enumerations.Resource.*;
import static org.junit.jupiter.api.Assertions.*;

class CardSchemeTest {
    Corner UL,DL,UR,DR;
    Side front,retro;
    InitialCard initial;
    List<Resource> goldRsc,initRsc;
    CardScheme scheme;
    boolean OK;
    int points;
    @BeforeEach
    public void setup(){
       UL = new Corner(true,true,Animal,null);
       DL = new Corner(true,true,null,null);
       UR = new Corner(true,true,null,null);
       DR = new Corner(true,true,Fungi,null);
       front = new Side(UL,DL,UR,DR,null);
       UL = new Corner(true,true,Plant,null);
       DL = new Corner(true,true,Fungi,null);
       UR = new Corner(true,true,Animal,null);
       DR = new Corner(true,true,Insects,null);
       retro = new Side(UL,DL,UR,DR,null);
       initRsc = new ArrayList<Resource>();
       initRsc.add(Fungi);
       initRsc.add(Animal);
       scheme = new CardScheme();
       initial = new InitialCard(front,retro,2,initRsc);
    }

    @Test
    void placeInitialCardTest() {
        try {
            OK = scheme.placeInitialCard(initial,"front");
        } catch (InvalidSideException e) {
            throw new RuntimeException(e);
        }
        assertEquals(true,OK);
        assertEquals(2,scheme.getResourceNum(Fungi));
        assertEquals(2,scheme.getResourceNum(Animal));
        assertEquals(0,scheme.getResourceNum(Plant));
        assertEquals(0,scheme.getResourceNum(Insects));
        scheme = new CardScheme();
        try {
            OK = scheme.placeInitialCard(initial,"retro");
        } catch (InvalidSideException e) {
            throw new RuntimeException(e);
        }
        assertEquals(true,OK);
        assertEquals(1,scheme.getResourceNum(Fungi));
        assertEquals(1,scheme.getResourceNum(Animal));
        assertEquals(1,scheme.getResourceNum(Plant));
        assertEquals(1,scheme.getResourceNum(Insects));
    }

    @Test
    void placeCardTest() {
        UL = new Corner(true,true,Insects,null);
        DL = new Corner(true,true,null,Quill);
        UR = new Corner(true,true,Plant, null);
        DR = new Corner(false,false,null,null);
        front = new Side(UL,DL,UR,DR,null);
        UL = new Corner(true,true,null,null);
        DL = new Corner(true,true,null,null);
        UR = new Corner(true,true,null, null);
        DR = new Corner(true,true,null,null);
        retro = new Side(UL,DL,UR,DR,null);
        ResourceCard rsc = new ResourceCard(front,retro,40,Insects,2);
        //Test the placement of an initial card;
        try {
            OK = scheme.placeInitialCard(initial,"retro");
        } catch (InvalidSideException e) {
            OK = false;

        }
        assertTrue(OK);

        //Test a OutOfBoundsException
        int[] pos = new int[]{161,160};
        try {
            points = scheme.placeCard(rsc,pos,"front");
        } catch (GoldCardPlacementException e) {
           points = -1;
        } catch (OutOfBoundsException e) {
            points = -2;
        } catch (InvalidPositionException e) {
            points = -3;
        } catch (InvalidSideException e) {
            points = -4;
        }
        assertEquals(points,-2);

        //Tests an InvalidPositionException
        pos =new int[]{82,80};
        try {
            points = scheme.placeCard(rsc,pos,"front");
        } catch (GoldCardPlacementException e) {
            points = -1;
        } catch (OutOfBoundsException e) {
            points = -2;
        } catch (InvalidPositionException e) {
            points = -3;
        } catch (InvalidSideException e) {
            points = -4;
        }
        assertEquals(points, -3);

        //Tests a valid placement
        pos = new int[]{78,82};
        try {
            points = scheme.placeCard(rsc,pos,"front");
        } catch (GoldCardPlacementException e) {
            points = -1;
        } catch (OutOfBoundsException e) {
            points = -2;
        } catch (InvalidPositionException e) {
            points = -3;
        } catch (InvalidSideException e) {
            points = -4;
        }
        assertEquals(points, 2);

        //Test the fact that the matrix is correctly updated
        pos[0] = 82;
        pos[1] = 82;
        try {
            points = scheme.placeCard(rsc,pos,"front");
        } catch (GoldCardPlacementException e) {
            throw new RuntimeException(e);
        } catch (OutOfBoundsException e) {
            throw new RuntimeException(e);
        } catch (InvalidPositionException e) {
            throw new RuntimeException(e);
        } catch (InvalidSideException e) {
            throw new RuntimeException(e);
        }
        assertEquals(3,scheme.show()[83][83]);
        assertEquals(points,2);

        //Test the fact that the number of resources and items is correctly updated
        assertEquals(0,scheme.getResourceNum(Animal));
        assertEquals(2,scheme.getResourceNum(Insects));
        assertEquals(3,scheme.getResourceNum(Plant));
        assertEquals(1,scheme.getResourceNum(Fungi));
        assertEquals(0,scheme.getItemNum(Inkwell));
        assertEquals(2,scheme.getItemNum(Quill));
        assertEquals(0,scheme.getItemNum(Manuscript));


        UL = new Corner(true,true,Animal,null);
        DL = new Corner(true,true,null, Inkwell);
        UR = new Corner(false,false,null,null);
        DR  = new Corner(false,false,null,null);
        front = new Side(UL,DL,UR,DR,null);
        UL = new Corner(true,true,null,null);
        DL = new Corner(true,true,null,null);
        UR = new Corner(true,true,null, null);
        DR = new Corner(true,true,null,null);
        retro = new Side(UL,DL,UR,DR,null);
        ArrayList<Resource> necc = new ArrayList<Resource>();
        necc.add(Plant);
        necc.add(Fungi);
        necc.add(Fungi);
        PointCondition point = new PointCondition(true,null);
        GoldCard gold= new GoldCard(front,retro,41,Fungi,4,necc,point);

        //Test the GoldCardPlacementException
        pos[0]=84;
        pos[1]=84;
        try {
            points = scheme.placeCard(gold,pos,"front");
        } catch (GoldCardPlacementException e) {
            points = -1;
        } catch (OutOfBoundsException e) {
            points = -2;
        } catch (InvalidPositionException e) {
            points = -3;
        } catch (InvalidSideException e) {
            points = -4;
        }
        assertEquals(points,-1);

        //Test the right placement of a gold card
        pos[0] = 84;
        pos[1] = 80;
        necc.remove(Fungi);
        try {
            points = scheme.placeCard(gold,pos,"front");
        } catch (GoldCardPlacementException e) {
            points = -1;
        } catch (OutOfBoundsException e) {
            points = -2;
        } catch (InvalidPositionException e) {
            points = -3;
        } catch (InvalidSideException e) {
            points = -4;
        }
        assertEquals(points,4);



    }


}