package it.polimi.ingsw.Network.socket;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.GameState;
import it.polimi.ingsw.Exceptions.GameExc.PlayerNotFoundException;
import it.polimi.ingsw.Exceptions.GameExc.PlayersLimitExceededException;
import it.polimi.ingsw.Network.ClientHandler;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.View.VirtualView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * This class represents the Server accepting only socket connections.
 * It also keeps track of the ClientHandlers.
 */
public class SocketServer implements Runnable{
    private final int port;
    private String address;
    private ServerSocket socketServer;
    private List<ClientHandler> clientHandler;
    private GameController gameController;

    /**
     * The constructor that initialise the socket server.
     * @param gameController The controller associated to the server
     * @param port The port in which the server is listening.
     */
    public SocketServer(GameController gameController,int port) {
        this.port = port;
        this.gameController = gameController;
        this.clientHandler = Collections.synchronizedList(new ArrayList<ClientHandler>());

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
                SocketClientHandler ClientHandler = new SocketClientHandler(this, clientSocket);
                Thread thread = new Thread(ClientHandler);
                thread.start();

            }catch(IOException e){
                System.out.println(e.getMessage());
            }

        }
    }

    /**
     * This method is responsible for the login phase when a player ask to connect specifying his nickname which will serve ass an authentication credential.
     * @param clientHandler The ClientHandler object associated to the player.
     * @throws PlayersLimitExceededException Throws this exception when trying to log in a player exceeding the maximum number of players the game must contain.
     */
    private void addClient(ClientHandler clientHandler) throws PlayersLimitExceededException {
        VirtualView virtualview =  new VirtualView(clientHandler);
        if(gameController.getState().equals(GameState.Lobby_State)){
            addNewClient(clientHandler,virtualview);
        }
        else if(gameController.getState().equals(GameState.In_Game)){
            this.clientHandler.add(clientHandler);
            gameController.reconnect(clientHandler.getNickName(),virtualview);


        }

    }

    /**
     * Method called from the public method addClient when a player is connecting for the first time.
     * We can see this method as a very simple sign in request.
     * @param clientHandler The ClientHandler object associated to the player.
     * @param virtualView The VirtualView object associated to the ClientHandler object of the player.
     */
    private void addNewClient(ClientHandler clientHandler,VirtualView virtualView) throws PlayersLimitExceededException {
        if(gameController.checkNickName(clientHandler.getNickName())){
            this.clientHandler.add(clientHandler);
            gameController.firstLogin(clientHandler.getNickName(), virtualView);
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
                client.setNickName(message.getNickName());
                addClient(client);
            } catch (PlayersLimitExceededException e) {
                System.out.println("Couldn't add client....");
            }
        }
        else{
            gameController.onMessageReceived(message);
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
