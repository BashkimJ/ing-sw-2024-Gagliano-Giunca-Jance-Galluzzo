package it.polimi.ingsw.Network.Messages;

import it.polimi.ingsw.Model.Cards.ObjectiveCard;
import it.polimi.ingsw.Model.Cards.ResourceCard;

import java.util.*;

/**
 * The class represents the response to the ShowGameMess.
 * It extends the Message class.
 */
public class ShowGameResp extends Message{
    private final ArrayList<ResourceCard> revealed;
    private final ArrayList<ObjectiveCard> globalObj;
    private final ArrayList<ResourceCard> deck;
    private String playerTurn;
    private ArrayList<String> players;

    /**
     * Constructs the ShowGameResp message.
     * @param name The name of the side sending the message.
     * @param rev The list of the revealed card of the game.
     * @param obj The list of the commune objectives.
     * @param deck The list containing the last card of the resource deck and objective deck.
     * @param turn The player that has the turn to playe.
     */
    public ShowGameResp(String name,ArrayList<ResourceCard> rev,ArrayList<ObjectiveCard> obj,ArrayList<ResourceCard> deck,String turn,ArrayList<String> players) {
        super(name, MessageType.Game_St);
        this.revealed = rev;
        this.globalObj = obj;
        this.deck  = deck;
        this.playerTurn = turn;
        this.players = players;
    }

    /**
     * Retrieves the list of the commune objectives.
     * @return The list of the objective cards.
     */
    public ArrayList<ObjectiveCard> getGlobalObj() {
        return globalObj;
    }

    /**
     * Retrieves the list of the revealed cards.
     *@return The list of the revealed cards.
     */
    public ArrayList<ResourceCard> getRevealed() {
        return revealed;
    }

    /**
     * Retrieves the last card of the resource and gold deck.
     * @return The list containing the last cards of the resource and gold deck.
     */
    public ArrayList<ResourceCard> getDeck() {
        return deck;
    }

    /**
     * @return The name of the player having the turn.
     */
    public String getPlayerTurn(){
        return this.playerTurn;
    }

    public ArrayList<String> getPlayers() {
        return players;
    }
}
