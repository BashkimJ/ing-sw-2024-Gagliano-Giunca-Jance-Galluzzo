package it.polimi.ingsw.Model.Save;
import it.polimi.ingsw.Controller.GameState;
import it.polimi.ingsw.Model.Cards.ObjectiveCard;
import it.polimi.ingsw.Model.GameStatus.Game;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Save implements Serializable {
    /**
     * Attributes with default visibility to be accessible from within the package,
     * expecially from the SavesManager but not from outside.
     */
    GameState gameState;
    Game game;
    Map<String, List<ObjectiveCard>> objectives;
    Map<String, Integer> offlinePlayers;
    String playerTurn;
    int numPlayers;

    public Save() {
    }

    public Save(GameState gs, Game g, Map<String, List<ObjectiveCard>> obj, Map<String, Integer> offPlayers, String turn, int numPlayers) {
        this.gameState = gs;
        this.game = g;
        this.objectives = obj;
        this.offlinePlayers = offPlayers;
        this.playerTurn = (turn == null ? "Player1" : turn);
        this.numPlayers = numPlayers;
    }

    public GameState getGameState() {
        return this.gameState;
    }

    public Game getGame() {
        return this.game;
    }

    public Map<String, Integer> getOfflinePlayers() {
        return this.offlinePlayers;
    }

    public Map<String, List<ObjectiveCard>> getObjectives() {
        return this.objectives;
    }

    public String getPlayerTurn() {
        return this.playerTurn;
    }

    public int getNumPlayers() {
        return numPlayers;
    }
}