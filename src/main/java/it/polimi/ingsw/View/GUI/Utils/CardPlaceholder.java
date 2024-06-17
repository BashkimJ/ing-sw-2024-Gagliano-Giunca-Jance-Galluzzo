package it.polimi.ingsw.View.GUI.Utils;

import it.polimi.ingsw.View.GUI.GUI;

import javax.swing.*;
import java.awt.*;
/**
 * JLabel that represents a placeholder used for selecting coordinates for card placing in the card scheme
 */
public class CardPlaceholder extends JLabel{
    private final Point position;
    private final Color hooverColor = Color.WHITE;

    /**
     * Class contructor
     * @param position indicates the coordinates that the placeholder represents
     */
    public CardPlaceholder(Point position){
        this.position = position;
        this.setIcon(GUI.getImageIcon("Images/Cards/CardPlaceholder.png", 180, 120));
        setDefaultBorder();
        setToolTipText("{" + position.x + ", " + position.y + "}");
    }
    public void setDefaultBorder(){
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(3,3,3,3),
                BorderFactory.createLineBorder(Color.BLACK, 2)));
    }
    public void setEnteredBorder(){
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(3,3,3,3),
                BorderFactory.createLineBorder(hooverColor, 2)));
    }
    public Point getPosition() {
        return position;
    }
}
