package test.java.it.polimi.ingsw.Cards;

import main.java.it.polimi.ingsw.Model.Cards.*;
import main.java.it.polimi.ingsw.Model.Enumerations.Items;
import main.java.it.polimi.ingsw.Model.Enumerations.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GoldCardTest {
    Corner upLeft;
    Corner downLeft;
    Corner upRight;
    Corner DownRight;
    File image;
    Side front;
    Side retro;
    GoldCard card;
    int id;
    int points;
    List<Resource> myrsc;
    PointCondition cond;
    @BeforeEach
    void setUp() {
        upLeft = new Corner(true,true, null,Items.Inkwell);
        downLeft = new Corner(true,true,null, null);
        upRight = new Corner(true,true, null, null);
        DownRight = new Corner(false,true,null,null);
        image = new File("github-recovery-codes.txt");
        front = new Side(upLeft,downLeft,upRight,DownRight,image);
        upLeft = new Corner(true, true, null, null);
        downLeft = new Corner(true, true, null,null);
        upRight =new Corner(true,false,null,null);
        DownRight =new Corner(true,true,null,null);
        image =new File("github-recovery-codes.txt");
        retro =new Side(upLeft,downLeft,upRight,DownRight,image);
        id =42;
        points = 1;
        myrsc = new ArrayList<Resource>();
        myrsc.add(Resource.Animal);
        myrsc.add(Resource.Animal);
        myrsc.add(Resource.Insects);
        cond = new PointCondition(false,Items.Inkwell);
        card = new GoldCard(front,retro,id,Resource.Animal,points,myrsc,cond);
    }

    @Test
    void getCondition() {
        assertEquals(cond, card.getCondition());
        boolean val;
        assertEquals(false, card.getCondition().getCornerCondition());
        assertEquals(Items.Inkwell,card.getCondition().getItemType());

    }

    @Test
    void getNeccesaryRes() {
        assertEquals(Resource.Animal,card.getNecessaryRes().get(0));
        assertEquals(Resource.Animal, card.getNecessaryRes().get(1));
        assertEquals(Resource.Insects,card.getNecessaryRes().get(2));
    }
}