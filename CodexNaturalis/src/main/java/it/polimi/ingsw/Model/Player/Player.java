/*  Player Class
    contains in the following order:
    - Player Attributes
    - Card related attributes and an instance of the Hand class
    - Player constructor
    - Get and Set of each Player Attribute
    - upPoints() takes as argument an amount to add to the player points
    - showHand() TUI implemented method
    - Initial and Objective Card related methods
    - Player constructors work with nickName and playerColour,
        insert all the Player attributes inside the constructor
        to recreate a pre-existing player
*/
package main.java.it.polimi.ingsw.Model.Player;

import main.java.it.polimi.ingsw.Model.Enumerations.*;
import main.java.it.polimi.ingsw.Model.Cards.*;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.*;

public class Player {
    private String nickName;
    private int points;
    private Colour playerColour;
    private BufferedImage tokenImage;

    private InitialCard playerInitial;
    private ObjectiveCard playerObjective;
    private List<ResourceCard> playerHand;
    private CardScheme playerScheme;

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

    //TUI implemented
    /*public void showHand() {
        int i=1;
        for (ResourceCard r : playerHand.getrCards()){
            System.out.println("--- Card " + i + ":");
            System.out.println("o Card type: " + r.getResourceType());
            System.out.println("o Front Side");

            //If free writes "free", else if has Item writes Item type, else writes Resource type
            //(Nested lambda expressions for synthetic purposes)
            System.out.println("  - Top left corner: " + ( r.getFront().getUpLeft().isFree() ? "free" : ( r.getFront().getUpLeft().getItems()!=null ? r.getFront().getUpLeft().getItems().toString() : r.getFront().getUpLeft().getResource().toString())));
            System.out.println("  - Top right corner: " + ( r.getFront().getUpRight().isFree() ? "free" : ( r.getFront().getUpRight().getItems()!=null ? r.getFront().getUpRight().getItems().toString() : r.getFront().getUpRight().getResource().toString())));
            System.out.println("  - Bottom left corner: " + ( r.getFront().getDownLeft().isFree() ? "free" : ( r.getFront().getDownLeft().getItems()!=null ? r.getFront().getDownLeft().getItems().toString() : r.getFront().getDownLeft().getResource().toString())));
            System.out.println("  - Bottom right corner: " + ( r.getFront().getUpRight().isFree() ? "free" : ( r.getFront().getDownRight().getItems()!=null ? r.getFront().getDownRight().getItems().toString() : r.getFront().getDownRight().getResource().toString())));
            System.out.println("  - Points on usage: "+ r .getPoints());
            i++;
        }
        for (GoldCard g : playerHand.getgCards()){
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
    }*/

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


    public void pickCard(ResourceCard c) throws ArrayIndexOutOfBoundsException {
        if(this.playerHand.size()==3)
            throw new ArrayIndexOutOfBoundsException(out.println("The playerHand is already full"));
        else
            this.playerHand.add(c);
    }
    public void setPlayerHand(List<ResourceCard> l) throws IllegalArgumentException {
        if(l.isEmpty() || l.size()>3)
            throw new IllegalArgumentException(out.println("Trying to assign a wrong sized list to playerHand."));

        if (this.playerHand.isEmpty()) {
            this.playerHand.addAll(l);
        } else {
            this.playerHand.removeAll();
            this.playerHand.addAll(l);
        }
    }

    public List<ResourceCard> getPlayerHand(){
        return this.playerHand;
    }
    public ResourceCard getCardById(int id) throws IllegalArgumentException{
        if(id<0 || id>86)
            throw new IllegalArgumentException(out.println("Invalid card Id to get."));

        for(ResourceCard c: this.playerHand)
            if (c.getCardId() == id)
                return c;

        out.println("This card is not present in the current player hand.");
        return null;
        }

    // INDEXXXXXXXXXXXXXXXXXXXXXXX
    public void removeCardById(int id) throws IllegalArgumentException {
        if (id < 0 || id > 86)
            throw new IllegalArgumentException(out.println("Invalid card Id to remove."));

        for(ResourceCard c: this.playerHand)
            if (c.getCardId() == id)
                this.playerHand.remove();

        out.println("This card is already not present in the current player hand.");
    }



}