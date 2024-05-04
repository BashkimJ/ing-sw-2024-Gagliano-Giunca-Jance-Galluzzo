package main.java.it.polimi.ingsw.Model.Save;
import main.java.it.polimi.ingsw.Model.GameStatus.Deck;
import main.java.it.polimi.ingsw.Model.GameStatus.Game;
import main.java.it.polimi.ingsw.Model.Player.Player;

import java.util.*;

public class SavesManager
{
    private List<Save> savedGames;  //All saved Games


    public static List<Save> getSavedGames() {
        return this.savedGames;
    }



}
