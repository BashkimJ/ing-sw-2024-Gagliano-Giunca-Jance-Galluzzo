package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Exceptions.SchemeCardExc.InvalidSideException;
import it.polimi.ingsw.Model.Cards.Corner;
import it.polimi.ingsw.Model.Cards.InitialCard;
import it.polimi.ingsw.Model.Cards.ResourceCard;
import it.polimi.ingsw.Model.Cards.Side;
import it.polimi.ingsw.Model.Enumerations.Resource;
import it.polimi.ingsw.Model.Player.CardScheme;
import it.polimi.ingsw.Model.Player.CardSchemeView;
import it.polimi.ingsw.View.GUI.Utils.CardPlaceholder;
import it.polimi.ingsw.View.GUI.Utils.UnselectableCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

import static it.polimi.ingsw.Model.Enumerations.Items.Quill;
import static it.polimi.ingsw.Model.Enumerations.Resource.*;

/**
 * Class that handles and shows the card scheme of a certain player.
 */
public class JViewSchemePanel extends JPanel implements JSchemePanel{
    private final int PANEL_WIDTH;
    private final int PANEL_HEIGHT;
    private final int X_CARD_OFFSET;
    private final int Y_CARD_OFFSET;
    private final Point matrixOrigin = new Point(80, 80);
    private Point pixelOrigin;
    private GUI gui;
    private JLayeredPane clientPane;
    private JScrollPane scrollPane;
    private Integer currentLayer = 0;
    private final List<Point> adjacentPositions;

