package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.Cards.ObjectiveCard;
import it.polimi.ingsw.Model.Cards.ResourceCard;
import it.polimi.ingsw.Model.Player.CardSchemeView;
import it.polimi.ingsw.Model.Player.PlayerView;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * JPanel that is a composition of a JBottomPanel and a JSchemePanel. It is relative to one specific player
 */
public class JCenterPanel extends JPanel {
    private JBottomPanel bottomPanel;
    private JSchemePanel schemePanel;

    /**
     * Class constructor
     * @param schemePanel object of a class that implements JSchemePanel
     * @param bottomPanel object of a class that implements JBottomPanel
     */
    public JCenterPanel(JSchemePanel schemePanel, JBottomPanel bottomPanel){
        setLayout(new BorderLayout());
        this.bottomPanel = bottomPanel;
        this.schemePanel = schemePanel;
        add(schemePanel.getSchemePanel(), BorderLayout.CENTER);
        add((JPanel)bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Propagates the updates to the subcomponents
     * @param player the updated player
     */
    public void update(PlayerView player){
        Integer playerObjective = player.getPlayerObjective().getCardId();
        List<Integer> playerHand = player.getPlayerHand().stream().map(x -> x.getCardId()).toList();
        CardSchemeView cardSchemeView = player.getPlayerScheme();
        bottomPanel.update(playerObjective, playerHand);
        schemePanel.update(cardSchemeView);
    }

    /**
     * Propagates the resize request to the JBottomPanel
     */
    public void resize(){
        bottomPanel.resize();
    }
}
