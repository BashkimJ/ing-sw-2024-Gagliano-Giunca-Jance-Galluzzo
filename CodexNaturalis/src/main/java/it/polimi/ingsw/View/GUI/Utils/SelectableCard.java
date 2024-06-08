package main.java.it.polimi.ingsw.View.GUI.Utils;

import main.java.it.polimi.ingsw.View.GUI.GUI;
import main.java.it.polimi.ingsw.View.GUI.GameFrame;

import javax.swing.*;
import java.awt.*;

public class SelectableCard extends JLabel {

    private final int cardId;
    private final Color selectionColor;
    private final boolean resizable;
    public SelectableCard(int cardId, Color selectionColor, boolean frontSide, boolean resizable){
        this.resizable = resizable;
        this.cardId = cardId;
        this.selectionColor = selectionColor;
        showSide(frontSide);
        setDefaultBorder();
        setToolTipText("ID: " + cardId);
    }
    public void showSide(boolean frontSide){
        if(resizable)
            this.setIcon(GUI.getImageIcon("Images/Cards/"+ (frontSide ? "Front" : "Back") + "/" + cardId +".png", (int)(GameFrame.CARD_WIDTH*GameFrame.getScalingFactor()),
                    (int)(GameFrame.CARD_HEIGHT*GameFrame.getScalingFactor())));
        else
            this.setIcon(GUI.getImageIcon("Images/Cards/"+ (frontSide ? "Front" : "Back") + "/" + cardId +".png", GameFrame.CARD_WIDTH, GameFrame.CARD_HEIGHT));
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
