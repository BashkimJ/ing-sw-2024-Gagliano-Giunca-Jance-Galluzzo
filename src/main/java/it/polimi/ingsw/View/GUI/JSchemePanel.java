package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.Player.CardSchemeView;

import javax.swing.*;

public interface JSchemePanel {
    public void update(CardSchemeView cardSchemeView);
    public JPanel getSchemePanel();
}
