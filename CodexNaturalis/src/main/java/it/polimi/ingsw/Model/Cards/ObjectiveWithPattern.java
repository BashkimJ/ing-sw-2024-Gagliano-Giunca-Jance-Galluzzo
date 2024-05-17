package main.java.it.polimi.ingsw.Model.Cards;

import main.java.it.polimi.ingsw.Model.Enumerations.Pattern;
import main.java.it.polimi.ingsw.Model.Enumerations.Resource;

public class ObjectiveWithPattern implements MainObjective{
    private final Pattern patternType;
    private final int points;


    /**
     * The constructor of the objective with patterns.
     * @param pattern The pattern that must be observable in the game to complete the objective.
     * @param points The points of the objective.
     */
    public ObjectiveWithPattern(Pattern pattern, int points){
        this.points = points;
        this.patternType = pattern;

    }

    /**
     *
     * @return The points of the objective
     */
    public int getPoints(){
        return this.points;
    }

    /**
     *
     * @return The pattern of the objective.
     */
    public Pattern getObjectives(){
        return this.patternType;
    }

    @Override
    public String toString(){
        String Info = patternType.name() + " points: " +points;
        return Info;
    }
}
