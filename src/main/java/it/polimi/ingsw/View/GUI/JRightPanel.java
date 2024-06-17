package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.Cards.GoldCard;
import it.polimi.ingsw.Model.Cards.ObjectiveCard;
import it.polimi.ingsw.Model.Cards.ResourceCard;
import it.polimi.ingsw.View.GUI.Utils.SelectableCard;
import it.polimi.ingsw.View.GUI.Utils.UnselectableCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * JPanel that handles card picking. It shows the cards at the top of the decks and the face up cards.
 */
public class JRightPanel extends JPanel {
    private GUI gui;
    private List<Integer> objIDs;
    private List<Integer> revealedIDs;
    private List<Integer> deckIDs;
    private JTextArea chat;
    private JComboBox<String> choosePlayer;
    private JTextField messageField;
    private JPanel tableCards;
    private List<String> chatReceivers;
    private JPanel bottomPanel;

    /**
     * Class constructor. It creates a Panel with the chat box and placeholders instead of cards.
     * @param gui
     */
    public JRightPanel(GUI gui){
        this.gui = gui;
        setBackground(new Color(230, 230, 230));
        setLayout(new GridBagLayout());
        chatReceivers=new ArrayList<>();
        tableCards = new JPanel();
        tableCards.setLayout(new GridLayout(4, 2, 10, 10));
        for(int i=0; i<8; i++){
            JLabel placeholder = new JLabel(GUI.getPlaceholder(180, 120));
            placeholder.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            tableCards.add(placeholder);
        }
        tableCards.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        add(tableCards, gbc);
        JPanel chatPanel = new JPanel();
        chatPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(chatPanel, gbc);

        chatPanel.add(Box.createVerticalGlue());
        chat = new JTextArea(10, 20);
        chat.setFont(new Font("Serif", Font.ITALIC, 16));
        chat.setLineWrap(true);
        chat.setWrapStyleWord(true);
        chat.setEditable(false);

        JScrollPane scroll = new JScrollPane(chat);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        chatPanel.add(scroll);

        bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setMaximumSize(new Dimension(bottomPanel.getMaximumSize().width, 70));
        messageField = new JTextField();
        messageField.addActionListener(new SendMsgListener());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.65;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 10);
        bottomPanel.add(messageField, gbc);
        JLabel toLabel = new JLabel("to:");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        bottomPanel.add(toLabel, gbc);
        String[] arrayOfPlayers = new String[chatReceivers.size()];
        chatReceivers.toArray(arrayOfPlayers);
        choosePlayer = new JComboBox<>(arrayOfPlayers);
        choosePlayer.setSelectedItem(null);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.35;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        bottomPanel.add(choosePlayer, gbc);
        JButton sendButton = new JButton("→");
        sendButton.addActionListener(new SendMsgListener());
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.ipadx = 10;
        bottomPanel.add(sendButton, gbc);

