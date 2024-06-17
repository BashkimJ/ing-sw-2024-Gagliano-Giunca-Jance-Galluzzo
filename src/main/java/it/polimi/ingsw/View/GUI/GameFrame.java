package it.polimi.ingsw.View.GUI;


import it.polimi.ingsw.Model.Cards.InitialCard;
import it.polimi.ingsw.Model.Cards.ObjectiveCard;
import it.polimi.ingsw.Model.Cards.ResourceCard;
import it.polimi.ingsw.Model.Player.PlayerView;
import it.polimi.ingsw.Network.ClientManager;
import it.polimi.ingsw.Network.Messages.ChooseObjResp;
import it.polimi.ingsw.View.GUI.Utils.SelectableCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Frame of the game phase
 */
public class GameFrame extends JFrame {
    private static final String SMALL_PANEL = "1G";
    private static final String GAME_PANEL = "2G";
    public static final int CARD_WIDTH = 180;
    public static final int CARD_HEIGHT = 120;
    public static final int X_OVERLAPPING = 48;
    public static final int Y_OVERLAPPING = 40;
    private static float scalingFactor = 1;
    private GUI gui;
    private JPanel cardPanel;
    private JPanel centralPanel;
    private SelectableCard card1;
    private SelectableCard card2;

    private JRightPanel rightPanel;
    private JCenterManager centerManager;
    private JPanel gamePanel;

    /**
     * Construct the frame and its subcomponent cards
     * @param gui reference to the gui used for sending messages to server
     */
    public GameFrame(GUI gui){
        this.gui = gui;
        ImageIcon gameIcon = GUI.getImageIcon("Images/General/GameIcon.png", 0, 0);

        centralPanel = new JPanel();
        centralPanel.setPreferredSize(new Dimension(500, 400));
        centralPanel.setLayout(new GridBagLayout());
        centralPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

        JPanel smallPanel = new JPanel();
        smallPanel.setLayout(new GridBagLayout());
        smallPanel.add(centralPanel);
        smallPanel.setBackground(new Color(210, 166, 121));

        gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout());
        centerManager= new JCenterManager(gui);
        rightPanel = new JRightPanel(gui);
        gamePanel.add(centerManager, BorderLayout.CENTER);
        gamePanel.add(rightPanel, BorderLayout.EAST);

        cardPanel = new JPanel(new CardLayout());
        cardPanel.add(smallPanel, SMALL_PANEL);
        cardPanel.add(gamePanel, GAME_PANEL);

        addComponentListener(new resizedListener());
        setTitle("Codex Naturalis - " + gui.clientManager.getNickName());
        setIconImage(gameIcon.getImage());
        setContentPane(cardPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
        setMinimumSize(new Dimension(1280, 600));
    }

