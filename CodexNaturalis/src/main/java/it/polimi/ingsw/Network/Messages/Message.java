package main.java.it.polimi.ingsw.Network.Messages;

import java.io.Serializable;

public class Message implements Serializable {
    private String NickName;
    private MessageType type;

    public Message(String name, MessageType type){
        this.NickName = name;
        this.type = type;
    }

    public String getNickName(){
        return this.NickName;
    }

    public MessageType getType(){
        return this.type;
    }
     @Override
    public String toString(){
            return "Message{ "+getType()+" from " + getNickName();

     }


}
