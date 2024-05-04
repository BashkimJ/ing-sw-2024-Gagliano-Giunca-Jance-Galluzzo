package main.java.it.polimi.ingsw.Network.Messages;

public class ErrorMessage extends Message{
    private String err;
    public ErrorMessage(String err){
        super(null,MessageType.Error_Message);
        this.err = err;
        }
    @Override
    public String toString(){
        return err;
    }
}
