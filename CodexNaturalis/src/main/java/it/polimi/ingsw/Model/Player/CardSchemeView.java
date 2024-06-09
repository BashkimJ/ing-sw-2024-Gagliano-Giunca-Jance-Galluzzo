package it.polimi.ingsw.Model.Player;

import it.polimi.ingsw.Model.Cards.Corner;
import it.polimi.ingsw.Model.Cards.Side;
import it.polimi.ingsw.Model.Enumerations.Items;
import it.polimi.ingsw.Model.Enumerations.Resource;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/**
 * This class is very similar to the CardScheme,but it doesn't have the setter methods. It is used
 * to send the CardScheme from the server to player in order not to risk any changes during the travel of the corrisponding
 * message.
 * @see CardScheme
 */
public class CardSchemeView implements Serializable {
    private final Map<ArrayList<Integer>, Side> playedCards;
    private final Map<ArrayList<Integer>, Resource> CardsResource;
    private final Map<Point, Integer> cardsIds;
    private final int[][] Scheme;//0-> Free, 1->Part of the Card, 2->Free corner, 3->Corner taken or Not visible
    private final int numAnimal;
    private final int numInsects;
    private final int numFungi;
    private final int numPlants;
    private final int numInkwell;
    private final int numQuill;
    private final int numManuscript;
    String red = "\033[30;41;1m";
    String green = "\033[30;42;1m";
    String purple = "\033[30;45;1m";
    String blue = "\033[30;44;1m";
    String black = "\033[30;40;1m";
    String yellow = "\033[30;43;1m";
    String reset = "\033[0;0;0m";


    public CardSchemeView(CardScheme scheme) {
        this.playedCards = scheme.getPlayedCards();
        this.CardsResource = scheme.playedResources();
        this.cardsIds= scheme.getCardsIds();
        this.Scheme = scheme.show();
        this.numAnimal = scheme.getResourceNum(Resource.Animal);
        this.numInsects = scheme.getResourceNum(Resource.Insects);
        this.numFungi = scheme.getResourceNum((Resource.Fungi));
        this.numPlants = scheme.getResourceNum(Resource.Plant);
        this.numInkwell = scheme.getItemNum(Items.Inkwell);
        this.numManuscript = scheme.getItemNum(Items.Manuscript);
        this.numQuill = scheme.getItemNum(Items.Quill);

    }

    public Map<ArrayList<Integer>, Side> getPlayedCards() {
        return playedCards;
    }

    public Map<ArrayList<Integer>, Resource> getCardsResource() {
        return CardsResource;
    }

    public Map<Point, Integer> getCardsIds() {
        return cardsIds;
    }

    public int[][] getScheme() {
        return Scheme;
    }

    public int getNumAnimal() {
        return this.numAnimal;
    }

    public int getNumInsects() {
        return numInsects;
    }

    public int getNumFungi() {
        return numFungi;
    }

    public int getNumPlants() {
        return this.numPlants;
    }

    public int getNumInkwell() {
        return this.numInkwell;
    }

    public int getNumManuscript() {
        return numManuscript;
    }

    public int getNumQuill() {
        return numQuill;
    }

    private String getColor(ArrayList<Integer> position) {
        switch (CardsResource.get(position)) {
            case Animal -> {
                return blue;
            }
            case Insects -> {
                return purple;
            }
            case Fungi -> {
                return red;
            }
            case Plant -> {
                return green;
            }
        }
        return black;
    }

