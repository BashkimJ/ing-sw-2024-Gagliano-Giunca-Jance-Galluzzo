package main.java.it.polimi.ingsw.Model.Player;

import main.java.it.polimi.ingsw.Model.Cards.GoldCard;
import main.java.it.polimi.ingsw.Model.Cards.InitialCard;
import main.java.it.polimi.ingsw.Model.Cards.ObjectiveCard;
import main.java.it.polimi.ingsw.Model.Cards.ResourceCard;
import main.java.it.polimi.ingsw.Model.Enumerations.Colour;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.List;

public class PlayerView implements Serializable {
    private final String nickName;
    private final int points;
    private final Colour playerColour;
    private final BufferedImage tokenImage;

    private final  InitialCard playerInitial;
    private final ObjectiveCard playerObjective;
    private final List<ResourceCard> playerHand;
    private final CardSchemeView playerScheme;

    public PlayerView(Player player) {
         this.nickName = player.getNickName();
         this.playerColour = player.getPlayerColour();
         this.points = player.getPoints();
         this.tokenImage = player.getTokenImage();
         this.playerInitial = player.getPlayerInitial();
         this.playerObjective = player.getPlayerObjective();
         this.playerHand = player.getPlayerHand();
         this.playerScheme  =new CardSchemeView(player.getPlayerScheme());
    }


    @Override
    public String toString(){
        String Player = "";
        Player = Player +"Nickname: " +  nickName + "\n";
        Player = Player + "Color: " + playerColour.name() + "\n";
        Player = Player + "Point: " + points + "\n";
        Player  = Player + playerScheme.toString();
        Player = Player + "\nHand: \n";
        for(ResourceCard rsc: playerHand){
            if(rsc.getNecessaryRes()==null && rsc.getCondition()==null){
                Player = Player + "Resource Card: " + rsc.toString() + "\n";
            }
            else{
                Player = Player + "GoldCard: " + ((GoldCard)rsc).toString()+"\n";
            }
        }
        return Player;

    }
}
