package main.java.it.polimi.ingsw.Cards;

import main.java.it.polimi.ingsw.Enumerations.Items;

public class PointCondition {
    private boolean CornerCondition;
    private Items ItemType;

    public PointCondition(boolean CornerCondition,Items ItemType){
        this.CornerCondition = CornerCondition;
        this.ItemType = ItemType;
    }
    public boolean getCornerCondition(){
        return this.CornerCondition;
    }
    public Items getItemType(){
        return this.ItemType;
    }


}
