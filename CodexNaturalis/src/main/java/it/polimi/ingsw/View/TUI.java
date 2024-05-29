package main.java.it.polimi.ingsw.View;

import main.java.it.polimi.ingsw.Model.Cards.ObjectiveCard;
import main.java.it.polimi.ingsw.Model.Cards.ResourceCard;
import main.java.it.polimi.ingsw.Model.Player.PlayerView;
import main.java.it.polimi.ingsw.Network.ClientManager;
import main.java.it.polimi.ingsw.Network.Messages.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class implements the View class and contains all the necessary view for the CL view.
 * @see View
 */
public class TUI implements View{
    private Thread inputThread;
    private ClientManager clientManager;
    private Scanner sc;
    private final List<String> commands;
    private boolean stop;

    /**
     * Constructor that initialises the TUI class.
     */
    public TUI(){
        sc = new Scanner(System.in);
        commands = new ArrayList<String>();
        commands.add("chat {player name}");
        commands.add("place {-f,-r} {cardID} {posY} {posX}");
        commands.add("pick {cardID}");
        stop = false;
    }

    /**
     * Associates the clientManager attribute with the proper ClientManager object which is used to hide the network from the view.
     * @param clientManager
     */
    public void setClientManager(ClientManager clientManager){
        this.clientManager = clientManager;
    }

    /**
     * Prints a welcome message for the game!
     */
    public void initTUI(){
        System.out.println("Welcome to CODEXNATURALIS");
        serverInfo();
    }

    /**
     * Controls if a certain strings is a valid IP address.
     * @param IP The string that represents the IP address to be checked.
     * @return True if it is a valid string, false otherwise!
     */
    private boolean checkAddress(String IP){
      if(IP==null || IP.isEmpty()){
          return false;
      }
      String zeroto255 = "(\\d{1,2}|(0|1)\\" + "d{2}|2[0-4]\\d|25[0-5])";
      String regex  = zeroto255+"\\."+zeroto255+"\\."+zeroto255+"\\."+zeroto255;
      Pattern  p = Pattern.compile(regex);
      Matcher m  =p.matcher(IP);
      return m.matches();

    }

    /**
     * This method implements the serverInfo() method from the View interface, and
     * it is used to ask for the Ip address and the connection type the player wants for the connection!
     */
    @Override
    public void serverInfo(){
           this.stop = false;
           int defaultPort = 1099;
           int socketPort = 6000;
           String serverIP,connectionType;
           int serverPort = socketPort;
           boolean rmi = false;
           System.out.println("What type of connection would you like: -r or -s");
           connectionType = sc.nextLine();
           if(!connectionType.equals("-s") && !connectionType.equals("-r")){
               System.out.println("No such connection");
               serverInfo();
           }
           if(connectionType.equals("-r")){
               rmi = true;
           }
           do {
               System.out.println("Enter the ip address of the server:");
               serverIP = sc.nextLine();
           }while(!checkAddress(serverIP));
           if(rmi){
               serverPort = defaultPort;
           }
           clientManager.onUpdateServerInfo(serverIP,serverPort,rmi);
    }

    /**
     * Implements the errorMessage method from the View interface. It prints an error message coming from the server!
     * @param message
     */
    @Override
    public void errorMessage(String message){
        String red = "\u001B[31m";
        String reset = "\u001B[0m";
        System.out.println(red +message+ reset);
    }

    /**
     * This method implements the askNickName() method of the View interface. This method asks for the nickname of the
     * player which is going to be used during the login phase!
     */
    @Override
    public void askNickName(){
        System.out.println("Please enter your nickname to log in the game:  ");
        String nickname = sc.nextLine();
        clientManager.updateNickName(nickname);
    }

    /**
     * Implements the showLogin method form the View interface. It is used tho show the result of the login
     * @param connected Boolean which shows if the player is connected!
     * @param nameAccepted Boolean which shows if the name is accepted during the login phase!
     */
    @Override
    public void showLogin(boolean connected, boolean nameAccepted){
        if(nameAccepted && connected){
            waitingPlayers();
        }
        else if(!nameAccepted && connected){
            System.out.println("Invalid nickname");
            askNickName();
        }
    }

    /**
     * Method that prints a message during the phase in which the players wait for others to connect!
     */
    public void waitingPlayers(){
        System.out.println("Waiting for other players to connect...");
    }

