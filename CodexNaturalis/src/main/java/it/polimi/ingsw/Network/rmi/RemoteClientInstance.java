package main.java.it.polimi.ingsw.Network.rmi;

import main.java.it.polimi.ingsw.Network.Client;
import main.java.it.polimi.ingsw.Network.ClientManager;
import main.java.it.polimi.ingsw.Network.Messages.LoginRequest;
import main.java.it.polimi.ingsw.Network.Messages.Message;
import main.java.it.polimi.ingsw.Network.Messages.MessageType;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RemoteClientInstance extends Client implements  RemoteClient,Runnable {
    private RemoteServer server;
    ExecutorService readService =Executors.newSingleThreadExecutor();
    public RemoteClientInstance(String ipAddress, ClientManager clientManager) throws RemoteException {
        Registry registry = LocateRegistry.getRegistry(ipAddress,1099);
        try {
            server =(RemoteServer) registry.lookup("Server");
        } catch (NotBoundException e) {
            System.out.println("Couldn't find server");
            return;
        }
        setClientManager(clientManager);
    }

    @Override
    public void run() {
        //Non fa nulla, perche non ho bisogno dei thread
    }

    @Override
    public void sendMessage(Message message) {
            try {
                server.messageToServer(message,this);
            } catch (RemoteException e) {
                System.out.println("Couldn't send message");
                this.Disconnect();
            }
    }

    @Override
    public void receiveMessage() {
    }

    @Override
    public void Disconnect() {
        if(server!=null){
            this.server = null;

        }
    }

    @Override
    public void messageToClient(Message message) throws RemoteException {
          readService.execute(()->{
                  clientManager.update(message);

          });
    }
}
