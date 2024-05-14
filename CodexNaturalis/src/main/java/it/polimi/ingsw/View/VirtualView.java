package main.java.it.polimi.ingsw.View;

import main.java.it.polimi.ingsw.Model.Cards.InitialCard;
import main.java.it.polimi.ingsw.Network.ClientHandler;
import main.java.it.polimi.ingsw.Network.Messages.*;

import static main.java.it.polimi.ingsw.Network.Messages.MessageType.Winner_Mess;

public class VirtualView implements View {
    ClientHandler clientHandler;
    public VirtualView(ClientHandler ClientHandler){
        this.clientHandler = ClientHandler;
    }
    @Override
    public void askNickName() {
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
    public void initialiseCl(Message message) {
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
    public void Stop() {

    }


}
