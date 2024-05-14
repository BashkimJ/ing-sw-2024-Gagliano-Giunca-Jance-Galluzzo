package main.java.it.polimi.ingsw.Network.Messages;

/**
 * This class represents a simple error message.
 */
public class ErrorMessage extends Message{
    private String err;

    /**
     * Initialises a new error message.
     * @param err The message to be sent as an error.
     */
    public ErrorMessage(String err){
        super(null,MessageType.Error_Message);
        this.err = err;
        }

    /**
     * Prints the message.
     * @return The message as an error.
     */
    @Override
    public String toString(){
        return err;
    }
}
