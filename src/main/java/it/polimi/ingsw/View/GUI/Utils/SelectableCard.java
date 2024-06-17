package it.polimi.ingsw.View.GUI.Utils;

import it.polimi.ingsw.View.GUI.GUI;
import it.polimi.ingsw.View.GUI.GameFrame;

import javax.swing.*;
import java.awt.*;
/**
 * JLabel that represents a selectable card.
 */
public class SelectableCard extends JLabel {

    private final int cardId;
    private final Color selectionColor;
    private final boolean resizable;
    /**
     * Class constructor
     * @param cardId id of the card
     * @param selectionColor color of mouse entering and/or selecting the label
     * @param frontSide side of the card
     * @param resizable indicates if the size of the label should take into account the GameFrame scaling factor
     */
    public SelectableCard(int cardId, Color selectionColor, boolean frontSide, boolean resizable){
        this.resizable = resizable;
        this.cardId = cardId;
        this.selectionColor = selectionColor;
        showSide(frontSide);
        setDefaultBorder();
        setToolTipText("ID: " + cardId);
    }

    /**
     * Set the side of the card to be shown
     * @param frontSide true if front side is to be shown, false otherwise
     */
    public void showSide(boolean frontSide){
        if(resizable)
            this.setIcon(GUI.getImageIcon("Images/Cards/"+ (frontSide ? "Front" : "Back") + "/" + cardId +".png", (int)(GameFrame.CARD_WIDTH*GameFrame.getScalingFactor()),
                    (int)(GameFrame.CARD_HEIGHT*GameFrame.getScalingFactor())));
        else
            this.setIcon(GUI.getImageIcon("Images/Cards/"+ (frontSide ? "Front" : "Back") + "/" + cardId +".png", GameFrame.CARD_WIDTH, GameFrame.CARD_HEIGHT));
    }

    /**
     * Sets a black border. Used for non-selected status
     */
    public void setDefaultBorder(){
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(3,3,3,3),
                BorderFactory.createLineBorder(Color.BLACK, 2)));
    }
    /**
     * Sets a thick colored border. Used for selected status
     */
    public void setSelectedBorder(){
        this.setBorder(BorderFactory.createLineBorder(selectionColor, 5));
    }
    /**
     * Sets a thin colored border. Used for entered but not selected status
     */
    public void setEnteredBorder(){
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(3,3,3,3),
                BorderFactory.createLineBorder(selectionColor, 2)));
    }

    public int getCardId() {
        return cardId;
    }
}