        chatPanel.add(bottomPanel);
    }

    /**
     * Updates the JPanel with the new information
     * @param revealed ids of faceup cards
     * @param obj ids of common objective cards
     * @param deck ids of decks top card
     * @param onlinePlayers nicknames of the online players
     */
    public void update(List<Integer> revealed, List<Integer> obj, List<Integer> deck, List<String> onlinePlayers){
        JPanel tableCards = new JPanel();
        tableCards.setLayout(new GridLayout(4, 2, 10, 10));
        //Objectives
        objIDs = obj;
                //obj.stream().map(x -> x.getCardId()).toList();
        UnselectableCard u_card = new UnselectableCard(objIDs.get(0), true, true);
        tableCards.add(u_card);
        u_card = new UnselectableCard(objIDs.get(1), true, true);
        tableCards.add(u_card);
        //Decks
        this.deckIDs = deck;
                //deck.stream().map(x -> x.getCardId()).toList();
        SelectableCard card = new SelectableCard(deckIDs.get(0), Color.RED, false, true);
        card.addMouseListener(new PickCardListener());
        tableCards.add(card);
        card = new SelectableCard(deckIDs.get(1), Color.RED, false, true);
        tableCards.add(card);
        card.addMouseListener(new PickCardListener());
        //Faceup cards: 2 resources and 2 golds
        this.revealedIDs  = revealed;
                //revealed.stream().map(x -> x.getCardId()).toList();
        card = new SelectableCard(revealedIDs.get(0), Color.RED, true, true);
        tableCards.add(card);
        card.addMouseListener(new PickCardListener());

        card = new SelectableCard(revealedIDs.get(2), Color.RED, true, true);
        tableCards.add(card);
        card.addMouseListener(new PickCardListener());

        card = new SelectableCard(revealedIDs.get(1), Color.RED, true, true);
        tableCards.add(card);
        card.addMouseListener(new PickCardListener());

        card = new SelectableCard(revealedIDs.get(3), Color.RED, true, true);
        tableCards.add(card);
        card.addMouseListener(new PickCardListener());

        remove(this.tableCards);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        tableCards.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(tableCards, gbc);
        this.tableCards = tableCards;
        if(onlinePlayers != null)
            updatePlayers(onlinePlayers);

        revalidate();
        repaint();
    }

    /**
     * Updates the list of players to send messages to. A player is added if he's not yet in the chatReceivers list
     * @param players currenly online players
     */
    private void updatePlayers(List<String> players){
        List<String> pl = new ArrayList<>(players);
        pl.remove(gui.clientManager.getNickName());
        if(!chatReceivers.containsAll(pl)) {
            chatReceivers.removeAll(pl);
            chatReceivers.addAll(pl);
            String[] arrayOfPlayers = new String[chatReceivers.size()];
            chatReceivers.toArray(arrayOfPlayers);
            bottomPanel.remove(choosePlayer);
            choosePlayer = new JComboBox<>(arrayOfPlayers);
            choosePlayer.setSelectedItem(null);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 2;
            gbc.gridy = 0;
            gbc.weightx = 0.35;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(10, 10, 10, 10);
            bottomPanel.add(choosePlayer, gbc);
        }
    }

    /**
     * Prints a message in the chat JTextArea
     * @param sender nickname of the sender
     * @param receiver nickname of the receiver
     * @param msg message
     */
    public void printChatMessage(String sender, String receiver, String msg){
        sender = Objects.equals(sender, gui.clientManager.getNickName()) ? "You" : sender;
        receiver = Objects.equals(receiver, gui.clientManager.getNickName()) ? "You" : receiver;
        chat.append("["+ sender + "→" + receiver + "]: " + msg + "\n");
    }

    /**
     * Resizes the card labels by calling the update method with the current cards
     */
    public void resize(){
        if(revealedIDs != null && objIDs != null && deckIDs != null)
            update(revealedIDs, objIDs, deckIDs, null);
    }

    /**
     * ActionListener that sends a chat message via the clientManager
     */
    private class SendMsgListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(choosePlayer.getSelectedItem() == null)
                JOptionPane.showMessageDialog(null, "You must select the receiver of the message.",
                        "Error!", JOptionPane.ERROR_MESSAGE);
            else if(!Objects.equals(messageField.getText(), "")) {
                gui.clientManager.sendMessage((String) choosePlayer.getSelectedItem(), messageField.getText());
                printChatMessage(gui.clientManager.getNickName(), (String) choosePlayer.getSelectedItem(), messageField.getText());
                messageField.setText("");
            }
        }
    }

    /**
     * MouseListener that handles card picking and mouse hover on the cards
     */
    private class PickCardListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            SelectableCard card = (SelectableCard) e.getSource();
            gui.clientManager.pickCard(card.getCardId());
        }
        @Override
        public void mouseEntered(MouseEvent e) {
            SelectableCard card = (SelectableCard) e.getSource();
            card.setEnteredBorder();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            SelectableCard card = (SelectableCard) e.getSource();
            card.setDefaultBorder();
        }

    }
    public static void main(String[] args) throws InterruptedException {
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        GameFrame.setScalingFactor(0.7777777f);
                    }
                }
        );

        List<ResourceCard> revealed = new ArrayList<>();
        revealed.add(new ResourceCard(null, null, 10, null, 0));
        revealed.add(new ResourceCard(null, null, 11, null, 0));
        revealed.add(new GoldCard(null, null, 41, null, 0, null, null));
        revealed.add(new GoldCard(null, null, 51, null, 0, null, null));

        List<ResourceCard> deck = new ArrayList<>();
        deck.add(new ResourceCard(null, null, 12, null, 0));
        deck.add(new GoldCard(null, null, 52, null, 0, null, null));

        List<ObjectiveCard> obj = new ArrayList<>();
        obj.add(new ObjectiveCard(null, 96));
        obj.add(new ObjectiveCard(null, 97));


        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        JRightPanel rightPanel = new JRightPanel(null);
        frame.add(rightPanel, BorderLayout.EAST);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Thread.sleep(1000);
        List<String> s = new ArrayList<>();
        s.add("A");
        s.add("B");
        s.add("C");
        rightPanel.update(
                revealed.stream().map(x -> x.getCardId()).toList(),
                obj.stream().map(x -> x.getCardId()).toList(),
                deck.stream().map(x -> x.getCardId()).toList(),
                s);
    }
}
