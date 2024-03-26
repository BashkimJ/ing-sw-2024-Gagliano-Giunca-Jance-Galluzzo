package main.java.it.polimi.ingsw.Model.Cards;

import main.java.it.polimi.ingsw.Model.Enumerations.Resource;

import java.util.List;

public class ObjectiveWithResources implements MainObjective{
    private List<Resource> objectives;
    private int points;

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
    public List getObjectives(){
        return this.objectives;
    }

    /**
     *
     * @return The points of the objective.
     */
    public int getPoints(){
        return this.points;
    }
}
