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
import java.util.List;

public class Player {
    private String nickName;
    private int points;
    private Colour playerColour;
    private BufferedImage tokenImage;


    private Hand playerHand;
    private InitialCard playerInitial;
    private ObjectiveCard playerObjective;


    public Player(String name, Colour c){
        this.nickName = name;
        this.playerColour = c;
        this.points = 0;
        //Need to ask permission for this lib
        try {
            this.tokenImage = ImageIO.read(new File("/../ImageFiles/" + c + ".png"));
        } catch (IOException e) {
            System.out.println("Impossible to load token image.");
        }

        this.playerHand = new Hand();
    }
    public Player(String name, Colour c, List<ResourceCard> r, List<GoldCard> g, InitialCard i, ObjectiveCard o){
        this.nickName = name;
        this.playerColour = c;
        this.points = 0;
        //Need to ask permission for this lib
        try {
            this.tokenImage = ImageIO.read(new File("/../ImageFiles/" + c + ".png"));
        } catch (IOException e) {
            System.out.println("Impossible to load token image.");
        }

        this.playerHand = new Hand(r, g);
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
    public void showHand() {
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
            System.out.println("  - Requires: " + g.getNeccesaryRes().toString());
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


}