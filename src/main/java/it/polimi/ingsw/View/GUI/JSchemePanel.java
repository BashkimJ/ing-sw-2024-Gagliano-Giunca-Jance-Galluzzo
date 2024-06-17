package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.Player.CardSchemeView;

import javax.swing.*;
/**
 *  Interface of JComponents that shows the card scheme
 */
public interface JSchemePanel {
    public void update(CardSchemeView cardSchemeView);
    public JPanel getSchemePanel();
}