    /**
     * This method implements the askNumPlayers of the View interface. It asks for the number of the players
     * the game is going to have. This method is called only for the first logged player!
     */
    @Override
    public void askNumPlayers(){
        int numPlayers  = 0;
        System.out.println("You are the first to connect. Please choose the number of players to partecipate: ");
        try{
            numPlayers = Integer.parseInt(sc.nextLine());
        }catch(NumberFormatException e){
            System.out.println("Invalid output!!!");
            askNumPlayers();
        }
        if(numPlayers<1 || numPlayers>4){
            System.out.println("The number of players must be between 1 and 4");
            askNumPlayers();
        }
        clientManager.updateNumplayers(numPlayers);
    }

    /**
     * Implements the chooseObjectiveCard method of the View interface. It shows the objective card to the player
     * who must choose only one between them.
     * @param message The message which contains the cards to be chosen by the player.
     * @see ChooseObjReq
     */
    @Override
    public void chooseObjectiveCard(Message message){
        int chosenCard = 0;
        System.out.println("Please choose your objective card: \n1-->For first card\n2-->For second\n ");
        System.out.println(message.toString());
        try {
           chosenCard =  Integer.parseInt(sc.nextLine());
        }catch(NumberFormatException e){
            System.out.println("Invalid input");
            chooseObjectiveCard(message);
        }
        if(chosenCard!=1 && chosenCard!=2){
            System.out.println("Invalid input");
            chooseObjectiveCard(message);
        }
        else {
            clientManager.updateObjCard(new ChooseObjResp(clientManager.getNickName(), chosenCard));
        }
    }

    /**
     * Implements the showInitial method of the View interface. It shows the initial card of the player
     * who must choose which side to play.
     * @param message The message in which is contained the initial card.
     * @see InitialCardMess
     */
    @Override
    public void showInitial(Message message){
        int choice = 0;
        System.out.println("This is your initial card" +
                "\nChoose the side to play" +
                "\n1-->Front" +
                "\n2-->Retro\n");
        System.out.println(((InitialCardMess)message).toString());
        try{
            choice = Integer.parseInt(sc.nextLine());
        }catch(NumberFormatException e){
            System.out.println("Invalid input...");
            showInitial(message);
        }
        if(choice!=1 && choice!=2){
            System.out.println("Invalid Input....");
            showInitial(message);
        }
        else{
            clientManager.placeInitial(choice);
        }
    }

    @Deprecated
    @Override
    public void initialiseCl(Message message) {
    }

    /**
     * Implements the showGameInfo method of the View interface. It has to show the information related to the state of the game.
     * @param message The message sent from the server and in which are contained all the necessary info. For more read the documentation
     *                of the ShowGameResp class.
     * @see ShowGameResp
     */

    @Override
    public void showGameInfo(Message message){
        ArrayList<ResourceCard> revealed = ((ShowGameResp)message).getRevealed();
        for(ResourceCard rsc: revealed){
            if(rsc.getCondition()==null && rsc.getNecessaryRes()==null){
                System.out.println("******ResourceCard:  " + rsc.getCardId()+  " ********");
                System.out.println(rsc.toString() + "\n");
            }
            else{
                System.out.println("******GoldCard:  " + rsc.getCardId()+  " *********");
                System.out.println(rsc.toString() + "\n");
            }
        }
        ArrayList<ObjectiveCard> obj =((ShowGameResp)message).getGlobalObj();
        for(ObjectiveCard obje:obj){
            System.out.println("*********Objective********" );
            System.out.println(obje.toString() + "\n");
        }
        ArrayList<ResourceCard> deck = ((ShowGameResp)message).getDeck();
        for(ResourceCard card: deck){
            if(card.getCondition()==null && card.getNecessaryRes()==null){
                System.out.println("******ResourceDeck:  " + card.getCardId() +"********");
                System.out.println(card.getRetro().toString() +"    Resource:"+card.getResourceType().name()+  "\n");
            }
            else{
                System.out.println("******GoldDeck: "+card.getCardId()+" *********");
                System.out.println(card.getRetro().toString()+"    Resource:"+card.getResourceType().name()+  "\n");
            }
        }
        System.out.println("\nConnected players:");
        for(String name: ((ShowGameResp) message).getPlayers()){
            System.out.println(name);
        }
        System.out.println("\n Turn: " + ((ShowGameResp)message).getPlayerTurn());
    }

    /**
     * Methods that prints in the command line the winner of the game!
     * @param message A string containing the name of the winner!
     */
    @Override
    public void winner(String message)
    {
       System.out.println(message);
       stop();
    }

