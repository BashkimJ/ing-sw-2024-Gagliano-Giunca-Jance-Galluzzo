package main.java.it.polimi.ingsw.View.GUI;

import main.java.it.polimi.ingsw.Model.Cards.ObjectiveCard;
import main.java.it.polimi.ingsw.Model.Cards.ResourceCard;
import main.java.it.polimi.ingsw.Model.Player.PlayerView;
import main.java.it.polimi.ingsw.Network.ClientManager;
import main.java.it.polimi.ingsw.Network.Messages.*;
import main.java.it.polimi.ingsw.View.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GUI implements View {
    protected ClientManager clientManager;
    private ConfigFrame configFrame = null;
    private GameFrame gameFrame = null;

    public void setClientManager(ClientManager clientManager){
        this.clientManager = clientManager;
    }

    public void initGUI(){
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        serverInfo();
    }
    public void serverInfo(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                configFrame = new ConfigFrame(GUI.this);
            }
        });

    }

    @Override
    public void stop() {

    }

    @Override
    public void askForNewGame() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                configFrame.changePanel("2");
                configFrame.setSwitchable(false);
            }
        });
    }

    @Override
    public void askNickName() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                configFrame.changePanel("3");
                configFrame.setSwitchable(true);
            }
        });
    }

    @Override
    public void errorMessage(String message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(null, message,
                        "Error!", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    @Override
    public void showLogin(boolean connected, boolean nameAccepted) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (nameAccepted && connected) {
                    configFrame.changePanel("5");
                } else if (!nameAccepted && connected) {
                    errorMessage("You can't use this nickname.");
                }
            }
        });
    }

    @Override
    public void askNumPlayers() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                configFrame.changePanel("4");
                configFrame.setSwitchable(false);
            }
        });
    }

    @Override
    public void chooseObjectiveCard(Message message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                configFrame.closeWindow();
                configFrame = null;
                gameFrame = new GameFrame(GUI.this);
                gameFrame.showTwoObj(((ChooseObjReq)message).getOptions().get(0), ((ChooseObjReq)message).getOptions().get(1));
            }
        });
    }

    @Override
    public void showInitial(Message message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if(configFrame != null) {
                    configFrame.closeWindow();
                    configFrame = null;
                    gameFrame = new GameFrame(GUI.this);
                }
                gameFrame.showInitial(((InitialCardMess)message).getCard());
            }
        });
    }

    @Override
    public void alertGameStarted(Message message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if(configFrame != null) {
                    configFrame.closeWindow();
                    configFrame = null;
                    gameFrame = new GameFrame(GUI.this);
                }
                gameFrame.showGamePanel();
            }
        });
        clientManager.showGameSt();
        clientManager.showPlayer(clientManager.getNickName());
    }


    @Override
    public void showChatMessage(Message message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gameFrame.showChatMessage(message.getNickName(), ((ChatMess)message).getMess());
            }
        });
    }

    @Override
        public void showPlayer(Message message) {
        ShowPlayerInfo showPlayerInfo = (ShowPlayerInfo) message;
        PlayerView player = showPlayerInfo.getPlayer();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gameFrame.updateCenterPanel(player);
            }
        });

    }

    @Override
    public void showGameInfo(Message message) {
        ArrayList<ResourceCard> revealed = ((ShowGameResp)message).getRevealed();
        ArrayList<ObjectiveCard> obj =((ShowGameResp)message).getGlobalObj();
        ArrayList<ResourceCard> deck = ((ShowGameResp)message).getDeck();
        List<String> onlinePlayers = ((ShowGameResp) message).getPlayers();
        String turn = ((ShowGameResp)message).getPlayerTurn();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gameFrame.updateRightPanel(revealed, obj, deck, onlinePlayers);
                gameFrame.updateLeftPanel(onlinePlayers, turn);
            }
        });

    }


    @Override
    public void winner(String message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gameFrame.showWinner(message);
            }
        });

    }

    @Override
    public void afterPlayerMove(Message message) {
        PlayerMoveResp msg = (PlayerMoveResp) message;
        PlayerView player = msg.getUpdatedPlayer();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gameFrame.updateCenterPanel(player);
            }
        });



    }
    public static ImageIcon getImageIcon(String path, int pixelWidth, int pixelHeight){
        ImageIcon icon;
        try {
            icon = new ImageIcon(ImageIO.read(GUI.class.getClassLoader().getResourceAsStream(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(pixelWidth != 0 && pixelHeight !=0){
            icon= new ImageIcon(icon.getImage().getScaledInstance(pixelWidth, pixelHeight,  Image.SCALE_SMOOTH));
        }
        return icon;
    }
    public static ImageIcon getPlaceholder(int pixelWidth, int pixelHeight){
        return getImageIcon("Images/Cards/Placeholder.png", pixelWidth, pixelHeight);
    }
}
