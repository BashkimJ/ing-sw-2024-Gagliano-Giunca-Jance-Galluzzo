package main.java.it.polimi.ingsw.Network.Messages;

import main.java.it.polimi.ingsw.Model.Cards.InitialCard;
import main.java.it.polimi.ingsw.Model.Cards.Side;
import main.java.it.polimi.ingsw.Model.Enumerations.Resource;

/**
 * This class that also extends Message is used by the server to show a player his initial card.
 */
public class InitialCardMess extends Message{
    private InitialCard Card;

    /**
     * Initialises the message.
     * @param card The card to be shown.
     */
    public InitialCardMess(InitialCard card){
        super("Server",MessageType.Initial_Card_Mess);
        this.Card = card;

    }

    /**
     * Retrieves the initial card.
     * @return The initial card of the player.
     */
    public InitialCard getCard() {
        return Card;
    }

    /**
     * Prints the card for the player.
     * @return The string to be printed.
     */
    @Override
    public String toString(){
        return Card.toString();
    }


}
