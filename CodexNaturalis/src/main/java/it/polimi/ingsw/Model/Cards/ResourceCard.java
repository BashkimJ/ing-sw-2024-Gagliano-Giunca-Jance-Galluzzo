package main.java.it.polimi.ingsw.Model.Cards;

import main.java.it.polimi.ingsw.Model.Enumerations.Resource;

import java.util.List;


public  class ResourceCard extends Card {
    private final int points;
    private final Resource resourceType;
    private final String red = "\033[30;41;1m";
    private final String green = "\033[30;42;1m";
    private final String purple = "\033[30;45;1m";
    private final String blue = "\033[30;44;1m";
    private final String yellow = "\033[30;43;1m";
    private final String reset = "\033[0;0;0m";

    /**
     * The resource card's constructor.
     *
     * @param front  The front side of the resource card
     * @param retro  The retro side of the resource card.
     * @param Id     The resource card ID.
     * @param rsc    The type of the resource the card is.
     * @param points The points that the card can give to a player when it is played.
     */
    public ResourceCard(Side front, Side retro, int Id, Resource rsc, int points) {
        super(front, retro, Id);
        this.points = points;
        this.resourceType = rsc;
    }

    /**
     * @return The points of the card.
     */
    public int getPoints() {
        return this.points;
    }

    /**
     * @return The type of the resource the card is.
     */
    public Resource getResourceType() {
        return this.resourceType;
    }
    public PointCondition getCondition(){
        return null;
    }
    public List<Resource> getNecessaryRes(){
        return null;
    }

    private String[][] colorCorner(Corner corner,String[][] board,String pos,String side){
        int x = 0;
        int y = 0;
        switch (pos) {
            case "UL" -> {
                if(side.equals("front")) {
                    x = 0;
                    y = 0;
                    break;
                }else{
                    x = 0;
                    y = 4;
                }
            }
            case "UR" -> {
                if(side.equals("front")) {
                    x = 0;
                    y = 2;
                    break;
                }else{
                    x = 0;
                    y = 6;
                }

            }
            case "DL" -> {
                if(side.equals("front")) {
                    x = 2;
                    y = 0;
                    break;
                }else{
                    x = 2;
                    y = 4;
                }

            }
            case "DR" -> {
                if(side.equals("front")) {
                    x = 2;
                    y = 2;
                    break;
                }else{
                    x = 2;
                    y = 6;
                }
            }
        }
        if (!corner.isVisible()) {
            return board;
        } else {
            if (corner.getResource() != null) {
                switch (corner.getResource()) {
                    case Animal -> {
                        board[x][y] = yellow + " A" + reset;
                        break;
                    }
                    case Insects -> {
                        board[x][y] = yellow + " I" + reset;
                        break;
                    }
                    case Plant -> {
                        board[x][y] = yellow + " P" + reset;
                        break;
                    }
                    case Fungi -> {
                        board[x][y] = yellow + " F" + reset;
                        break;
                    }
                }
                return board;
            }
            else if (corner.getItems() != null) {
                switch (corner.getItems()) {
                    case Quill -> {
                        board[x][y] = yellow + " q" + reset;
                        break;
                    }
                    case Inkwell -> {
                        board[x][y] = yellow + " i" + reset;
                        break;
                    }
                    case Manuscript -> {
                        board[x][y] = yellow + " m" + reset;
                        break;
                    }
                }

            }
            else{
                board[x][y] = yellow + "  " + reset;
            }
        }
        return board;
    }

    @Override
    public String toString(){
        String myCardID = "**********ID: " + getCardId()+"*****************\n";
        String[][] card = new String[3][7];
        String defColor = " ";
        switch (resourceType){
            case Animal ->defColor = blue;
            case Plant -> defColor = green;
            case Insects -> defColor = purple;
            case Fungi -> defColor = red;
        }
       for(int i=0;i<3;i++){
           for(int j=0;j<7;j++){
               if(j==3){
                   card[i][j] = " ";
               }
               else{
                   card[i][j] = defColor + "  " + reset;
               }
           }
       }
       Side front =getFront();
       Side retro = getRetro();
       Corner corner = front.getUpLeft();
       card = colorCorner(corner,card,"UL","front");
       corner = front.getUpRight();
       card = colorCorner(corner,card,"UR","front");
       corner = front.getDownRight();
       card = colorCorner(corner,card,"DR","front");
       corner = front.getDownLeft();
       card = colorCorner(corner,card,"DL","front");

       corner = retro.getUpLeft();
       card = colorCorner(corner,card,"UL","retro");
       corner = retro.getUpRight();
       card = colorCorner(corner,card,"UR","retro");
       corner = retro.getDownRight();
       card = colorCorner(corner,card,"DR","retro");
       corner = retro.getDownLeft();
       card = colorCorner(corner,card,"DL","retro");
       for(int i =0;i<3;i++){
           for(int j =0;j<7;j++){
               myCardID = myCardID + card[i][j];
           }
           myCardID+="\n";
       }
       myCardID+="\nPoints: " + getPoints() + "\n";
       return myCardID;

    }
}
