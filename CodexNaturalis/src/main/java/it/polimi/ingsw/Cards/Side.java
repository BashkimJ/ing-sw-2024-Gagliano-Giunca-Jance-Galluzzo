package main.java.it.polimi.ingsw.Cards;

import java.io.File;

public class Side {
    private Corner upLeft;
    private Corner downLeft;
    private Corner upRight;
    private Corner downRight;
    private File image;

    public Side(Corner upLeft,Corner downLeft,Corner upRight,Corner downRight, File img)
    {
        this.upLeft = upLeft;
        this.downLeft = downLeft;
        this.upRight = upRight;
        this.downRight = downRight;
        this.image = img;
    }

    public Corner getUpLeft(){
        return this.upLeft;
    }
    public Corner getDownLeft(){
        return this.downLeft;
    }
    public Corner getUpRight(){
        return this.upRight;
    }
    public Corner getDownRight(){
        return this.downRight;
    }
    public File getImage(){
        return this.image;
    }
}
