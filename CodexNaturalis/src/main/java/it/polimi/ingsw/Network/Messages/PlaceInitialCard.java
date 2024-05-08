package main.java.it.polimi.ingsw.Network.Messages;

public class PlaceInitialCard extends Message{
    private final int Side;
    public PlaceInitialCard(String NickName, int side){
        super(NickName,MessageType.Place_Initial_Card);
        this.Side = side;

    }
    public int getSide(){
        return this.Side;
    }
}
