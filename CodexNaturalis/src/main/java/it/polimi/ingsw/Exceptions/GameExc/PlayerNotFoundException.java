package it.polimi.ingsw.Exceptions.GameExc;

/**
 * Exception launched when a player is not found in a game.
 */
public class PlayerNotFoundException extends Exception{
    public PlayerNotFoundException(){
        super("Player not found in the current game");
    }
}
