package main.java.it.polimi.ingsw.View;

import main.java.it.polimi.ingsw.Model.Cards.InitialCard;
import main.java.it.polimi.ingsw.Network.Messages.ChatMess;
import main.java.it.polimi.ingsw.Network.Messages.Message;

/**
 * Interface used for the implementation of the TUI or GUI.
 */
public interface View {
    public void askNickName();
    public void errorMessage(String message);
    public void showLogin(boolean connected,boolean nameAccepted);
    public void askNumPlayers();
    public void chooseObjectiveCard(Message message);
    public void showInitial(Message message);
    public void alertGameStarted(Message message);
    public void showChatMessage(Message message);
    public void showPlayer(Message message);
    public void showGameInfo(Message message);
    public void winner(String message);
    public void afterPlayerMove(Message message);
    public void serverInfo();
    public void stop();
    public void askForNewGame();

}
