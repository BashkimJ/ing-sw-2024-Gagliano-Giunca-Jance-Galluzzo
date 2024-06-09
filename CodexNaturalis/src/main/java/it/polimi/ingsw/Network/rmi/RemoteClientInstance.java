package it.polimi.ingsw.Network.rmi;

import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Network.ClientManager;
import it.polimi.ingsw.Network.Messages.*;
import it.polimi.ingsw.View.TUI;
import it.polimi.ingsw.View.View;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Class that implements the RMI logic for the Client
 */
public class RemoteClientInstance extends Client implements  RemoteClient,Runnable {
    private RemoteServer server;
    ExecutorService readService =Executors.newSingleThreadExecutor();
    private ScheduledExecutorService pingService;


    /**
     * Initializes the Client as RemoteClient.
     * @param ipAddress The address of the server used to get the registry.
     * @param clientManager The ClientManager object related to the Client.
     * @throws RemoteException In cases when couldn't find the registry.
     */
    public RemoteClientInstance(String ipAddress, ClientManager clientManager) throws RemoteException{
        connect(ipAddress);
        pingService = Executors.newScheduledThreadPool(1);
        readService  =  Executors.newSingleThreadExecutor();
        ping();
        setClientManager(clientManager);
    }

    private void connect(String IP) throws RemoteException{
        Registry registry = LocateRegistry.getRegistry(IP,1099);
        try {
            server =(RemoteServer) registry.lookup("Server");
        } catch (NotBoundException e) {
            System.out.println("Couldn't find server");
        }
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
                }
            } catch (RemoteException e) {
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
            pingService.shutdown();
            clientManager.update(new ErrorMessage("Disconnected please try to close and reopen the app. To reconnect you need to use " +
                    "the previous name."));
            System.exit(0);
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

    /**
     * Implements a simple ping service to keep track of the server.
     */
    public void ping(){
        pingService.scheduleAtFixedRate(()->
                        sendMessage(new Ping()),
                0,
                1000,
                TimeUnit.MILLISECONDS
        );
    }

}
