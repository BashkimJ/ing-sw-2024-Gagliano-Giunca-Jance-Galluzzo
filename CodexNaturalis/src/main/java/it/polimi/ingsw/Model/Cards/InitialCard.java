package main.java.it.polimi.ingsw.Model.Cards;
import main.java.it.polimi.ingsw.Model.Enumerations.Resource;


import java.util.List;

public class InitialCard extends Card{
    private final List<Resource> middleResource;

    /**
     * The constructor of the card.
     * @param front The front side of the card.
     * @param retro The retro side of the card.
     * @param Id The card ID, which is an integer.
     * @param rsc The resources contained in the front of the card. Type List<Resource>.
     */
    public InitialCard(Side front,Side retro, int Id, List<Resource> rsc){
        super(front,retro,Id);
        middleResource = rsc;

    }

    /**
     *
     * @return The list of the resources contained int the card.
     */
    public List<Resource> getMiddleResource(){
        return this.middleResource;
    }


    @Override
    public String toString(){
        String myCard = "***********ID: " + getCardId() + " ****************\n";
        myCard = myCard + "Fronte: " + getFront().toString() +  "\n";
        myCard  = myCard + "Retro: " + getRetro().toString() + "\nResources on the front: ";
        for( Resource rsc: middleResource){
           myCard = myCard + rsc.name() + " ";
        }
        return myCard;

    }
}
