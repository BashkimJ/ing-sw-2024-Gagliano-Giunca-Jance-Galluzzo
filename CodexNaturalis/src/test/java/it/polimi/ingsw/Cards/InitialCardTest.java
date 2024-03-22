package test.java.it.polimi.ingsw.Cards;

import main.java.it.polimi.ingsw.Cards.Corner;
import main.java.it.polimi.ingsw.Cards.InitialCard;
import main.java.it.polimi.ingsw.Cards.Side;
import main.java.it.polimi.ingsw.Enumerations.Items;
import main.java.it.polimi.ingsw.Enumerations.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InitialCardTest {
    Corner upLeft;
    Corner downLeft;
    Corner upRight;
    Corner DownRight;
    File image;
    Side front;
    Side retro;
    List<Resource> myList;
    InitialCard card;
    int id;

    @BeforeEach
    void setUp() {
        upLeft = new Corner(true,true, Resource.Fungi,null);
        downLeft = new Corner(true,true,Resource.Animal,null);
        upRight = new Corner(true,true, null, null);
        DownRight = new Corner(true,true,null,null);
        image = new File("github-recovery-codes.txt");
        front = new Side(upLeft,downLeft,upRight,DownRight,image);
        upLeft = new Corner(true, true, null, null);
        downLeft = new Corner(true, true, null,null);
        upRight =new Corner(false,false,null,null);
        DownRight =new Corner(true,true,null,null);
        image =new File("github-recovery-codes.txt");
        retro =new Side(upLeft,downLeft,upRight,DownRight,image);
        id =4;
        myList = new ArrayList<Resource>();
        myList.add(Resource.Plant);
        myList.add(Resource.Insects);
        myList.add(Resource.Fungi);
        card =new InitialCard(front,retro,id,myList);

    }

    @Test
    void getMiddleResource() {
        assertEquals(myList,card.getMiddleResource());
        for(int i=0;i<3;i++){
            assertEquals(myList.get(i),card.getMiddleResource().get(i));
        }
    }
    @Test   //Control the methods from Card which is an abstract class
    void generalTest(){
        assertEquals(id,card.getCardId());
        assertEquals(front,card.getFront());
        assertEquals(retro,card.getRetro());
    }
}