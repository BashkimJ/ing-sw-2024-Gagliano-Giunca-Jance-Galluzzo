package main.java.it.polimi.ingsw.Model.Cards;

import main.java.it.polimi.ingsw.Model.Enumerations.Resource;

import java.util.List;


public  class ResourceCard extends Card {
    private final int points;
    private final Resource resourceType;
    /**
     * The resource card's constructor.
     *
     * @param front  The front side of the resource card
     * @param retro  The retro side of the resource card.
     * @param Id     The resource card ID.
     * @param rsc    The type of the resource the card is.
     * @param points The points that the card can give to a player when it is played.
     */
    public ResourceCard(Side front, Side retro, int Id, Resource rsc, int points) {
        super(front, retro, Id);
        this.points = points;
        this.resourceType = rsc;
    }

    /**
     * @return The points of the card.
     */
    public int getPoints() {
        return this.points;
    }

    /**
     * @return The type of the resource the card is.
     */
    public Resource getResourceType() {
        return this.resourceType;
    }
    public PointCondition getCondition(){
        return null;
    }
    public List<Resource> getNecessaryRes(){
        return null;
    }

    @Override
    public String toString(){
        String myCard = "**********ID: " + getCardId()+"*****************\n";
        myCard = myCard + "Fronte: " + getFront().toString() +  "\n";
        myCard  = myCard + "Retro: " + getRetro().toString() + "\nResource: " + resourceType.name() + "\nPoints: " + getPoints();
        return myCard;

    }
}
