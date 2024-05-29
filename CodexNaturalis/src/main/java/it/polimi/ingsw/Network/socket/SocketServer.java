package main.java.it.polimi.ingsw.Network.socket;

import main.java.it.polimi.ingsw.Controller.GameController;
import main.java.it.polimi.ingsw.Controller.GameState;
import main.java.it.polimi.ingsw.Exceptions.GameExc.PlayerNotFoundException;
import main.java.it.polimi.ingsw.Exceptions.GameExc.PlayersLimitExceededException;
import main.java.it.polimi.ingsw.Network.ClientHandler;
import main.java.it.polimi.ingsw.Network.Messages.Message;
import main.java.it.polimi.ingsw.Network.Messages.MessageType;
import main.java.it.polimi.ingsw.View.VirtualView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the Server accepting only socket connections.
 * It also keeps track of the ClientHandlers.
 */
public class SocketServer implements Runnable{
    private final int port;
    private String address;
    private ServerSocket socketServer;
    private Map<String, ClientHandler> clientHandler;
    private GameController gameController;

    /**
     * The constructor that initialise the socket server.
     * @param gameController The controller associated to the server
     * @param port The port in which the server is listening.
     */
    public SocketServer(GameController gameController,int port) {
        this.port = port;
        this.gameController = gameController;
        this.clientHandler = Collections.synchronizedMap(new HashMap<String,ClientHandler>());

    }

    /**
     * Creates a separated thread which constantly accepts new connections.
     */
    @Override
    public void run()  {
        try{
            this.socketServer = new ServerSocket(this.port);
            System.out.println("Socket started...");
        } catch (IOException e) {
            System.out.println("Couldn't Start server...");
            return;
        }
        while(!Thread.currentThread().isInterrupted()){
            try {
                Socket clientSocket = socketServer.accept();
                System.out.println("New connection requested");
                clientSocket.setSoTimeout(10000);
                SocketClientHandler ClientHandler = new SocketClientHandler(this, clientSocket);
                Thread thread = new Thread(ClientHandler);
                thread.start();

            }catch(IOException e){
                System.out.println(e.getMessage());
            }

        }
    }

    /**
     * This method is responsible for the login phase when a playe ask to connect specifying his nickname which will serve ass an authentication credential.
     * @param NickName The nickname of the player to login.
     * @param clientHandler The ClientHandler object associated to the player.
     * @throws PlayersLimitExceededException Throws this exception when trying to log in a player exceeding the maximum number of players the game must contain.
     */
    private void addClient(String NickName, ClientHandler clientHandler) throws PlayersLimitExceededException {
        VirtualView virtualview =  new VirtualView(clientHandler);
        if(gameController.getState().equals(GameState.Lobby_State)){
            addNewClient(NickName, clientHandler,virtualview);
        }
        else if(gameController.getState().equals(GameState.In_Game)){
            this.clientHandler.put(NickName,clientHandler);
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

    /**
     * This method passes a message received from the client to the controller which is responsible to elaborate it.
     * @param message The message to pass to the controller.
     * @param client The ClientHandler associated to player(Client) who sent this message.
     */
    public void onMessageReceived(Message message,ClientHandler client){
        if(message.getType()==MessageType.Login_Req){
            try {
                addClient(message.getNickName(),client);
            } catch (PlayersLimitExceededException e) {
                System.out.println("Couldn't add client....");
            }
        }
        else{
            gameController.onMessageReceived(message);
            if(gameController.getState().equals(GameState.End_Game)){
                this.gameController = new GameController();
            }
        }
    }

    /**
     * Method to manages the disconnection of a player(client).
     * @param clientHandler The ClientHandler object associated to the player which is disconnecting from the server.
     * @throws PlayerNotFoundException Exception thrown when the player was not found in the game.
     */
    public void ClientDisconnection(ClientHandler clientHandler ) throws PlayerNotFoundException {
        String name = getName(clientHandler);
        System.out.println(name + " Disconnecting...");
        gameController.removePlayer(name);
        this.clientHandler.remove(name);

    }

    /**
     * Method that returns the name of the player associated with a specified ClientHandler object.
     * @param clientHandler The ClientHandler object, for whom we want to know the name.
     * @return The name(String).If the clientHandler is not contained in the server it will return "No name..."
     * in order to not return Null.
     */
    private String getName(ClientHandler clientHandler){
        for(String name:this.clientHandler.keySet()){
            if(clientHandler.equals(this.clientHandler.get(name))){
                return name;
            }
        }
        return "No name...";
    }


}
