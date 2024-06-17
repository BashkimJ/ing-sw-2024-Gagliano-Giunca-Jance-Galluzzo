package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.Cards.ObjectiveCard;
import it.polimi.ingsw.Model.Cards.ResourceCard;
import it.polimi.ingsw.Model.Player.PlayerView;
import it.polimi.ingsw.Network.ClientManager;
import it.polimi.ingsw.Network.Messages.*;
import it.polimi.ingsw.View.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the View class for the GUI. Handles the change of frame and calls the frame's methods for handling View's methods.
 * @see View
 */
public class GUI implements View {

    protected ClientManager clientManager;
    private ConfigFrame configFrame = null;
    private GameFrame gameFrame = null;
    /**
     * Associates the clientManager attribute with the proper ClientManager object which is used to hide the network from the view.
     * @param clientManager
     */
    public void setClientManager(ClientManager clientManager){
        this.clientManager = clientManager;
    }

    /**
     * Sets the java Look and Feel.
     */
    public void initGUI(){
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        serverInfo();
    }

    /**
     * Creates the config frame.
     */
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
    /**
     * Changes the config frame's shown panel: ASK_GAME_PANEL.
     */
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
    /**
     * Changes the config frame's shown panel: NICKNAME_PANEL.
     */
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
    /**
     * Shows an error message as Message Dialog.
     * @param message the message to be shown
     */
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

    /**
     * If the connection and name are accepted changes the config frame's shown panel: WAITING_PANEL.
     * @param connected
     * @param nameAccepted
     */
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
    /**
     * Changes the config frame's shown panel: CREATE_PANEL.
     */
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

    /**
     * Initialize game frame and passes the two objective cards to the frame
     * @param message contains the objective cards to choose from
     */
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
    /**
     * Passes the initial card to the frame
     * @param message contains the initial card
     */
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
    /**
     * Shows the game frame GAME_PANEL and asks for data on the game and the player
     * @param message
     */
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

    /**
     * Shows the chat message.
     * @param message contains the chat message and the sender's nickname
     */
    @Override
    public void showChatMessage(Message message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gameFrame.showChatMessage(message.getNickName(), ((ChatMess)message).getMess());
            }
        });
    }

    /**
     * Calls the method to update player infos
     * @param message contains the new player infos
     */
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

    /**
     * Calls the methods to update game infos
     * @param message contains the new game infos
     */
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

    /**
     * Shows the winner screen.
     * @param message contains the winner message
     */
    @Override
    public void winner(String message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gameFrame.showWinner(message);
            }
        });

    }
    /**
     * Calls the method to update player infos
     * @param message contains the new player infos
     */
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

    /**
     * Gets an ImageIcon from file
     * @param path location of image
     * @param pixelWidth width of the returned ImageIcon
     * @param pixelHeight height of the returned ImageIcon
     * @return the ImageIcon
     */
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

    /**
     * Gets an empty white ImageIcon
     * @param pixelWidth width of the returned ImageIcon
     * @param pixelHeight height of the returned ImageIcon
     * @return the empty ImageIcon
     */
    public static ImageIcon getPlaceholder(int pixelWidth, int pixelHeight){
        return getImageIcon("Images/Cards/Placeholder.png", pixelWidth, pixelHeight);
    }
}
