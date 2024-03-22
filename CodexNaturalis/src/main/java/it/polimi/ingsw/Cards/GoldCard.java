package main.java.it.polimi.ingsw.Cards;

import main.java.it.polimi.ingsw.Enumerations.Resource;
import java.util.List;

public class GoldCard extends ResourceCard{
    private PointCondition Condition;
    private List<Resource> neccesaryRes;

    public GoldCard(Side front,Side retro,int Id, Resource rsc,int points,List<Resource> nessRes,PointCondition Cond){
        super(front,retro,Id,rsc,points);
        this.neccesaryRes = nessRes;
        this.Condition = Cond;
    }
    public PointCondition getCondition(){
        return this.Condition;
    }
    public List<Resource> getNeccesaryRes(){
        return this.neccesaryRes;
    }
}
