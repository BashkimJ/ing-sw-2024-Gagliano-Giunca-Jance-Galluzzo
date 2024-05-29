package main.java.it.polimi.ingsw.Network;

import main.java.it.polimi.ingsw.Model.Cards.InitialCard;
import main.java.it.polimi.ingsw.Model.Player.PlayerView;
import main.java.it.polimi.ingsw.Network.Messages.*;
import main.java.it.polimi.ingsw.Network.rmi.RemoteClientInstance;
import main.java.it.polimi.ingsw.Network.socket.SocketClient;
import main.java.it.polimi.ingsw.View.TUI;
import main.java.it.polimi.ingsw.View.View;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Objects;

import static java.lang.Thread.sleep;

/**
 * Class that acts as a bridge between the client and his view.
 */
public class ClientManager {
    private View view;
    private Client client;
    private String NickName;

    /**
     * Constructor to initialise the ClientManager object
     * @param view The view asssociated to the ClientManager
     */
    public ClientManager(View view){
        this.view = view;
    }

    /**
     * Receives all the info from the view necessary to stabilise a coonection
     * @param IpAddress The ip address of the server to connect
     * @param port The port the server is listenning for new connections
     * @param RMI Boolean. Indicates if the connections is RMI or Socket.
     */
    public void onUpdateServerInfo(String IpAddress, int port,boolean RMI){
        if(!RMI){
            try{

                client = new SocketClient(this,IpAddress, port);
                client.receiveMessage();
                view.askNickName();
            }catch(IOException e){
               view.errorMessage("Unable to connect...");
               System.exit(0);
            }
        }
        else{
            try {
                client  = new RemoteClientInstance(IpAddress,this);
                view.askNickName();
            } catch (RemoteException e) {
                view.errorMessage("Unable to connect...");
                System.exit(0);
            }
        }
    }

    /**
     * Getter method to retrieve the nickname of the connected client.
     * @return A string which corresponds to the client nickname.
     */
    public String getNickName(){
        return this.NickName;
    }

    /**
     * Receives the nickname chosen by the client to login.
     * It also sends a login message which contains the nickname chosen.
     * @param NickName The string entered by the client.
     */
    public void updateNickName(String NickName){
        this.NickName = NickName;
        client.sendMessage(new LoginRequest(NickName));
    }

    /**
     * Sends the number of players chosen from the first logged player.
     * @param Numplayers The number of player chosen.
     */
    public void updateNumplayers(int Numplayers){
        client.sendMessage(new PlayerNumberResponse(Numplayers,getNickName()));
    }

    /**
     * Method used to send a chat message to another player.
     * @param NickName The destination player where the message is being sent.
     * @param message The string being sent.
     */
    public void sendMessage(String NickName, String message){
        client.sendMessage(new ChatMess(getNickName(),NickName,message));
    }

    /**
     * Sends a ShowPlayerMess to the server in order to retrieve information about a player.
     * @param NickName The nickname of the player to ask info about.
     */
    public void showPlayer(String NickName){
        if(NickName.equals("me")){
            NickName = getNickName();
        }
        client.sendMessage(new ShowPlayerInfo(getNickName(),null,NickName));
    }

    /**
     * Updates the view regarding the message type received from the server.
     * @param message The message received from the server.
     */
    public void  update(Message message){
        switch(message.getType()){
            case Error_Message ->{
                view.errorMessage(message.toString());
                if(message.toString().equals("Error: You tried entering the game before the main player chose the max num of players")){
                    this.client.Disconnect();
                    System.exit(0);
                }
            }
            case Login_Rpl -> {
                view.showLogin(((LoginReply) message).Connected(),((LoginReply) message).nameAccepted());
            }
            case Player_Num_Req -> {
                view.askNumPlayers();
            }
            case Choose_Obj_Req -> {
                view.chooseObjectiveCard(message);
            }
            case Initial_Card_Mess -> {
                view.showInitial(message);

            }
            case Game_Started -> {
                view.alertGameStarted(message);
            }
            case Chat_Mess -> {
                view.showChatMessage(message);
            }
            case Player_Info -> {
                view.showPlayer(message);
            }
            case Game_St -> {
                view.showGameInfo(message);
            }
            case Winner_Mess -> {
                view.winner(((WinnerMess)message).getWinner());
            }
            case Player_Move_Resp -> {
                view.afterPlayerMove(message);
            }

        }
    }

    /**
     * Sends a message to the server containing the chosen objective card from the player.
     * @param message The message to be sent to the server.
     */
    public void updateObjCard(Message message){
        client.sendMessage(message);
    }

    /**
     * Sends a PlaceInitialCard message to the server containing the side of the initial card chosen by the player.
     * @param Side It is an int: If 1 the front side has been chosen, if 2 the retro one.
     */
    public void placeInitial(int Side){
        client.sendMessage(new PlaceInitialCard(getNickName(),Side));
    }

    /**
     * Sends a new PlaceCardMess to the server containing the info of the card to be played from the player.
     * @param cardID The id of the card to play.
     * @param side The side of the card: if "-f" front and if "-r" retro.
     * @param pos A list of size 2 containing the coordinates of the card to be played.
     */
    public void placeCard(int cardID,String side,int[] pos){
        client.sendMessage(new PlaceCardMess(getNickName(),cardID,side,pos));
    }

    /**
     * Sends a message to the server asking for information about the current state of the game.
     */
    public void showGameSt(){
        client.sendMessage(new ShowGameMess(getNickName()));
    }

    /**
     * Sends a new PickCardMessage containing the necessary info of the card the player wants to pick.
     * @param CardID The id of the card to pick.
     */
    public void pickCard(int CardID){
        client.sendMessage(new PickCardMess(getNickName(),CardID));
    }


}
