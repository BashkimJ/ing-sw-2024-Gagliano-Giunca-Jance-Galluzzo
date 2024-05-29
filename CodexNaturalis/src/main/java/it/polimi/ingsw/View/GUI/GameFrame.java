package main.java.it.polimi.ingsw.View.GUI;


import main.java.it.polimi.ingsw.Model.Cards.InitialCard;
import main.java.it.polimi.ingsw.Model.Cards.ObjectiveCard;
import main.java.it.polimi.ingsw.Model.Cards.ResourceCard;
import main.java.it.polimi.ingsw.Model.Player.CardSchemeView;
import main.java.it.polimi.ingsw.Model.Player.PlayerView;
import main.java.it.polimi.ingsw.Network.ClientManager;
import main.java.it.polimi.ingsw.Network.Messages.ChooseObjResp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GameFrame extends JFrame {
    private static final String OBJECT_PANEL = "1G";
    private static final String INITIAL_PANEL = "2G";
    private static final String GAME_PANEL = "3G";

    private int chosenObjCard = 0;
    private int chosenSide = 0;

    private GUI gui;
    private JPanel cardPanel;
    private JLabel card1;
    private JLabel card2;
    private JLabel front;
    private JLabel back;

    private JRightPanel rightPanel;
    private JCenterManager centerManager;
    private JPanel gamePanel;
    public GameFrame(GUI gui){
        this.gui = gui;
        ImageIcon gameIcon = GUI.getImageIcon("Images/General/GameIcon.png", 0, 0);

        JPanel chooseObjPanel = new JPanel();
        JPanel placeInitialPanel = new JPanel();
        gamePanel = new JPanel();
        cardPanel = new JPanel(new CardLayout());
        cardPanel.add(chooseObjPanel, OBJECT_PANEL);
        cardPanel.add(placeInitialPanel, INITIAL_PANEL);
        cardPanel.add(gamePanel, GAME_PANEL);


        //Object panel "1G"
        JPanel centralPanel = new JPanel();
        centralPanel.setPreferredSize(new Dimension(1000, 400));
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));
        centralPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        JLabel askObjCard = new JLabel("Choose your objective card:");
        askObjCard.setFont(new Font("Serif", Font.BOLD, 25));
        askObjCard.setAlignmentX(Component.CENTER_ALIGNMENT);
        centralPanel.add(askObjCard);

        JPanel twoObjPanel = new JPanel(new GridLayout(1, 2));
        card1 = new JLabel(GUI.getImageIcon("Images/Cards/Front/1.png", 420, 280));
        card1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                chosenObjCard = 1;
                gui.clientManager.updateObjCard(new ChooseObjResp(gui.clientManager.getNickName(), chosenObjCard));
            }
        });
        twoObjPanel.add(card1);



        card2 = new JLabel(GUI.getImageIcon("Images/Cards/Front/1.png", 420, 280));
        card2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                chosenObjCard = 2;
                gui.clientManager.updateObjCard(new ChooseObjResp(gui.clientManager.getNickName(), chosenObjCard));
            }
        });
        twoObjPanel.add(card2);

        centralPanel.add(twoObjPanel);

        chooseObjPanel.setLayout(new GridBagLayout());
        chooseObjPanel.add(centralPanel);
        chooseObjPanel.setBackground(new Color(235, 161, 52));

        //Initial panel "2G"
        centralPanel = new JPanel();
        centralPanel.setPreferredSize(new Dimension(1000, 400));
        centralPanel.setLayout(new GridLayout(1, 2));

        placeInitialPanel.setLayout(new GridBagLayout());
        placeInitialPanel.add(centralPanel);
        placeInitialPanel.setBackground(new Color(235, 161, 52));

        front = new JLabel(GUI.getImageIcon("Images/Cards/Front/1.png", 420, 280));
        front.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                chosenSide = 1;
                gui.clientManager.placeInitial(chosenSide);
            }
        });
        centralPanel.add(front);



        back = new JLabel(GUI.getImageIcon("Images/Cards/Front/1.png", 420, 280));
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                chosenSide = 2;
                gui.clientManager.placeInitial(chosenSide);
            }
        });
        centralPanel.add(back);

        //Game panel 3G
        //gamePanel.setLayout(new GridBagLayout());
        gamePanel.setLayout(new BorderLayout());
        centerManager= new JCenterManager(gui);
        rightPanel = new JRightPanel(gui);
        gamePanel.add(centerManager, BorderLayout.CENTER);
        gamePanel.add(rightPanel, BorderLayout.EAST);






        setTitle("Codex Naturalis - " + gui.clientManager.getNickName());
        setIconImage(gameIcon.getImage());
        setContentPane(cardPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
    }
    public void showTwoObj(ObjectiveCard one, ObjectiveCard two){
        card1.setIcon(GUI.getImageIcon("Images/Cards/Front/" + one.getCardId() +".png", 420, 280));
        card2.setIcon(GUI.getImageIcon("Images/Cards/Front/" + two.getCardId() +".png", 420, 280));
    }
    public void showInitial(InitialCard initialCard){
        front.setIcon(GUI.getImageIcon("Images/Cards/Front/" + initialCard.getCardId() + ".png", 420, 280));
        back.setIcon(GUI.getImageIcon("Images/Cards/Back/" + initialCard.getCardId() + ".png", 420, 280));
        CardLayout cl = (CardLayout)(cardPanel.getLayout());
        cl.show(cardPanel, INITIAL_PANEL);
    }
    public void showGamePanel(){
        CardLayout cl = (CardLayout)(cardPanel.getLayout());
        cl.show(cardPanel, GAME_PANEL);
    }
    public void updateCenterPanel(PlayerView playerView){
        centerManager.handlePlayerView(playerView);
    }
    public void updateLeftPanel(List<String> onlinePlayers, String turn){
        centerManager.updatePlayers(onlinePlayers, turn);
    }

    public void updateRightPanel(List<ResourceCard> revealed, List<ObjectiveCard> obj, List<ResourceCard> deck){
        rightPanel.update(revealed, obj, deck);
    }
    public static void main(String[] args) {
        GUI gui = new GUI();
        ClientManager clientManager = new ClientManager(gui);
        gui.setClientManager(clientManager);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GameFrame gf = new GameFrame(gui);
                CardLayout cl = (CardLayout) (gf.cardPanel.getLayout());
                cl.show(gf.cardPanel, "3G");
            }
        });
    }
}