    /**
     * Class constructor. Creates a JScollPane with an empty panel as client, and adds the "center view" button
     * @param gui Reference to the gui used for sending messages to server
     */
    public JViewSchemePanel(GUI gui){
        this.gui = gui;
        this.adjacentPositions = Arrays.asList(
                new Point(2,2),
                new Point(2,-2),
                new Point(-2,2),
                new Point(-2,-2)
        );

        X_CARD_OFFSET = GameFrame.CARD_HEIGHT - GameFrame.X_OVERLAPPING;
        Y_CARD_OFFSET = GameFrame.CARD_WIDTH - GameFrame.Y_OVERLAPPING;
        PANEL_HEIGHT = X_CARD_OFFSET*80;
        PANEL_WIDTH = Y_CARD_OFFSET*80;
        pixelOrigin = new Point(PANEL_HEIGHT/2 - GameFrame.CARD_HEIGHT/2, PANEL_WIDTH/2 - GameFrame.CARD_WIDTH/2);

        setLayout(new GridBagLayout());
        this.clientPane = createEmptyClientPane();

        GridBagConstraints gbc = new GridBagConstraints();
        JButton cornerButton = new JButton("⛶");
        cornerButton.setFont(new Font("Serif", Font.BOLD, 25));
        cornerButton.setFocusable(false);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.insets = new Insets(10, 10, 10, 25);
        cornerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                centerView();
            }
        });
        add(cornerButton, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        scrollPane = new JScrollPane(clientPane, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(900, 550));
        add(scrollPane, gbc);

    }

    /**
     * Updates the JViewScheme by creating a new JLayeredPanel and setting it as JScollPane's client
     * @param cardSchemeView the updated card scheme
     */
    public void update(CardSchemeView cardSchemeView){
        replaceClientPane(cardSchemeView);
    }

    /**
     * Creates an empty JLayeredPanel used as JScollPane's client
     * @return the JLayeredPanel
     */
    private JLayeredPane createEmptyClientPane(){
        JLayeredPane clientPane = new JLayeredPane();
        currentLayer = 0;
        clientPane.setBackground(new Color(210, 166, 121));
        clientPane.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        clientPane.setOpaque(true);
        return clientPane;
    }
    /**
     * Creates a JLayeredPanel that represents the card scheme
     * @param cardSchemeView the card scheme to represent
     */
    private void replaceClientPane(CardSchemeView cardSchemeView){
        JLayeredPane clientPane = createEmptyClientPane();
        boolean isFront;
        Integer id;
        List<Integer> coordinates = new ArrayList<>();
        Map<Point,Integer> cardsIds = cardSchemeView.getCardsIds();
        Map<ArrayList<Integer>, Side> playedCards = cardSchemeView.getPlayedCards();
        Iterator<Point> pointIterator = cardsIds.keySet().iterator();

        id = cardsIds.get(pointIterator.next());
        coordinates.add(0, matrixOrigin.x);
        coordinates.add(1, matrixOrigin.y);
        Side side = playedCards.get(coordinates);

        if(side.getDownLeft().getResource()!=null && side.getUpLeft().getResource()!=null && side.getDownRight().getResource()!=null && side.getUpRight().getResource()!=null)
            isFront = false;
        else
            isFront = true;
        createCard(clientPane, id, isFront, matrixOrigin);
        while (pointIterator.hasNext()) {
            Point p = pointIterator.next();
            id = cardsIds.get(p);
            coordinates = new ArrayList<>();
            coordinates.add(0, p.x);
            coordinates.add(1, p.y);
            side = playedCards.get(coordinates);
            isFront = getSide(side);
            createCard(clientPane, id, isFront, p);
        }
        this.clientPane = clientPane;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                scrollPane.setViewportView(clientPane);
                centerView();
            }
        });


    }
    public JPanel getSchemePanel(){return this;}

    /**
     * Analyzes a Side object
     * @param side the Side to analyze
     * @return true if a card Side is the front one, else false
     */
    private boolean getSide(Side side){
        return !(side.getDownLeft().isVisible() && side.getUpLeft().isVisible() && side.getDownRight().isVisible() && side.getUpRight().isVisible()
                && side.getUpLeft().getResource() == null);
    }

    /**
     * Adds a card to the given JLayeredPane
     * @param clientPane the JLayeredPane to which the card is to be added
     * @param id id of the card to be added
     * @param isFront side of the card to be added
     * @param placeTo coordinates for the card placement
     */
    private void createCard(JLayeredPane clientPane, Integer id, boolean isFront, Point placeTo){
        int xDistance = ((placeTo.x - matrixOrigin.x)/2)*X_CARD_OFFSET;
        int yDistance = ((placeTo.y - matrixOrigin.y)/2)*Y_CARD_OFFSET;
        JLabel card = new UnselectableCard(id, isFront, false);
        card.setBounds(pixelOrigin.y + yDistance, pixelOrigin.x + xDistance, GameFrame.CARD_WIDTH + 10, GameFrame.CARD_HEIGHT + 10);
        clientPane.add(card, currentLayer);
        currentLayer++;
    }

    /**
     * Adds clickable placeholders to the JPanel to allow the user to choose the placement position
     * @param cardsIds Map of the currently placed cards in the JViewSchemePanel
     */
    public void createPlaceholders(Map<Point,Integer> cardsIds){
        HashSet<Point> placeholderPositions = new HashSet<>();
        Point toAdd;
        for(Point p : cardsIds.keySet()){
            for(Point toCheck : adjacentPositions){
                if(!cardsIds.containsKey(toAdd = new Point(p.x + toCheck.x, p.y + toCheck.y)))
                    placeholderPositions.add(toAdd);
            }
        }
        for (Point p: placeholderPositions){
            createCardPlaceholder(p);
        }
    }

    /**
     * Creates a single placeholder
     * @param position where has to be placed
     */
    private void createCardPlaceholder(Point position){
        CardPlaceholder cardPlaceholder = new CardPlaceholder(position);
        int xDistance = ((position.x - matrixOrigin.x)/2)*X_CARD_OFFSET;
        int yDistance = ((position.y - matrixOrigin.y)/2)*Y_CARD_OFFSET;
        cardPlaceholder.setBounds(pixelOrigin.y + yDistance, pixelOrigin.x + xDistance, GameFrame.CARD_WIDTH + 10, GameFrame.CARD_HEIGHT + 10);
        cardPlaceholder.addMouseListener(new PlaceholderListener());
        this.clientPane.add(cardPlaceholder, -1);
    }

    /**
     * Centers the ScrollPanel ViewPort's view
     */
    private void centerView(){
        Rectangle bounds = scrollPane.getViewport().getViewRect();
        int x = (PANEL_WIDTH - bounds.width) / 2;
        int y = (PANEL_HEIGHT - bounds.height) / 2;
        scrollPane.getViewport().setViewPosition(new Point(x, y));
    }

    /**
     * Allows overlapping of the "center view" button and the JLayeredPane
     */
    @Override
    public boolean isOptimizedDrawingEnabled() {
        return false;
    }

    /**
     * MouseListener that handles the clicking and hovering on a placeholder; when clicked it sends a message of card placing via the client manager
     */
    private class PlaceholderListener extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
            Point position = ((CardPlaceholder)e.getSource()).getPosition();
            int[] pos = {position.x, position.y};
            int cardId = JMainBottomPanel.getSelectedCardId();
            boolean frontSide = JMainBottomPanel.getSide();
            if(cardId >=0) {
                gui.clientManager.placeCard(cardId, frontSide ? "-f" : "-r", pos);
                System.out.println(gui.clientManager.getNickName() + " placing card: [id, side, position] " + cardId + ", " + (frontSide ? "-f" : "-r") + ", " + "{" + pos[0] + ", " + pos[1] +"}");

            }
            else
                JOptionPane.showMessageDialog(null, "You must select a card first.",
                    "Error!", JOptionPane.ERROR_MESSAGE);

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            ((CardPlaceholder)e.getSource()).setEnteredBorder();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            ((CardPlaceholder)e.getSource()).setDefaultBorder();

        }
    }
    public static void main(String[] args) throws InterruptedException {

        JFrame frame = new JFrame();
        frame.setContentPane(new JPanel());
        frame.setLayout(new GridBagLayout());

        Corner UL = new Corner(true,true,Animal,null);
        Corner DL = new Corner(true,true,null,null);
        Corner UR = new Corner(true,true,null,null);
        Corner DR = new Corner(true,true,Fungi,null);
        Side front = new Side(UL,DL,UR,DR,null);
        UL = new Corner(true,true,Plant,null);
        DL = new Corner(true,true,Fungi,null);
        UR = new Corner(true,true,Animal,null);
        DR = new Corner(true,true,Insects,null);
        Side retro = new Side(UL,DL,UR,DR,null);
        List<Resource> initRsc = new ArrayList<Resource>();
        initRsc.add(Fungi);
        initRsc.add(Animal);
        InitialCard initial = new InitialCard(front,retro,84,initRsc);

        CardScheme cardScheme = new CardScheme();
        try {
            cardScheme.placeInitialCard(initial,"Retro");
        } catch (InvalidSideException e) {
            throw new RuntimeException(e);
        }
        UL = new Corner(true,true,Insects,null);
        DL = new Corner(true,true,null,Quill);
        UR = new Corner(true,true,Plant, null);
        DR = new Corner(false,false,null,null);
        front = new Side(UL,DL,UR,DR,null);
        UL = new Corner(true,true,null,null);
        DL = new Corner(true,true,null,null);
        UR = new Corner(true,true,null, null);
        DR = new Corner(true,true,null,null);
        retro = new Side(UL,DL,UR,DR,null);
        ResourceCard rsc = new ResourceCard(front,retro,40,Insects,2);
        int[] pos = new int[]{78,82};
        int[] pos2 = new int[]{78,78};
        int[] pos3 = new int[]{76,80};
        try {
            int points = cardScheme.placeCard(rsc,pos,"front");
            points = cardScheme.placeCard(rsc,pos2,"retro");
            points = cardScheme.placeCard(rsc,pos3,"front");
        } catch (Exception e){}



        CardSchemeView cardSchemeView = new CardSchemeView(cardScheme);
        JViewSchemePanel schemePanel = new JViewSchemePanel(null);


        schemePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(e.getX());
                System.out.println(e.getY());
                }
            });
        frame.setLayout(new BorderLayout());
        frame.add(schemePanel, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(1000, 1000));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        Thread.sleep(2000);
        schemePanel.update(cardSchemeView);
    }
}
