package it.polimi.ingsw.Network.Messages;

import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Player.PlayerView;

/**
 * This class represents a response from the server in case of a successful placement of a card.Used also for a successful picking.
 */
public class PlayerMoveResp extends Message{
    private final PlayerView updatedPlayer;
    private final String moveType;

    /**
     * Constructs the PlayerMoveResp message.
     * @param player The updated player.
     * @param type The type of the move to be responded.
     */
    public PlayerMoveResp(Player player,String type) {
        super("Server", MessageType.Player_Move_Resp);
        if(player!=null) {
            updatedPlayer = new PlayerView(player);
        }
        else
            updatedPlayer = null;
        moveType =  type;
    }

    /**
     * Retrieves the updated player.
     * @return The player.
     */
    public PlayerView getUpdatedPlayer() {
        return updatedPlayer;
    }

    /**
     * @return The move type as a string.
     */
    public String getMoveType(){
    return this.moveType;
    }
}
