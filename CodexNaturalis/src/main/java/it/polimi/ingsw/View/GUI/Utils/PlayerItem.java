package main.java.it.polimi.ingsw.View.GUI.Utils;

import main.java.it.polimi.ingsw.Model.Enumerations.Colour;
import main.java.it.polimi.ingsw.Model.Player.PlayerView;

public class PlayerItem {
    private final String nickName;
    private int points;
    private Colour playerColour;
    private int numAnimal;
    private int numInsects;
    private int numFungi;
    private int numPlants;
    private int numInkwell;
    private int numQuill;
    private int numManuscript;
    private boolean isOnline;
    public PlayerItem(String nickName){
        this.nickName = nickName;
        this.points = 0;
        this.numAnimal = 0;
        this.numInsects = 0;
        this.numFungi = 0;
        this.numPlants = 0;
        this.numInkwell = 0;
        this.numQuill = 0;
        this.numManuscript = 0;
        this.isOnline = true;
    }
    public void setStatus(boolean isOnline){
        this.isOnline = isOnline;
    }
    public void updatePlayer(PlayerView playerView){
        this.playerColour = playerView.getPlayerColour();
        this.points = playerView.getPoints();
        this.numAnimal = playerView.getPlayerScheme().getNumAnimal();
        this.numInsects = playerView.getPlayerScheme().getNumInsects();
        this.numFungi = playerView.getPlayerScheme().getNumFungi();
        this.numPlants = playerView.getPlayerScheme().getNumPlants();
        this.numInkwell = playerView.getPlayerScheme(). getNumInkwell();
        this.numManuscript = playerView.getPlayerScheme().getNumManuscript();
        this.numQuill = playerView.getPlayerScheme().getNumQuill();
    }
    public String getNickname(){
        return nickName;
    }
    @Override
    public String toString(){
        return this.getNickname();
    }

    public int getPoints() {
        return points;
    }

    public int getNumAnimal() {
        return numAnimal;
    }

    public int getNumInsects() {
        return numInsects;
    }

    public int getNumFungi() {
        return numFungi;
    }

    public int getNumPlants() {
        return numPlants;
    }

    public int getNumInkwell() {
        return numInkwell;
    }

    public int getNumQuill() {
        return numQuill;
    }

    public int getNumManuscript() {
        return numManuscript;
    }
}
