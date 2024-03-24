/*  Hand Class
    contains in the following order:
    - Player's Hand attributes which includes the list of (up to) 3 cards
    - Hand constructor
    - Player actions such as draw or use cards after the initial phase

    - An empty declaration of Hand results in a Hand class with empty List
    - A Hand declaration with Resource card List and Gold card List
        results in an already filled hand, if card number is acceptable.

    - Method getCards() returns the list of all cards in Hand as Resource cards, while
        invoking the individual getrCards() and getgCards() returns the list with
        distinct Class types

    - To draw cards we can use setrCards() and setgCards()
*/
package main.java.it.polimi.ingsw.Player;

import main.java.it.polimi.ingsw.Cards.*;

import java.util.List;

public class Hand {
    private static final int DIM = 3;
    private List<ResourceCard> rCards;
    private List<GoldCard> gCards;

    //Empty Hand
    public Hand(){
        this.rCards = null;
        this.gCards = null;
    }
    //Already existing hand
    public Hand(List<ResourceCard> rCards, List<GoldCard> gCards) {
        int handSize = rCards.size() + gCards.size();
        if ( handSize > 3) {
            System.out.println("This hand has an incorrect amount of cards.");
        } else {
            this.rCards = rCards;
            this.gCards = gCards;
        }
    }

    public List<ResourceCard> getrCards() {
        return rCards;
    }

    public List<GoldCard> getgCards() {
        return gCards;
    }

    public static int getDim() {
        return DIM;
    }

    public List<ResourceCard> getHand(){
        List<ResourceCard> cards = null;
        int size = getrCards().size();
        if(size>0) {
            cards.addAll(getrCards());
        }
        size = getgCards().size();
        if(size>0) {
            cards.addAll(getgCards());
        }
        return cards;
    }

    public void setrCards(List<ResourceCard> rCards) {
        this.rCards = rCards;
    }

    public void setgCards(List<GoldCard> gCards) {
        this.gCards = gCards;
    }
}