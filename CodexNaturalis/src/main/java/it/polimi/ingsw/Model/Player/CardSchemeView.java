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
    @Override
    public String toString(){
        String myScheme = "*********************\n";
        for(int i = 0;i<160;i++){
            for(int j = 0;j<160;j++){
                if(Scheme[i][j]==0){
                    myScheme = myScheme + " ";
                }
                else{
                    myScheme = myScheme + Scheme[i][j];               }
            }
            myScheme = myScheme + "\n";
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
