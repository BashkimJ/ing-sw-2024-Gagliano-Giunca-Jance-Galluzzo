package main.java.it.polimi.ingsw.Model.Cards;

public abstract class Card {
    private Side front;
    private Side retro;
    private int CardId;

    /**
     * It is the constructor of the class
     * @param front The front side of the card to be created
     * @param retro The retro side of the card to be created
     * @param Id The id of the card
     */
    public Card(Side front,Side retro,int Id){
        this.front = front;
        this.retro = retro;
        this.CardId = Id;
    }

    /**
     *
     * @return The front side of the card.
     */
    public Side getFront(){
        return this.front;
    }

    /**
     *
     * @return The retro side of the card
     */
    public Side getRetro() {
        return this.retro;
    }

    /**
     *
     * @return The card ID
     */
    public int getCardId() {
        return this.CardId;
    }
}
