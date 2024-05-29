package main.java.it.polimi.ingsw.View.GUI;

import main.java.it.polimi.ingsw.Model.Cards.GoldCard;
import main.java.it.polimi.ingsw.Model.Cards.ObjectiveCard;
import main.java.it.polimi.ingsw.Model.Cards.ResourceCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class JRightPanel extends JPanel {
    private GUI gui;
    private List<Integer> revealedIDs;
    private List<Integer> deckIDs;
    public JRightPanel(GUI gui){
        this.gui = gui;
        setBackground(new Color(230, 230, 230));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel tableCards = new JPanel();
        tableCards.setLayout(new GridLayout(4, 2, 20, 20));
        for(int i=0; i<8; i++){
            JLabel placeholder = new JLabel(GUI.getPlaceholder(180, 120));
            placeholder.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            tableCards.add(placeholder);
        }
        add(tableCards);
        add(Box.createRigidArea(new Dimension(0, 200)));

        JTextArea chat = new JTextArea();
        chat.setText("CHAT: TO DO");
        add(chat);

//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.gridx = 1;
//        gbc.gridy = 0;
//        gbc.weightx = 0;
//        gbc.weighty = 1;
//        gbc.gridheight = 2;
//        gbc.fill = GridBagConstraints.VERTICAL;
//        gamePanel.add(this, gbc);
    }
    public void update(List<ResourceCard> revealed, List<ObjectiveCard> obj, List<ResourceCard> deck){
        JPanel tableCards = new JPanel();
        tableCards.setLayout(new GridLayout(4, 2, 20, 20));
        //Objectives
        List<Integer> ids = obj.stream().map(x -> x.getCardId()).toList();
        UnselectableCard u_card = new UnselectableCard(ids.getFirst(), true);
        tableCards.add(u_card);
        u_card = new UnselectableCard(ids.get(1), true);
        tableCards.add(u_card);
        //Decks
        this.deckIDs = deck.stream().map(x -> x.getCardId()).toList();
        SelectableCard card = new SelectableCard(deckIDs.get(0), Color.RED, false);
        card.addMouseListener(new pickCardListener());
        tableCards.add(card);
        card = new SelectableCard(deckIDs.get(1), Color.RED, false);
        tableCards.add(card);
        card.addMouseListener(new pickCardListener());
        //Faceup cards: 2 resources and 2 golds
        this.revealedIDs  = revealed.stream().map(x -> x.getCardId()).toList();
        card = new SelectableCard(revealedIDs.get(0), Color.RED, true);
        tableCards.add(card);
        card.addMouseListener(new pickCardListener());

        card = new SelectableCard(revealedIDs.get(2), Color.RED, true);
        tableCards.add(card);
        card.addMouseListener(new pickCardListener());

        card = new SelectableCard(revealedIDs.get(1), Color.RED, true);
        tableCards.add(card);
        card.addMouseListener(new pickCardListener());

        card = new SelectableCard(revealedIDs.get(3), Color.RED, true);
        tableCards.add(card);
        card.addMouseListener(new pickCardListener());

        removeAll();
        add(tableCards);
        add(Box.createRigidArea(new Dimension(0, 200)));

        JTextArea chat = new JTextArea();
        chat.setText("CHAT: TO DO");
        add(chat);

        revalidate();
        repaint();
    }
    private class pickCardListener extends MouseAdapter {
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
        rightPanel.update(revealed, obj, deck);
    }
}
