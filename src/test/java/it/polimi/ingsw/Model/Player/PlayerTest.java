package it.polimi.ingsw.Model.Player;

import it.polimi.ingsw.Model.Cards.InitialCard;
import it.polimi.ingsw.Model.Cards.ResourceCard;
import it.polimi.ingsw.Model.Enumerations.Colour;
import it.polimi.ingsw.Model.GameStatus.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PlayerTest {
    Game game;
    @BeforeEach
    void setup(){
        game = new Game(null,2);
    }


    @Test
    void playerTest(){
        //General Tests
        Player test = new Player("Hey", Colour.blue);
        Player test1 = new Player("Hey",Colour.red,null,null,null,3);
        assertEquals(test.getNickName(),"Hey");
        assertEquals(test.getPlayerColour(),Colour.blue);
        assertEquals(test1.getPoints(),3);
        test1.setPlayerColour(Colour.red);
        test.setPoints(3);
        test.upPoints(1);
        assertEquals(test.getPoints(),4);
        assertNull(test.getTokenImage());
        CardScheme scheme = test.getPlayerScheme();

        //Setters and getters for initial and objective cards, tests
        test.setPlayerInitial((InitialCard) game.getInitialDeck().getCards().get(4));
        assertEquals(test.getPlayerInitial().getCardId(),85);
        test.setPlayerObjective(game.getObjectiveCards().get(4));
        assertEquals(test.getPlayerObjective().getCardId(),91);

        //Setters and getters for hand tests

        test.pickCard((ResourceCard) game.getResourceDeck().pickCard());
        assertEquals(test.getPlayerHand().size(),1);
        ResourceCard card = test.getCardByIdx(0);
        test.removeCardByIdx(0);
        assertEquals(0,test.getPlayerHand().size());
        List<ResourceCard> list = new ArrayList<>();
        list.add((ResourceCard) game.getGoldDeck().pickCard());
        list.add((ResourceCard) game.getResourceDeck().pickCard());
        list.add((ResourceCard) game.getResourceDeck().pickCard());
        test.setPlayerHand(list);
        assertEquals(test.getPlayerHand().size(),3);
        try{
            test.pickCard((ResourceCard) game.getResourceDeck().pickCard());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Too many cards.");
        }
        list.add((ResourceCard) game.getResourceDeck().pickCard());
        try{
            test.setPlayerHand(list);
        } catch (IllegalArgumentException e) {
            System.out.println("Too many cards.");
        }
        try{
            test.getCardByIdx(31);
        } catch (IllegalArgumentException e) {
        }
        try{
            test.removeCardByIdx(31);
        } catch (IllegalArgumentException e) {
        }


        //Testing the PlayerView of the player
        PlayerView testView = new PlayerView(test);
        assertEquals(testView.getNickName(),test.getNickName());
        assertEquals(testView.getPoints(),test.getPoints());
        assertEquals(testView.getPlayerColour(),test.getPlayerColour());
        assertEquals(testView.getTokenImage(),test.getTokenImage());
        assertEquals(testView.getPlayerInitial(),test.getPlayerInitial());
        assertEquals(testView.getPlayerObjective(),test.getPlayerObjective());
        assertEquals(testView.getPlayerHand(),test.getPlayerHand());
        System.out.println(testView.toString());



    }
}
