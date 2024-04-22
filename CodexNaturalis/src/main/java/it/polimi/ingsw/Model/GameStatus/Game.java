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


import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
public class Game {
    private static final String resourceCardPath ="./CodexNaturalis/src/main/java/it/polimi/ingsw/" +
            "Model/Cards_data/resource_cards_Gson.json";
    private static final String goldCardPath ="./CodexNaturalis/src/main/java/it/polimi/ingsw/" +
            "Model/Cards_data/gold_cards_Gson.json";
    private static final String initialCardPath = "./CodexNaturalis/src/main/java/it/polimi/ingsw/" +
            "Model/Cards_data/initial_cards_Gson.json";

    private final int MAX_N_PLAYERS;
    private List<Player> players;
    private List<ObjectiveCard> objectiveCards;
    private Deck resourceDeck;
    private Deck goldDeck;
    private Deck initialDeck;

    //player who starts the game and number of players chosen
    public Game(Player player, int MAX_N_PLAYERS){
        this.MAX_N_PLAYERS = MAX_N_PLAYERS;
        players = new ArrayList<>();
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
        List<ObjectiveCard> objList = new ArrayList<>();
        //Card 87
        MainObjective mainObj = new ObjectiveWithPattern(Pattern.downUpLR_RED, 2);
        objList.add(new ObjectiveCard(mainObj));
        //Card 88
        mainObj = new ObjectiveWithPattern(Pattern.upDownLR_GREEN, 2);
        objList.add(new ObjectiveCard(mainObj));
        //Card 89
        mainObj = new ObjectiveWithPattern(Pattern.upDownLR_BLUE, 2);
        objList.add(new ObjectiveCard(mainObj));
        //Card 90
        mainObj = new ObjectiveWithPattern(Pattern.downUpLR_PURPLE, 2);
        objList.add(new ObjectiveCard(mainObj));
        //Card 91
        mainObj = new ObjectiveWithPattern(Pattern.LdownRight, 3);
        objList.add(new ObjectiveCard(mainObj));
        //Card 92
        mainObj = new ObjectiveWithPattern(Pattern.LdownLeft, 3);
        objList.add(new ObjectiveCard(mainObj));
        //Card 93
        mainObj = new ObjectiveWithPattern(Pattern.LupRight, 3);
        objList.add(new ObjectiveCard(mainObj));
        //Card 94
        mainObj = new ObjectiveWithPattern(Pattern.LupLeft, 3);
        objList.add(new ObjectiveCard(mainObj));
        //Card 95
        List<Resource> resList = new ArrayList<Resource>();
        resList.add(Resource.Fungi);
        resList.add(Resource.Fungi);
        resList.add(Resource.Fungi);
        mainObj = new ObjectiveWithResources(resList, 2);
        objList.add(new ObjectiveCard(mainObj));
        //Card 96
        resList = new ArrayList<Resource>();
        resList.add(Resource.Plant);
        resList.add(Resource.Plant);
        resList.add(Resource.Plant);
        mainObj = new ObjectiveWithResources(resList, 2);
        objList.add(new ObjectiveCard(mainObj));
        //Card 97
        resList = new ArrayList<Resource>();
        resList.add(Resource.Animal);
        resList.add(Resource.Animal);
        resList.add(Resource.Animal);
        mainObj = new ObjectiveWithResources(resList, 2);
        objList.add(new ObjectiveCard(mainObj));
        //Card 98
        resList = new ArrayList<Resource>();
        resList.add(Resource.Insects);
        resList.add(Resource.Insects);
        resList.add(Resource.Insects);
        mainObj = new ObjectiveWithResources(resList, 2);
        objList.add(new ObjectiveCard(mainObj));
        //Card 99
        List<Items> itemsList = new ArrayList<>();
        itemsList.add(Items.Quill);
        itemsList.add(Items.Inkwell);
        itemsList.add(Items.Manuscript);
        mainObj = new ObjectiveWithItems(itemsList, 3);
        objList.add(new ObjectiveCard(mainObj));
        //Card 100
        itemsList = new ArrayList<>();
        itemsList.add(Items.Manuscript);
        itemsList.add(Items.Manuscript);
        mainObj = new ObjectiveWithItems(itemsList, 2);
        objList.add(new ObjectiveCard(mainObj));
        //Card 101
        itemsList = new ArrayList<>();
        itemsList.add(Items.Inkwell);
        itemsList.add(Items.Inkwell);
        mainObj = new ObjectiveWithItems(itemsList, 2);
        objList.add(new ObjectiveCard(mainObj));
        //Card 102
        itemsList = new ArrayList<>();
        itemsList.add(Items.Quill);
        itemsList.add(Items.Quill);
        mainObj = new ObjectiveWithItems(itemsList, 2);
        objList.add(new ObjectiveCard(mainObj));

        return objList;
    }
    private Deck readResFile () throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .create();
        JsonReader jsonReader = new JsonReader(new FileReader(resourceCardPath));
        Type listType = new TypeToken<List<ResourceCard>>(){}.getType();
        List<ResourceCard> listRes = gson.fromJson(jsonReader, listType);
        List<Card> listCards = new ArrayList<>(listRes);
        return new Deck(listCards);
    }
    private Deck readGoldFile () throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonReader jsonReader = new JsonReader(new FileReader(goldCardPath));
        Type listType = new TypeToken<List<GoldCard>>(){}.getType();
        List<GoldCard> listGolds = gson.fromJson(jsonReader, listType);
        List<Card> listCards = new ArrayList<>(listGolds);
        return new Deck(listCards);
    }
    private Deck readInitFile () throws IOException {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonReader jsonReader = new JsonReader(new FileReader(initialCardPath));
        Type listType = new TypeToken<List<InitialCard>>(){}.getType();
        List<InitialCard> listInits = gson.fromJson(jsonReader, listType);
        List<Card> listCards = new ArrayList<>(listInits);
        return new Deck(listCards);
    }


}
