package main.java.it.polimi.ingsw.Model.Player;

import main.java.it.polimi.ingsw.Model.Enumerations.*;
import main.java.it.polimi.ingsw.Model.Cards.*;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import static java.lang.System.*;

public class Player implements Serializable {
    private final String nickName;
    private int points;
    private Colour playerColour;
    private transient BufferedImage tokenImage;
    private InitialCard playerInitial;
    private ObjectiveCard playerObjective;
    private List<ResourceCard> playerHand;
    private CardScheme playerScheme;


/**
 * This is the standard constructor that prepares all the Player attributes.
 * @param name the player's name.
 * @param c the player's colour.
 **/
    public Player(String name, Colour c){
        this.nickName = name;
        this.playerColour = c;
        this.points = 0;
        this.playerHand = new ArrayList<ResourceCard>();
        this.playerScheme = new CardScheme();
        //Need to ask permission for this lib
        try {
            this.tokenImage = ImageIO.read(new File("/../ImageFiles/" + c + ".png"));
        } catch (IOException e) {
            out.println("Impossible to load token image.");
        }
    }

    /**
     * This is the secondary Player constructors that helps to restore a pre-existing Player from a datasave.
     * @param name the player's name.
     * @param c the player's colour.
     * @param r the player's previous hand.
     * @param i the player's Inital card placed at the centre of his CardScheme.
     * @param o the player's secret ObjectiveCard chosen.
     * @param p the player's previous score.
     **/
    public Player(String name, Colour c, List<ResourceCard> r, InitialCard i, ObjectiveCard o, int p){
        this.nickName = name;
        this.playerColour = c;
        this.points = p;
        //Need to ask permission for this lib
        try {
            this.tokenImage = ImageIO.read(new File("/../ImageFiles/" + c + ".png"));
        } catch (IOException e) {
            out.println("Impossible to load token image.");
        }

        this.playerHand = r;
        this.playerInitial = i;
        this.playerObjective = o;
    }

    public String getNickName() {
        return nickName;
    }

    public Colour getPlayerColour() {
        return playerColour;
    }

