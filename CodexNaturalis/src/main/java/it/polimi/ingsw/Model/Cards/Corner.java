package main.java.it.polimi.ingsw.Cards;
import main.java.it.polimi.ingsw.Enumerations.Items;
import main.java.it.polimi.ingsw.Enumerations.Resource;

public class Corner {
    private final boolean Visible;
    private boolean Free;
    private final Resource resourceType;
    private final Items itemstype;

    public Corner(boolean Visible,boolean Free, Resource myRsc, Items myItm){
        this.Visible = Visible;
        this.Free = Free;
        this.resourceType = myRsc;
        this.itemstype = myItm;
    }
    public boolean isVisible(){
        return this.Visible;
    }

    public boolean isFree(){
        return this.Free;
    }
    public void setFreeValue(boolean Free ){
        this.Free = Free;
    }
    public Resource getResource(){
        return this.resourceType;
    }
    public Items getItems(){
        return this.itemstype;
    }


}
