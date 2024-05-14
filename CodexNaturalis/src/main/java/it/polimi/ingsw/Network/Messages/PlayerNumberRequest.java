package main.java.it.polimi.ingsw.Network.Messages;

/**
 * This class represents a request from the server to ask the player for the number of the player to connect to the game.
 */
public class PlayerNumberRequest extends Message {
    /**
     * Constructs the PlayerNumberRequest message.
     */
    public PlayerNumberRequest(){
        super("Server", MessageType.Player_Num_Req);
    }

}
