package it.polimi.ingsw.Model.Cards;

import com.google.gson.annotations.SerializedName;
import it.polimi.ingsw.Model.Enumerations.Resource;

import java.util.List;

public class ObjectiveWithResources implements MainObjective{
    @SerializedName("objectivesRESOURCES")
    private final List<Resource> objectives;
    private final int points;

    /**
     * The constructor of the class
     * @param rsc The list of the resources tha must be present in the game to complete the objective.
     * @param points The points of the objective.
     */
    public ObjectiveWithResources(List<Resource> rsc, int points){
        this.objectives = rsc;
        this.points = points;
    }

    /**
     *
     * @return The list of the objective resources.
     */
    public List<Resource> getObjectives(){
        return this.objectives;
    }

    /**
     *
     * @return The points of the objective.
     */
    public int getPoints(){
        return this.points;
    }
    @Override
    public String toString(){
        String Info="";
        for(Resource rsc: objectives){
            Info  = Info  +  " " + rsc.name();
        }
        Info=Info + "points: " + points;
        return Info;

    }
}
