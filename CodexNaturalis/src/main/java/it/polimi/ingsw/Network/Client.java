package it.polimi.ingsw.Network;

import it.polimi.ingsw.Network.Messages.Message;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Class that represents the client
 */
public abstract class Client extends UnicastRemoteObject {
    protected ClientManager clientManager;

    protected Client() throws RemoteException {
    }

    /**
     * Abstract method to send a message to the server
     * @param message The message to send
     */
    public abstract void sendMessage(Message message);

    /**
     * This method waits for potential messages sent from the server side
     */
    public abstract void receiveMessage();

    /**
     * Allows the client to disconnect
     */
    public abstract void Disconnect();

    /**
     * Getter method to retrieve the ClientManager associated to the client class
     * @return The ClientManager object
     */
    public ClientManager getClientMAnager(){
        return this.clientManager;
    }

    /**
     * Setter method to set the ClientManager associated to the client class
     * @param clientMAnager The ClientManager object to set
     */
    public void setClientManager(ClientManager clientMAnager) {
        this.clientManager = clientMAnager;
    }

}
