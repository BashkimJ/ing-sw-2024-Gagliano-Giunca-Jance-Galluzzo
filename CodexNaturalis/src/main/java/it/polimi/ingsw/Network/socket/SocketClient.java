package main.java.it.polimi.ingsw.Network.socket;

import main.java.it.polimi.ingsw.Controller.GameController;
import main.java.it.polimi.ingsw.Network.Client;
import main.java.it.polimi.ingsw.Network.ClientHandler;
import main.java.it.polimi.ingsw.Network.ClientManager;
import main.java.it.polimi.ingsw.Network.Messages.ErrorMessage;
import main.java.it.polimi.ingsw.Network.Messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class SocketClient extends Client {
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private final ExecutorService readService = Executors.newSingleThreadExecutor();


     public SocketClient(ClientManager clientManager,String IP, int port) throws IOException {
         this.setClientManager(clientManager);
         this.socket = new Socket();
         this.socket.connect(new InetSocketAddress(IP,port));
         output = new ObjectOutputStream(socket.getOutputStream());
         input = new ObjectInputStream(socket.getInputStream());
     }

    @Override
    public void sendMessage(Message message) {
        try{
            output.writeObject(message);
            output.flush();
            output.reset();
            output.flush();

        }catch(IOException e){
            this.Disconnect();
            clientManager.update(new ErrorMessage("Couldn't send message. Disconnecting....."));
        }
    }

    @Override
    public void receiveMessage() {
        readService.execute(() -> {
            while (!readService.isShutdown()) {
                Message message = null;
                try {
                    message = (Message) input.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    Disconnect();
                    message = new ErrorMessage("Couldn't receive message.Disconnecting...");
                    readService.shutdownNow();
                }
               clientManager.update(message);
            }
        });
    }



    @Override
    public void Disconnect() {
         try{
             if(!socket.isClosed()){
                 readService.shutdown();
                 socket.close();
             }
         }catch(IOException e ){

         }

    }
}
