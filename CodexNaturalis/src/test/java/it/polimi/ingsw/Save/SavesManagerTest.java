package test.java.it.polimi.ingsw.Save;

import main.java.it.polimi.ingsw.Controller.GameController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SavesManagerTest {
    GameController gameController;

    @Test
    void SaveGame(){
        this.gameController = new GameController();
        this.gameController.saveGame();
    }

    @Test
    void LoadGame(){
        this.gameController = new GameController();
        this.gameController.loadGame();
    }
}
