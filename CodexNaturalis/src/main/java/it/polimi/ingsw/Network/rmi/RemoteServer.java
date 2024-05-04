package main.java.it.polimi.ingsw.Network.rmi;

import main.java.it.polimi.ingsw.Network.ClientHandler;
import main.java.it.polimi.ingsw.Network.ClientManager;
import main.java.it.polimi.ingsw.Network.Messages.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteServer extends Remote {
    void messageToServer(Message message,RemoteClient client) throws RemoteException;

}
