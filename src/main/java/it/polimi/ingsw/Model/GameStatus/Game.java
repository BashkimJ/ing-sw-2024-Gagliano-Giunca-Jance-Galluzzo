package it.polimi.ingsw.Model.GameStatus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.Exceptions.GameExc.PlayerNotFoundException;
import it.polimi.ingsw.Exceptions.GameExc.PlayersLimitExceededException;
import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Player.Player;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Gets all the cards from JSON files and stores them in Decks and Lists.
 */
public class Game implements Serializable {
    private int MAX_N_PLAYERS;
    private final int N_FACEUP_CARDS = 4;
    private List<Player> players;
    private List<ObjectiveCard> objectiveCards;
    private Deck resourceDeck;
    private Deck goldDeck;
    private Deck initialDeck;
    private List<ResourceCard> faceupCards;
    private List<ObjectiveCard> globalObj;

    /**
     * Construct a Game object.
     * @param player the player who creates the game
     * @param MAX_N_PLAYERS the maxium number of players chosen by the first player
     */
    public Game(Player player, int MAX_N_PLAYERS){
        this.MAX_N_PLAYERS = MAX_N_PLAYERS;
        players = new ArrayList<>();
        faceupCards = new ArrayList<ResourceCard>();
        globalObj = new ArrayList<>();
        try {
            this.addPlayer(player);
        }catch (PlayersLimitExceededException e){
            throw new RuntimeException(e);
        }
        this.objectiveCards =  initializeObjCards();
        try {
            this.resourceDeck = readResFile();
            this.goldDeck = readGoldFile();
            this.initialDeck = readInitFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public List<ObjectiveCard> getGlobalObj(){
        return this.globalObj;
    }
    public void setMAX_N_PLAYERS(int Players){
        this.MAX_N_PLAYERS = Players;
    }
    public int getMAX_N_PLAYERS(){
        return MAX_N_PLAYERS;
    }
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Adds a player to che Game.
     * @param player player to be added
     * @throws PlayersLimitExceededException when the maxium number of players is exceeded.
     */
    public void addPlayer(Player player) throws PlayersLimitExceededException {
        if(players.size() < MAX_N_PLAYERS)
            players.add(player);
        else {
            throw new PlayersLimitExceededException();
        }
    }

    public List<ResourceCard> getFaceupCards() {
        return faceupCards;
    }

    public void setFaceupCards(ArrayList<ResourceCard> faceupCards) {
        this.faceupCards = faceupCards;
    }

    public List<ObjectiveCard> getObjectiveCards() {
        return objectiveCards;
    }

    public Deck getResourceDeck() {
        return resourceDeck;
    }

    public Deck getGoldDeck() {
        return goldDeck;
    }

    public Deck getInitialDeck() {
        return initialDeck;
    }

    /**
     * Removes a player
     * @param player player to be removed
     * @throws PlayerNotFoundException when player is not found in the game
     */
    public void removePlayer(Player player) throws PlayerNotFoundException {
        boolean deleted = players.remove(player);
        if (!deleted){
            throw new PlayerNotFoundException();
        }

    }

    /**
     * Reads the ObjectiveCards from the corresponding JSON file
     * @return the list of ObjectiveCard
     */
    private List<ObjectiveCard>  initializeObjCards(){
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(MainObjective.class, new MainObjectiveDeserializer())
                .create();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("Cards_data/obj_cards_Gson.json");
        InputStreamReader reader = new InputStreamReader(inputStream);
        JsonReader jsonReader = new JsonReader(reader);
        Type listType = new TypeToken<List<ObjectiveCard>>(){}.getType();
        List<ObjectiveCard> listObj = gson.fromJson(jsonReader, listType);

        return listObj;
    }

    /**
     * Reads the ResourceCards from the corresponding JSON file.
     * @return a deck of ResourceCards
     * @throws IOException when cannot read the file
     */
    private Deck readResFile () throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .create();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("Cards_data/resource_cards_Gson.json");
        InputStreamReader reader = new InputStreamReader(inputStream);
        JsonReader jsonReader = new JsonReader(reader);
        Type listType = new TypeToken<List<ResourceCard>>(){}.getType();
        List<ResourceCard> listRes = gson.fromJson(jsonReader, listType);
        List<Card> listCards = new ArrayList<>(listRes);
        return new Deck(listCards);
    }
    /**
     * Reads the GoldCards from the corresponding JSON file.
     * @return a deck of GoldCards
     * @throws IOException when cannot read the file
     */
    private Deck readGoldFile () throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("Cards_data/gold_cards_Gson.json");
        InputStreamReader reader = new InputStreamReader(inputStream);
        JsonReader jsonReader = new JsonReader(reader);
        Type listType = new TypeToken<List<GoldCard>>(){}.getType();
        List<GoldCard> listGolds = gson.fromJson(jsonReader, listType);
        List<Card> listCards = new ArrayList<>(listGolds);
        return new Deck(listCards);
    }
    /**
     * Reads the InitialCards from the corresponding JSON file.
     * @return a deck of InitialCards
     * @throws IOException when cannot read the file
     */
    private Deck readInitFile () throws IOException {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("Cards_data/initial_cards_Gson.json");
        InputStreamReader reader = new InputStreamReader(inputStream);
        JsonReader jsonReader = new JsonReader(reader);
        Type listType = new TypeToken<List<InitialCard>>(){}.getType();
        List<InitialCard> listInits = gson.fromJson(jsonReader, listType);
        List<Card> listCards = new ArrayList<>(listInits);
        return new Deck(listCards);
    }


}
