package main.java.it.polimi.ingsw.Cards;
import main.java.it.polimi.ingsw.Enumerations.Resource;

import java.util.List;

public class InitialCard extends Card {
    private List<Resource> middleResource;

    public InitialCard(Side front,Side retro, int Id, List<Resource> rsc){
        super(front,retro,Id);
        middleResource = rsc;

    }

    public List<Resource> getMiddleResource(){
        return this.middleResource;
    }
}
