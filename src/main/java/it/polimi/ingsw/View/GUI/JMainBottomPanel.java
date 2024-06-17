package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.Cards.GoldCard;
import it.polimi.ingsw.Model.Cards.ObjectiveCard;
import it.polimi.ingsw.Model.Cards.ResourceCard;
import it.polimi.ingsw.View.GUI.Utils.SelectableCard;
import it.polimi.ingsw.View.GUI.Utils.UnselectableCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JBottomPanel of the client's player. Shows player's hand and handles the selection of the card to place
 */
public class JMainBottomPanel extends JPanel implements JBottomPanel{
    private GUI gui;
    private static List<SelectableCard> handCards = new ArrayList<>();
    private static boolean frontSide = true;
    private static int selectedCardIndex = -1;
    private int objectiveID = -1;

    /**
     * Class constructor. It creates a Panel with placeholders instead of cards
     * @param gui
     */
    public JMainBottomPanel(GUI gui){
        this.gui = gui;
        setLayout(new FlowLayout());
        setBackground(new Color(230, 230, 230));
        setBorder(BorderFactory.createLoweredBevelBorder());
        JToggleButton changeSideBtn = createToggleButton();
        changeSideBtn.setEnabled(false);
        this.add(changeSideBtn);
        for(int i=0; i<4; i++){
            JLabel placeholder = new JLabel(GUI.getPlaceholder(180, 120));
            placeholder.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            add(placeholder);
        }

    }

    /**
     * Updates the Panel with the player private objective card and the cards in his hand
     * @param playerObjective private objective card
     * @param playerHand cards in the player hand
     */
    public void update(Integer playerObjective, List<Integer> playerHand){
        handCards=new ArrayList<>();
        objectiveID = playerObjective;
        selectedCardIndex = -1;
        frontSide = true;
        removeAll();
        JToggleButton changeSideBtn = createToggleButton();
        this.add(changeSideBtn);
        for(Integer id : playerHand){
            SelectableCard card = new SelectableCard(id, Color.GREEN, true, true);
            card.addMouseListener(new handCardListener());
            handCards.add(card);
            add(card);
        }
        UnselectableCard card = new UnselectableCard(playerObjective, true, true);
        this.add(card);
        revalidate();
        repaint();
    }

    /**
     * Resizes the currently shown cards
     */
    @Override
    public void resize() {
        if(objectiveID >= 0 && !handCards.isEmpty())
            update(objectiveID, handCards.stream().map(x -> x.getCardId()).toList());
    }

    private JToggleButton createToggleButton() {
        JToggleButton changeSideBtn = new JToggleButton("↷");
        changeSideBtn.setFont(new Font("Serif", Font.BOLD, 25));
        changeSideBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(changeSideBtn.isSelected()) {
                    frontSide = false;
                    changeSideBtn.setText("↶");
                }
                else {
                    frontSide = true;
                    changeSideBtn.setText("↷");
                }
                flipAllCards();
            }
        });
        return changeSideBtn;
    }
    protected static int getSelectedCardId(){
        if(selectedCardIndex!=-1)
            return handCards.get(selectedCardIndex).getCardId();
        return -1;
    }
    protected static boolean getSide(){
        return frontSide;
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
        List<ResourceCard> playerHand = new ArrayList<>();
        playerHand.add(new ResourceCard(null, null, 10, null, 0));
        playerHand.add(new ResourceCard(null, null, 11, null, 0));
        playerHand.add(new GoldCard(null, null, 41, null, 0, null, null));

        ObjectiveCard playerObjective = new ObjectiveCard(null, 96);

        JPanel main = new JPanel();
        JFrame frame = new JFrame();
        JMainBottomPanel bottomPanel = new JMainBottomPanel(null);
        frame.add(bottomPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Thread.sleep(1000);
        bottomPanel.update(96, playerHand.stream().map(x -> x.getCardId()).toList());

    }
    private void flipAllCards(){
        for(SelectableCard card : handCards)
            card.showSide(frontSide);
    }

    /**
     * MouseListener that keeps track of the currently selected card
     */
    private class handCardListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            SelectableCard card = (SelectableCard) e.getSource();
            int newIndex = handCards.indexOf(card);

            if(selectedCardIndex >= 0 && selectedCardIndex != newIndex)
                handCards.get(selectedCardIndex).setDefaultBorder();
            selectedCardIndex = newIndex;
            card.setSelectedBorder();
        }
        @Override
        public void mouseEntered(MouseEvent e) {
            SelectableCard card = (SelectableCard) e.getSource();
            if(selectedCardIndex <0 || !handCards.get(selectedCardIndex).equals(card))
                card.setEnteredBorder();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            SelectableCard card = (SelectableCard) e.getSource();
            if(selectedCardIndex < 0 || !handCards.get(selectedCardIndex).equals(card))
                card.setDefaultBorder();
        }

    }
}
