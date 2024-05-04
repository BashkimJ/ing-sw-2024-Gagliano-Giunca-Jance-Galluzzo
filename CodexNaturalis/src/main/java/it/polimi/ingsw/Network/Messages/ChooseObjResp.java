package main.java.it.polimi.ingsw.Network.Messages;

public class ChooseObjResp extends Message{
    private int chosen;
    public ChooseObjResp(String NickName, int choice){
        super(NickName,MessageType.Choose_Obj_Res);
        this.chosen = choice;
    }

    public int getChosen(){
        return this.chosen;
    }
}
