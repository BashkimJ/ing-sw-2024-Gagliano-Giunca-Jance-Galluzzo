package main.java.it.polimi.ingsw.Controller;
import main.java.it.polimi.ingsw.Exceptions.GameExc.PlayersLimitExceededException;
import main.java.it.polimi.ingsw.Exceptions.SchemeCardExc.GoldCardPlacementException;
import main.java.it.polimi.ingsw.Exceptions.SchemeCardExc.InvalidPositionException;
import main.java.it.polimi.ingsw.Exceptions.SchemeCardExc.InvalidSideException;
import main.java.it.polimi.ingsw.Exceptions.SchemeCardExc.OutOfBoundsException;
import main.java.it.polimi.ingsw.Model.Cards.GoldCard;
import main.java.it.polimi.ingsw.Model.Cards.InitialCard;
import main.java.it.polimi.ingsw.Model.Cards.ObjectiveCard;
import main.java.it.polimi.ingsw.Model.Cards.ResourceCard;
import main.java.it.polimi.ingsw.Model.Enumerations.Colour;
import main.java.it.polimi.ingsw.Model.GameStatus.Game;
import main.java.it.polimi.ingsw.Model.Player.Player;
import main.java.it.polimi.ingsw.Network.Messages.*;
import main.java.it.polimi.ingsw.View.VirtualView;

import java.util.*;


import static main.java.it.polimi.ingsw.Controller.GameState.*;

public class GameController {
    private GameState gameState;
    private Game game;
    private Map<String, VirtualView> view;
    private Map<String, List<ObjectiveCard>> objectives;
    private List<String> onlinePlayers;
    private int chosenObjInit;
    private String playerTurn;

    public GameController(){
        chosenObjInit = 0;
        this.gameState = Lobby_State;
        Player player = new Player("",null);
        objectives = new HashMap<>();
        game = new Game(player,1);
        game.getGoldDeck().shuffle();
        game.getInitialDeck().shuffle();
        game.getResourceDeck().shuffle();
        game.getFaceupCards().add((ResourceCard)game.getResourceDeck().pickCard());
        game.getFaceupCards().add((ResourceCard)game.getResourceDeck().pickCard());
        game.getFaceupCards().add((GoldCard)game.getGoldDeck().pickCard());
        game.getFaceupCards().add((GoldCard)game.getGoldDeck().pickCard());
        Collections.shuffle(game.getObjectiveCards());
        game.getGlobalObj().add(game.getObjectiveCards().remove(game.getObjectiveCards().size()-1));
        game.getGlobalObj().add(game.getObjectiveCards().remove(game.getObjectiveCards().size()-1));
        onlinePlayers = new ArrayList<>();
        this.view = Collections.synchronizedMap(new HashMap<>());
        removePlayer("");
    }

