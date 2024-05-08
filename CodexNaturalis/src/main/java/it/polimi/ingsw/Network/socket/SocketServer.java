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

public class SocketServer implements Runnable{
    private int port;
    private String address;
    private int defaultPort = 6000;
    private ServerSocket socketServer;
    private Map<String, ClientHandler> clientHandler;
    private GameController gameController;

    public SocketServer(GameController gameController,int port) {
        this.port = port;
        this.gameController = gameController;
        this.clientHandler = Collections.synchronizedMap(new HashMap<String,ClientHandler>());

    }
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

    private void addClient(String NickName, ClientHandler clientHandler) throws PlayersLimitExceededException {
        VirtualView virtualview =  new VirtualView(clientHandler);
        if(gameController.getState().equals(GameState.Lobby_State)){
            addNewClient(NickName, clientHandler,virtualview);
        }
        else{
            //TO DO addClientRecconection(NickName,clientHander,irtualview);
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
    public void ClientDisconnection(ClientHandler clientHandler ) throws PlayerNotFoundException {
        this.clientHandler.remove(clientHandler);
        gameController.removePlayer(getName(clientHandler));
        //Comunnica con la View.....

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
