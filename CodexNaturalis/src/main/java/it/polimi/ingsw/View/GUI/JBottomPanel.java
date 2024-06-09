package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.Cards.ObjectiveCard;
import it.polimi.ingsw.Model.Cards.ResourceCard;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public interface JBottomPanel{
    void update(Integer playerObjective, List<Integer> playerHand);
    void resize();

}
