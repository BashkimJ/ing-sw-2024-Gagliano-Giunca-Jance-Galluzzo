package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.Cards.ObjectiveCard;
import it.polimi.ingsw.Model.Enumerations.Colour;
import it.polimi.ingsw.Model.Enumerations.Items;
import it.polimi.ingsw.Model.Enumerations.Pattern;
import it.polimi.ingsw.Model.Enumerations.Resource;
import it.polimi.ingsw.Model.GameStatus.Game;
import it.polimi.ingsw.Model.Player.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ObjectiveCardTest {
    Game game;
    Player p1 = new Player("Simone", Colour.red);
    ObjectiveCard objectiveCard_pattern;
    ObjectiveCard objectiveCard_resources;
    ObjectiveCard objectiveCard_items;
    List<Resource> rscList;
    List<Items> itemsList;

    @BeforeEach
    void setUp() {
        game = new Game(p1, 3);

        objectiveCard_pattern = game.getObjectiveCards().get(4); // card 5

        objectiveCard_resources = game.getObjectiveCards().get(9); // card 10

        objectiveCard_items = game.getObjectiveCards().get(14); //card 15

        rscList = new ArrayList<Resource>();

        rscList.add(Resource.Plant);
        rscList.add(Resource.Plant);
        rscList.add(Resource.Plant);

        itemsList = new ArrayList<Items>();

        itemsList.add(Items.Inkwell);
        itemsList.add(Items.Inkwell);
    }

    @Test
    void getObjectives(){
        assertEquals(Pattern.LdownRight, objectiveCard_pattern.getObjective().getObjectives());
        assertEquals(3, objectiveCard_pattern.getObjective().getPoints());

        assertEquals(rscList, objectiveCard_resources.getObjective().getObjectives());
        assertEquals(2, objectiveCard_resources.getObjective().getPoints());

        assertEquals(itemsList, objectiveCard_items.getObjective().getObjectives());
        assertEquals(2, objectiveCard_items.getObjective().getPoints());
    }

}
