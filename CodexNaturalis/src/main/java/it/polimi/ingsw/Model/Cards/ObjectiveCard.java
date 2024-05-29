package main.java.it.polimi.ingsw.Model.Cards;

import java.io.Serializable;

public class ObjectiveCard implements Serializable {
    private final MainObjective objective;
    private final int cardId;

    /**
     * The constructor of the objective card.
     * @param obj The objective of the card.
     */
    public ObjectiveCard(MainObjective obj, int cardId){
        this.objective = obj;
        this.cardId = cardId;
    }

    public MainObjective getObjective(){
        return this.objective;
    }
    public int getCardId() {
        return this.cardId;
    }

    @Override
    public String toString(){
        return objective.toString();
    }

}
