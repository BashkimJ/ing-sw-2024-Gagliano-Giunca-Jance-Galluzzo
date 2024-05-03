package main.java.it.polimi.ingsw.Network.Messages;

public class LoginReply extends Message {
    private boolean Name;
    private boolean Connected;
    public LoginReply(boolean Name,boolean connected){
        super(null,MessageType.Login_Rpl);
        this.Name = Name;
        this.Connected = connected;
    }

    public boolean nameAccepted(){
        return this.Name;
    }
    public boolean Connected(){
        return this.Connected;
    }
}
