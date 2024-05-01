package main.java.it.polimi.ingsw;

import main.java.it.polimi.ingsw.Network.ClientManager;
import main.java.it.polimi.ingsw.View.TUI;
import main.java.it.polimi.ingsw.View.View;

public class ClientMain {
    public static void main(String[] args) {
        TUI myView = new TUI();
        ClientManager clientManager = new ClientManager(myView);
        myView.setClientManager(clientManager);
        myView.initTUI();
    }
}
