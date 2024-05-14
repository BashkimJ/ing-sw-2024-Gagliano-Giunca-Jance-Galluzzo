package main.java.it.polimi.ingsw.Network.Messages;

/**
 * A class representing a place card request.
 * It extends the Message class.
 */

public class PlaceCardMess extends Message{
    private final int cardID;
    private final String side;
    private final int[] pos;

    /**
     * Constructs a new PlaceCardMess.
     * @param NickName The name of the player requesting to play a card.
     * @param Card The id of the card tpo be played.
     * @param Side The side of the card to be played as a string.
     * @param Pos The x,y position to play the card.
     */
    public PlaceCardMess(String NickName,int Card,String Side,int[] Pos){
        super(NickName,MessageType.Place_Card);
        this.cardID = Card;
        this.side = Side;
        this.pos  = Pos;
    }

    /**
     * Retrieves the id of the card
     * @return The id.
     */
    public int getCardID() {
        return cardID;
    }

    /**
     * Retrieves the side chosen by the player.
     * @return The side as a string.
     */
     public String getSide(){
        return this.side;
    }

    /**
     * Retreives the position of the card.
     * @return The array containing the y and x position.
     */
    public int[] getPos(){
        return this.pos;
    }
}
