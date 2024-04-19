package main.java.it.polimi.ingsw.Exceptions.SchemeCardExc;

public class InvalidPositionException extends Exception {
    public InvalidPositionException(){
        super("You can't place the card there.");
    }

}
