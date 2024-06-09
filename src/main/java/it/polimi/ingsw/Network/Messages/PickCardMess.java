package it.polimi.ingsw.Network.Messages;

/**
 * A class representing a pick card request.
 */
public class PickCardMess extends Message{
    private final int CardToPick;

    /**
     * Constructs the PickCardMess message.
     * @param name The name of the player picking the card.
     * @param CardID The id of the card to pick.
     */
    public PickCardMess(String name,int CardID) {
        super(name, MessageType.Pick_Card);
        this.CardToPick = CardID;
    }

    /**
     * Retrieves the id of the card to pick.
     * @return The id of the card.
     */
    public int getCardToPick(){
           return this.CardToPick;
    }

}