    /**
     * Implements the afterPlayerMove method of the View interface. It is invocated every time
     * a player makes a move to show him any possible update of the game.
     *  @param message The message sent from the server containing any possible update of the game!
     * @see PlayerMoveResp
     */
    @Override
    public void afterPlayerMove(Message message) {
        if(((PlayerMoveResp)message).getMoveType().equals("Pick") && ((PlayerMoveResp)message).getUpdatedPlayer().getNickName().equals(clientManager.getNickName())){
            System.out.println("Card picked");
        }
        else if(((PlayerMoveResp)message).getUpdatedPlayer().getNickName().equals(clientManager.getNickName())){
            System.out.println("Card placed");
        }
    }

    /**
     * The method asks for the message(string) to be sent to another player!
     * @param NickName The string representing the name of the destination for the message.
     */
    private void askChatMess(String NickName){
        System.out.print(">Message to "+NickName + ":");
        String mess = sc.nextLine();
        clientManager.sendMessage(NickName, mess);
    }

    /**
     * Controls if an entered command is valid and based on that takes decisions calling the clientManager!
     *The valid commands and their formats are defined during the initialization of the TUI.
     * @param command The command(string) to be checked!
     */
    private void checkCommand(String command){
        String[] parts = command.split("\\s+");
        switch (parts[0]){
                case "chat"->{
                    if(parts.length!=2){
                        System.out.println("Invalid arguments for the chat command");
                    }
                    else{
                        askChatMess(parts[1]);
                    }
                }
                case "show" -> {
                    if (parts.length != 2) {
                        System.out.println("Invalid arguments for the show command");
                    } else {
                        clientManager.showPlayer(parts[1]);
                    }
                }
                case "place"->{
                    if(parts.length!=5){
                        System.out.println("Invalid arguments for the place command");
                    }
                    else{
                        String side;
                        int cardID;
                        int[] pos = {0,0};
                        try{
                            cardID = Integer.parseInt(parts[1]);
                        }catch(NumberFormatException e){
                            System.out.println("Invalid cardID");
                            return;
                        }
                        side = parts[2];
                        if(!parts[2].equals("-f") && !parts[2].equals("-r")){
                            System.out.println("Invalid argument for side...");
                            return;
                        }
                        try{
                            pos[0] = Integer.parseInt(parts[3]);
                            pos[1] = Integer.parseInt((parts[4]));
                        }catch(NumberFormatException e){
                            System.out.println("Invalid argument for position");
                            return;
                        }
                        clientManager.placeCard(cardID,side,pos);

                    }
                }
                case "game"->{
                    clientManager.showGameSt();
                }
                case "pick"->{
                    if(parts.length!=2){
                        System.out.println("Invalid arguments for the pick command");
                    }
                    else{
                        try{
                            int cardId = Integer.parseInt(parts[1]);
                            clientManager.pickCard(cardId);
                        }catch(NumberFormatException e){
                            System.out.println("Invalid argument for the card ID");
                        }
                    }
                }
        }
    }

    /**
     * Initializes the command line, which waits for user commands!
     */
    public void initialiseCl() {
        Scanner comm = new Scanner(System.in);
        String command = "";
        for(String cmd: commands){
            System.out.println(cmd);
        }
        System.out.println("\nDigit your command:\n> ");
        while(!stop && !Thread.currentThread().isInterrupted()) {
            System.out.print(">");
            command = comm.nextLine();
            checkCommand(command);
        }
    }

    /**
     * Stops the inputThread thread, used for the command line.
     */
    @Override
    public void stop(){
        this.stop = true;
    }

    /**
     * Starts a new thread for the command line using the initialiseCl() method and the inputThread.
     */
    public void start(){
        inputThread = new Thread(this::initialiseCl);
        inputThread.start();

    }

    /**
     * Implements the showChatMessage of the View interface! It simply shows the messages sent from another player through the server!
     * @param message The message sent from the server containing the string to be printed.
     * @see ChatMess
     */
    @Override
    public void showChatMessage(Message message) {

        System.out.println("\nMessage from "+message.getNickName() + ": " + ((ChatMess)message).getMess() + "\n>");

    }

    /**
     * Implements the showPlayer method of the View interface. It shows all the data of a certain connected player!
     * @param message Contains the data of a player.
     * @see ShowPlayerInfo
     */
    @Override
    public void showPlayer(Message message) {
        PlayerView player = ((ShowPlayerInfo)message).getPlayer();
        System.out.println(player.toString());
        System.out.print(">");

    }

}
