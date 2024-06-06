package main.java.it.polimi.ingsw.Model.Save;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import main.java.it.polimi.ingsw.Controller.GameState;
import main.java.it.polimi.ingsw.Model.Cards.*;
import main.java.it.polimi.ingsw.Model.GameStatus.*;

import java.io.*;
import java.lang.reflect.Array;
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
     * @param gs         The Enum GameState.
     * @param g          The Game itself with the PlayerList, CardSchemes and Decks.
     * @param obj        The objective cards.
     * @param offPlayers The list of all online and offline players which in the current state are all deemed as offline.
     * @param turn       The last player required to take action.
     * @param numPlayers The number of players playing the game.
     */
    public void SaveGame(GameState gs, Game g, Map<String, List<ObjectiveCard>> obj, Map<String,Integer> offPlayers, String turn,int numPlayers){
        Save s = new Save(gs,g,obj,offPlayers,turn,numPlayers);
        WriteSave(s);
    }

    /**
     * Writes to file the data gathered from the Save class taken as input.
     * @param s The Save class with all the information about the current Game.
     */
    private void WriteSave(Save s){
        try{
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("CodexNaturalis/src/main/java/it/polimi/ingsw/resources/Games_data/saved_game.ser"));
            out.writeObject(s);
        } catch (IOException e) {
            System.out.println("Couldn't save");
        }
    }
    /**
     * Reads from file to gather the last saved Game's data.
     * @return the Save class containing all the data from the last Game.
     */
    private Save ReadSave() {

        Save s = null;
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("CodexNaturalis/src/main/java/it/polimi/ingsw/resources/Games_data/saved_game.ser"))){
            s = (Save) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Couldn't read");;
        }
        return s;
    }
}