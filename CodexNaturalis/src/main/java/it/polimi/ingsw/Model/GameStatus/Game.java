package main.java.it.polimi.ingsw.Model.GameStatus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import main.java.it.polimi.ingsw.Exceptions.GameExc.PlayerNotFoundException;
import main.java.it.polimi.ingsw.Exceptions.GameExc.PlayersLimitExceededException;
import main.java.it.polimi.ingsw.Model.Cards.*;
import main.java.it.polimi.ingsw.Model.Enumerations.Items;
import main.java.it.polimi.ingsw.Model.Enumerations.Pattern;
import main.java.it.polimi.ingsw.Model.Enumerations.Resource;
import main.java.it.polimi.ingsw.Model.Player.Player;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
public class Game {
    private int MAX_N_PLAYERS;
    private final int N_FACEUP_CARDS = 4;
    private List<Player> players;
    private List<ObjectiveCard> objectiveCards;
    private Deck resourceDeck;
    private Deck goldDeck;
    private Deck initialDeck;
    private List<ResourceCard> faceupCards;
    private List<ObjectiveCard> globalObj;

    //player who starts the game and number of players chosen
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
    public void removePlayer(Player player) throws PlayerNotFoundException {
        boolean deleted = players.remove(player);
        if (!deleted){
            throw new PlayerNotFoundException();
        }

    }

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
