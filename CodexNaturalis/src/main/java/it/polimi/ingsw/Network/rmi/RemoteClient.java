package main.java.it.polimi.ingsw.Network.rmi;

import main.java.it.polimi.ingsw.Network.Messages.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteClient extends Remote {
    public void messageToClient(Message message)throws RemoteException;
}
