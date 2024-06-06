package main.java.it.polimi.ingsw.Network.Messages;

public class NewGameMess extends Message{
    private boolean newGame;
    public NewGameMess(String Nickname,boolean newGame){
        super(Nickname,MessageType.New_game);
        this.newGame = newGame;
    }

    public boolean isNewGame() {
        return newGame;
    }
}