    @Override
    public String toString() {
        String myScheme = "*********************\n";
        int left = 0, right = 0, up = 0, down = 0;
        boolean found = false;
        String[][] coloredBoard = new String[160][160];
        coloredBoard = initialise(coloredBoard);
        for (int i = 0; i < 160; i++) {
            for (int j = 0; j < 160; j++) {
                if (Scheme[i][j] != 0) {
                    up = i;
                    found = true;
                    break;
                }
            }
            if (found) {
                found = false;
                break;
            }
        }
        for (int j = 0; j < 160; j++) {
            for (int i = 0; i < 160; i++) {
                if (Scheme[i][j] != 0) {
                    left = j;
                    found = true;
                    break;
                }
            }
            if (found) {
                found = false;
                break;
            }
        }
        for (int i = 159; i >= 0; i--) {
            for (int j = 0; j < 160; j++) {
                if (Scheme[i][j] != 0) {
                    down = i;
                    found = true;
                    break;
                }
            }
            if (found) {
                found = false;
                break;
            }
        }
        for (int j = 159; j >= 0; j--) {
            for (int i = 0; i < 160; i++) {
                if (Scheme[i][j] != 0) {
                    right = j;
                    found = true;
                    break;
                }
            }
            if (found) {
                found = false;
                break;
            }
        }
        String defaultColor;
        myScheme = myScheme + "\n";
        for (ArrayList<Integer> pos : new ArrayList<>(playedCards.keySet())) {
            if (pos.get(1) == 80 && pos.get(0) == 80) {
                defaultColor = black;
                coloredBoard[80][80] = defaultColor + "  " + reset;
                coloredBoard[80][79] = defaultColor + "  " + reset;
                coloredBoard[80][81] = defaultColor + "  " + reset;
                coloredBoard[79][80] = defaultColor + "  " + reset;
                coloredBoard[81][80] = defaultColor + "  " + reset;
            } else {
                defaultColor = getColor(pos);
                coloredBoard[pos.get(0)][pos.get(1)] = defaultColor + "  " + reset;
                coloredBoard[pos.get(0)][pos.get(1) - 1] = defaultColor + "  " + reset;
                coloredBoard[pos.get(0)][pos.get(1) + 1] = defaultColor + "  " + reset;
                coloredBoard[pos.get(0) - 1][pos.get(1)] = defaultColor + "  " + reset;
                coloredBoard[pos.get(0) + 1][pos.get(1)] = defaultColor + "  " + reset;
            }
            Side side = playedCards.get(pos);
            Corner corner = side.getUpLeft();
            coloredBoard = colorCorner(corner, coloredBoard, pos, "UL", defaultColor);
            corner = side.getDownLeft();
            coloredBoard = colorCorner(corner, coloredBoard, pos, "DL", defaultColor);
            corner = side.getUpRight();
            coloredBoard = colorCorner(corner, coloredBoard, pos, "UR", defaultColor);
            corner = side.getDownRight();
            coloredBoard = colorCorner(corner, coloredBoard, pos, "DR", defaultColor);
        }

        for(int i=up;i<=down;i++){
            for(int j=left;j<=right;j++){
                myScheme = myScheme + coloredBoard[i][j];
            }
            myScheme = myScheme + "\n";
        }
        myScheme = myScheme + "\n";
        myScheme = myScheme + "Nr.Animal: " + numAnimal + " Nr.Fungi:" + numFungi + " Nr.Plant: " + numPlants + " Nr.Insects: " + numInsects + "" + "\n";
        myScheme = myScheme + "Nr.Inkwell: " + numInkwell + " Nr.Quill: " + numQuill + " Nr.Manuscript: " + numManuscript;
        return myScheme;
    }

    private String[][] initialise(String[][] coloredBoard) {
        for(int i =0;i<160;i++){
            for(int j =0;j<160;j++){
                coloredBoard[i][j] = "  ";
            }
        }
        return coloredBoard;
    }

    private String[][] colorCorner(Corner corner, String[][] board, ArrayList<Integer> position, String pos, String color) {
        int x = 0, y = 0;
        switch (pos) {
            case "UL" -> {
                x = position.get(0) - 1;
                y = position.get(1) - 1;
                break;
            }
            case "UR" -> {
                x = position.get(0) - 1;
                y = position.get(1) + 1;
                break;
            }
            case "DL" -> {
                x = position.get(0) + 1;
                y = position.get(1) - 1;
                break;
            }
            case "DR" -> {
                x = position.get(0) + 1;
                y = position.get(1) + 1;
                break;
            }
        }
        if (!corner.isVisible()) {
            board[x][y] = color + "  " + reset;
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
}
