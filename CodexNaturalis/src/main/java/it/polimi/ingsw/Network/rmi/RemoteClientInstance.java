package main.java.it.polimi.ingsw.Network.rmi;

import main.java.it.polimi.ingsw.Network.Client;
import main.java.it.polimi.ingsw.Network.ClientManager;
import main.java.it.polimi.ingsw.Network.Messages.ErrorMessage;
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

/**
 * Class that implements the RMI logic for the Client
 */
public class RemoteClientInstance extends Client implements  RemoteClient,Runnable {
    private RemoteServer server;
    ExecutorService readService =Executors.newSingleThreadExecutor();

    /**
     * Initializes the Client as RemoteClient.
     * @param ipAddress The address of the server used to get the registry.
     * @param clientManager The ClientManager object related to the Client.
     * @throws RemoteException In cases when couldn't find the registry.
     */
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
    }

    /**
     * Sends a message to the serve.
     * @param message The message to send
     */
    @Override
    public void sendMessage(Message message) {
            try {
                if(server!=null) {
                    server.messageToServer(message, this);
                }else{
                    clientManager.update(new ErrorMessage("Disconnected please try to close and reopen the app. To reconnect you need to use " +
                            "the previous name."));
                }
            } catch (RemoteException e) {
                System.out.println("Couldn't send message");
                this.Disconnect();
            }
    }

    @Override
    public void receiveMessage() {
    }

    /**
     * Handles the disconnection of the client.
     */
    @Override
    public void Disconnect() {
        if(server!=null) {
            this.server = null;
            clientManager.update(new ErrorMessage("Disconnected please try to close and reopen the app. To reconnect you need to use " +
                    "the previous name."));
        }
    }

    /**
     * Remote method used from the server to send a message to the client.
     * @param message The message to send to the client.
     */
    @Override
    public void messageToClient(Message message) throws RemoteException {
          readService.execute(()->{
                  clientManager.update(message);

          });
    }
}
