package test.java.it.polimi.ingsw.GameStatus;

import main.java.it.polimi.ingsw.Exceptions.GameExc.PlayerNotFoundException;
import main.java.it.polimi.ingsw.Exceptions.GameExc.PlayersLimitExceededException;
import main.java.it.polimi.ingsw.Model.Cards.ObjectiveCard;
import main.java.it.polimi.ingsw.Model.Enumerations.Colour;
import main.java.it.polimi.ingsw.Model.Enumerations.Items;
import main.java.it.polimi.ingsw.Model.Enumerations.Pattern;
import main.java.it.polimi.ingsw.Model.Enumerations.Resource;
import main.java.it.polimi.ingsw.Model.GameStatus.Game;
import main.java.it.polimi.ingsw.Model.Player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Game game;
    Player p1 = new Player("Simone", Colour.red);
    Player p2 = new Player("Luca", Colour.blue);
    Player p3 = new Player("Pietro", Colour.green);
    Player p4 = new Player("Mario", Colour.yellow);
    List<Player> expectedList;
    @BeforeEach
    void setUp() {
        game = new Game(p1, 3);
        expectedList=new ArrayList<>();
        expectedList.add(p1);

    }
    @Test
    void getMAX_N_PLAYERS(){
        assertEquals(3, game.getMAX_N_PLAYERS());
    }
    @Test
    void playersManagement() throws PlayersLimitExceededException, PlayerNotFoundException {
        assertEquals(expectedList, game.getPlayers());
        game.addPlayer(p2);
        game.addPlayer(p3);
        expectedList.add(p2);
        expectedList.add(p3);
        assertEquals(expectedList, game.getPlayers());
        game.removePlayer(p2);
        expectedList.remove(p2);
        assertEquals(expectedList, game.getPlayers());
        game.removePlayer(p1);
        expectedList.remove(p1);
        assertEquals(expectedList, game.getPlayers());

        //TO DO: test exceptions

    }


    @Test
    void getObjectiveCards() {
        ObjectiveCard objectiveCard;
        //Card 87 - index 0
        objectiveCard = game.getObjectiveCards().get(0);
        assertEquals(Pattern.downUpLR_RED, objectiveCard.getObjective().getObjectives());
        assertEquals(2, objectiveCard.getObjective().getPoints());
        //Card 93 - index 6
        objectiveCard =  game.getObjectiveCards().get(6);
        assertEquals(Pattern.LupRight, objectiveCard.getObjective().getObjectives());
        assertEquals(3, objectiveCard.getObjective().getPoints());
        //Card 96 - index 9
        objectiveCard =  game.getObjectiveCards().get(9);
        List<Resource> resList = new ArrayList<Resource>();
        resList.add(Resource.Plant);
        resList.add(Resource.Plant);
        resList.add(Resource.Plant);
        assertEquals(resList, objectiveCard.getObjective().getObjectives());
        assertEquals(2, objectiveCard.getObjective().getPoints());
        //Card 99 - index 12
        objectiveCard =  game.getObjectiveCards().get(12);
        List<Items> itemsList = new ArrayList<>();
        itemsList.add(Items.Quill);
        itemsList.add(Items.Inkwell);
        itemsList.add(Items.Manuscript);
        assertEquals(itemsList, objectiveCard.getObjective().getObjectives());
        assertEquals(3, objectiveCard.getObjective().getPoints());
    }

    @Test
    void getResourceDeck() {
        //TO DO
    }

    @Test
    void getGoldDeck() {
        //TO DO
    }

    @Test
    void getInitialDeck() {
        //TO DO
    }

}