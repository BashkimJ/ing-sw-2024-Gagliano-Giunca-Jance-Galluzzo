package test.java.it.polimi.ingsw.Cards;

import main.java.it.polimi.ingsw.Model.Cards.Corner;
import main.java.it.polimi.ingsw.Model.Cards.Side;
import main.java.it.polimi.ingsw.Model.Enumerations.Items;
import main.java.it.polimi.ingsw.Model.Enumerations.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class SideTest {
    Corner upLeft;
    Corner downLeft;
    Corner upRight;
    Corner DownRight;
    File image;
    Side front;

    @BeforeEach
    void setUp() {
        upLeft = new Corner(true,true, Resource.Fungi,null);
        downLeft = new Corner(false,false,null,null);
        upRight = new Corner(true,false, null, Items.Quill);
        DownRight = new Corner(true,true,null,null);
        image = new File("github-recovery-codes.txt");
        front = new Side(upLeft,downLeft,upRight,DownRight,image);

    }

    @Test
    void getUpLeft() {

        assertEquals(false,front.getUpLeft().isFree());
        assertEquals(upLeft, front.getUpLeft());
        assertEquals(true,front.getUpLeft().isVisible());
        assertEquals(Resource.Fungi,front.getUpLeft().getResource());
    }

    @Test
    void getDownLeft() {
        assertEquals(downLeft,front.getDownLeft());
        assertEquals(null,front.getDownLeft().getResource());
    }

    @Test
    void getUpRight() {
        assertEquals(upRight,front.getUpRight());

    }

    @Test
    void getDownRight() {
        assertEquals(DownRight,front.getDownRight());
    }

    @Test
    void getImage() {
        assertEquals(image, front.getImage());
        assertEquals(image.getName(),front.getImage().getName());
    }
}