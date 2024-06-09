package it.polimi.ingsw.Model.Cards;
import it.polimi.ingsw.Model.Enumerations.Resource;


import java.util.List;

public class InitialCard extends Card{
    private final List<Resource> middleResource;
    private final String reset = "\033[0;0;0m";

    /**
     * The constructor of the card.
     * @param front The front side of the card.
     * @param retro The retro side of the card.
     * @param Id The card ID, which is an integer.
     * @param rsc The resources contained in the front of the card. Type List<Resource>.
     */
    public InitialCard(Side front,Side retro, int Id, List<Resource> rsc){
        super(front,retro,Id);
        middleResource = rsc;
    }

    /**
     *
     * @return The list of the resources contained int the card.
     */
    public List<Resource> getMiddleResource(){
        return this.middleResource;
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
            String yellow = "\033[30;43;1m";
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
        String myCardId = "***********ID: " + getCardId() + " ****************\n";
        String[][] card = new String[3][7];
        for(int i=0;i<3;i++){
            for(int j=0;j<7;j++){
                if(j==3){
                    card[i][j] = " ";
                }
                else{
                    String black = "\033[30;40;1m";
                    card[i][j] = black + "  " + reset;
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
                myCardId = myCardId + card[i][j];
            }
            myCardId+="\n";
        }
        myCardId+="\n";
        for( Resource rsc: middleResource){
           myCardId = myCardId + rsc.name() + " ";
        }
        return myCardId;

    }
}
