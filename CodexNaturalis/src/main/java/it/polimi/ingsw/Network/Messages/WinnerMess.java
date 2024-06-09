package it.polimi.ingsw.Network.Messages;

/**
 * This class extends the Message class and communicates to the players the winner of the game.
 */
public class WinnerMess extends Message{
    private String winner;

    /**
     * Constructs the message.
     * @param name Who is sending the message.
     * @param mess The winner.
     */
    public WinnerMess(String name,String mess) {
        super(name, MessageType.Winner_Mess);
        this.winner = mess;
    }

    /**
     *
     * @return The winner.
     */
    public String getWinner() {
        return winner;
    }
}
