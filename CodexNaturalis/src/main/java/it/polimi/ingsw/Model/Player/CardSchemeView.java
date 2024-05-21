package main.java.it.polimi.ingsw.Model.Player;

import main.java.it.polimi.ingsw.Model.Cards.Side;
import main.java.it.polimi.ingsw.Model.Enumerations.Items;
import main.java.it.polimi.ingsw.Model.Enumerations.Resource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class CardSchemeView implements Serializable {
    private final Map<ArrayList<Integer>, Side> playedCards;
    private final Map<ArrayList<Integer>, Resource> CardsResource;
    private final int [][] Scheme;//0-> Free, 1->Part of the Card, 2->Free corner, 3->Corner taken or Not visible
    private final int numAnimal;
    private  final int numInsects;
    private final int numFungi;
    private final int numPlants;
    private final int numInkwell;
    private final int numQuill;
    private final int numManuscript;
    private String red ="\u001B[31m";
    private String reset  ="\u001B[0m";

    public CardSchemeView(CardScheme scheme){
        this.playedCards = scheme.getPlayedCards();
        this.CardsResource = scheme.playedResources();
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
    public int[][] getScheme() {
        return Scheme;
    }
    public int getNumAnimal(){
        return this.numAnimal;
    }
    public int getNumInsects() {
        return numInsects;
    }
    public int getNumFungi() {
        return numFungi;
    }
    public int getNumPlants(){
        return this.numPlants;
    }
    public int getNumInkwell(){
        return this.numInkwell;
    }
    public int getNumManuscript() {
        return numManuscript;
    }
    public int getNumQuill() {
        return numQuill;
    }

    @Override
    public String toString(){
        String myScheme = "*********************\n";
        int left=0,right=0,up=0,down = 0;
        boolean found= false;
        for(int i = 0;i<160;i++){
            for(int j=0;j<160;j++){
                if(Scheme[i][j]!=0){
                    up = i;
                    found = true;
                    break;
                }
            }
            if(found){
                found = false;
                break;
            }
        }
        for(int j = 0;j<160;j++){
            for(int i=0;i<160;i++){
                if(Scheme[i][j]!=0){
                    left = j;
                    found = true;
                    break;
                }
            }
            if(found){
                found = false;
                break;
            }
        }
        for(int i = 159;i>=0;i--){
            for(int j=0;j<160;j++){
                if(Scheme[i][j]!=0){
                    down = i;
                    found = true;
                    break;
                }
            }
            if(found){
                found = false;
                break;
            }
        }
        for(int j = 159;j>=0;j--){
            for(int i=0;i<160;i++){
                if(Scheme[i][j]!=0){
                    right = j;
                    found = true;
                    break;
                }
            }
            if(found){
                found = false;
                break;
            }
        }

        for(int i = up;i<=down;i++){
            for(int j = left;j<=right;j++){
                if(Scheme[i][j]==0){
                    myScheme = myScheme + " ";/*"\033[30;41;1m" + " " + "\033[0;0;0m"*/ ;
                }
                else {
                    if ((i == 80 && j == 80) || (i == 79 && j == 80) || (i == 81 && j == 80) ||(i == 80 && j == 79) || (i == 80 && j == 81)) {
                        myScheme = myScheme + red + Scheme[i][j] + reset;
                    } else {
                        myScheme = myScheme + Scheme[i][j];
                    }
                }
            }
            myScheme = myScheme +"\n";
        }
        myScheme = myScheme + "\n";
        for(ArrayList<Integer> pos: playedCards.keySet()){
            myScheme = myScheme + "Pos: " + "(" + pos.get(0) + "," + pos.get(1) + ")--> " + playedCards.get(pos).toString() + "\n";
            if(CardsResource.containsKey(pos)){
                myScheme = myScheme + CardsResource.get(pos).name() +"\n";
            }



        }
        myScheme = myScheme + "Nr.Animal: " + numAnimal + " Nr.Fungi:"  + numFungi + " Nr.Plant: " + numPlants + " Nr.Insects: " + numInsects + "" +"\n";
        myScheme = myScheme + "Nr.Inkwell: " +numInkwell + " Nr.Quill: " +  numQuill + " Nr.Manuscript: " + numManuscript;
        return myScheme;
    }
}
