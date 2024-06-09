package it.polimi.ingsw.Network.Messages;

/**
 * This class represents a login request message. It extends the message class.
 */
public class LoginRequest extends Message {
    /**
     * Constructs a new LoginRequest message.
     * @param NickName The name chosen to log in the game.
     */
    public LoginRequest(String NickName){
        super(NickName,MessageType.Login_Req );

    }

    /**
     *
     * @return The string containing the name and the type of the message.
     */
    @Override
    public String toString(){
        return getNickName() + " "  + getType();
    }


}
