package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.Enumerations.Items;

import java.io.Serializable;

public class PointCondition implements Serializable {
    private final boolean CornerCondition;
    private final Items ItemType;

    /**
     * The constructor of the Class
     * @param CornerCondition It is a boolean. If true then it means that the card will give you: number of corners occupied*points of the card.
     * @param ItemType The item needed in the game to get the points. The player gets points*nr of items found=total_points. If null it means CornerCondition=True.
     */
    public PointCondition(boolean CornerCondition,Items ItemType){
        this.CornerCondition = CornerCondition;
        this.ItemType = ItemType;
    }

    /**
     *
     * @return True if there is a corner condition. False otherwise.
     */
    public boolean getCornerCondition(){
        return this.CornerCondition;
    }

    /**
     *
     * @return The item type if present.Else null.
     */
    public Items getItemType(){
        return this.ItemType;
    }

    @Override
    public String toString(){
        String myCondition = "";
        if(!CornerCondition && ItemType!=null){
            myCondition = myCondition + ItemType.name() + " ";
        }
        else if(CornerCondition && ItemType==null){
            myCondition = myCondition + " Corner covering point condition ";
        }
        else{
            myCondition = myCondition + " No condition ";
        }
        return myCondition;
    }


}
