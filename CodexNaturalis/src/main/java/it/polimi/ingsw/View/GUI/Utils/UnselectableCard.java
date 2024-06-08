package main.java.it.polimi.ingsw.View.GUI.Utils;

import main.java.it.polimi.ingsw.View.GUI.GUI;

import javax.swing.*;
import java.awt.*;

public class UnselectableCard extends JLabel {

    public UnselectableCard(int cardId, boolean frontSide){
        this.setIcon(GUI.getImageIcon("Images/Cards/"+ (frontSide ? "Front" : "Back") + "/" + cardId +".png", 180, 120));
        setDefaultBorder();
        setToolTipText("ID: " + cardId);
    }
    private void setDefaultBorder(){
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(3,3,3,3),
                BorderFactory.createLineBorder(Color.GRAY, 2)));
    }

}
