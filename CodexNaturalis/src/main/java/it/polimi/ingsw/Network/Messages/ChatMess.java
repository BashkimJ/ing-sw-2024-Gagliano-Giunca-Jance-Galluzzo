package main.java.it.polimi.ingsw.Network.Messages;

public class ChatMess extends Message{
    private String dest;
    private String mess;

    public ChatMess(String author,String Dest,String text){
        super(author,MessageType.Chat_Mess);
        this.dest = Dest;
        this.mess = text;
    }

    public String getMess(){
        return this.mess;
    }
    public String getDest(){
        return this.dest;
    }

}
