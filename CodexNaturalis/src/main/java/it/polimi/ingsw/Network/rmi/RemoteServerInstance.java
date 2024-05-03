package main.java.it.polimi.ingsw.Network.rmi;

import main.java.it.polimi.ingsw.Controller.GameController;
import main.java.it.polimi.ingsw.Controller.GameState;
import main.java.it.polimi.ingsw.Exceptions.GameExc.PlayerNotFoundException;
import main.java.it.polimi.ingsw.Exceptions.GameExc.PlayersLimitExceededException;
import main.java.it.polimi.ingsw.Network.Client;
import main.java.it.polimi.ingsw.Network.ClientHandler;
import main.java.it.polimi.ingsw.Network.Messages.ErrorMessage;
import main.java.it.polimi.ingsw.Network.Messages.Message;
import main.java.it.polimi.ingsw.Network.Messages.MessageType;
import main.java.it.polimi.ingsw.View.VirtualView;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RemoteServerInstance extends UnicastRemoteObject implements RemoteServer {
    private Map<String, ClientHandler> clientHandler;
    private GameController gameController;

    public RemoteServerInstance(GameController gameController) throws RemoteException {
       try{

           Registry registry = LocateRegistry.createRegistry(1099);
           registry.bind("Server",this);
           System.out.println("Rmi server started");

       } catch (AlreadyBoundException e) {
           System.out.println("Couldn't start RMI server");
           return;
       }
       this.gameController = gameController;
       clientHandler = Collections.synchronizedMap(new HashMap<String,ClientHandler>());
    }

    @Override
    public void messageToServer(Message message,RemoteClient client){
        if (message.getType() == MessageType.Login_Req) {
            addClient(message.getNickName(), client);
        } else {
            gameController.onMessageReceived(message);
        }
    }

    private void addClient(String NickName, RemoteClient client)  {
        RemoteClientHandler clientHandler= new RemoteClientHandler(client,this);
        VirtualView virtualview =  new VirtualView(clientHandler);
        if(gameController.getState().equals(GameState.Lobby_State)){
            try {
                addNewClient(NickName, clientHandler,virtualview);
            } catch (PlayersLimitExceededException e) {
                System.out.println("Couldn't add player");
            }
        }
        else{
            //TO DO clientReconnection(NickName,clientHander,irtualview);
        }

    }

    private void addNewClient(String NickName, ClientHandler clientHandler,VirtualView virtualView) throws PlayersLimitExceededException {
        if(gameController.checkNickName(NickName)){
            this.clientHandler.put(NickName,clientHandler);
            gameController.firstLogin(NickName, virtualView);
            virtualView.showLogin(true,true);
            if(gameController.getState()==GameState.In_Game){
                gameController.objectiveCardOptionsSender();
            }
        }
        else{
            virtualView.showLogin(false,true);
        }
    }

    public void ClientDisconnection(ClientHandler clientHandler ) throws PlayerNotFoundException {
        this.clientHandler.remove(clientHandler);
        gameController.removePlayer(getName(clientHandler));
        clientHandler.sendMessage(new ErrorMessage("Disconnected..."));

    }

    private String getName(ClientHandler clientHandler){
        for(String name:this.clientHandler.keySet()){
            if(clientHandler.equals(this.clientHandler.get(name))){
                return name;
            }
        }
        return null;
    }
}
