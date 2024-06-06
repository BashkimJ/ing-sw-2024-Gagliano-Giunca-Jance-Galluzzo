package main.java.it.polimi.ingsw.View.GUI;

import main.java.it.polimi.ingsw.Model.Cards.ObjectiveCard;
import main.java.it.polimi.ingsw.Model.Cards.ResourceCard;
import main.java.it.polimi.ingsw.Model.Player.CardSchemeView;
import main.java.it.polimi.ingsw.Model.Player.PlayerView;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class JCenterPanel extends JPanel {
    private JBottomPanel bottomPanel;
    private JSchemePanel schemePanel;
    public JCenterPanel(JSchemePanel schemePanel, JBottomPanel bottomPanel){
        setLayout(new BorderLayout());
        this.bottomPanel = bottomPanel;
        this.schemePanel = schemePanel;
        add(schemePanel.getSchemePanel(), BorderLayout.CENTER);
        add((JPanel)bottomPanel, BorderLayout.SOUTH);
    }
    public void update(PlayerView player){
        ObjectiveCard playerObjective = player.getPlayerObjective();
        List<ResourceCard> playerHand = player.getPlayerHand();
        CardSchemeView cardSchemeView = player.getPlayerScheme();
        bottomPanel.update(playerObjective, playerHand);
        schemePanel.update(cardSchemeView);
    }
}