    //Receives messages from the server. In base of the state of the game and the message received takes action
    public void onMessageReceived(Message message) {
         switch (gameState) {
             case Lobby_State ->{
                 if(message.getType().equals(MessageType.Player_Num_Resp)){
                     game.setMAX_N_PLAYERS(((PlayerNumberResponse) message).getNumPlayers());
                 }
             }
             case In_Game,Last_Lap-> {
                 if(message.getType().equals(MessageType.Choose_Obj_Res)){
                     updateObjCard(message);
                 }
                 else if(message.getType().equals(MessageType.Place_Initial_Card)){
                     placeInitialCard(message.getNickName(),((PlaceInitialCard)message).getSide());
                 }
                 else if(message.getType().equals((MessageType.Chat_Mess))){
                     chatMessage(message);
                 }
                 else if(message.getType().equals((MessageType.Player_Info))){
                     playerInfo(message);
                 }
                 else if(message.getType().equals(MessageType.Place_Card)){
                     placeCard(message);
                 }
                 else if(message.getType().equals(MessageType.Game_St)){
                     showGameInfo(message);
                 }
                 else if(message.getType().equals(MessageType.Pick_Card)){
                     pickCard(message);
                 }
             }
         }
    }
    private void pickCard(Message message){
        String nickname=  message.getNickName();
        int cardID = ((PickCardMess)message).getCardToPick();
        Player player = null;
        Iterator iterator = game.getPlayers().iterator();
        while(iterator.hasNext()){
            player = (Player)iterator.next();
            if(player.getNickName().equals(nickname)){
                break;
            }
        }
        if(!nickname.equals(playerTurn) || player.getPlayerHand().size()==3){
            view.get(nickname).errorMessage("Its not the moment to pick a card");
            return;
        }
        ResourceCard card = null;
        ArrayList<ResourceCard> rev = (ArrayList<ResourceCard>) game.getFaceupCards();
        for(ResourceCard rsc: rev){
            if(rsc.getCardId() == cardID){
                card = rsc;
                rev.remove(rsc);
                if(rsc.getNecessaryRes()==null && rsc.getCondition()==null){
                    rev.add((ResourceCard) game.getResourceDeck().pickCard());
                }
                else{
                    rev.add((GoldCard) game.getGoldDeck().pickCard());
                }
                break;
            }
        }
        if(!game.getGoldDeck().getCards().isEmpty() && game.getGoldDeck().getCards().get(game.getGoldDeck().getCards().size()-1).getCardId()==cardID ){
            card  = (GoldCard) game.getGoldDeck().pickCard();
        }
        else if(!game.getResourceDeck().getCards().isEmpty() && game.getResourceDeck().getCards().get(game.getResourceDeck().getCards().size()-1).getCardId()==cardID){
            card = (ResourceCard) game.getResourceDeck().pickCard();
        }
        if(card==null){
            view.get(nickname).errorMessage("You cannot pick this card yet...");
            return;
        }
        else{
            if(game.getResourceDeck().getCards().isEmpty() && game.getGoldDeck().getCards().isEmpty()){
                gameState = Last_Lap;
            }
            player.pickCard(card);
            setPlayerTurn(nickname);
            view.get(nickname).errorMessage("Card picked");
        }
    }
    private void setPlayerTurn(String name){
          int index = onlinePlayers.indexOf(name);
          index++;
          if(index==onlinePlayers.size()-1 && gameState.equals(Last_Lap)){
              gameState = End_Game;
              decideWinner();
          }
          else if(index>=onlinePlayers.size()){
              index=0;
          }
          if(gameState!=End_Game){
          playerTurn = onlinePlayers.get(index);
          view.get(onlinePlayers.get(index)).errorMessage("Your turn.....");
          }
    }
    public void decideWinner(){
        Iterator iterator = game.getPlayers().iterator();
        String Winner = "";
        int max = 0;
        while(iterator.hasNext()){
            Player player =(Player) iterator.next();
            player.upPoints(player.getPlayerScheme().ControlObjective(player.getPlayerObjective()));
            player.upPoints(player.getPlayerScheme().ControlObjective(game.getGlobalObj().get(0)));
            player.upPoints(player.getPlayerScheme().ControlObjective(game.getGlobalObj().get(1)));
            if(player.getPoints()>max){
                Winner = player.getNickName();
                max = player.getPoints();
            }

        }

        for(String name: new ArrayList<String>(view.keySet())){
            view.get(name).winner("\nThe winner is   " +  Winner);
        }
    }
    private void showGameInfo(Message message){
        ArrayList<ResourceCard> revealed = (ArrayList<ResourceCard>) game.getFaceupCards();
        ArrayList<ObjectiveCard>  global = (ArrayList<ObjectiveCard>) game.getGlobalObj();
        ArrayList<ResourceCard> deck = new ArrayList<>();
        deck.add((ResourceCard) game.getResourceDeck().getCards().get(game.getResourceDeck().getCards().size()-1));
        deck.add((GoldCard) game.getGoldDeck().getCards().get(game.getGoldDeck().getCards().size()-1));
        view.get(message.getNickName()).showGameInfo(new ShowGameResp(message.getNickName(),revealed,global,deck));

    }
    private void placeCard(Message message){
        if(!playerTurn.equals(message.getNickName())){
            view.get(message.getNickName()).errorMessage("Not your turn");
            return;
        }
        else{
            String side = ((PlaceCardMess)message).getSide();
            if(side.equals("-f")){
                side = "front";
            }
            else{
                side = "retro";
            }
            Iterator iterator = game.getPlayers().iterator();
            Player player = null;
            while(iterator.hasNext()){
                player = (Player) iterator.next();
                if(player.getNickName().equals(message.getNickName())){
                    break;
                }
            }
            if(player.getPlayerHand().size()<3){
                view.get(message.getNickName()).errorMessage("You already played your card");
                return;
            }
            iterator = player.getPlayerHand().iterator();
            while(iterator.hasNext()){
                ResourceCard card =(ResourceCard) iterator.next();
                if(card.getCardId() == ((PlaceCardMess)message).getCardID()){
                    try {
                        player.upPoints( player.getPlayerScheme().placeCard(card,((PlaceCardMess)message).getPos(),side));
                        player.getPlayerHand().remove(card);
                        if(player.getPoints()>=20){
                            gameState = Last_Lap;
                        }
                        view.get(message.getNickName()).errorMessage("Card placed");

                    } catch (GoldCardPlacementException e) {
                        view.get(message.getNickName()).errorMessage("Couldn't place gold card. Check the requirements");
                    } catch (OutOfBoundsException e) {
                        view.get(message.getNickName()).errorMessage("You are exceeding the scheme limits");
                    } catch (InvalidPositionException e) {
                        view.get(message.getNickName()).errorMessage("Card can't be placed there");
                    } catch (InvalidSideException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
    private void
    playerInfo(Message message) {
        String Nickname = message.getNickName();
        String toShow  = ((ShowPlayerInfo)message).getToShow();
        Iterator iterator = game.getPlayers().iterator();
        while(iterator.hasNext()){
            Player player = (Player)iterator.next();
            if(player.getNickName().equals(toShow) && view.containsKey(toShow)){
                view.get(Nickname).showPlayer(new ShowPlayerInfo("Server",player,toShow));
            }
        }
    }
    private void chatMessage(Message message){
        if(!onlinePlayers.contains(((ChatMess)message).getDest())){
            System.out.println("Player non esiste");
            Message ChatMess = new ChatMess("Server",message.getNickName(),"No such player");
            view.get(message.getNickName()).showChatMessage(ChatMess);

        }
        else{
            System.out.println("Player esiste");
            Message response = new ChatMess(message.getNickName(),((ChatMess) message).getDest(),((ChatMess) message).getMess());
            view.get(((ChatMess) message).getDest()).showChatMessage(response);
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

    public void removePlayer(String NickName)  {
        Iterator<Player> iterator = game.getPlayers().iterator();
        while(iterator.hasNext()){
            Player player = iterator.next();
            if(player.getNickName().equals(NickName)){
                iterator.remove();
                view.remove(NickName);
                onlinePlayers.remove(NickName);
                return;
            }
        }
    }
    private void setInitial(String NickName){
        InitialCard card = (InitialCard) game.getInitialDeck().pickCard();
        Iterator iterator = game.getPlayers().iterator();
        while(iterator.hasNext()){
            Player player = (Player) iterator.next();
            if(player.getNickName().equals(NickName)){
                player.setPlayerInitial(card);
            }
        }
        view.get(NickName).showInitial(new InitialCardMess(card));
    };

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
        int choice = ((ChooseObjResp)message).getChosen();
        Iterator iterator = game.getPlayers().iterator();
        while(iterator.hasNext()){
            Player player = (Player) iterator.next();
            if(player.getNickName().equals(name)){
                player.setPlayerObjective(objectives.get(name).remove(choice-1));
            }
        }
       setInitial(name);
    }
    private void placeInitialCard(String NickName, int Side){
        Iterator iterator = game.getPlayers().iterator();
        while(iterator.hasNext()){
            Player player = (Player)iterator.next();
            if(player.getNickName().equals(NickName)){
                if(Side==1){
                    try{
                        player.getPlayerScheme().placeInitialCard(player.getPlayerInitial(),"front");
                        chosenObjInit++;
                        System.out.println(NickName+"s card played   " );
                    }catch(InvalidSideException e){System.out.println("Invalid side");}
                }
                if(Side==2){
                    try{
                        chosenObjInit++;
                        player.getPlayerScheme().placeInitialCard(player.getPlayerInitial(),"retro");
                        System.out.println(NickName+"s card played   " );
                    }catch(InvalidSideException ex){System.out.println("Invalid side");}
                }
            }
        }
            initGame(NickName);



    }
    private void initGame(String NickName){
        Iterator<Player> iterator  = game.getPlayers().iterator();
        while(iterator.hasNext()){
            Player player = iterator.next();
            if(player.getNickName().equals(NickName)){
               player.pickCard((ResourceCard) game.getResourceDeck().pickCard());
               player.pickCard((ResourceCard) game.getResourceDeck().pickCard());
               player.pickCard((GoldCard) game.getGoldDeck().pickCard());
            }
        }
        view.get(NickName).initialiseCl(new Message("Server",MessageType.Init_Cl));
    }


    public void firstLogin(String NickName,VirtualView view) throws PlayersLimitExceededException {
        if(game.getPlayers().size()==0){
            try {
                game.addPlayer(new Player(NickName, getColor()));
                removePlayer("");
            }catch(PlayersLimitExceededException e){}
            this.view.put(NickName,view);
            onlinePlayers.add(NickName);
            System.out.println(NickName + "'s view added" );
            playerTurn = NickName;
            view.askNumPlayers();
        }
        else if(game.getPlayers().size()<game.getMAX_N_PLAYERS()){
            this.view.put(NickName,view);
            onlinePlayers.add(NickName);
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
