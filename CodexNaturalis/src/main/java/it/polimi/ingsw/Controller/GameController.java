package main.java.it.polimi.ingsw.Controller;

import main.java.it.polimi.ingsw.Exceptions.GameExc.PlayerNotFoundException;
import main.java.it.polimi.ingsw.Exceptions.GameExc.PlayersLimitExceededException;
import main.java.it.polimi.ingsw.Model.Cards.ObjectiveCard;
import main.java.it.polimi.ingsw.Model.Enumerations.Colour;
import main.java.it.polimi.ingsw.Model.GameStatus.Game;
import main.java.it.polimi.ingsw.Model.Player.Player;
import main.java.it.polimi.ingsw.Network.Messages.*;
import main.java.it.polimi.ingsw.View.VirtualView;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static main.java.it.polimi.ingsw.Controller.GameState.In_Game;
import static main.java.it.polimi.ingsw.Controller.GameState.Lobby_State;

public class GameController {
    private GameState gameState;
    private Game game;
    private Map<String, VirtualView> view;
    private Map<String, List<ObjectiveCard>> objectives;
    public GameController(){
        this.gameState = Lobby_State;
        Player player = new Player("",null);
        objectives = new HashMap<>();
        game = new Game(player,1);
        game.getGoldDeck().shuffle();
        game.getInitialDeck().shuffle();
        game.getResourceDeck().shuffle();
        Collections.shuffle(game.getObjectiveCards());

        this.view = Collections.synchronizedMap(new HashMap<>());
        try {
            removePlayer("");
        } catch (PlayerNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //Receives messages from the server. In base of the state of the game and the message received takes action
    public void onMessageReceived(Message message) {
         switch (gameState) {
             case Lobby_State ->{
                 if(message.getType().equals(MessageType.Player_Num_Resp)){
                     game.setMAX_N_PLAYERS(((PlayerNumberResponse) message).getNumPlayers());
                 }
             }
             case In_Game -> {
                 if(message.getType().equals(MessageType.Choose_Obj_Res)){
                     updateObjCard(message);
                 }
             }
         }
    }

    public GameState getState(){
        return this.gameState;
    }

    public boolean checkNickName(String Name){
        Iterator<Player> iterator  = game.getPlayers().iterator();
        while(iterator.hasNext()){
            Player player = iterator.next();
            if(player.getNickName().equals(Name)){
                return false;
            }
        }
        return true;
    }

    private Colour getColor(){
        boolean present = false;
        for(Colour color: Colour.values()){
            for(Player player: game.getPlayers()){
                if(player.getPlayerColour()!=null && player.getPlayerColour().equals(color)){
                    present = true;
                    break;
                }
            }
            if(present==false){
                return color;
            }
        }
        return Colour.blue;
    }

    public void removePlayer(String NickName) throws PlayerNotFoundException {
        Iterator<Player> iterator = game.getPlayers().iterator();
        while(iterator.hasNext()){
            Player player = iterator.next();
            if(player.getNickName().equals(NickName)){
                iterator.remove();
                view.remove(NickName);
                return;
            }
        }
        throw new PlayerNotFoundException();
    }

    public void objectiveCardOptionsSender(){
            for (String name : new ArrayList<String>(view.keySet())) {
                ArrayList<ObjectiveCard> options = new ArrayList<>();
                options.add(game.getObjectiveCards().remove(game.getObjectiveCards().size() - 1));
                options.add(game.getObjectiveCards().remove(game.getObjectiveCards().size() - 1));
                objectives.put(name, options);
                view.get(name).chooseObjectiveCard(new ChooseObjReq(options));
            }


    }

    private void updateObjCard(Message message){
        String name = message.getNickName();
        System.out.println(name);
        int choice = ((ChooseObjResp)message).getChosen();
        Iterator iterator = game.getPlayers().iterator();
        while(iterator.hasNext()){
            Player player = (Player) iterator.next();
            if(player.getNickName().equals(name)){
                player.setPlayerObjective(objectives.get(name).remove(choice-1));
            }
        }
        view.get(name).errorMessage("To be continued.....");
    }


    public void firstLogin(String NickName,VirtualView view) throws PlayersLimitExceededException {
        if(game.getPlayers().size()==0){
            try {
                game.addPlayer(new Player(NickName, getColor()));
                try {
                    removePlayer("");
                }catch(PlayerNotFoundException e){}
            }catch(PlayersLimitExceededException e){}
            this.view.put(NickName,view);
            System.out.println(NickName + "'s view added" );
            view.askNumPlayers();
        }
        else if(game.getPlayers().size()<game.getMAX_N_PLAYERS()){
            this.view.put(NickName,view);
            System.out.println(NickName + "'s view added" );
            try {
                game.addPlayer(new Player(NickName, getColor()));
            }catch(PlayersLimitExceededException e){
            }
            if(game.getPlayers().size()==game.getMAX_N_PLAYERS()){
                gameState = In_Game;
            }

        }
        else {
            if(game.getMAX_N_PLAYERS()==1){
                view.errorMessage("You try entering the game before the main player chose the max num of players" +
                        "Please disconnect and connect again...");
            }
            else{
                view.errorMessage("All players are connected...");
            }
        }

    }

}
