package main.java.it.polimi.ingsw.Network.Messages;

public class LoginRequest extends Message {
    public LoginRequest(String NickName){
        super(NickName,MessageType.Login_Req );

    }

    @Override
    public String toString(){
        return getNickName() + " "  + getType();
    }


}
