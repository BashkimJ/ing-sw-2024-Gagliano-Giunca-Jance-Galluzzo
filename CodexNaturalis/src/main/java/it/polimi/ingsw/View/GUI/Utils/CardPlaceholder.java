package main.java.it.polimi.ingsw.View.GUI.Utils;

import main.java.it.polimi.ingsw.View.GUI.GUI;

import javax.swing.*;
import java.awt.*;

public class CardPlaceholder extends JLabel{
    private final Point position;
    private final Color hooverColor = Color.WHITE;
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
