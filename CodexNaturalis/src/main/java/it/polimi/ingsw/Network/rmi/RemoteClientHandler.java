package main.java.it.polimi.ingsw.Network.rmi;

import main.java.it.polimi.ingsw.Exceptions.GameExc.PlayerNotFoundException;
import main.java.it.polimi.ingsw.Network.ClientHandler;
import main.java.it.polimi.ingsw.Network.Messages.Message;

import java.rmi.RemoteException;

public class RemoteClientHandler implements ClientHandler {
    private RemoteClient client;
    private RemoteServerInstance server;
    private boolean isConnected;
    public RemoteClientHandler(RemoteClient client, RemoteServerInstance remoteServerInstance) {
        this.client = client;
        this.server = server;
        isConnected = true;
    }

    @Override
    public void sendMessage(Message message) {
        try {
            client.messageToClient(message);
        } catch (RemoteException e) {
            System.out.println("Couldn't send message...");
            Disconnect();
        }
    }

    @Override
    public void Disconnect() {
        if(client!=null){
            this.client = null;
            isConnected = false;
            try {
                server.ClientDisconnection(this);
            } catch (PlayerNotFoundException e) {
                System.out.println("Couldn't find player");
            }
        }

    }

    @Override
    public boolean isConnected() {
        return this.isConnected;
    }
}