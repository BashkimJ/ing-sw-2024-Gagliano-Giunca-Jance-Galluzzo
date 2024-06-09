package it.polimi.ingsw.View.GUI.Utils;

import it.polimi.ingsw.View.GUI.GUI;
import it.polimi.ingsw.View.GUI.GameFrame;

import javax.swing.*;
import java.awt.*;

public class UnselectableCard extends JLabel {

    public UnselectableCard(int cardId, boolean frontSide, boolean resizable){
        if(resizable)
            this.setIcon(GUI.getImageIcon("Images/Cards/"+ (frontSide ? "Front" : "Back") + "/" + cardId +".png", (int)(GameFrame.CARD_WIDTH*GameFrame.getScalingFactor()),
                    (int)(GameFrame.CARD_HEIGHT*GameFrame.getScalingFactor())));
        else
            this.setIcon(GUI.getImageIcon("Images/Cards/"+ (frontSide ? "Front" : "Back") + "/" + cardId +".png", GameFrame.CARD_WIDTH, GameFrame.CARD_HEIGHT));
        setDefaultBorder();
        setToolTipText("ID: " + cardId);
    }
    private void setDefaultBorder(){
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(3,3,3,3),
                BorderFactory.createLineBorder(Color.GRAY, 2)));
    }

}
