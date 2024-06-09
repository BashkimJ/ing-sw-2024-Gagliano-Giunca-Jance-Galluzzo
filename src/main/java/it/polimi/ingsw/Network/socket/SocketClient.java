package it.polimi.ingsw.Network.socket;

import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Network.ClientManager;
import it.polimi.ingsw.Network.Messages.ErrorMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.Ping;
import it.polimi.ingsw.View.TUI;
import it.polimi.ingsw.View.View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This class implements the methods of the abstract class of the Client using the socket logic.
 */
public class SocketClient extends Client {
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private final Object lockConn;


    /**
     * Initialises the Client as SocketClient
     * @param clientManager The ClientManager used to pass the messages received from the server.
     * @param IP The ip address of the server to connect the Client
     * @param port The port the server is listening.
     * @throws IOException When cannot open a new connection.
     */
     public SocketClient(ClientManager clientManager,String IP, int port) throws IOException {
         this.setClientManager(clientManager);
         this.socket = new Socket();
         connect(socket,IP,port);
         lockConn = new Object();
     }

     private void connect(Socket socket,String Ip,int port) throws IOException{
             this.socket.connect(new InetSocketAddress(Ip, port));
             output = new ObjectOutputStream(socket.getOutputStream());
             input = new ObjectInputStream(socket.getInputStream());
     }

    /**
     * Sends a message to the server.
     * @param message The message to send
     */
    @Override
    public void sendMessage(Message message) {
        try{
            output.writeObject(message);
            output.flush();
            output.reset();
            output.flush();

        }catch(IOException e){
            System.out.println("Couldn't send message");
            Disconnect();
        }
    }

    /**
     * A separated thread tha constantly waits for new messages from the server. Once the message is received is passed to the ClientManager.
     *
     */
    @Override
    public void receiveMessage() {
        Thread readService  = new Thread(()->{
            while(!Thread.currentThread().isInterrupted()){
                Message message = null;
                try{
                    message = (Message) input.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    Thread.currentThread().interrupt();
                    Disconnect();
                }
                if(message!=null && !message.getType().equals(MessageType.Ping)){
                    clientManager.update(message);
                }
            }
        });
        readService.start();
    }


    /**
     * Manages the disconnections of the client from the server.
     */
    @Override
    public void Disconnect() {
        synchronized (lockConn) {
            try {
                if (!socket.isClosed()) {
                    socket.close();
                    clientManager.update(new ErrorMessage("Disconnected please try to close and reopen the app. To reconnect you need to use " +
                            "the previous name."));
                    System.exit(0);
                }
            } catch (IOException e) {
                System.out.println("Couldn't close socket...");
            }

        }
    }



}
