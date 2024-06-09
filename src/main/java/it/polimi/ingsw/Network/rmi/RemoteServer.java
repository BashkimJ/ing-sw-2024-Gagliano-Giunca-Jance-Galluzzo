package it.polimi.ingsw.Network.rmi;

import it.polimi.ingsw.Network.ClientHandler;
import it.polimi.ingsw.Network.ClientManager;
import it.polimi.ingsw.Network.Messages.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The remote interface representing the server in a RMI connection.
 */
public interface RemoteServer extends Remote {
    /**
     * Remote methode to be called from a client when he tries to send a message.
     * @param message The message to pass to the server.
     * @param client The client sending the message.
     * @throws RemoteException In disconnection cases.
     */
    void messageToServer(Message message,RemoteClient client) throws RemoteException;


}
