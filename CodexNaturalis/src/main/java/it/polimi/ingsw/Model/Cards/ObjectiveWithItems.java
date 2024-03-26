package main.java.it.polimi.ingsw.Cards;

import main.java.it.polimi.ingsw.Enumerations.Items;

import java.util.List;

public class ObjectiveWithItems implements MainObjective{
    private List<Items> objectives;
    int points;

    public ObjectiveWithItems(List<Items> items, int points)
    {
        this.points = points;
        this.objectives = items;
    }
    public List<Items> getObjectives(){
        return this.objectives;
    }
    public int getPoints(){
        return this.points;
    }
}
