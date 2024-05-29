package main.java.it.polimi.ingsw.View.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigFrame extends JFrame{
    final static String CONNECTION_PANEL = "1";
    final static String NICKNAME_PANEL = "2";
    final static String CREATE_PANEL = "3";
    final static String WAITING_PANEL = "4";


    private JTextField ip;
    private JTextField nickname;
    private boolean isRMI = false;
    private GUI gui;
    private JPanel cards;
    private int playersNumber = 2;

    private boolean panelIsSwitchable = true;


    public ConfigFrame(GUI gui){
        this.gui = gui;
        ImageIcon gameIcon = null;
        ImageIcon gameIconHQ = null;


        setLayout (new BorderLayout());

        gameIconHQ = GUI.getImageIcon("Images/General/GameIconHQ.png", 0, 0);
        gameIcon = GUI.getImageIcon("Images/General/GameIcon.png", 0, 0);


        //center label
        Image gameIconMediumQ = gameIconHQ.getImage().getScaledInstance(300, 300,  Image.SCALE_SMOOTH);
        JLabel labelHQ = new JLabel(new ImageIcon(gameIconMediumQ));;
        //labelHQ.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        add(labelHQ, BorderLayout.CENTER);

        //East panel 1: connection type
        JPanel connPanel = new JPanel();
        connPanel.setBorder(BorderFactory.createEmptyBorder(30,0,25,0));
        connPanel.setLayout(new BoxLayout(connPanel, BoxLayout.Y_AXIS));

        //Connection radio buttons
        JLabel conType = new JLabel("Connection type:");
        connPanel.add(conType);

        JRadioButton socketRadio = new JRadioButton("Socket", true);
        socketRadio.setMnemonic(KeyEvent.VK_S);
        socketRadio.setToolTipText("Use socket connection. Shortcut: Alt+S");
        connPanel.add(socketRadio);
        socketRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isRMI = false;
            }
        });

        JRadioButton rmiRadio = new JRadioButton("RMI", false);
        rmiRadio.setMnemonic(KeyEvent.VK_R);
        rmiRadio.setToolTipText("Use RMI connection. Shortcut: Alt+R");
        connPanel.add(rmiRadio);
        rmiRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isRMI = true;
            }
        });

        ButtonGroup connectGrp = new ButtonGroup();
        connectGrp.add(socketRadio);
        connectGrp.add(rmiRadio);


        connPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        //Ip address text field
        JLabel ipLabel = new JLabel("IP address:");
        connPanel.add(ipLabel);
        connPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        ip = new JTextField(25);
        ip.setMaximumSize(ip.getPreferredSize());
        ip.setToolTipText("Insert server address.");
        connPanel.add(ip);
        ip.addActionListener(new CanPlayListener());
        connPanel.add(Box.createVerticalGlue());

        //Connect button
        JButton connBtn = new JButton("Connect");
        connBtn.addActionListener(new CanPlayListener());
        connPanel.add(connBtn);

        connPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
                "EnterAction");
        connPanel.getActionMap().put("EnterAction",
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        connBtn.doClick();
                    }
                });


        //East Panel 2: nickname request
        JPanel nicknamePanel = new JPanel();
        nicknamePanel.setBorder(BorderFactory.createEmptyBorder(30,0,25,0));
        nicknamePanel.setLayout(new BoxLayout(nicknamePanel, BoxLayout.Y_AXIS));
        //nickname field
        JLabel nicknameLabel = new JLabel("Nickname:");
        nicknamePanel.add(nicknameLabel);
        nicknamePanel.add(Box.createRigidArea(new Dimension(0, 3)));
        nickname = new JTextField(25);
        nickname.setMaximumSize(ip.getPreferredSize());
        nickname.setToolTipText("Insert your nickname. Must be unique");
        nickname.addActionListener(new NicknameListener());
        nicknamePanel.add(nickname);
        nicknamePanel.add(Box.createVerticalGlue());

        JButton cjBtn = new JButton("Create/Join game");
        cjBtn.addActionListener(new NicknameListener());
        nicknamePanel.add(cjBtn);

        nicknamePanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
                        "EnterAction");
        nicknamePanel.getActionMap().put("EnterAction",
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cjBtn.doClick();
                    }
                });


        //East panel 3: asks number of players
        JPanel createPanel = new JPanel();
        createPanel.setBorder(BorderFactory.createEmptyBorder(30,0,25,0));
        createPanel.setLayout(new BoxLayout(createPanel, BoxLayout.Y_AXIS));
        //player number text field
        JLabel playerNumLabel = new JLabel("Max number of players:");
        createPanel.add(playerNumLabel);
        JRadioButton twoRadio = new JRadioButton("Two players", true);
        twoRadio.setMnemonic(KeyEvent.VK_2);
        twoRadio.setToolTipText("Shortcut: Alt+2");
        createPanel.add(twoRadio);
        twoRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playersNumber = 2;
            }
        });
        JRadioButton threeRadio = new JRadioButton("Three players", false);
        threeRadio.setMnemonic(KeyEvent.VK_3);
        threeRadio.setToolTipText("Shortcut: Alt+3");
        createPanel.add(threeRadio);
        threeRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playersNumber = 3;
            }
        });
        JRadioButton fourRadio = new JRadioButton("Four players", false);
        fourRadio.setMnemonic(KeyEvent.VK_4);
        fourRadio.setToolTipText("Shortcut: Alt+4");
        createPanel.add(fourRadio);
        fourRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playersNumber = 4;
            }
        });
        ButtonGroup playerNumGrp = new ButtonGroup();
        playerNumGrp.add(twoRadio);
        playerNumGrp.add(threeRadio);
        playerNumGrp.add(fourRadio);
        createPanel.add(Box.createVerticalGlue());

        JButton createGame = new JButton("Create game");
        createGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.clientManager.updateNumplayers(playersNumber);
                setSwitchable(true);
                changePanel(WAITING_PANEL);
            }
        });
        createPanel.add(createGame);
        createPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
                        "EnterAction");
        createPanel.getActionMap().put("EnterAction",
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        createGame.doClick();
                    }
                });


        //East panel 4: waits for others players to connect
        JPanel waitingPanel = new JPanel();
        waitingPanel.setBorder(BorderFactory.createEmptyBorder(30,0,25,0));
        waitingPanel.setLayout(new BoxLayout(waitingPanel, BoxLayout.Y_AXIS));
        //nickname field
        JLabel waitingLabel = new JLabel("<html>Waiting for the other <br> players to connect...</html>");
        waitingPanel.add(waitingLabel);


        //Adding focusOnCurrentCard Listener to each card
        connPanel.addComponentListener(new focusOnCurrentCard());
        nicknamePanel.addComponentListener(new focusOnCurrentCard());
        createPanel.addComponentListener(new focusOnCurrentCard());
        waitingPanel.addComponentListener(new focusOnCurrentCard());


        cards = new JPanel(new CardLayout());
        cards.add(connPanel, CONNECTION_PANEL);
        cards.add(nicknamePanel, NICKNAME_PANEL);
        cards.add(createPanel, CREATE_PANEL);
        cards.add(waitingPanel, WAITING_PANEL);
        cards.setPreferredSize(new Dimension(175, 400));
        add(cards, BorderLayout.EAST);





        setTitle("Codex Naturalis - Config");
        setIconImage(gameIcon.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 375);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);

    }
    private boolean checkAddress(String IP){
        if(IP==null || IP.isEmpty()){
            return false;
        }
        String zeroto255 = "(\\d{1,2}|(0|1)\\" + "d{2}|2[0-4]\\d|25[0-5])";
        String regex  = zeroto255+"\\."+zeroto255+"\\."+zeroto255+"\\."+zeroto255;
        Pattern p = Pattern.compile(regex);
        Matcher m  =p.matcher(IP);
        return m.matches();

    }
    public void changePanel(String string){
        if(panelIsSwitchable) {
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, string);
        }
    }
    public void closeWindow(){
        setVisible(false);
        dispose();
    }
    public void setSwitchable(boolean switchable){
        panelIsSwitchable = switchable;
    }
    private class CanPlayListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String input = ip.getText();
            if(!checkAddress(input)){
                JOptionPane.showMessageDialog(ConfigFrame.this, "Ip address not valid!",
                        "Error!", JOptionPane.ERROR_MESSAGE);
                ip.setText("");
            }
            else
                gui.clientManager.onUpdateServerInfo(input,6000,isRMI);
        }
    }
    private class NicknameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            gui.clientManager.updateNickName(nickname.getText());
        }
    }
    private class focusOnCurrentCard extends ComponentAdapter {

        @Override
        public void componentShown(ComponentEvent cEvt) {
            Component src = (Component) cEvt.getSource();
            src.requestFocusInWindow();
        }

    }
}
