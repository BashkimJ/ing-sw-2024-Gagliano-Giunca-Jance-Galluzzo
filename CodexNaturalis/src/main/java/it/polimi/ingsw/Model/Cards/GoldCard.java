package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.Enumerations.Resource;
import java.util.List;

public class GoldCard extends ResourceCard{
    private final PointCondition Condition;
    private final List<Resource> necessaryRes;

    /**
     *  The constructor of the Gold Card.
     * @param front The front side of the card.
     * @param retro The retro side of the card.
     * @param Id The ID of the card.
     * @param rsc The type of the resource the card is.
     * @param points The points that the card can give when is played.
     * @param nessRes The list of the necessary resources needed for the card to be played.
     * @param Cond The condition in which the card gives the points to the player. Type PointCondition.
     */
    public GoldCard(Side front,Side retro,int Id, Resource rsc,int points,List<Resource> nessRes,PointCondition Cond){
        super(front,retro,Id,rsc,points);
        this.necessaryRes = nessRes;
        this.Condition = Cond;
    }

    /**
     *
     * @return The points condition
     */
    @Override
    public PointCondition getCondition(){
        return this.Condition;
    }

    /**
     *
     * @return The list of the necessary resources.
     */
    @Override
    public List<Resource> getNecessaryRes(){
        return this.necessaryRes;
    }

    @Override
    public String toString(){
        String myCard = " ";
        myCard = myCard + super.toString();
        myCard = myCard+ "\nNecessary resources to be played: ";
        for( Resource rsc: necessaryRes){
            myCard = myCard + rsc.name() + " ";
        }
        myCard = myCard + "\nPoint condition: " + Condition.toString() + "\n";
        return myCard;

    }
}
