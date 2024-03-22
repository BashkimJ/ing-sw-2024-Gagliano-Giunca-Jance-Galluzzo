package main.java.it.polimi.ingsw.Cards;

import main.java.it.polimi.ingsw.Enumerations.Pattern;

public class ObjectiveWithPattern implements MainObjective{
    private Pattern patternType;
    private int points;

    public ObjectiveWithPattern(Pattern pattern, int points){
        this.points = points;
        this.patternType = pattern;
    }

    public int getPoints(){
        return this.points;
    }

    public Pattern getObjectives(){
        return this.patternType;
    }

}
