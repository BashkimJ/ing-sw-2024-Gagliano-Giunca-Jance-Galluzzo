package main.java.it.polimi.ingsw.Network.rmi;

import main.java.it.polimi.ingsw.Network.Messages.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteClient extends Remote {
    /**
     * Method called from the server when it needs to send a message.
     * @param message The message to send to the client.
     */
    public void messageToClient(Message message)throws RemoteException;
}
