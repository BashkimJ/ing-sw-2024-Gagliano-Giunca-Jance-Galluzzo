package main.java.it.polimi.ingsw.Network.socket;

import main.java.it.polimi.ingsw.Exceptions.GameExc.PlayerNotFoundException;
import main.java.it.polimi.ingsw.Exceptions.GameExc.PlayersLimitExceededException;
import main.java.it.polimi.ingsw.Network.ClientHandler;
import main.java.it.polimi.ingsw.Network.Messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import static main.java.it.polimi.ingsw.Network.Messages.MessageType.Login_Req;

public class SocketClientHandler implements ClientHandler,Runnable {
    private Socket socketClient;
    private SocketServer socketServer;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private boolean isConnected;
    private final Object inputLockObject;
    private final  Object outputLockObject;

    public SocketClientHandler(SocketServer socketServer, Socket socketClient){
        this.socketServer = socketServer;
        this.socketClient = socketClient;
        isConnected = true;
        inputLockObject = new Object();
        outputLockObject = new Object();
        try{
            this.out = new ObjectOutputStream(this.socketClient.getOutputStream());
            this.in = new ObjectInputStream(this.socketClient.getInputStream());

        } catch (IOException e) {
            System.out.println("Couldnt read objects, handler wont initialize properly and no thread will start..");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
            System.out.println("Client connected...");
                while (!Thread.currentThread().isInterrupted()) {
                    synchronized (inputLockObject) {
                        Message message = null;
                        try {
                            message = (Message) in.readObject();
                            if (message != null) {
                                System.out.println("Message received");
                            }
                        }catch(SocketTimeoutException e){
                            continue;
                        }
                        catch (IOException | ClassNotFoundException e) {
                            try {
                                socketClient.close();
                            } catch (IOException ex) {
                                System.out.println("Couldn't close socket...");
                            }
                            isConnected = false;
                            Thread.currentThread().interrupt();
                            break;
                        }
                        if (message != null) {
                                socketServer.onMessageReceived(message,this);
                        }
                    }
                }
                try {
                    socketServer.ClientDisconnection(this);
                } catch (PlayerNotFoundException e1) {
                    e1.printStackTrace();
                }

    }

    @Override
    public void sendMessage(Message message) {

            try {
                synchronized (outputLockObject) {
                    out.writeObject(message);
                    out.reset();
                }
            } catch (IOException e) {
                System.out.println("I/O error");
                Disconnect();
            }
    }

    @Override
    public void Disconnect() {
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

        try {
            socketServer.ClientDisconnection(this);
        } catch (PlayerNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean isConnected() {
        return isConnected;
    }
}
