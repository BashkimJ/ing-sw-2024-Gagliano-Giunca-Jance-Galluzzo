package main.java.it.polimi.ingsw.Model.Cards;

import java.io.File;

public class Side {
    private Corner upLeft;
    private Corner downLeft;
    private Corner upRight;
    private Corner downRight;
    private File image;

    /**
     *
     * @param upLeft The up-left Corner of the card.
     * @param downLeft The down-left corner of the card to be created
     * @param upRight The up-right corner of the card to be created.
     * @param downRight The down-right corner of the card to be created.
     * @param img The file containing the image of the side.
     */
    public Side(Corner upLeft,Corner downLeft,Corner upRight,Corner downRight, File img)
    {
        this.upLeft = upLeft;
        this.downLeft = downLeft;
        this.upRight = upRight;
        this.downRight = downRight;
        this.image = img;
    }

    /**
     *
     * @return The up-left corner of the side.
     */
    public Corner getUpLeft(){
        return this.upLeft;
    }

    /**
     *
     * @return The down-left corner of the side.
     */
    public Corner getDownLeft(){
        return this.downLeft;
    }

    /**
     *
     * @return The up-right corner of the side.
     */
    public Corner getUpRight(){
        return this.upRight;
    }

    /**
     *
     * @returnThe down-right corner of the side.
     */
    public Corner getDownRight(){
        return this.downRight;
    }

    /**
     *
     * @return The image file of the side.
     */
    public File getImage(){
        return this.image;
    }
}
