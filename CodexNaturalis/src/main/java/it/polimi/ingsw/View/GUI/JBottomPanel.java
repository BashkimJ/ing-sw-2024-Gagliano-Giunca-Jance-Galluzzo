package main.java.it.polimi.ingsw.View.GUI;

import main.java.it.polimi.ingsw.Model.Cards.ObjectiveCard;
import main.java.it.polimi.ingsw.Model.Cards.ResourceCard;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public interface JBottomPanel{
    void update(ObjectiveCard playerObjective, List<ResourceCard> playerHand);

}
