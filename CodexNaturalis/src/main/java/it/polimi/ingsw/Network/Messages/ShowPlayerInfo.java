package it.polimi.ingsw.Network.Messages;

import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Player.PlayerView;

/**
 * This class is used as a way to ask for the info of the player. It is also used as a response form the server.
 */
public class ShowPlayerInfo extends Message{
    private PlayerView player = null;
    private String showPlayer;

    /**
     * Constructs the ShowPlayerInfo message
     * @param name The name of the player asking for info.
     * @param player The player to be shown.
     * @param toShow The name of the player asked to be shown.
     */
    public ShowPlayerInfo(String name,Player player,String toShow) {
        super(name, MessageType.Player_Info);
        if(player!=null){
            this.player = new PlayerView (player);
        }
        this.showPlayer = toShow;
    }

    /**
     *
     * @return The object PlayerView to be shown.
     */
    public PlayerView getPlayer() {
        return player;
    }

    /**
     *
     * @return The name of the player to be shown.
     */
    public String getToShow(){
        return this.showPlayer;
    }
}
