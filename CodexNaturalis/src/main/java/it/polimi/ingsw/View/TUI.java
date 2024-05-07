package main.java.it.polimi.ingsw.View;

import main.java.it.polimi.ingsw.Model.Cards.InitialCard;
import main.java.it.polimi.ingsw.Model.Cards.ObjectiveCard;
import main.java.it.polimi.ingsw.Model.Cards.ResourceCard;
import main.java.it.polimi.ingsw.Model.Player.Player;
import main.java.it.polimi.ingsw.Model.Player.PlayerView;
import main.java.it.polimi.ingsw.Network.ClientManager;
import main.java.it.polimi.ingsw.Network.Messages.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.*;

public class TUI implements View{
    Thread inputThread;
    private ClientManager clientManager;
    private Scanner sc;
    private List<String> commands;
    private boolean stop;
    public TUI(){
        sc = new Scanner(System.in);
        commands = new ArrayList<String>();
        commands.add("chat");
        commands.add("place");
        commands.add("pick");
        commands.add("save");
        stop = false;
    }
    public void setClientManager(ClientManager clientManager){
        this.clientManager = clientManager;
    }
    public void initTUI(){
        System.out.println("Welcome to CODEXNATURALIS");
        serverInfo();
    }

    private boolean checkAddress(String IP, int port){
      if(port<1024 && port>65535 ){
          return false;
      }
      if(IP==null || IP.isEmpty()){
          return false;
      }
      String zeroto255 = "(\\d{1,2}|(0|1)\\" + "d{2}|2[0-4]\\d|25[0-5])";
      String regex  = zeroto255+"\\."+zeroto255+"\\."+zeroto255+"\\."+zeroto255;
      Pattern  p = Pattern.compile(regex);
      Matcher m  =p.matcher(IP);
      return m.matches();

    }

    public void serverInfo(){
           int defaultPort = 1099;
           String defaultHost = "127.0.0.1";
           String serverIP,connectionType;
           int serverPort = defaultPort;
           Boolean rmi = false,validInput = true;
           System.out.println("What type of connection would you like: -r or -s");
           connectionType = sc.nextLine();
           if(!connectionType.equals("-s") && !connectionType.equals("-r")){
               System.out.println("No such connection");
               return;
           }
           if(connectionType.equals("-r")){
               rmi = true;
           }
           do {
               System.out.println("Enter the ip address of the server:");
               serverIP = sc.nextLine();
               System.out.println("Enter the port of the server:");
               try {
                   serverPort = Integer.parseInt(sc.nextLine());
               }catch(NumberFormatException e){
                   validInput = false;
                   continue;
               }
           }while(!checkAddress(serverIP,serverPort));
           if(rmi==true){
               serverPort = defaultPort;
           }
           clientManager.onUpdateServerInfo(serverIP,serverPort,rmi);
    }
    public void errorMessage(String message){
        System.out.println(message);
    }

    public void askNickName(){
        System.out.println("Please enter your nickname to log in the game:  ");
        String nickname = sc.nextLine();
        clientManager.updateNickName(nickname);
    }

    public void showLogin(boolean connected, boolean nameAccepted){
        if(nameAccepted==true && connected==true){
            waitingPlayers();
        }
        else if(nameAccepted==false && connected==true){
            askNickName();
        }
    }
    public void waitingPlayers(){
        System.out.println("Waiting for other players to connect...");
    }
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
    public void chooseObjectiveCard(Message message){
        int chosenCard = 0;
        System.out.println("Please choose your objective card: \n1-->For first card\n2-->For second\n ");
        System.out.println(message.toString());
        try {
           chosenCard =  sc.nextInt();
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
    public void showInitial(Message message){
        int choice = 0;
        System.out.println("This is your initial card" +
                "\nChoose the side to play" +
                "\n1-->Front" +
                "\n2-->Retro\n");
        System.out.println(((InitialCardMess)message).toString());
        try{
            choice = sc.nextInt();
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
    }

    @Override
    public void winner(String message)
    {
       System.out.println(message);
       Stop();
    }

    private void askChatMess(String NickName){
        System.out.print(">Message to "+NickName + ":");
        String mess = sc.nextLine();
        clientManager.sendMessage(NickName, mess);
    }
    private void checkCommand(String command){
        String[] parts = command.split("\\s+");
        switch (parts[0]){
                case "chat"->{
                    if(parts.length!=2){
                        System.out.println("Invalid arguments for the chat command");
                        return;
                    }
                    else{
                        askChatMess(parts[1]);
                        return;
                    }
                }
                case "show" -> {
                    if (parts.length != 2) {
                        System.out.println("Invalid arguments for the chat command");
                        return;
                    } else {
                        clientManager.showPlayer(parts[1]);
                        return;
                    }
                }
                case "place"->{
                    if(parts.length!=5){
                        System.out.println("Invalid arguments for the place command");
                        return;
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
                        return;
                    }
                    else{
                        try{
                            int cardId = Integer.parseInt(parts[1]);
                            clientManager.pickCard(cardId);
                        }catch(NumberFormatException e){
                            System.out.println("Invalid argument for the card ID");
                            return;
                        }
                    }
                }
        }
    }


    public void initialiseCl() {
        String command = "";
        System.out.println("Digit your command: ");
        for(String cmd: commands){
            System.out.println(cmd);
        }
        sc.nextLine();
        while(!stop) {
            System.out.print(">");
            command = sc.nextLine();
            checkCommand(command);
        }
    }
    public void Stop(){
        this.stop = true;
    }
    public void start(){
        inputThread = new Thread(this::initialiseCl);
        inputThread.start();

    }


    @Override
    public void showChatMessage(Message message) {

        System.out.println("\nMessage from "+message.getNickName() + ": " + ((ChatMess)message).getMess() + "\n>");

    }

    @Override
    public void showPlayer(Message message) {
        PlayerView player = ((ShowPlayerInfo)message).getPlayer();
        System.out.println(player.toString());
        System.out.print(">");

    }

}
