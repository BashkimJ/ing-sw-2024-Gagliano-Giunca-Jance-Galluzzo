package main.java.it.polimi.ingsw.Model.Cards;

import com.google.gson.annotations.SerializedName;
import main.java.it.polimi.ingsw.Model.Enumerations.Items;

import java.util.List;

public class ObjectiveWithItems implements MainObjective{
    @SerializedName("objectivesITEMS")
    private List<Items> objectives;
    private final int points;

    /**
     * The constructor of the ObjectiveWithItems
     * @param items The list of the items that should appear in the game to complete the objective.
     * @param points The points of the objective.
     */
    public ObjectiveWithItems(List<Items> items, int points)
    {
        this.points = points;
        this.objectives = items;
    }

    /**
     *
     * @return The list of the items of the objective card.
     */
    public List<Items> getObjectives(){
        return this.objectives;
    }

    /**
     *
     * @return The points of the card.
     */
    public int getPoints(){
        return this.points;
    }
    @Override
    public String toString(){
        String Info="";
        for(Items tems: objectives){
            Info  = Info + " " +tems.name();
        }
        Info=Info + " points: " + points;
        return Info;

    }
}
