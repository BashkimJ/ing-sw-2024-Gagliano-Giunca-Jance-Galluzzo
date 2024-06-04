package main.java.it.polimi.ingsw.Network.socket;

import main.java.it.polimi.ingsw.Exceptions.GameExc.PlayerNotFoundException;
import main.java.it.polimi.ingsw.Exceptions.GameExc.PlayersLimitExceededException;
import main.java.it.polimi.ingsw.Network.ClientHandler;
import main.java.it.polimi.ingsw.Network.Messages.Message;
import main.java.it.polimi.ingsw.Network.Messages.MessageType;
import main.java.it.polimi.ingsw.Network.Messages.Ping;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static main.java.it.polimi.ingsw.Network.Messages.MessageType.Login_Req;

/**
 * This class implements the ClientHandler interface using the socket logic.
 */
public class SocketClientHandler implements ClientHandler,Runnable {
    private Socket socketClient;
    private SocketServer socketServer;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private boolean isConnected;
    private final Object inputLockObject;
    private final  Object outputLockObject;
    private ScheduledExecutorService pingService;
    private String NickName;
    public SocketClientHandler(SocketServer socketServer, Socket socketClient){
        this.socketServer = socketServer;
        this.socketClient = socketClient;
        isConnected = true;
        inputLockObject = new Object();
        outputLockObject = new Object();
        this.pingService = Executors.newScheduledThreadPool(1);
        try{
            this.out = new ObjectOutputStream(this.socketClient.getOutputStream());
            this.in = new ObjectInputStream(this.socketClient.getInputStream());

        } catch (IOException e) {
            System.out.println("Couldn't read objects, handler won't initialize properly and no thread will start..");
            e.printStackTrace();
        }
        ping();
    }

    /**
     * Creates a separated thread which constantly listens for new messages arriving from the client side.The messages
     * once received, are passed to the SocketServer object associated to the ClientHandler.
     */
    @Override
    public void run() {
            System.out.println("Client connected...");
                while (!Thread.currentThread().isInterrupted()) {
                    synchronized (inputLockObject) {
                        Message message = null;
                        try {
                            message = (Message) in.readObject();
                            if (message != null && message.getType() != MessageType.Ping) {
                                System.out.println("Message received from "  +getNickName());
                            }
                        }
                        catch (IOException | ClassNotFoundException e) {
                            Thread.currentThread().interrupt();
                        }
                        if (message != null) {
                                socketServer.onMessageReceived(message,this);
                        }
                    }
                }


    }

    /**
     * Sends a message to the client using the ObjectOutputStream object of the SocketClient.
     * @param message The message to be sent to the client.
     */

    @Override
    public void sendMessage(Message message) {

            try {
                synchronized (outputLockObject) {
                    out.writeObject(message);
                    out.flush();
                    out.reset();
                    out.flush();
                }
            } catch (IOException e) {
                System.out.println("I/O error");
                disconnect();
            }
    }

    /**
     * Responsible for the disconnection of the SocketClient associated to the ClientHandler.
     */
    @Override
    public void disconnect() {
           if(!isConnected()){
               return;
           }
           else{
               try{
                   if(!socketClient.isClosed()){
                       socketClient.close();
                   }
               }catch(IOException e){
                   e.printStackTrace();
               }

           }
           Thread.currentThread().interrupt();
           this.isConnected = false;
           pingService.shutdown();

        try {
            socketServer.ClientDisconnection(this);
        } catch (PlayerNotFoundException e) {
            System.out.println("Couldn't find player");
        }

    }

    /**
     *Indicates if the SocketClient is connected.
     * @return True if connected and false otherwise.
     */
    @Override
    public boolean isConnected() {
        return isConnected;
    }

    /**
     * Implements a simple ping service to keep track of the connection
     */
    @Override
    public void ping(){
        pingService.scheduleAtFixedRate(()->
                sendMessage(new Ping()),
                0,
                5000,
                TimeUnit.MILLISECONDS
                );
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }
    public String getNickName(){
        return NickName;
    }
}
