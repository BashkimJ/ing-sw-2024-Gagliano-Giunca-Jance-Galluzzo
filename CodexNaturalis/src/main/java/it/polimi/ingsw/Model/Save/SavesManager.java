package main.java.it.polimi.ingsw.Model.Save;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import main.java.it.polimi.ingsw.Controller.GameState;
import main.java.it.polimi.ingsw.Model.Cards.Card;
import main.java.it.polimi.ingsw.Model.Cards.ObjectiveCard;
import main.java.it.polimi.ingsw.Model.Cards.ResourceCard;
import main.java.it.polimi.ingsw.Model.GameStatus.Deck;
import main.java.it.polimi.ingsw.Model.GameStatus.Game;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class SavesManager
{
    public SavesManager(){
    }

    /**
     * @return the Save class containing al necessary data to restore a saved game from file.
     */
    public Save LoadGame(){
        return ReadSave();
    }

    /**
     * Takes as input the necessary data to fill a Save class to be stored to file.
     * @param gs The Enum GameState.
     * @param g The Game itself with the PlayerList, CardSchemes and Decks.
     * @param obj The objective cards.
     * @param offPlayers The list of all online and offline players which in the current state are all deemed as offline.
     * @param turn The last player required to take action.
     */
    public void SaveGame(GameState gs, Game g, Map<String, List<ObjectiveCard>> obj, Map<String,Integer> offPlayers, String turn){
        Save s = new Save(gs,g,obj,offPlayers,turn);
        WriteSave(s);
    }

    /**
     * Writes to file the data gathered from the Save class taken as input.
     * @param s The Save class with all the information about the current Game.
     * @throws IOException if can not write to file.
     */
    private void WriteSave(Save s){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fw = new FileWriter("saved_game.json");
            gson.toJson(s, fw);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Reads from file to gather the last saved Game's data.
     * @return the Save class containing all the data from the last Game.
     * @throws IOException for any issues with file read.
     */
    private Save ReadSave() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Save s;
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("Games_data/saved_game.json");
        InputStreamReader reader = new InputStreamReader(inputStream);
        JsonReader jsonReader = new JsonReader(reader);

        s = gson.fromJson(jsonReader, Save.class);
        return s;
    }
}