package main.java.it.polimi.ingsw.View.GUI.Utils;

import main.java.it.polimi.ingsw.View.GUI.GUI;

import javax.swing.*;
import java.awt.*;

public class SelectableCard extends JLabel {

    private final int cardId;
    private final Color selectionColor;
    public SelectableCard(int cardId, Color selectionColor, boolean frontSide){
        this.setIcon(GUI.getImageIcon("Images/Cards/"+ (frontSide ? "Front" : "Back") + "/" + cardId +".png", 180, 120));
        this.cardId = cardId;
        this.selectionColor = selectionColor;
        setDefaultBorder();
        setToolTipText("ID: " + cardId);
    }
    public void showSide(boolean frontSide){
        this.setIcon(GUI.getImageIcon("Images/Cards/"+ (frontSide ? "Front" : "Back") + "/" + cardId +".png", 180, 120));
    }
    public void setDefaultBorder(){
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(3,3,3,3),
                BorderFactory.createLineBorder(Color.BLACK, 2)));
    }
    public void setSelectedBorder(){
        this.setBorder(BorderFactory.createLineBorder(selectionColor, 5));
    }
    public void setEnteredBorder(){
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(3,3,3,3),
                BorderFactory.createLineBorder(selectionColor, 2)));
    }

    public int getCardId() {
        return cardId;
    }
}
