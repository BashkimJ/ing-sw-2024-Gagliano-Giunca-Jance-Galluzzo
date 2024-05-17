package main.java.it.polimi.ingsw.Network.Messages;

/**
 * This class represents a PlaceInitialCard request from the client.
 * It extends the Message class.
 */
public class PlaceInitialCard extends Message{
    private final int Side;

    /**
     * Constructs the PlaceInitialCard message.
     * @param NickName The name of the player placing the card.
     * @param side The side chosen by the player.(1->Front),(2->Retro).
     */
    public PlaceInitialCard(String NickName, int side){
        super(NickName,MessageType.Place_Initial_Card);
        this.Side = side;

    }

    /**
     * Retrieves the side.
     * @return The integer representing the side.
     */
    public int getSide(){
        return this.Side;
    }
}
