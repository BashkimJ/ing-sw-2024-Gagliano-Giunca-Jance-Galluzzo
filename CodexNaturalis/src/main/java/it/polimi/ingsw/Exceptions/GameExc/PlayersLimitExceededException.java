package main.java.it.polimi.ingsw.Exceptions.GameExc;

public class PlayersLimitExceededException extends Exception{
    public PlayersLimitExceededException(){
        super("Exceeded the max number of players in the game");
    }
}
