package main.java.it.polimi.ingsw.Network.Messages;

import main.java.it.polimi.ingsw.Model.Cards.InitialCard;
import main.java.it.polimi.ingsw.Model.Cards.Side;
import main.java.it.polimi.ingsw.Model.Enumerations.Resource;

public class InitialCardMess extends Message{
    private InitialCard Card;
    public InitialCardMess(InitialCard card){
        super("Server",MessageType.Initial_Card_Mess);
        this.Card = card;

    }


    @Override
    public String toString(){
        String InitialCard="";
        String upLeft="";
        String upRight = "";
        String downLeft="";
        String downRight="";
        Side side = Card.getFront();
        if(side.getUpLeft().isVisible()){
            if(side.getUpLeft().getItems()!=null){
                upLeft = side.getUpLeft().getItems().name();
            }
            else if(side.getUpLeft().getResource()!=null){
                upLeft = side.getUpLeft().getResource().name();
            }
            else{
                upLeft = "empty";
            }
        }
        else{
            upLeft = "not visible";
        }
        if(side.getUpRight().isVisible()){
            if(side.getUpRight().getItems()!=null){
                upRight = side.getUpRight().getItems().name();
            }
            else if(side.getUpRight().getResource()!=null){
                upRight = side.getUpRight().getResource().name();
            }
            else{
                upRight = "empty";
            }
        }
        else{
            upRight = "not visible";
        }
        if(side.getDownRight().isVisible()){
            if(side.getDownRight().getItems()!=null){
                downRight = side.getDownRight().getItems().name();
            }
            else if(side.getDownRight().getResource()!=null){
                downRight = side.getDownRight().getResource().name();
            }
            else{
                downRight = "empty";
            }
        }
        else{
            downRight = "not visible";
        }
        if(side.getDownLeft().isVisible()){
            if(side.getDownLeft().getItems()!=null){
                downLeft = side.getDownLeft().getItems().name();
            }
            if(side.getDownLeft().getResource()!=null){
                downLeft = side.getDownLeft().getResource().name();
            }
            else{
                downLeft = "empty";
            }
        }
        else{
            downLeft = "not visible";
        }

        InitialCard = InitialCard + "Front:\n" +
                "upRight: " + upRight + " upLeft: " + upLeft + " downRight: " + downRight + " downLeft: " + downLeft;
        side = Card.getRetro();

        if(side.getUpLeft().isVisible()){
            if(side.getUpLeft().getItems()!=null){
                upLeft = side.getUpLeft().getItems().name();
            }
            else if(side.getUpLeft().getResource()!=null){
                upLeft = side.getUpLeft().getResource().name();
            }
            else{
                upLeft = "empty";
            }
        }
        else{
            upLeft = "not visible";
        }
        if(side.getUpRight().isVisible()){
            if(side.getUpRight().getItems()!=null){
                upRight = side.getUpRight().getItems().name();
            }
            else if(side.getUpRight().getResource()!=null){
                upRight = side.getUpRight().getResource().name();
            }
            else{
                upRight = "empty";
            }
        }
        else{
            upRight = "not visible";
        }
        if(side.getDownRight().isVisible()){
            if(side.getDownRight().getItems()!=null){
                downRight = side.getDownRight().getItems().name();
            }
            else if(side.getDownRight().getResource()!=null){
                downRight = side.getDownRight().getResource().name();
            }
            else{
                downRight = "empty";
            }
        }
        else{
            downRight = "not visible";
        }
        if(side.getDownLeft().isVisible()){
            if(side.getDownLeft().getItems()!=null){
                downLeft = side.getDownLeft().getItems().name();
            }
            if(side.getDownLeft().getResource()!=null){
                downLeft = side.getDownLeft().getResource().name();
            }
            else{
                downLeft = "empty";
            }
        }
        else{
            downLeft = "not visible";
        }
        InitialCard = InitialCard + "\nRetro:\n" +
                "upRight: " + upRight + " upLeft: " + upLeft + " downRight: " + downRight + " downLeft: " + downLeft + "\nResources on the front: ";
        for(Resource rs:Card.getMiddleResource()){
            InitialCard = InitialCard + rs.name()+" ";
        }
        return InitialCard;
    }


}
