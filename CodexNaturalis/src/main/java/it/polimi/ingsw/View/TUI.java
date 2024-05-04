package main.java.it.polimi.ingsw.View;

import main.java.it.polimi.ingsw.Network.ClientManager;
import main.java.it.polimi.ingsw.Network.Messages.ChooseObjResp;
import main.java.it.polimi.ingsw.Network.Messages.Message;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.*;

public class TUI implements View{
    private ClientManager clientManager;
    private Scanner sc;
    public TUI(){
        sc = new Scanner(System.in);
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
        clientManager.updateObjCard(new ChooseObjResp(clientManager.getNickName(),chosenCard));
    }

}