    /**
     * Configures the frame to show the objective card choice
     * @param one first obj card
     * @param two second obj card
     */
    public void showTwoObj(ObjectiveCard one, ObjectiveCard two){
        addTopLabel("Choose your objective card:");

        card1 = new SelectableCard(one.getCardId(), Color.GREEN, true, false);
        card1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gui.clientManager.updateObjCard(new ChooseObjResp(gui.clientManager.getNickName(), 1));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ((SelectableCard)e.getSource()).setEnteredBorder();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ((SelectableCard)e.getSource()).setDefaultBorder();
            }
        });


        card2 = new SelectableCard(two.getCardId(), Color.GREEN, true, false);
        card2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                gui.clientManager.updateObjCard(new ChooseObjResp(gui.clientManager.getNickName(), 2));
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                ((SelectableCard)e.getSource()).setEnteredBorder();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ((SelectableCard)e.getSource()).setDefaultBorder();
            }
        });
        addCards();
        revalidate();
        repaint();
    }
    /**
     * Configures the frame to show the initial card side choice
     * @param initialCard card to choose the side of
     */
    public void showInitial(InitialCard initialCard){
        System.out.println("Showing initial card with id: " + initialCard.getCardId());
        centralPanel.removeAll();
        addTopLabel("Choose initial card's side:");

        card1 = new SelectableCard(initialCard.getCardId(), Color.GREEN, true, false);
        card2 = new SelectableCard(initialCard.getCardId(), Color.GREEN, false, false);

        card1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                gui.clientManager.placeInitial(1);
                System.out.println(gui.clientManager.getNickName() + " placing initial card: front");

            }
            @Override
            public void mouseEntered(MouseEvent e) {
                ((SelectableCard)e.getSource()).setEnteredBorder();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ((SelectableCard)e.getSource()).setDefaultBorder();
            }
        });
        card2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                gui.clientManager.placeInitial(2);
                System.out.println(gui.clientManager.getNickName() + " placing initial card: back");
            }
        });
        addCards();
        revalidate();
        repaint();

    }

    /**
     * Sets the GAME_PANEL as cardLayout shown card
     */
    public void showGamePanel(){
        CardLayout cl = (CardLayout)(cardPanel.getLayout());
        cl.show(cardPanel, GAME_PANEL);
    }
    /**
     * Configures the frame to show the winner
     * @param winnerMessage message to be shown
     */
    public void showWinner(String winnerMessage){
        centralPanel.removeAll();
        addTopLabel(winnerMessage);
        JButton quitButton = new JButton("Quit game");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy=1;
        gbc.gridx=0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.ipadx = 10;
        gbc.ipady = 10;
        centralPanel.add(quitButton, gbc);
        revalidate();
        repaint();
        CardLayout cl = (CardLayout)(cardPanel.getLayout());
        cl.show(cardPanel, SMALL_PANEL);
    }

    /**
     * Methods that propagate the updates to the frame subcomponents
     */
    public void updateCenterPanel(PlayerView playerView){
        centerManager.handlePlayerView(playerView);
    }
    public void updateLeftPanel(List<String> onlinePlayers, String turn){
        centerManager.updatePlayersStatus(onlinePlayers, turn);
    }
    public void updateRightPanel(List<ResourceCard> revealed, List<ObjectiveCard> obj, List<ResourceCard> deck, List<String> onlinePlayers){
        List <Integer> objIDs = obj.stream().map(x -> x.getCardId()).toList();
        List <Integer> deckIDs = deck.stream().map(x -> x.getCardId()).toList();
        List <Integer> revealedIDs = revealed.stream().map(x -> x.getCardId()).toList();
        rightPanel.update(revealedIDs, objIDs, deckIDs, onlinePlayers);
    }

    /**
     * Calls the rightPanel method to print the chat message in the chat box
     * @param sender nickname of the sender
     * @param message message to be printed
     */
    public void showChatMessage(String sender, String message){
        rightPanel.printChatMessage(sender, gui.clientManager.getNickName(), message);
    }

    /**
     * Create the label at the top of the screen
     * @param text the text of the label
     */
    private void addTopLabel(String text){
        JLabel topLabel = new JLabel(text);
        topLabel.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 25));
        topLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth=2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 30, 30, 30);
        centralPanel.add(topLabel, gbc);
    }

    /**
     * Configures the card1 and card2 label
     */
    private void addCards(){
        card1.addMouseListener(new MouseSelection());
        card2.addMouseListener(new MouseSelection());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=1;
        gbc.insets = new Insets(10, 10, 10, 10);
        centralPanel.add(card1, gbc);
        gbc.gridx=1;
        centralPanel.add(card2, gbc);
    }

    /**
     * MouseListener that handles the mouse hover on the label
     */
    private class MouseSelection extends MouseAdapter{
        @Override
        public void mouseEntered(MouseEvent e) {
            ((SelectableCard)e.getSource()).setEnteredBorder();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            ((SelectableCard)e.getSource()).setDefaultBorder();
        }
    }
    public static void main(String[] args) {
        GUI gui = new GUI();
        ClientManager clientManager = new ClientManager(gui);
        gui.setClientManager(clientManager);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GameFrame gf = new GameFrame(gui);
                gf.showTwoObj(new ObjectiveCard(null, 100), new ObjectiveCard(null, 101));
//                gf.showInitial(new InitialCard(null, null, 85, null));
//                gf.showWinner("Winner is Pietro");
            }
        });
    }
    public static float getScalingFactor(){ return scalingFactor;}
    public static void setScalingFactor(float scalingFactor){
        if(SwingUtilities.isEventDispatchThread())
            GameFrame.scalingFactor = scalingFactor;}

    /**
     * ComponentListener that changes the GameFrame's scaling factor based on the current size of the frame.
     * If the factor is changed, some of the component are resized via resize()
     */
    private class resizedListener extends ComponentAdapter{
        @Override
        public void componentResized(ComponentEvent e) {
            boolean changed = false;
            if(getSize().height < 740 || getSize().width < 1550){
                if(GameFrame.getScalingFactor() != 0.777777f) {
                    changed = true;
                    GameFrame.setScalingFactor(0.777777f);
                }
            }
            else
                if(GameFrame.getScalingFactor() != 1f) {
                    changed = true;
                    GameFrame.setScalingFactor(1f);
                }
            if(changed) {
                rightPanel.resize();
                centerManager.resize();
            }
        }
    }
}
