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

public class ClientManager {
    private View view;
    private Client client;
    private String NickName;
    public ClientManager(View view){
        this.view = view;
    }

    public void onUpdateServerInfo(String IpAddress, int port,boolean RMI){
        if(!RMI){
            try{
                client = new SocketClient(this,IpAddress, port);
                client.receiveMessage();
                view.askNickName();
            }catch(IOException e){
               view.errorMessage("Unable to connect...");
            }
        }
        else{
            try {
                client  = new RemoteClientInstance(IpAddress,this);
                view.askNickName();
            } catch (RemoteException e) {
                view.errorMessage("Unable to connect...");
            }
        }
    }
    public String getNickName(){
        return this.NickName;
    }
    public void updateNickName(String NickName){
        this.NickName = NickName;
        client.sendMessage(new LoginRequest(NickName));
    }
    public void updateNumplayers(int Numplayers){
        client.sendMessage(new PlayerNumberResponse(Numplayers,getNickName()));
    }
    public void sendMessage(String NickName, String message){
        client.sendMessage(new ChatMess(getNickName(),NickName,message));
    }
    public void showPlayer(String NickName){
        if(NickName.equals("me")){
            NickName = getNickName();
        }
        client.sendMessage(new ShowPlayerInfo(getNickName(),null,NickName));
    }
    public void  update(Message message){
        switch(message.getType()){
            case Error_Message ->{
                view.errorMessage(message.toString());
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
            case Init_Cl -> {
                ((TUI)view).start();
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

        }
    }
    public void updateObjCard(Message message){
        client.sendMessage(message);
    }
    public void placeInitial(int Side){
        client.sendMessage(new PlaceInitialCard(getNickName(),Side));
    }
    public void placeCard(int cardID,String side,int[] pos){
        client.sendMessage(new PlaceCardMess(getNickName(),cardID,side,pos));
    }
    public void showGameSt(){
        client.sendMessage(new ShowGameMess(getNickName()));
    }
    public void pickCard(int CardID){
        client.sendMessage(new PickCardMess(getNickName(),CardID));
    }

}
