package it.polimi.ingsw.Network.Messages;

import java.io.Serializable;

/**
 * Message is a class representing a generic message.
 */
public class Message implements Serializable {
    private String NickName;
    private MessageType type;

    /**
     * Constructs a new message.
     * @param name The name of the sender.
     * @param type The type of the message.
     */
    public Message(String name, MessageType type){
        this.NickName = name;
        this.type = type;
    }

    /**
     * Retrieves the sender of the message.
     * @return The name.
     */
    public String getNickName(){
        return this.NickName;
    }

    /**
     * Retrieves the type of the message.
     * @return The message type.
     */
    public MessageType getType(){
        return this.type;
    }
     @Override
    public String toString(){
            return "Message{ "+getType()+" from " + getNickName();

     }


}
