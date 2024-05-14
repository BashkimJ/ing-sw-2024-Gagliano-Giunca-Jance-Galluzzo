package main.java.it.polimi.ingsw.Network.socket;

import main.java.it.polimi.ingsw.Network.Client;
import main.java.it.polimi.ingsw.Network.ClientManager;
import main.java.it.polimi.ingsw.Network.Messages.ErrorMessage;
import main.java.it.polimi.ingsw.Network.Messages.Message;
import main.java.it.polimi.ingsw.Network.Messages.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class implements the methods of the abstract class of the Client using the socket logic.
 */
public class SocketClient extends Client {
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private final ExecutorService readService = Executors.newSingleThreadExecutor();
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
         this.socket.connect(new InetSocketAddress(IP,port));
         output = new ObjectOutputStream(socket.getOutputStream());
         input = new ObjectInputStream(socket.getInputStream());
         lockConn  = new Object();
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
        }
    }

    /**
     * A separated thread tha constantly waits for new messages from the server. Once the message is received is passed to the ClientManager.
     *
     */
    @Override
    public void receiveMessage() {
        readService.execute(() -> {
            while (!readService.isShutdown()) {
                Message message = null;
                try {
                    message = (Message) input.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    Disconnect();
                }
                if(message!=null && !message.getType().equals(MessageType.Ping)) {
                    clientManager.update(message);
                }
            }
        });
    }


    /**
     * Manages the disconnections of the client from the server.
     */
    @Override
    public void Disconnect() {
        synchronized (lockConn) {
            try {
                if (!socket.isClosed()) {
                    readService.shutdown();
                    socket.close();
                    clientManager.update(new ErrorMessage("Disconnected please try to close and reopen the app. To reconnect you need to use " +
                            "the previous name."));
                }
            } catch (IOException e) {
                System.out.println("Couldn't close socket...");
            }

        }
    }
}
