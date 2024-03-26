package test.java.it.polimi.ingsw.Cards;

import main.java.it.polimi.ingsw.Model.Cards.Corner;
import main.java.it.polimi.ingsw.Model.Cards.ResourceCard;
import main.java.it.polimi.ingsw.Model.Cards.Side;
import main.java.it.polimi.ingsw.Model.Enumerations.Items;
import main.java.it.polimi.ingsw.Model.Enumerations.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ResourceCardTest {
    Corner upLeft;
    Corner downLeft;
    Corner upRight;
    Corner DownRight;
    File image;
    Side front;
    Side retro;
    ResourceCard card;
    int id;
    int points;
    Resource type;

    @BeforeEach
    void setUp() {
        upLeft = new Corner(true,true, Resource.Fungi,null);
        downLeft = new Corner(true,true,null, Items.Manuscript);
        upRight = new Corner(true,true, null, null);
        DownRight = new Corner(true,true,Resource.Plant,null);
        image = new File("github-recovery-codes.txt");//Il nome del file e solo una prova per vedere che tutto vienne instanziato nel modo giusto
        front = new Side(upLeft,downLeft,upRight,DownRight,image);
        upLeft = new Corner(true, true, Resource.Plant, null);
        downLeft = new Corner(true, true, null,Items.Inkwell);
        upRight =new Corner(false,false,null,null);
        DownRight =new Corner(true,true,Resource.Animal,null);
        image =new File("github-recovery-codes.txt");
        retro =new Side(upLeft,downLeft,upRight,DownRight,image);
        id =13;
        points = 4;
        type= Resource.Animal;
        card = new ResourceCard(front,retro,13,type,points);
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
        assertEquals(13,card.getCardId());
        assertEquals(Resource.Fungi, card.getFront().getUpLeft().getResource());
        assertEquals(Items.Manuscript, card.getFront().getDownLeft().getItems());
        assertEquals(false, card.getRetro().getUpRight().isVisible());
    }
}