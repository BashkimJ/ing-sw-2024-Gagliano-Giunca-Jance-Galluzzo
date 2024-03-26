package main.java.it.polimi.ingsw.Model.Cards;

public abstract class Card {
    private Side front;
    private Side retro;
    private int CardId;

    public Card(Side front,Side retro,int Id){
        this.front = front;
        this.retro = retro;
        this.CardId = Id;
    }

    public Side getFront(){
        return this.front;
    }

    public Side getRetro() {
        return this.retro;
    }

    public int getCardId() {
        return this.CardId;
    }
}
