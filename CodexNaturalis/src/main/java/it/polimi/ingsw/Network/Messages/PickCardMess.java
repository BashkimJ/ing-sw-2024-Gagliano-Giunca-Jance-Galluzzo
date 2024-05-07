package main.java.it.polimi.ingsw.Network.Messages;

public class PickCardMess extends Message{
    private final int CardToPick;
    public PickCardMess(String name,int CardID) {
        super(name, MessageType.Pick_Card);
        this.CardToPick = CardID;
    }

    public int getCardToPick(){
           return this.CardToPick;
    }

}
