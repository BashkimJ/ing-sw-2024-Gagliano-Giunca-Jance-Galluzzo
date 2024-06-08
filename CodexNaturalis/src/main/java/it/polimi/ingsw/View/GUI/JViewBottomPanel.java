package main.java.it.polimi.ingsw.View.GUI;

import main.java.it.polimi.ingsw.Model.Cards.GoldCard;
import main.java.it.polimi.ingsw.Model.Cards.ObjectiveCard;
import main.java.it.polimi.ingsw.Model.Cards.ResourceCard;
import main.java.it.polimi.ingsw.View.GUI.Utils.SelectableCard;
import main.java.it.polimi.ingsw.View.GUI.Utils.UnselectableCard;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class JViewBottomPanel extends JPanel implements JBottomPanel{
    private GUI gui;
    private static List<Integer> handCardsIDs = new ArrayList<>();
    private int objectiveID = -1;

    public JViewBottomPanel(GUI gui){
        this.gui = gui;
        setLayout(new FlowLayout());
        setBackground(new Color(230, 230, 230));
        setBorder(BorderFactory.createLoweredBevelBorder());
    }
    public void update(Integer playerObjective, List<Integer> playerHand){
        removeAll();
        objectiveID = playerObjective;
        handCardsIDs = playerHand;
        JToggleButton changeSideBtn = createToggleButton();
        changeSideBtn.setEnabled(false);
        this.add(changeSideBtn);
        for(Integer id : playerHand){
            UnselectableCard card = new UnselectableCard(id, false, true);
            add(card);
        }
        UnselectableCard card = new UnselectableCard(playerObjective, false, true);
        this.add(card);
        revalidate();
        repaint();
    }

    @Override
    public void resize() {
        if(objectiveID >= 0 && !handCardsIDs.isEmpty())
            update(objectiveID, handCardsIDs);
    }

    private JToggleButton createToggleButton() {
        JToggleButton changeSideBtn = new JToggleButton("↷");
        changeSideBtn.setFont(new Font("Serif", Font.BOLD, 25));
        return changeSideBtn;
    }
    public static void main(String[] args){
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



        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        JViewBottomPanel viewBottomPanel = new JViewBottomPanel(null);
        viewBottomPanel.update(96, playerHand.stream().map(x -> x.getCardId()).toList());
        frame.add(viewBottomPanel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
