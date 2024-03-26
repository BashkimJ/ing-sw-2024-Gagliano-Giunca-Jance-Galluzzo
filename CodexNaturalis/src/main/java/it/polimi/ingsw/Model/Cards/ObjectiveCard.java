package main.java.it.polimi.ingsw.Cards;

import main.java.it.polimi.ingsw.Enumerations.Pattern;

public class ObjectiveCard {
    private MainObjective objective;
     public ObjectiveCard(MainObjective obj){
         this.objective = obj;
     }

     public MainObjective getObjective(){
         return this.objective;
     }

}
