package main.java.it.polimi.ingsw.Network.Messages;

import main.java.it.polimi.ingsw.Model.Cards.MainObjective;
import main.java.it.polimi.ingsw.Model.Cards.ObjectiveCard;
import main.java.it.polimi.ingsw.Model.Cards.ObjectiveWithResources;
import main.java.it.polimi.ingsw.Model.Enumerations.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * The ChooseObjReq represents a message that containing the options of the objective card.
 */
public class ChooseObjReq extends Message{
    private List<ObjectiveCard> options;

    /**
     * Initialises the message.
     * @param opt The list of the ObjectiveCard to be sent.
     */
    public ChooseObjReq(List<ObjectiveCard> opt){
        super("Server", MessageType.Choose_Obj_Req);
        this.options = new ArrayList<ObjectiveCard>();
        options.addAll(opt);
    }

    /**
     * Prints the in the command line the characteristics of each card.
     * @return The string to be shown in the command line.
     */
    @Override
    public String toString(){
        String message;
        message = "Card 1: " + options.get(0).getObjective().toString();
        message = message  + "\nCard 2: " +options.get(1).getObjective().toString();
        return message;
    }

    /**
     * Retreives the list of the ObjectiveCards.
     * @return The list of the objective cards.
     */
    public List<ObjectiveCard> getOptions() {
        return options;
    }
}
