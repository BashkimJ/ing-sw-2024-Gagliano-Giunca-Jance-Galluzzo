package main.java.it.polimi.ingsw.Cards;

import main.java.it.polimi.ingsw.Enumerations.Resource;

import java.util.List;

public class ResourceCard extends Card{
    private int points;
    private Resource resourceType;

    public ResourceCard(Side front,Side retro, int Id,Resource rsc,int points){
        super(front,retro,Id);
        this.points = points;
        this.resourceType = rsc;
    }

    public int getPoints(){
        return this.points;
    }
    public Resource getResourceType(){
        return this.resourceType;
    }
}
