package main.java.it.polimi.ingsw.Network.Messages;

public class PlayerNumberResponse extends Message{
    private int players;
    public PlayerNumberResponse(int Players,String Name){
        super(Name,MessageType.Player_Num_Resp);
        players = Players;

    }
    public int getNumPlayers(){
        return this.players;
    }
}