    public void setPlayerColour(Colour playerColour) {
        this.playerColour = playerColour;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void upPoints(int increment) {
        this.points += increment;
    }

    public BufferedImage getTokenImage() {
        return tokenImage;
    }


    public CardScheme getPlayerScheme() {
        return playerScheme;
    }

/**
 * This method provides a brief summary of each card in the player's possession via text interface.
 */
    public void showHand() {
        int i=0;
        GoldCard g;
        for (ResourceCard r : this.playerHand){
            if( r.getNecessaryRes()!=null){
                //Gold card
                g = (GoldCard) r;
                System.out.println("--- Card " + i + ":");
                System.out.println("o Card type: " + g.getResourceType());
                System.out.println("o Front Side");

                System.out.println("  - Top left corner: " + ( g.getFront().getUpLeft().isFree() ? "free" : ( g.getFront().getUpLeft().getItems()!=null ? g.getFront().getUpLeft().getItems().toString() : g.getFront().getUpLeft().getResource().toString())));
                System.out.println("  - Top right corner: " +(  g.getFront().getUpRight().isFree() ? "free" : ( g.getFront().getUpRight().getItems()!=null ? g.getFront().getUpRight().getItems().toString() : g.getFront().getUpRight().getResource().toString())));
                System.out.println("  - Bottom left corner: " + ( g.getFront().getDownLeft().isFree() ? "free" : ( g.getFront().getDownLeft().getItems()!=null ? g.getFront().getDownLeft().getItems().toString() : g.getFront().getDownLeft().getResource().toString())));
                System.out.println("  - Bottom right corner: " + ( g.getFront().getUpRight().isFree() ? "free" : ( g.getFront().getDownRight().getItems()!=null ? g.getFront().getDownRight().getItems().toString() : g.getFront().getDownRight().getResource().toString())));
                if( g.getCondition()!=null ){
                    System.out.println("  - One point per: " + g.getCondition().toString());
                }
                else {
                    System.out.println("  - Points on usage: " + g.getPoints());
                }
                System.out.println("  - Requires: " + g.getNecessaryRes().toString());
            }
            else {
                System.out.println("--- Card " + i + ":");
                System.out.println("o Card type: " + r.getResourceType());
                System.out.println("o Front Side");

                //If free writes "free", else if has Item writes Item type, else writes Resource type
                //(Nested lambda expressions for synthetic purposes)
                System.out.println("  - Top left corner: " + (r.getFront().getUpLeft().isFree() ? "free" : (r.getFront().getUpLeft().getItems() != null ? r.getFront().getUpLeft().getItems().toString() : r.getFront().getUpLeft().getResource().toString())));
                System.out.println("  - Top right corner: " + (r.getFront().getUpRight().isFree() ? "free" : (r.getFront().getUpRight().getItems() != null ? r.getFront().getUpRight().getItems().toString() : r.getFront().getUpRight().getResource().toString())));
                System.out.println("  - Bottom left corner: " + (r.getFront().getDownLeft().isFree() ? "free" : (r.getFront().getDownLeft().getItems() != null ? r.getFront().getDownLeft().getItems().toString() : r.getFront().getDownLeft().getResource().toString())));
                System.out.println("  - Bottom right corner: " + (r.getFront().getUpRight().isFree() ? "free" : (r.getFront().getDownRight().getItems() != null ? r.getFront().getDownRight().getItems().toString() : r.getFront().getDownRight().getResource().toString())));
                System.out.println("  - Points on usage: " + r.getPoints());
                i++;
            }
        }
    }

    public InitialCard getPlayerInitial(){
        return this.playerInitial;
    }

    public void setPlayerInitial(InitialCard playerInitial) {
        this.playerInitial = playerInitial;
    }

    public ObjectiveCard getPlayerObjective() {
        return playerObjective;
    }

    public void setPlayerObjective(ObjectiveCard playerObjective) {
        this.playerObjective = playerObjective;
    }


    /**
     * This method takes as input a card to add to the current playerHand.
     * @param c card to be added.
     * @throws IndexOutOfBoundsException if the caller is trying to add a card whilst the playerHand already contains 3.
     */
    public void pickCard(ResourceCard c) throws IndexOutOfBoundsException{
        if(this.playerHand.size()==3)
            throw new IndexOutOfBoundsException("The playerHand is already full");
        else
            this.playerHand.add(c);
    }

    /**
     * This method takes as input a list of cards to replace the current playerHand.
     * @param l list to be added.
     * @throws IllegalArgumentException if the caller provides as argument an empty list or a list with a size bigger than 3.
     */
    public void setPlayerHand(List<ResourceCard> l) throws IllegalArgumentException {
        if(l.isEmpty() || l.size()>3)
            throw new IllegalArgumentException("Trying to assign a wrong sized list to playerHand.");

        if (!this.playerHand.isEmpty())
            this.playerHand.removeAll(this.playerHand);
        this.playerHand.addAll(l);

    }

    public List<ResourceCard> getPlayerHand(){
        return this.playerHand;
    }

    /**
     * This method takes as input the index of the playerHand's card to be returned.
     * @param idx index of the card desired.
     * @return the card with the provided index if exists.
     * @throws IllegalArgumentException if the caller provides as argument an index which is less than 0 or bigger than the current hand size.
     */
    public ResourceCard getCardByIdx(int idx) throws IllegalArgumentException {
        if (idx < 0 || idx > this.playerHand.size()-1)
            throw new IllegalArgumentException("Invalid card index to get.");

        return this.playerHand.get(idx);
    }

    /**
     * This method takes as input the index of the playerHand's card to be removed.
     * @param idx index of the card desired.
     * @throws IllegalArgumentException if the caller provides as argument an index which is less than 0 or bigger than the current hand size.
     */
    public void removeCardByIdx(int idx) throws IllegalArgumentException {
        if (idx < 0 || idx > this.playerHand.size()-1)
            throw new IllegalArgumentException("Invalid card index to remove.");

        this.playerHand.remove(idx);
    }


}