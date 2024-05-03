package main.java.it.polimi.ingsw.Network;

import main.java.it.polimi.ingsw.Network.Messages.*;
import main.java.it.polimi.ingsw.Network.rmi.RemoteClientInstance;
import main.java.it.polimi.ingsw.Network.socket.SocketClient;
import main.java.it.polimi.ingsw.View.View;

import java.io.IOException;
import java.rmi.RemoteException;

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

        }
    }
    public void updateObjCard(Message message){
        client.sendMessage(message);
    }

}
