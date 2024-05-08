package main.java.it.polimi.ingsw.Network.Messages;

public class PlaceCardMess extends Message{
    private final int cardID;
    private final String side;
    private final int[] pos;
    public PlaceCardMess(String NickName,int Card,String Side,int[] Pos){
        super(NickName,MessageType.Place_Card);
        this.cardID = Card;
        this.side = Side;
        this.pos  = Pos;
    }

    public int getCardID() {
        return cardID;
    }
     public String getSide(){
        return this.side;
    }
    public int[] getPos(){
        return this.pos;
    }
}
