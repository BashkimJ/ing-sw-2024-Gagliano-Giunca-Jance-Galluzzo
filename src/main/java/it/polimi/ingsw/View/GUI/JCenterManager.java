package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.Player.PlayerView;
import it.polimi.ingsw.View.GUI.Utils.PlayerItem;
import it.polimi.ingsw.View.GUI.Utils.PlayerRenderer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Subcomponent of the GameFrame that contains the leftPanel and the List of JCenterPanels.
 * Handles the different JCenterPanels, binding them with the nickname of the corresponding player.
 */
//
public class JCenterManager extends JPanel {
    private GUI gui;
    private Map<String, JCenterPanel> centerPanels;
    /**
     *  JPanel which contains a JList of the players who have played at least one turn in the game.
     */
    private JPanel leftPanel;
    private JPanel cardsPanel;
    private JList<PlayerItem> gamePlayers;
    private DefaultListModel<PlayerItem>  listModel;

    /**
     * Class constructor. It creates the client's player JCenterPanel and the leftPanel.
     * @param gui
     */
    public JCenterManager(GUI gui){
        this.gui = gui;
        String nickname = gui.clientManager.getNickName();

        setLayout(new BorderLayout());
        cardsPanel = new JPanel(new CardLayout());
        centerPanels = new HashMap<>();
        JCenterPanel centerPanel = createMainPlayerPanel();
        centerPanels.put(nickname, centerPanel);
        cardsPanel.add(centerPanel, nickname);

        leftPanel = createLeftPanel(nickname);

        this.add(cardsPanel, BorderLayout.CENTER);
        this.add(leftPanel, BorderLayout.WEST);
    }

    /**
     * Handles the update of the Panel when an updated PlayerView is received
     * @param player the updated player
     */
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
            PlayerItem playerItem = new PlayerItem(player.getNickName(), false);
            playerItem.updatePlayer(player);
            listModel.addElement(playerItem);
            cardsPanel.add(centerPanel, player.getNickName());
        }
        centerPanel.update(player);
    }
    /**
     * Resizes all the JCenterPanels
     */
    public void resize(){
        for(JCenterPanel panel : centerPanels.values()){
            panel.resize();
        }
    }

    /**
     * Updates the leftPanel's JList with updated information
     * @param onlinePlayers players currently online
     * @param turn player who has the turn
     */
    public void updatePlayersStatus(List<String> onlinePlayers, String turn){
        boolean isOnline, hasTurn;
        for(int i = 0; i<listModel.size(); i++){
            isOnline = onlinePlayers.contains(listModel.get(i).getNickname());
            hasTurn = Objects.equals(listModel.get(i).getNickname(), turn);
            listModel.get(i).setStatus(isOnline);
            listModel.get(i).setTurn(hasTurn);
            //calls listmodel.fireContentsChanged()
            listModel.setElementAt(listModel.get(i) ,i);
        }
    }

    /**
     * Methods that creates a JCenterPanel for the client's player
     * @return the JCenterPanel
     */
    private JCenterPanel createMainPlayerPanel(){
        return new JCenterPanel(new JMainSchemePanel(gui), new JMainBottomPanel(gui));
    }
    /**
     * Methods that creates a JCenterPanel for a client's player opponent
     * @return the JCenterPanel
     */
    private JCenterPanel createOtherPlayerPanel(){
        return new JCenterPanel(new JViewSchemePanel(gui), new JViewBottomPanel(gui));
    }

    /**
     * Creates the left panel and initialize the JList with only the client's player.
     * @param nickname nickname of the client's player
     * @return the created JPanel
     */
    private JPanel createLeftPanel(String nickname){
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(230, 230, 230));
        listModel = new DefaultListModel<>();
        listModel.addElement(new PlayerItem(nickname, true));
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

    /**
     * Handles the selection of JCenterPanel to be shown
     */
    private class PlayerSelectionHandler implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                CardLayout cl = (CardLayout)(cardsPanel.getLayout());
                cl.show(cardsPanel, gamePlayers.getSelectedValue().getNickname());
            }
        }
    }

}
