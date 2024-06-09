package it.polimi.ingsw.Network.Messages;

/**
 * The ChatMess class represents a chat message with a destination.
 * It extends the Message class.
 */

public class ChatMess extends Message{
    private String dest;
    private String mess;

    /**
     * Contructs the ChatMess message.
     * @param author The sender of the message.
     * @param Dest The receiver of the message
     * @param text The text to be sent.
     */
    public ChatMess(String author,String Dest,String text){
        super(author,MessageType.Chat_Mess);
        this.dest = Dest;
        this.mess = text;
    }

    /**
     * Retreives the content of the messages.
     * @return the content in text.
     */
    public String getMess(){
        return this.mess;
    }

    /**
     * Retreives the destination of the message.
     * @return The name of the destination.
     */
    public String getDest(){
        return this.dest;
    }

}
