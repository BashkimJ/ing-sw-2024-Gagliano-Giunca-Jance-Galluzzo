package it.polimi.ingsw.Network.rmi;

import it.polimi.ingsw.Exceptions.GameExc.PlayerNotFoundException;
import it.polimi.ingsw.Network.ClientHandler;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.Ping;

import java.rmi.RemoteException;
import java.util.concurrent.*;

/**
 * Implements the ClientHandler interface using the RMI logic. All the methods are similar to the SocketClientHandler.See its documentation.
 */
public class RemoteClientHandler implements ClientHandler {
    private RemoteClient client;
    private RemoteServerInstance server;
    private boolean isConnected;
    private ScheduledExecutorService pingService;
    private String NickName;
    public RemoteClientHandler(RemoteClient client, RemoteServerInstance remoteServerInstance) {
        this.client = client;
        this.server = remoteServerInstance;
        isConnected = true;
        pingService = Executors.newScheduledThreadPool(1);
        ping();
    }

    @Override
    public void sendMessage(Message message) {
        try {
            client.messageToClient(message);
        } catch (RemoteException e) {
            System.out.println("Couldn't send message...");
            disconnect();
        }
    }

    @Override
    public void disconnect() {
        if(client!=null){
            this.client = null;
            isConnected = false;
            try {
                server.ClientDisconnection(this);
                pingService.shutdown();
            } catch (PlayerNotFoundException e) {
                System.out.println("Couldn't find player");
            }
        }

    }
    public void ping(){
        pingService.scheduleAtFixedRate(()->
                        sendMessage(new Ping()),
                0,
                5000,
                TimeUnit.MILLISECONDS
        );
    }

    @Override
    public boolean isConnected() {
        return this.isConnected;
    }

    @Override
    public void setNickName(String nickName) {
        NickName = nickName;
    }

    @Override
    public String getNickName(){
        return NickName;
    }
}