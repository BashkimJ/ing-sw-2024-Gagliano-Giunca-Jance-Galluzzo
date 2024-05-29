package main.java.it.polimi.ingsw.View.GUI;

import main.java.it.polimi.ingsw.Model.Player.PlayerView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Created the first time, then updated
//Handles the different JCenterPanels, binding them with the nickname of the corresponding player.
public class JCenterManager extends JPanel {
    private GUI gui;
    private Map<String, JCenterPanel> centerPanels;
    private JPanel leftPanel;
    private JPanel cardsPanel;
    private JList<String> gamePlayers;
    private DefaultListModel<String>  listModel;
    public JCenterManager(GUI gui){
        this.gui = gui;
        String nickname = gui.clientManager.getNickName();

        setLayout(new BorderLayout());
        cardsPanel = new JPanel(new CardLayout());
        centerPanels = new HashMap<>();
        JCenterPanel centerPanel = createMainPlayerPanel();
        centerPanels.put(nickname, centerPanel);
        System.out.println(centerPanels.keySet());
        cardsPanel.add(centerPanel, nickname);

        leftPanel = createLeftPanel(nickname);

        this.add(cardsPanel, BorderLayout.CENTER);
        this.add(leftPanel, BorderLayout.WEST);
    }


    public void handlePlayerView(PlayerView player){
        JCenterPanel centerPanel;
        if(centerPanels.containsKey(player.getNickName())){
            //Existing scheme
            centerPanel = centerPanels.get(player.getNickName());
        }else{
            //Create scheme
            centerPanel = createOtherPlayerPanel();
            centerPanels.put(player.getNickName(), centerPanel);
            listModel.addElement(player.getNickName());
            cardsPanel.add(centerPanel, player.getNickName());
        }
        centerPanel.update(player);
    }
    public void updatePlayers(List<String> onlinePlayers, String turn){
        String[] nicknames = new String[onlinePlayers.size()];
        onlinePlayers.toArray(nicknames);
        //TO DO: different style for online players and player who has the turn
    }
    private JCenterPanel createMainPlayerPanel(){
        return new JCenterPanel(gui, new JMainBottomPanel(gui));
    }
    private JCenterPanel createOtherPlayerPanel(){
        return new JCenterPanel(gui, new JViewBottomPanel(gui));
    }
    private JPanel createLeftPanel(String nickname){
        JPanel leftPanel = new JPanel();
        listModel = new DefaultListModel<>();
        listModel.addElement(nickname);
        gamePlayers = new JList<>(listModel);
        gamePlayers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        gamePlayers.setLayoutOrientation(JList.VERTICAL);
        gamePlayers.setVisibleRowCount(-1);
        gamePlayers.addListSelectionListener(new PlayerSelectionHandler());
        gamePlayers.setSelectedIndex(0);
        leftPanel.add(gamePlayers);
        return leftPanel;
    }
    private class PlayerSelectionHandler implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                CardLayout cl = (CardLayout)(cardsPanel.getLayout());
                cl.show(cardsPanel, gamePlayers.getSelectedValue());
            }
        }
    }

}
