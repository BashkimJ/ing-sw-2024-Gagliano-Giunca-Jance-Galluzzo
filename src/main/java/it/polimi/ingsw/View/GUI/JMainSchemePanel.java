package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.Player.CardSchemeView;

import javax.swing.*;

public class JMainSchemePanel implements JSchemePanel{
    JViewSchemePanel viewSchemePanel;
    public JMainSchemePanel(GUI gui){
        viewSchemePanel = new JViewSchemePanel(gui);
    }


    @Override
    public void update(CardSchemeView cardSchemeView) {
        viewSchemePanel.update(cardSchemeView);
        viewSchemePanel.createPlaceholders(cardSchemeView.getCardsIds());
    }
    public JPanel getSchemePanel(){
        return viewSchemePanel;
    }
}
