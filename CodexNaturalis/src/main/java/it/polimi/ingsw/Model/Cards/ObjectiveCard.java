package main.java.it.polimi.ingsw.Model.Cards;

import java.io.Serializable;

public class ObjectiveCard implements Serializable {
    private MainObjective objective;

    /**
     * The constructor of the objective card.
     * @param obj The objective of the card.
     */
     public ObjectiveCard(MainObjective obj){
         this.objective = obj;
     }

     public MainObjective getObjective(){
         return this.objective;
     }

}
