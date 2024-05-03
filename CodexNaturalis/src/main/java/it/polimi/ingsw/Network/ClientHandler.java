package main.java.it.polimi.ingsw.Network;

import main.java.it.polimi.ingsw.Network.Messages.Message;

import java.rmi.Remote;

public interface ClientHandler extends Remote {
    abstract void sendMessage(Message message);
    abstract void Disconnect();
    abstract boolean isConnected();
}
