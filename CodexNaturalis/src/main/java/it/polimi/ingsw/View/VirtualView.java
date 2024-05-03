package main.java.it.polimi.ingsw.View;

import main.java.it.polimi.ingsw.Network.ClientHandler;
import main.java.it.polimi.ingsw.Network.Messages.ErrorMessage;
import main.java.it.polimi.ingsw.Network.Messages.LoginReply;
import main.java.it.polimi.ingsw.Network.Messages.Message;
import main.java.it.polimi.ingsw.Network.Messages.PlayerNumberRequest;

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

    @Override
    public void chooseObjectiveCard(Message message){
        clientHandler.sendMessage(message);
    }


}
