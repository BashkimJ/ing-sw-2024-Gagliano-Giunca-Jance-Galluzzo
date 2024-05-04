package main.java.it.polimi.ingsw.Network;

import main.java.it.polimi.ingsw.Network.Messages.Message;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public abstract class Client extends UnicastRemoteObject {
    protected ClientManager clientManager;

    protected Client() throws RemoteException {
    }

    public abstract void sendMessage(Message message);
    public abstract void receiveMessage();
    public abstract void Disconnect();
    public ClientManager getClientMAnager(){
        return this.clientManager;
    }
    public void setClientManager(ClientManager clientMAnager) {
        this.clientManager = clientMAnager;
    }
}
