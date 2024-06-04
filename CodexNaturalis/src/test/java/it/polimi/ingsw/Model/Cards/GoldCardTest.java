package test.java.it.polimi.ingsw.Model.Cards;

import main.java.it.polimi.ingsw.Model.Cards.*;
import main.java.it.polimi.ingsw.Model.Enumerations.Colour;
import main.java.it.polimi.ingsw.Model.Enumerations.Items;
import main.java.it.polimi.ingsw.Model.Enumerations.Resource;
import main.java.it.polimi.ingsw.Model.GameStatus.Game;
import main.java.it.polimi.ingsw.Model.Player.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GoldCardTest {
    Game game;
    GoldCard card;
    PointCondition cond;
    Player p1 = new Player("Simone", Colour.red);
    @BeforeEach
    void setUp() {
        game = new Game(p1, 3);

        card = ((GoldCard) game.getGoldDeck().getCards().get(1)); //cardId = 42
    }

    @Test
    void getCondition() {
        cond = new PointCondition(false, Items.Inkwell);
        assertEquals(cond.getCornerCondition(), card.getCondition().getCornerCondition());
        assertEquals(cond.getItemType(), card.getCondition().getItemType());
    }

    @Test
    void getNeccesaryRes() {
        assertEquals(Resource.Fungi,card.getNecessaryRes().get(0));
        assertEquals(Resource.Fungi, card.getNecessaryRes().get(1));
        assertEquals(Resource.Plant,card.getNecessaryRes().get(2));
    }
}