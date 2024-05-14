package main.java.it.polimi.ingsw.Network.Messages;

/**
 * This class represents a ChooseObjResp, which is a response to the ChooseObjReq message.
 */
public class ChooseObjResp extends Message{
    private int chosen;

    /**
     * Initialises the message
     * @param NickName The name of the player choosing the objective card.
     * @param choice The order of the card in the sent list of ObjectiveCards options.
     */
    public ChooseObjResp(String NickName, int choice){
        super(NickName,MessageType.Choose_Obj_Res);
        this.chosen = choice;
    }

    /**
     * Retrieves the position of the chosen card.
     * @return The position as an integer.
     */
    public int getChosen(){
        return this.chosen;
    }
}
