package main.java.it.polimi.ingsw.Network.Messages;

import main.java.it.polimi.ingsw.Model.Cards.MainObjective;
import main.java.it.polimi.ingsw.Model.Cards.ObjectiveCard;
import main.java.it.polimi.ingsw.Model.Cards.ObjectiveWithResources;
import main.java.it.polimi.ingsw.Model.Enumerations.Resource;

import java.util.ArrayList;
import java.util.List;

public class ChooseObjReq extends Message{
    private List<ObjectiveCard> options;

    public ChooseObjReq(List<ObjectiveCard> opt){
        super("Server", MessageType.Choose_Obj_Req);
        this.options = new ArrayList<ObjectiveCard>();
        options.addAll(opt);
    }


    @Override
    public String toString(){
        String message;
        message = "Card 1: " + options.get(0).getObjective().toString();
        message = message  + "\nCard 2: " +options.get(1).getObjective().toString();
        return message;
    }
}
