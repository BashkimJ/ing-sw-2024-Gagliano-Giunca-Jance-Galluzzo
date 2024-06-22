package it.polimi.ingsw.View.GUI.Utils;

import it.polimi.ingsw.View.GUI.GUI;
import it.polimi.ingsw.View.GUI.GameFrame;

import javax.swing.*;
import java.awt.*;

/**
 * JLabel that represent a non-selectable card.
 */
public class UnselectableCard extends JLabel {
    /**
     * Class constructor
     * @param cardId id of the card
     * @param frontSide side of the card
     * @param resizable indicates if the size of the label should take into account the GameFrame scaling factor
     */
    public UnselectableCard(int cardId, boolean frontSide, boolean resizable){
        if(resizable)
            this.setIcon(GUI.getImageIcon("Images/Cards/"+ (frontSide ? "Front" : "Back") + "/" + cardId +".png", (int)(GameFrame.CARD_WIDTH*GameFrame.getScalingFactor()),
                    (int)(GameFrame.CARD_HEIGHT*GameFrame.getScalingFactor())));
        else
            this.setIcon(GUI.getImageIcon("Images/Cards/"+ (frontSide ? "Front" : "Back") + "/" + cardId +".png", GameFrame.CARD_WIDTH, GameFrame.CARD_HEIGHT));
        setDefaultBorder();
    }
    private void setDefaultBorder(){
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(3,3,3,3),
                BorderFactory.createLineBorder(Color.GRAY, 2)));
    }

}
