package main.java.it.polimi.ingsw.Network.Messages;

public class PlayerNumberRequest extends Message {
    public PlayerNumberRequest(){
        super("Server", MessageType.Player_Num_Req);
    }
    @Override
    public String toString(){
        return "Server requests the number of players";
    }
}
