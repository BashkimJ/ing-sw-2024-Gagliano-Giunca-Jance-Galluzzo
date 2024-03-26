package main.java.it.polimi.ingsw.Cards;

import main.java.it.polimi.ingsw.Enumerations.Resource;

import java.util.List;

public class ObjectiveWithResources implements MainObjective{
    private List<Resource> objectives;
    private int points;
    public ObjectiveWithResources(List<Resource> rsc, int points){
        this.objectives = rsc;
        this.points = points;
    }
    public List getObjectives(){
        return this.objectives;
    }
    public int getPoints(){
        return this.points;
    }
}
