/**
 * Class EightTileGUI
 * This class to give it a GUI and user interaction
 * Created: 02/05/16
 * Updated: 02/05/16
 * Author: Barret J. Nobel
 * Contact: bear.nobel at gmail
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class EightTileGUI {

    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel gameButtons;
    private JPanel rightSide;
    private JPanel searchTypes;
    private JTextArea textField;
    private JLabel hintLabel;
    private JButton generateButton;

    public EightTileGUI(){
        prepareGUI();
    }

    public static void main(String[] args){
        EightTileGUI swingControlDemo = new EightTileGUI();
    }

    private void prepareGUI(){
        mainFrame = new JFrame("EightTile Game");
        mainFrame.setSize(500,500);
        mainFrame.setLayout(new GridLayout(0, 2));

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
        gameButtons = new JPanel();
        gameButtons.setLayout(new GridLayout(3,3));

        for( JButton button : new BoardButtonFactory().getJButtonsAsAList() ){
            button.addActionListener( new ButtonClickListener() );
            gameButtons.add( button );
        }

        rightSide = new JPanel();
        rightSide.setLayout( new GridLayout(4,1) );

        searchTypes = new JPanel();
        searchTypes.setLayout( new GridLayout(0,2) );

        searchTypes.add( new JButton("Breadth") );
        searchTypes.add( new JButton("Depth") );

        textField = new JTextArea();
        textField.setEditable(false);

        hintLabel = new JLabel( "Hint", SwingConstants.CENTER );

        generateButton = new JButton("GENERATE");

        rightSide.add(searchTypes);
        rightSide.add(textField);
        rightSide.add(hintLabel);
        rightSide.add(generateButton);

        mainFrame.add(gameButtons);
        mainFrame.add(rightSide);

        mainFrame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            switch( e.getActionCommand() ){
                case "tl":
                    textField.setText( "TL button clicked");
                    break;

                case "tm":
                    textField.setText( "TM button clicked");
                    System.out.print("TM button clicked");
                    break;

                default:
                    System.out.print(e.getActionCommand());
                    break;
            }
        }
    }
}
