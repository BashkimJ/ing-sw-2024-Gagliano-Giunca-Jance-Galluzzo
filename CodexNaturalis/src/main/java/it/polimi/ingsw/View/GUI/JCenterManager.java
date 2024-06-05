package main.java.it.polimi.ingsw.View.GUI;

import main.java.it.polimi.ingsw.Model.Player.PlayerView;
import main.java.it.polimi.ingsw.View.GUI.Utils.PlayerItem;
import main.java.it.polimi.ingsw.View.GUI.Utils.PlayerRenderer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

//Created the first time, then updated
//Handles the different JCenterPanels, binding them with the nickname of the corresponding player.
public class JCenterManager extends JPanel {
    private GUI gui;
    private Map<String, JCenterPanel> centerPanels;
    private JPanel leftPanel;
    private JPanel cardsPanel;
    private JList<PlayerItem> gamePlayers;
    private DefaultListModel<PlayerItem>  listModel;
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
            for(int i = 0; i<listModel.size(); i++){
                if(Objects.equals(listModel.get(i).getNickname(), player.getNickName())){
                    PlayerItem playerItem = listModel.get(i);
                    playerItem.updatePlayer(player);
                    //calls listmodel.fireContentsChanged()
                    listModel.setElementAt(playerItem, i);
                    break;
                }
            }
        }else{
            //Create scheme
            centerPanel = createOtherPlayerPanel();
            centerPanels.put(player.getNickName(), centerPanel);
            PlayerItem playerItem = new PlayerItem(player.getNickName());
            playerItem.updatePlayer(player);
            listModel.addElement(playerItem);
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
        listModel.addElement(new PlayerItem(nickname));
        gamePlayers = new JList<>(listModel);
        gamePlayers.setCellRenderer(new PlayerRenderer());
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
                cl.show(cardsPanel, gamePlayers.getSelectedValue().getNickname());
            }
        }
    }

}
