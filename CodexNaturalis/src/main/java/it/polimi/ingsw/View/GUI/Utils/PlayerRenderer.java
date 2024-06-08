package main.java.it.polimi.ingsw.View.GUI.Utils;

import main.java.it.polimi.ingsw.View.GUI.GUI;

import javax.swing.*;
import java.awt.*;

public class PlayerRenderer extends JPanel implements ListCellRenderer<PlayerItem> {
    private JLabel nicknameLabel;
    private JLabel fungiLabel;
    private JLabel plantLabel;
    private JLabel animalLabel;
    private JLabel insectLabel;
    private JLabel inkwellLabel;
    private JLabel quillLabel;
    private JLabel manuscriptLabel;
    private JLabel pointsLabel;
    public PlayerRenderer() {
        setOpaque(true);
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        nicknameLabel = new JLabel(" ");
        nicknameLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        fungiLabel  = getLabel("Images/General/FungiIcon.png", "0");
        plantLabel  = getLabel("Images/General/PlantIcon.png", "0");
        animalLabel  = getLabel("Images/General/AnimalIcon.png", "0");
        insectLabel  = getLabel("Images/General/InsectIcon.png", "0");
        inkwellLabel  = getLabel("Images/General/InkwellIcon.png", "0");
        quillLabel  = getLabel("Images/General/QuillIcon.png", "0");
        manuscriptLabel  = getLabel("Images/General/ManuscriptIcon.png", "0");
        pointsLabel  = getLabel("Images/General/blackIcon.png", "0");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        add(nicknameLabel, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        add(fungiLabel, gbc);
        gbc.gridx = 1;
        add(plantLabel, gbc);
        gbc.gridx = 2;
        add(animalLabel, gbc);
        gbc.gridx = 3;
        add(insectLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(inkwellLabel, gbc);
        gbc.gridx = 1;
        add(quillLabel, gbc);
        gbc.gridx = 2;
        add(manuscriptLabel, gbc);
        gbc.gridx = 3;
        add(pointsLabel, gbc);
    }
    @Override
    public JPanel getListCellRendererComponent(JList<? extends PlayerItem> list, PlayerItem value, int index, boolean isSelected, boolean cellHasFocus) {

        fungiLabel.setText(String.valueOf(value.getNumFungi()));
        plantLabel.setText(String.valueOf(value.getNumPlants()));
        animalLabel.setText(String.valueOf(value.getNumAnimal()));
        insectLabel.setText(String.valueOf(value.getNumInsects()));
        inkwellLabel.setText(String.valueOf(value.getNumInkwell()));
        quillLabel.setText(String.valueOf(value.getNumQuill()));
        manuscriptLabel.setText(String.valueOf(value.getNumManuscript()));
        if(value.getPlayerColour() != null)
            pointsLabel.setIcon(GUI.getImageIcon("Images/General/" + value.getPlayerColour() + "Icon.png", 30, 30));
        pointsLabel.setText(String.valueOf(value.getPoints()));

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }
        else{
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        if(value.isMainPlayer())
            nicknameLabel.setText("<HTML><U>" + value.getNickname() + "</U></HTML>");
        else{
            nicknameLabel.setText(value.getNickname());
        }
        if(value.isOnline()) {
            if (value.getHasTurn())
                nicknameLabel.setForeground(Color.RED);
            else
                nicknameLabel.setForeground(Color.BLACK);
        }else
            nicknameLabel.setForeground(Color.GRAY);

        return this;
    }
    private JLabel getLabel(String path, String title){
        JLabel iconAndText;
        ImageIcon icon;
        icon = GUI.getImageIcon(path, 30, 30);
        iconAndText = new JLabel(title);
        iconAndText.setHorizontalAlignment(SwingConstants.LEFT);
        iconAndText.setVerticalAlignment(SwingConstants.CENTER);
        iconAndText.setIcon(icon);
        iconAndText.setPreferredSize(new Dimension(50, iconAndText.getPreferredSize().height));

        return iconAndText;
    }
}