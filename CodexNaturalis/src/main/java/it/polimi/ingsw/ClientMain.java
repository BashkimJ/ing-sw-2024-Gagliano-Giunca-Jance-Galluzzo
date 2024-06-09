package it.polimi.ingsw;

import it.polimi.ingsw.Network.ClientManager;
import it.polimi.ingsw.View.GUI.GUI;
import it.polimi.ingsw.View.TUI;

public class ClientMain {
    public static void main(String[] args) {
        if(args.length > 0 && (args[0].equalsIgnoreCase("-t") || (args[0].equalsIgnoreCase("-tui")))) {
            TUI myView = new TUI();
            ClientManager clientManager = new ClientManager(myView);
            myView.setClientManager(clientManager);
            myView.initTUI();
        }else{
            GUI gui = new GUI();
            ClientManager clientManager = new ClientManager(gui);
            gui.setClientManager(clientManager);
            gui.initGUI();
        }


    }
}
