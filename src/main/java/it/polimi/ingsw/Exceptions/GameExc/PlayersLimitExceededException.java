package it.polimi.ingsw.Exceptions.GameExc;

/**
 * Exception that is launched when trying to add more players that expected to a game.
 */
public class PlayersLimitExceededException extends Exception{
    public PlayersLimitExceededException(){
        super("Exceeded the max number of players in the game");
    }
}
