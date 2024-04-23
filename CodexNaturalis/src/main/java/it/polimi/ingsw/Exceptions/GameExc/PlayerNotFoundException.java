package main.java.it.polimi.ingsw.Exceptions.GameExc;

public class PlayerNotFoundException extends Exception{
    public PlayerNotFoundException(){
        super("Player not found in the current game");
    }
}
