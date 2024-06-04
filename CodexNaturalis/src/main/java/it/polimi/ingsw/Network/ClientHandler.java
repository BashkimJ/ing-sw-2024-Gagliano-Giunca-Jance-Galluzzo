package main.java.it.polimi.ingsw.Network;

import main.java.it.polimi.ingsw.Network.Messages.Message;

import java.rmi.Remote;

/**
 * Handler interface used by the server to handle the communications with a certain client
 */
public interface ClientHandler extends Remote {
    /**
     * Sends a message to the associated client.
     * @param message The message to be sent to the client.
     */
    abstract void sendMessage(Message message);

    /**
     * Allows the server to execute the disconnection of a client.
     */
    abstract void disconnect();

    /**
     * Shows if a client associated to this ClientHandler is connected or not
     * @return Boolean, true if connected, false otherwise.
     */
    abstract boolean isConnected();

    /**
     * Method to constantly send a ping message to the client in order to keep track of his connection.
     */
    abstract void ping();

    abstract String getNickName();
    abstract void setNickName(String nickName);
}
