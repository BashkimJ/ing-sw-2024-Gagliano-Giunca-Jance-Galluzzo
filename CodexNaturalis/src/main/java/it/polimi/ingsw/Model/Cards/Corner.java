package main.java.it.polimi.ingsw.Model.Cards;
import main.java.it.polimi.ingsw.Model.Enumerations.Items;
import main.java.it.polimi.ingsw.Model.Enumerations.Resource;

import java.io.Serializable;

public class Corner implements Serializable {
    private final boolean Visible;
    private final boolean Free;
    private final Resource resourceType;
    private final Items itemstype;

    /**
     * The constructor of the class Corner.
     * @param Visible True  if the corner is visible,false otherwise.
     * @param Free True if the corner is free, otherwise if the corner is taken or the corner is not visible false.
     * @param myRsc The resource contained in the corner. Null if no resource present or if there is an item present.
     * @param myItm The item contained in the corner. Null if no item present or if there is a resource on the corner
     */
    public Corner(boolean Visible,boolean Free, Resource myRsc, Items myItm){
        this.Visible = Visible;
        this.Free = Free;
        this.resourceType = myRsc;
        this.itemstype = myItm;

    }

    /**
     *
     * @return The visibility value. True if the corner is visible, false otherwise.
     */
    public boolean isVisible(){
        return this.Visible;
    }

    /**
     *
     * @return True if the corner is free. False on the contrary.
     */
    public boolean isFree(){
        return this.Free;
    }


    /**
     *
     * @return The resource contained in the corner.
     */
    public Resource getResource(){
        return this.resourceType;
    }

    /**
     *
     * @return The item contained in the corner.
     */
    public Items getItems(){
        return this.itemstype;
    }

    @Override
    public String toString(){
         if(Visible){
             if(isFree()){
                 if(itemstype!=null){
                     return itemstype.name();
                 }
                 else if(resourceType!=null){
                     return resourceType.name();
                 }
                 else{
                     return "empty";
                 }
             }
         }
         else{
             return "not visible";
         }
         return " ";
    }


}
