package main.java.it.polimi.ingsw.Network.Messages;

public class WinnerMess extends Message{
    private String winner;
    public WinnerMess(String name,String mess) {
        super(name, MessageType.Winner_Mess);
        this.winner = mess;
    }

    public String getWinner() {
        return winner;
    }
}
