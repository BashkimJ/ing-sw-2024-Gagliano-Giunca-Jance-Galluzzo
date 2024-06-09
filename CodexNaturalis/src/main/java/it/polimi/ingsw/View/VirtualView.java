package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Cards.InitialCard;
import it.polimi.ingsw.Network.ClientHandler;
import it.polimi.ingsw.Network.Messages.*;

import static it.polimi.ingsw.Network.Messages.MessageType.Winner_Mess;

/**
 * Class that implements the View. It is used from the controller to send messages to the client.
 * Also, it hides from the controller then network components such as the ClientHandler!
 */
public class VirtualView implements View {
    ClientHandler clientHandler;
    public VirtualView(ClientHandler ClientHandler){
        this.clientHandler = ClientHandler;
    }
    @Override
    public void askNickName() {
        clientHandler.sendMessage(new LoginRequest("Server"));
    }
    @Override
    public void errorMessage(String message){
        clientHandler.sendMessage(new ErrorMessage(message));
    }


    @Override
    public void showLogin(boolean NameAccepted,boolean connected){
        clientHandler.sendMessage(new LoginReply(NameAccepted,connected));
    }

    public void askNumPlayers(){
        clientHandler.sendMessage(new PlayerNumberRequest());
    }
    public void showInitial(Message message){
        clientHandler.sendMessage(message);
    }

    @Override
    public void alertGameStarted(Message message) {
        clientHandler.sendMessage(message);
    }

    @Override
    public void showChatMessage(Message message) {
        clientHandler.sendMessage(message);
    }

    @Override
    public void showPlayer(Message message) {
        clientHandler.sendMessage(message);
    }

    @Override
    public void chooseObjectiveCard(Message message){
        clientHandler.sendMessage(message);
    }
    @Override
    public void showGameInfo(Message message){
        clientHandler.sendMessage(message);
    }
    public void winner(String message){
        clientHandler.sendMessage(new WinnerMess("Server",message));
    }

    @Override
    public void afterPlayerMove(Message message) {
        clientHandler.sendMessage(message);
    }

    @Override
    public void serverInfo() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void askForNewGame() {

    }


}
