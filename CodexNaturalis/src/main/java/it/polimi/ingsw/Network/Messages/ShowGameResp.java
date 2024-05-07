package main.java.it.polimi.ingsw.Network.Messages;

import main.java.it.polimi.ingsw.Model.Cards.ObjectiveCard;
import main.java.it.polimi.ingsw.Model.Cards.ResourceCard;

import java.util.*;

public class ShowGameResp extends Message{
    private final ArrayList<ResourceCard> revealed;
    private final ArrayList<ObjectiveCard> globalObj;
    private final ArrayList<ResourceCard> deck;
    public ShowGameResp(String name,ArrayList<ResourceCard> rev,ArrayList<ObjectiveCard> obj,ArrayList<ResourceCard> deck) {
        super(name, MessageType.Game_St);
        this.revealed = rev;
        this.globalObj = obj;
        this.deck  = deck;
    }

    public ArrayList<ObjectiveCard> getGlobalObj() {
        return globalObj;
    }

    public ArrayList<ResourceCard> getRevealed() {
        return revealed;
    }

    public ArrayList<ResourceCard> getDeck() {
        return deck;
    }
}
