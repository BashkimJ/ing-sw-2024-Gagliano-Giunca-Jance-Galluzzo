package main.java.it.polimi.ingsw.Network.Messages;

import main.java.it.polimi.ingsw.Model.Player.Player;
import main.java.it.polimi.ingsw.Model.Player.PlayerView;

public class ShowPlayerInfo extends Message{
    private PlayerView player = null;
    private String showPlayer;
    public ShowPlayerInfo(String name,Player player,String toShow) {
        super(name, MessageType.Player_Info);
        if(player!=null){
            this.player = new PlayerView (player);
        }
        this.showPlayer = toShow;
    }

    public PlayerView getPlayer() {
        return player;
    }

    public String getToShow(){
        return this.showPlayer;
    }
}
