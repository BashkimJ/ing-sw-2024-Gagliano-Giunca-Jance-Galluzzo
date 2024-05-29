package test.java.it.polimi.ingsw.Model.Cards;

import main.java.it.polimi.ingsw.Model.Cards.Corner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static main.java.it.polimi.ingsw.Model.Enumerations.Items.Inkwell;
import static main.java.it.polimi.ingsw.Model.Enumerations.Resource.Animal;
import static org.junit.jupiter.api.Assertions.*;

class CornerTest {
    Corner upLeft;
    Corner downLeft;
    @BeforeEach
    public void setUp(){
         upLeft = new Corner(true,true,Animal,null);
         downLeft = new Corner(true,false,null, Inkwell);
    }
    @Test
    void isVisible() {
        assertTrue(upLeft.isVisible());
    }

    @Test
    void isFree() {
        assertTrue(upLeft.isFree());
    }

    @Test
    void setFreeValue() {
        assertTrue(upLeft.isFree());
    }

    @Test
    void getResource() {
        assertEquals(Animal, upLeft.getResource());
        assertNull(downLeft.getResource());
    }

    @Test
    void getItems() {
        assertEquals(Inkwell, downLeft.getItems());
    }
}