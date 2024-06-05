package main.java.it.polimi.ingsw.View.GUI;

import main.java.it.polimi.ingsw.Model.Cards.GoldCard;
import main.java.it.polimi.ingsw.Model.Cards.ObjectiveCard;
import main.java.it.polimi.ingsw.Model.Cards.ResourceCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class JRightPanel extends JPanel {
    private GUI gui;
    private List<Integer> revealedIDs;
    private List<Integer> deckIDs;
    private JTextArea chat;
    private JComboBox<String> choosePlayer;
    private JTextField messageField;
    private JPanel tableCards;
    private List<String> chatReceivers;
    public JRightPanel(GUI gui){
        this.gui = gui;
        setBackground(new Color(230, 230, 230));
        setLayout(new GridBagLayout());
        chatReceivers=new ArrayList<>();
        tableCards = new JPanel();
        tableCards.setLayout(new GridLayout(4, 2, 20, 20));
        for(int i=0; i<8; i++){
            JLabel placeholder = new JLabel(GUI.getPlaceholder(180, 120));
            placeholder.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            tableCards.add(placeholder);
        }
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.gridwidth = 4;
        add(tableCards, gbc);
        chat = new JTextArea();
        chat.setFont(new Font("Serif", Font.ITALIC, 16));
        chat.setLineWrap(true);
        chat.setWrapStyleWord(true);
        chat.setEditable(false);

        JScrollPane scroll = new JScrollPane(chat);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setPreferredSize(new Dimension(getPreferredSize().width, 200));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(10, 10, 0, 10);
        add(scroll, gbc);
        messageField = new JTextField();
        messageField.addActionListener(new SendMsgListener());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.65;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(messageField, gbc);
        JLabel toLabel = new JLabel("to:");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(toLabel, gbc);
        String[] arrayOfPlayers = new String[chatReceivers.size()];
        chatReceivers.toArray(arrayOfPlayers); // fill the array
        choosePlayer = new JComboBox<>(arrayOfPlayers);
        choosePlayer.setSelectedItem(null);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 0.35;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(choosePlayer, gbc);
        JButton sendButton = new JButton("→");
        sendButton.addActionListener(new SendMsgListener());
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 0, 10, 10);
        gbc.ipadx = 10;
        add(sendButton, gbc);


    }
    public void update(List<ResourceCard> revealed, List<ObjectiveCard> obj, List<ResourceCard> deck, List<String> onlinePlayers){
        JPanel tableCards = new JPanel();
        tableCards.setLayout(new GridLayout(4, 2, 20, 20));
        //Objectives
        List<Integer> ids = obj.stream().map(x -> x.getCardId()).toList();
        UnselectableCard u_card = new UnselectableCard(ids.get(0), true);
        tableCards.add(u_card);
        u_card = new UnselectableCard(ids.get(1), true);
        tableCards.add(u_card);
        //Decks
        this.deckIDs = deck.stream().map(x -> x.getCardId()).toList();
        SelectableCard card = new SelectableCard(deckIDs.get(0), Color.RED, false);
        card.addMouseListener(new PickCardListener());
        tableCards.add(card);
        card = new SelectableCard(deckIDs.get(1), Color.RED, false);
        tableCards.add(card);
        card.addMouseListener(new PickCardListener());
        //Faceup cards: 2 resources and 2 golds
        this.revealedIDs  = revealed.stream().map(x -> x.getCardId()).toList();
        card = new SelectableCard(revealedIDs.get(0), Color.RED, true);
        tableCards.add(card);
        card.addMouseListener(new PickCardListener());

        card = new SelectableCard(revealedIDs.get(2), Color.RED, true);
        tableCards.add(card);
        card.addMouseListener(new PickCardListener());

        card = new SelectableCard(revealedIDs.get(1), Color.RED, true);
        tableCards.add(card);
        card.addMouseListener(new PickCardListener());

        card = new SelectableCard(revealedIDs.get(3), Color.RED, true);
        tableCards.add(card);
        card.addMouseListener(new PickCardListener());

        remove(this.tableCards);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.gridwidth = 4;
        add(tableCards, gbc);
        this.tableCards = tableCards;
        updatePlayers(onlinePlayers);

        revalidate();
        repaint();
    }
    private void updatePlayers(List<String> players){
        List<String> pl = new ArrayList<>(players);
        pl.remove(gui.clientManager.getNickName());
        if(!chatReceivers.containsAll(pl)) {
            chatReceivers.removeAll(pl);
            chatReceivers.addAll(pl);
            String[] arrayOfPlayers = new String[chatReceivers.size()];
            chatReceivers.toArray(arrayOfPlayers);
            remove(choosePlayer);
            choosePlayer = new JComboBox<>(arrayOfPlayers);
            choosePlayer.setSelectedItem(null);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 2;
            gbc.gridy = 2;
            gbc.weightx = 0.35;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(10, 10, 10, 10);
            add(choosePlayer, gbc);
        }
    }
    public void printChatMessage(String sender, String receiver, String msg){
        chat.append("["+ sender + "-->" + receiver + "]: " + msg + "\n");
    }
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
        rightPanel.update(revealed, obj, deck, s);
    }
}
