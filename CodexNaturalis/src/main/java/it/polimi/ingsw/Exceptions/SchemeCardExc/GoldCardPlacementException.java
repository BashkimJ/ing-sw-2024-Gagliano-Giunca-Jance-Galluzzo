package main.java.it.polimi.ingsw.Exceptions.SchemeCardExc;

public class GoldCardPlacementException extends Exception{
    public GoldCardPlacementException(){
        super("You can't play this card at this moment");
    }
}
