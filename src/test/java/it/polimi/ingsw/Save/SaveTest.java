package it.polimi.ingsw.Save;

import it.polimi.ingsw.Controller.GameState;
import it.polimi.ingsw.Model.Enumerations.Colour;
import it.polimi.ingsw.Model.GameStatus.Game;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Save.Save;
import org.junit.jupiter.api.Test;

public class SaveTest {
    private Save save;
    @Test
    void initSave(){
        save = new Save(GameState.Lobby_State,new Game(new Player("1", Colour.blue),1),null,null,"1",0);
    }


}
