package main.java.it.polimi.ingsw.Network.Messages;

public class ShowGameMess extends Message{
    public ShowGameMess(String NickName){
        super(NickName,MessageType.Game_St);
    }
}
