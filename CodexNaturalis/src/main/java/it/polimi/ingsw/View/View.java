package main.java.it.polimi.ingsw.View;

import main.java.it.polimi.ingsw.Network.Messages.Message;

public interface View {
    public void askNickName();
    public void errorMessage(String message);
    public void showLogin(boolean conected,boolean nameAccepted);
    public void askNumPlayers();
    public void chooseObjectiveCard(Message message);
}
