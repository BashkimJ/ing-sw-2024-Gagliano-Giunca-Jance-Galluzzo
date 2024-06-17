package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.Player.CardSchemeView;

import javax.swing.*;
/**
 * Class that decorates a JViewSchemePanel by adding the placeholders when updating. It's used only by the JCenterPanel of the client's player
 */
public class JMainSchemePanel implements JSchemePanel{
    JViewSchemePanel viewSchemePanel;
    public JMainSchemePanel(GUI gui){
        viewSchemePanel = new JViewSchemePanel(gui);
    }

    /**
     * Updates the JViewSchemePanel and calls its method to add placeholders.
     * @param cardSchemeView the updated card scheme
     */
    @Override
    public void update(CardSchemeView cardSchemeView) {
        viewSchemePanel.update(cardSchemeView);
        viewSchemePanel.createPlaceholders(cardSchemeView.getCardsIds());
    }
    public JPanel getSchemePanel(){
        return viewSchemePanel;
    }
}
