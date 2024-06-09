package it.polimi.ingsw.Network.Messages;

/**
 * This class represents the response message for a PlayerNumberRequest message.
 *
 **/
public class PlayerNumberResponse extends Message{
    private int players;

    /**
     * Constructs the PlayerNumberResponse message.
     * @param Players The number of player chosen.
     * @param Name The name of the player sending the message.
     */
    public PlayerNumberResponse(int Players,String Name){
        super(Name,MessageType.Player_Num_Resp);
        players = Players;

    }

    /**
     * Retrieves the number of players chosen.
     * @return The number of players.
     */
    public int getNumPlayers(){
        return this.players;
    }
}
