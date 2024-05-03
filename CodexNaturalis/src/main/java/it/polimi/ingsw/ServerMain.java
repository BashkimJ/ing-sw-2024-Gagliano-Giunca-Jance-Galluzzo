package main.java.it.polimi.ingsw;

import main.java.it.polimi.ingsw.Controller.GameController;
import main.java.it.polimi.ingsw.Network.rmi.RemoteServer;
import main.java.it.polimi.ingsw.Network.rmi.RemoteServerInstance;
import main.java.it.polimi.ingsw.Network.socket.SocketServer;

import java.rmi.RemoteException;

public class ServerMain {
    public static void main(String[] args){
        GameController gameController = new GameController();
        SocketServer  socketServer= new SocketServer(gameController,6000);
        try {
            RemoteServer remoteServer = new RemoteServerInstance(gameController);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        Thread thread = new Thread(socketServer);
        thread.start();


    }
}
