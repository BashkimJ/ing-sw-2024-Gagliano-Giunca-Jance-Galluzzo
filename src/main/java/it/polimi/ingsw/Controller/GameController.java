package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Exceptions.GameExc.PlayersLimitExceededException;
import it.polimi.ingsw.Exceptions.SchemeCardExc.GoldCardPlacementException;
import it.polimi.ingsw.Exceptions.SchemeCardExc.InvalidPositionException;
import it.polimi.ingsw.Exceptions.SchemeCardExc.InvalidSideException;
import it.polimi.ingsw.Exceptions.SchemeCardExc.OutOfBoundsException;
import it.polimi.ingsw.Model.Cards.GoldCard;
import it.polimi.ingsw.Model.Cards.InitialCard;
import it.polimi.ingsw.Model.Cards.ObjectiveCard;
import it.polimi.ingsw.Model.Cards.ResourceCard;
import it.polimi.ingsw.Model.Enumerations.Colour;
import it.polimi.ingsw.Model.GameStatus.Game;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Save.Save;
import it.polimi.ingsw.Model.Save.SavesManager;
import it.polimi.ingsw.Network.Messages.*;
import it.polimi.ingsw.View.VirtualView;

import java.util.*;


import static it.polimi.ingsw.Controller.GameState.*;

    /**
     * Contains all the necessary methods to receive requests and elaborate them in order to update the model.
     */
    public class GameController {
        private GameState gameState;
        private Game game;
        private Map<String, VirtualView> view;
        private Map<String, List<ObjectiveCard>> objectives;
        private Map<String,Integer> onlinePlayers;
        private Map<String,Integer> offlinePlayers;
        private int chosenObjInit;
        private String playerTurn;
        private Object lockPlayers;
        private int numPlayers;
        private boolean askedForNewGame;
        private SavesManager sm;
        private  Object lock;

        public GameController(){
            newGame();
        }

        /**
         * Prepares a class with the necessary data from the current GameController to be written to file
         * with the support of the SavesManager.
         */
        public void saveGame(){
            SavesManager savesManager = new SavesManager();
            Map<String,Integer> off  =new HashMap<>();
            off.putAll(onlinePlayers);
            off.putAll(offlinePlayers);
            savesManager.SaveGame(this.gameState,this.game,this.objectives,off,this.playerTurn,this.numPlayers);
        }
        /**
         * Updates the current GameController with data from the last saved game if there is any.
         */
        public void loadGame(){
            SavesManager sm = new SavesManager();
            Save s = new Save();
            s = sm.LoadGame();
            if(s!=null) {
                this.gameState = s.getGameState();
                this.game = s.getGame();
                this.offlinePlayers = s.getOfflinePlayers();
                this.objectives = s.getObjectives();
                this.playerTurn = s.getPlayerTurn();
                this.numPlayers = s.getNumPlayers();
            }
        }

        /**
         * Receives the message sent from the client to the server and takes decision based on the type of the message.
         * @param message The message to elaborate.
         */
        public void onMessageReceived(Message message) {
            switch (gameState) {
                case Lobby_State ->{
                    if(message.getType().equals(MessageType.Player_Num_Resp)){
                        game.setMAX_N_PLAYERS(((PlayerNumberResponse) message).getNumPlayers());
                    }
                    synchronized (lock) {
                        if (message.getType().equals(MessageType.New_game) && !askedForNewGame) {
                            initialiseGame(message);
                        }
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

        private void initialiseGame(Message message) {
            if(((NewGameMess)message).isNewGame()){
                saveGame();
            }
            else{
                loadGame();
            }
            this.askedForNewGame = true;

        }

        /**
         * This method allows the controller to add the specified card from a player into his hand.It defines the pick movement at the end of the turn.
         * @param message It is the message that contains the cardID to add and the name of the player to add the card.
         */
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
                if(view.get(nickname)!=null) {
                    view.get(nickname).errorMessage("Its not the moment to pick a card");
                }
                return;
            }
            ResourceCard card = null;
            ArrayList<ResourceCard> rev = (ArrayList<ResourceCard>) game.getFaceupCards();
            for(ResourceCard rsc: rev){
                if(rsc.getCardId() == cardID){
                    card = rsc;
                    rev.remove(rsc);
                    if(rsc.getNecessaryRes()==null && rsc.getCondition()==null && !game.getResourceDeck().getCards().isEmpty()){
                        rev.add((ResourceCard) game.getResourceDeck().pickCard());
                    }
                    else if((rsc.getNecessaryRes()!=null || rsc.getCondition()==null) && !game.getGoldDeck().getCards().isEmpty()){
                        rev.add((GoldCard) game.getGoldDeck().pickCard());
                    }
                    break;
                }
            }
            if(!game.getGoldDeck().getCards().isEmpty() && game.getGoldDeck().getCards().get(game.getGoldDeck().getCards().size()-1).getCardId()==cardID && card==null ){
                card  = (GoldCard) game.getGoldDeck().pickCard();
            }
            else if(card==null && !game.getResourceDeck().getCards().isEmpty() && game.getResourceDeck().getCards().get(game.getResourceDeck().getCards().size()-1).getCardId()==cardID){
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
                for(String p: new ArrayList<String>(view.keySet())){
                    if(view.get(p)!=null) {
                        view.get(p).afterPlayerMove(new PlayerMoveResp(player, "Pick"));
                        showGameInfo(new ShowGameMess(p));
                    }

                }

            }
        }

        /**
         * This method allows to update player's turn based on the last player who had the turn.
         * @param name It is the nickname of the player who just finished his turn.
         */
        private void setPlayerTurn(String name){
            synchronized (lockPlayers) {
                int index = onlinePlayers.get(name);
                int max = Collections.max(onlinePlayers.values());
                boolean found = false;
                if (index == max && gameState.equals(Last_Lap)) {
                    gameState = End_Game;
                    decideWinner();
                    return;
                } else {
                    while(!found){
                        index++;
                        if(index - 1 == max || index==numPlayers){
                            int min = Collections.min(onlinePlayers.values());
                            for(String p: onlinePlayers.keySet()){
                                if(onlinePlayers.get(p)==min){
                                    playerTurn = p;
                                    found=true;
                                    saveGame();
                                }
                            }
                        }
                        else{
                            for(String p: onlinePlayers.keySet()){
                                if(onlinePlayers.get(p)==index){
                                    playerTurn = p;
                                    found = true;
                                    saveGame();
                                }
                            }
                        }
                    }
                }


                if (gameState != End_Game){
                    view.get(playerTurn).showChatMessage(new ChatMess("Server",playerTurn,"Your turn"));
                }

            }
        }

        /**
         * This method calculates which of the player has more points at the end of the game deciding who the winner is.
         */
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
                if(view.get(name)!=null) {
                    view.get(name).winner("\nThe winner is   " + Winner);
                }
            }
            newGame();
        }

        /**
         * This method initialises a new game.
         */
        public void newGame(){
            this.lockPlayers = new Object();
            chosenObjInit = 0;
            this.gameState = Lobby_State;
            Player player = new Player("", null);
            objectives = new HashMap<>();
            game = new Game(player, 1);
            game.getGoldDeck().shuffle();
            game.getInitialDeck().shuffle();
            game.getResourceDeck().shuffle();
            game.getFaceupCards().add((ResourceCard) game.getResourceDeck().pickCard());
            game.getFaceupCards().add((ResourceCard) game.getResourceDeck().pickCard());
            game.getFaceupCards().add((GoldCard) game.getGoldDeck().pickCard());
            game.getFaceupCards().add((GoldCard) game.getGoldDeck().pickCard());
            Collections.shuffle(game.getObjectiveCards());
            game.getGlobalObj().add(game.getObjectiveCards().remove(game.getObjectiveCards().size() - 1));
            game.getGlobalObj().add(game.getObjectiveCards().remove(game.getObjectiveCards().size() - 1));
            onlinePlayers = new HashMap<>();
            offlinePlayers = new HashMap<String,Integer>();
            this.view = Collections.synchronizedMap(new HashMap<>());
            numPlayers = 0;
            removePlayer("");
            this.askedForNewGame = false;
            this.lock = new Object();
        }

        /**
         * The method sends all the necessary information about the game to the player that asked for it.
         * @param message The message must contain the name of the player that asked for the game info.
         */
        private void showGameInfo(Message message){
            ArrayList<ResourceCard> revealed = (ArrayList<ResourceCard>) game.getFaceupCards();
            ArrayList<ObjectiveCard>  global = (ArrayList<ObjectiveCard>) game.getGlobalObj();
            ArrayList<ResourceCard> deck = new ArrayList<>();
            deck.add((ResourceCard) game.getResourceDeck().getCards().get(game.getResourceDeck().getCards().size()-1));
            deck.add((GoldCard) game.getGoldDeck().getCards().get(game.getGoldDeck().getCards().size()-1));
            ArrayList<String> players;
            synchronized (lockPlayers) {
                players = new ArrayList<>(onlinePlayers.keySet());
            }
            if(view.get(message.getNickName())!=null) {
                view.get(message.getNickName()).showGameInfo(new ShowGameResp(message.getNickName(), revealed, global, deck, playerTurn, players));
            }
        }

        /**
         * The method allows the controller to place a card in the cardScheme of the player.The card to be placed is specified using the cardID found int the message.
         * @param message Contains all the necessary information of the player and the card he wants to place.
         */
        private void placeCard(Message message){
            if(onlinePlayers.size()==1){
                if(view.get(message.getNickName())!=null) {
                    view.get(message.getNickName()).errorMessage("Waiting for other players");
                    return;
                }
            }
            if(!playerTurn.equals(message.getNickName())){
                if(view.get(message.getNickName())!=null) {
                    view.get(message.getNickName()).errorMessage("Not your turn");
                }
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
                    if(view.get(message.getNickName())!=null) {
                        view.get(message.getNickName()).errorMessage("You already played your card");
                    }
                    return;
                }
                iterator = player.getPlayerHand().iterator();
                while(iterator.hasNext()){
                    ResourceCard card =(ResourceCard) iterator.next();
                    if(card.getCardId() == ((PlaceCardMess)message).getCardID()){
                        try {
                            player.upPoints( player.getPlayerScheme().placeCard(card,((PlaceCardMess)message).getPos(),side));
                            iterator.remove();
                            if(player.getPoints()>=20){
                                gameState = Last_Lap;
                            }
                            for(String p: new ArrayList<String>(view.keySet())) {
                                if(view.get(p)!=null)
                                    view.get(p).afterPlayerMove(new PlayerMoveResp(player, "Place"));
                            }
                            break;

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

        /**
         * Allows the controller to obtain all the information related to  a player.
         * @param message Must contain the name of the player who asked for information and the name of the player to obtain the info about.
         */
        private void playerInfo(Message message) {
            String Nickname = message.getNickName();
            String toShow  = ((ShowPlayerInfo)message).getToShow();
            Iterator iterator = game.getPlayers().iterator();
            while(iterator.hasNext()){
                Player player = (Player)iterator.next();
                if(player.getNickName().equals(toShow) && view.containsKey(toShow)){
                    if(view.get(Nickname)!=null){
                        view.get(Nickname).showPlayer(new ShowPlayerInfo("Server", player, toShow));
                    }
                    break;
                }
            }
        }

        /**
         * This method implements the advanced functionality of the chat message. It simply sends the string to the specified player.
         * @param message Contains the message sender,the message receiver and the string message.
         */
        private void chatMessage(Message message){
            synchronized (lockPlayers) {
                if(((ChatMess)message).getDest().equals("all")){
                    for(String name: new ArrayList<String>(onlinePlayers.keySet())){
                        view.get(name).showChatMessage(new ChatMess(message.getNickName(),((ChatMess) message).getDest(), ((ChatMess) message).getMess()));
                    }
                }
                else if (!onlinePlayers.containsKey(((ChatMess) message).getDest())) {
                    System.out.println("Player doesn't exist");
                    Message ChatMess = new ChatMess("Server", message.getNickName(), "No such connected player");
                    view.get(message.getNickName()).showChatMessage(ChatMess);

                } else {
                    System.out.println("Player exists");
                    Message response = new ChatMess(message.getNickName(), ((ChatMess) message).getDest(), ((ChatMess) message).getMess());
                    view.get(((ChatMess) message).getDest()).showChatMessage(response);
                }
            }
        }

        /**
         * Getter method to obtain the state of the game
         * @return GameState
         */
        public GameState getState(){
            return this.gameState;
        }

        /**
         * Controls if a nickname is already present in the game.
         * @param Name The string to be checked.
         * @return Boolean. So if its true the name doesn't exist.
         */
        public synchronized boolean checkNickName(String Name){
            Iterator<Player> iterator  = game.getPlayers().iterator();
            while(iterator.hasNext()){
                Player player = iterator.next();
                if(player.getNickName().equals(Name)){
                    return false;
                }
            }
            return true;
        }

        /**
         * Returns a color that is not present currently in the game.
         * @return Colour.
         */
        private synchronized Colour getColor(){
            boolean present;
            for(Colour color: Colour.values()){
                present = false;
                for(Player player: game.getPlayers()){
                    if(player.getPlayerColour()!=null && player.getPlayerColour().equals(color)){
                        present = true;
                        break;
                    }
                }
                if(!present){
                    return color;
                }
            }
            return Colour.blue;
        }

        /**
         * Removes a player in case of a disconnection.
         * @param NickName The name of the disconnected player.
         */

        public synchronized void  removePlayer(String NickName)  {
            //If the game is still in the lobby state the server won't keep track of a disconnected player
            if (gameState == Lobby_State) {
                Iterator<Player> iterator = game.getPlayers().iterator();
                while (iterator.hasNext()) {
                    Player player = iterator.next();
                    if (player.getNickName().equals(NickName)) {
                        iterator.remove();
                        view.remove(NickName);
                        onlinePlayers.remove(NickName);
                        return;
                    }
                }
            } else if (gameState == In_Game) {
                if(onlinePlayers.size()==1) {
                    playerTurn = "";
                }
                else if (playerTurn.equals(NickName)) {
                    setPlayerTurn(NickName);
                }

                offlinePlayers.put(NickName, onlinePlayers.get(NickName));
                onlinePlayers.remove(NickName);
                view.remove(NickName);
            }

        }

        /**
         * Sets an initial card for all the players and sends a showInitial message to the client.
         * @param NickName The name of the player to send the initial card.
         */
        private void setInitial(String NickName){
            InitialCard card = (InitialCard) game.getInitialDeck().pickCard();
            Iterator iterator = game.getPlayers().iterator();
            while(iterator.hasNext()){
                Player player = (Player) iterator.next();
                if(player.getNickName().equals(NickName)){
                    player.setPlayerInitial(card);
                }
            }
            if(view.get(NickName)!=null) {
                view.get(NickName).showInitial(new InitialCardMess(card));
            }
        };

        /**
         * Sends two objective cards to all the players to choose from.
         */
        public synchronized void objectiveCardOptionsSender(){
            for (String name : new ArrayList<String>(view.keySet())) {
                ArrayList<ObjectiveCard> options = new ArrayList<>();
                options.add(game.getObjectiveCards().remove(game.getObjectiveCards().size() - 1));
                options.add(game.getObjectiveCards().remove(game.getObjectiveCards().size() - 1));
                objectives.put(name, options);
                view.get(name).chooseObjectiveCard(new ChooseObjReq(options));
            }
            ArrayList<String> random = new ArrayList<>(onlinePlayers.keySet());
            Collections.shuffle(random);
            int index = 0;
            for(String p: random){
                onlinePlayers.put(p,index);
                index++;
            }
            playerTurn = random.get(0);


        }

        /**
         * Updates the objective card chosen by the player and that resides in the options.
         * @param message The message to elaborate which contains the name of the player and the information of the chosen card.
         */
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

        /**
         * Places the initial card in the card scheme of the player.
         * @param NickName The players name.
         * @param Side The necessary information about the side chosen. If 1 it means the front one, if 2 it means the retro side.
         */

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
            saveGame();
            initGame(NickName);



        }

        /**
         * Starts the game for the player that has already chosen the initial and objective card.
         * @param NickName The name of the player to start the game.
         */
        private synchronized void initGame(String NickName){
            Iterator<Player> iterator  = game.getPlayers().iterator();
            while(iterator.hasNext()){
                Player player = iterator.next();
                if(player.getNickName().equals(NickName) && player.getPlayerHand().isEmpty()){
                    player.pickCard((ResourceCard) game.getResourceDeck().pickCard());
                    player.pickCard((ResourceCard) game.getResourceDeck().pickCard());
                    player.pickCard((GoldCard) game.getGoldDeck().pickCard());
                }
            }
            if(view.get(NickName)!=null)
                view.get(NickName).alertGameStarted(new Message("Server",MessageType.Game_Started));
            for(String name: view.keySet()){
                if(view.get(name)!=null && !name.equals(NickName))
                    showGameInfo(new ShowGameMess(name));
            }
        }

        /**
         * Executes the first login of the player. This method is called when the game is waiting for all players to connect.
         * @param NickName The nickname of the player to login.
         * @param view The VirtualView associated to the player used by the controller to communicate with the client
         * @throws PlayersLimitExceededException This exception occurs when we try to add more players to our game that the maximum number chosen.
         */
        public synchronized void firstLogin(String NickName,VirtualView view) throws PlayersLimitExceededException {
            if(game.getPlayers().isEmpty()){
                try {
                    game.addPlayer(new Player(NickName, getColor()));
                    removePlayer("");
                }catch(PlayersLimitExceededException e){}
                this.view.put(NickName,view);
                synchronized (lockPlayers) {
                    onlinePlayers.put(NickName,numPlayers);
                    numPlayers++;
                }
                System.out.println(NickName + "'s view added" );
                playerTurn = NickName;
                view.askNumPlayers();
            }
            else if(game.getPlayers().size()<game.getMAX_N_PLAYERS()){
                this.view.put(NickName,view);
                synchronized (lockPlayers){
                    onlinePlayers.put(NickName,numPlayers);
                    numPlayers++;
                }
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
                    view.errorMessage("Error: You tried entering the game before the main player chose the max num of players" );
                }
                else{
                    view.errorMessage("All players are connected...");
                }
            }

        }

        /**
         * Manages the reconnection of a player using its nickname as an authentication element.
         * @param nickName The name of the player to reconnect
         * @param virtualView The VirtualView associated to the player.
         */
        public void reconnect(String nickName, VirtualView virtualView) {
            synchronized (lockPlayers) {
                if (offlinePlayers.containsKey(nickName)) {
                    synchronized (lockPlayers) {
                        view.put(nickName, virtualView);
                        onlinePlayers.put(nickName, offlinePlayers.get((nickName)));
                        offlinePlayers.remove(nickName);
                        if (playerTurn.isEmpty()) {
                            playerTurn = nickName;
                        }
                    }
                    Iterator<Player> iterator = game.getPlayers().iterator();
                    Player player = null;
                    while (iterator.hasNext()) {
                        player = (Player) iterator.next();
                        if (player.getNickName().equals(nickName))
                            break;
                    }
                    //Control if he already chose his objective card
                    if (player != null && player.getPlayerObjective() == null) {
                        if (!objectives.containsKey(nickName)) {
                            List<ObjectiveCard> obj = new ArrayList<>();
                            obj.add(game.getObjectiveCards().remove(game.getObjectiveCards().size() - 1));
                            obj.add(game.getObjectiveCards().remove(game.getObjectiveCards().size() - 1));
                            objectives.put(nickName, obj);
                        }
                        view.get(nickName).chooseObjectiveCard(new ChooseObjReq(objectives.get(nickName)));
                    }

                    //Control if he has played the initial
                    else if (player != null && player.getPlayerScheme().getPlayedCards().isEmpty()) {
                        if (player.getPlayerInitial() == null) {
                            player.setPlayerInitial((InitialCard) game.getInitialDeck().pickCard());
                        }
                        view.get(nickName).showInitial(new InitialCardMess(player.getPlayerInitial()));
                    } else {
                        initGame(nickName);
                    }


                } else {
                    virtualView.showLogin(false, true);
                }
            }
        }
    }