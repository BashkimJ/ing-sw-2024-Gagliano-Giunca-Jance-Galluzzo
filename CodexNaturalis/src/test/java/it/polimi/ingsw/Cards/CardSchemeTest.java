package test.java.it.polimi.ingsw.Cards;

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
        OK = scheme.placeInitialCard(initial,"front");
        assertEquals(true,OK);
        assertEquals(2,scheme.getResourceNum(Fungi));
        assertEquals(2,scheme.getResourceNum(Animal));
        assertEquals(0,scheme.getResourceNum(Plant));
        assertEquals(0,scheme.getResourceNum(Insects));
        scheme = new CardScheme();
        OK = scheme.placeInitialCard(initial,"retro");
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
        ResourceCard rsc = new ResourceCard(front,retro,40,Insects,0);
        scheme.placeInitialCard(initial,"retro");
        int[] pos = new int[]{41,40};
        OK = scheme.placeCard(rsc,pos,"front");
        assertFalse(OK);
        pos =new int[]{40,37};
        OK = scheme.placeCard(rsc,pos,"front");
        assertFalse(OK);
        pos = new int[]{74,42};
        OK = scheme.placeCard(rsc,pos,"front");
        assertFalse(OK);
        pos[0] = 42;
        pos[1] = 42;
        OK = scheme.placeCard(rsc,pos,"front");
        assertEquals(3,scheme.show()[43][43]);
        assertTrue(OK);
        assertEquals(1,scheme.getResourceNum(Animal));
        assertEquals(1,scheme.getResourceNum(Insects));
        assertEquals(2,scheme.getResourceNum(Plant));
        assertEquals(1,scheme.getResourceNum(Fungi));
        assertEquals(0,scheme.getItemNum(Inkwell));
        assertEquals(1,scheme.getItemNum(Quill));
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
        //necc.add(Fungi);
        PointCondition point = new PointCondition(true,null);
        GoldCard gold= new GoldCard(front,retro,41,Fungi,4,necc,point);
        pos[0]=44;
        pos[1]=44;
        assertFalse(scheme.placeCard(gold,pos,"front"));
        pos[0]=38;
        pos[1] = 42;
        assertFalse(scheme.placeCard(gold,pos,"front"));



    }

    @Test
    void getResourceNum() {

    }

    @Test
    void getItemNum() {
    }
}