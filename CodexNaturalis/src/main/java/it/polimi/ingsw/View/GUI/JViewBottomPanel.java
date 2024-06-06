package main.java.it.polimi.ingsw.View.GUI;

import main.java.it.polimi.ingsw.Model.Cards.GoldCard;
import main.java.it.polimi.ingsw.Model.Cards.ObjectiveCard;
import main.java.it.polimi.ingsw.Model.Cards.ResourceCard;
import main.java.it.polimi.ingsw.View.GUI.Utils.UnselectableCard;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class JViewBottomPanel extends JPanel implements JBottomPanel{
    private GUI gui;

    public JViewBottomPanel(GUI gui){
        this.gui = gui;
        setLayout(new FlowLayout());
        setBackground(Color.ORANGE);
    }
    public void update(ObjectiveCard playerObjective, List<ResourceCard> playerHand){
        removeAll();
        JToggleButton changeSideBtn = createToggleButton();
        changeSideBtn.setEnabled(false);
        this.add(changeSideBtn);
        List<Integer> ids = playerHand.stream().map(x -> x.getCardId()).toList();
        for(Integer id : ids){
            UnselectableCard card = new UnselectableCard(id, false);
            add(card);
        }
        UnselectableCard card = new UnselectableCard(playerObjective.getCardId(), false);
        this.add(card);
        revalidate();
        repaint();
    }

    private JToggleButton createToggleButton() {
        JToggleButton changeSideBtn = new JToggleButton("↷");
        changeSideBtn.setFont(new Font("Serif", Font.BOLD, 25));
        return changeSideBtn;
    }
    public static void main(String[] args){
        List<ResourceCard> playerHand = new ArrayList<>();
        playerHand.add(new ResourceCard(null, null, 10, null, 0));
        playerHand.add(new ResourceCard(null, null, 11, null, 0));
        playerHand.add(new GoldCard(null, null, 41, null, 0, null, null));

        ObjectiveCard playerObjective = new ObjectiveCard(null, 96);


        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        JViewBottomPanel viewBottomPanel = new JViewBottomPanel(null);
        viewBottomPanel.update(playerObjective, playerHand);
        frame.add(viewBottomPanel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
