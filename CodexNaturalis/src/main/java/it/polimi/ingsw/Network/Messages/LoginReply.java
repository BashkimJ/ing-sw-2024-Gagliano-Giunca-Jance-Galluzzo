package it.polimi.ingsw.Network.Messages;

/**
 * The LoginReply class extends the Message class and represents a login response.
 */
public class LoginReply extends Message {
    private boolean Name;
    private boolean Connected;

    /**
     * Initialises the message.
     * @param Name Boolean showing if the name is accepted.
     * @param connected  Boolean showing if the player is connected.
     */
    public LoginReply(boolean Name,boolean connected){
        super(null,MessageType.Login_Rpl);
        this.Name = Name;
        this.Connected = connected;
    }

    /**
     *
     * @return The boolean showing if the name is accepted.
     */
    public boolean nameAccepted(){
        return this.Name;
    }

    /**
     *
     * @return The boolean showing if the player is connected.
     */
    public boolean Connected(){
        return this.Connected;
    }
}
