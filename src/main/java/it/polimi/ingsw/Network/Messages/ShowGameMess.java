package it.polimi.ingsw.Network.Messages;

/**
 * This class represents a request to show the  state of the game.
 * As always it extends the Message class.
 */
public class ShowGameMess extends Message{
    /**
     * Constructs the ShowGameMess
     * @param NickName The name of the player requesting the state of the game.
     */
    public ShowGameMess(String NickName){
        super(NickName,MessageType.Game_St);
    }
}
