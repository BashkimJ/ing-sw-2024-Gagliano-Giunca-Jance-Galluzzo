package it.polimi.ingsw.Network.rmi;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.GameState;
import it.polimi.ingsw.Exceptions.GameExc.PlayerNotFoundException;
import it.polimi.ingsw.Exceptions.GameExc.PlayersLimitExceededException;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Network.ClientHandler;
import it.polimi.ingsw.Network.Messages.ErrorMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.View.VirtualView;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * The instance of the server that implements the RMI logic for connections.
 */
public class RemoteServerInstance extends UnicastRemoteObject implements RemoteServer {
    private List<ClientHandler> clientHandler;
    private GameController gameController;

    /**
     * Initialises the server as RMI.
     * @param gameController The controller associated to the server.
     * @throws RemoteException When the server couldn't initialise.
     */
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
       clientHandler = Collections.synchronizedList(new ArrayList<ClientHandler>());
    }

    /**
     * @param message The message to pass to the server.
     * @param client The client sending the message.
     */
    @Override
    public void messageToServer(Message message,RemoteClient client){
        if(message.getType()!=MessageType.Ping){
            System.out.println("Message received: RMI connection");
        }
        if (message.getType() == MessageType.Login_Req) {
            addClient(message.getNickName(), client);
        } else {
            gameController.onMessageReceived(message);
        }
    }

    /**
     * This method is responsible for the login phase when a playe ask to connect specifying his nickname which will serve ass an authentication credential.
     * @param NickName The nickname of the player to login.
     * @param client The RemoteClient object associated to the player.
     */
    private void addClient(String NickName, RemoteClient client)  {
        RemoteClientHandler clientHandler= new RemoteClientHandler(client,this);
        clientHandler.setNickName(NickName);
        VirtualView virtualview =  new VirtualView(clientHandler);
        if(gameController.getState().equals(GameState.Lobby_State)){
            try {
                addNewClient(NickName, clientHandler,virtualview);
            } catch (PlayersLimitExceededException e) {
                System.out.println("Couldn't add player");
            }
        }
        else if(gameController.getState().equals(GameState.In_Game)){
           this.clientHandler.add(clientHandler);
            gameController.reconnect(NickName,virtualview);

        }

    }
    /**
     * Method called from the public method addClient when a player is connecting for the first time.
     * We can see this method as a very simple sign in request.
     * @param NickName The nickname chosen by the player to connect.
     * @param clientHandler The ClientHandler object associated to the player.
     * @param virtualView The VirtualView object associated to the ClientHandler object of the player.
     */
    private void addNewClient(String NickName, ClientHandler clientHandler,VirtualView virtualView) throws PlayersLimitExceededException {
        if(gameController.checkNickName(NickName)){
            this.clientHandler.add(clientHandler);
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
    /**
     * Method to manages the disconnection of a player(client).
     * @param clientHandler The ClientHandler object associated to the player which is disconnecting from the server.
     * @throws PlayerNotFoundException Exception thrown when the player was not found in the game.
     */
    public void ClientDisconnection(ClientHandler clientHandler ) throws PlayerNotFoundException {
        String name = clientHandler.getNickName();
        System.out.println(name + " Disconnecting...");
        gameController.removePlayer(name);
        this.clientHandler.remove(clientHandler);
    }

}
