package main.java.it.polimi.ingsw.Model.Save;
import main.java.it.polimi.ingsw.Model.GameStatus.Deck;
import main.java.it.polimi.ingsw.Model.Player.Player;

import java.util.List;

public class Save
{
    private long seed; //GameId
    private Deck resDeck;
    private Deck goldDeck;
    private Deck initDeck;

    private List<Player> players;


    public Save(long seed, Deck res, Deck gold, Deck init, List<Player> players){
        this.seed = seed;
        this.resDeck = res;
        this.goldDeck = gold;
        this.initDeck = init;
        this.players.addAll(players);
    }

    public static long getSeed(){
        return this.seed;
    }

}