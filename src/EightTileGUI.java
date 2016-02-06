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
//        swingControlDemo.showEventDemo();
    }

    private void prepareGUI(){
        mainFrame = new JFrame("EightTile Game");
        mainFrame.setSize(500,500);
        mainFrame.setLayout(new GridLayout(0, 2));

        headerLabel = new JLabel("",JLabel.CENTER );
        statusLabel = new JLabel("",JLabel.CENTER);

        statusLabel.setSize(350,100);
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
        gameButtons = new JPanel();
        gameButtons.setLayout(new GridLayout(3,3));

        for( JButton button : new BoardButtonFactory().getJButtons() ){
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

    private void showEventDemo(){
        headerLabel.setText("Control in action: Button");

        JButton okButton = new JButton("OK");
        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");

        okButton.setActionCommand("OK");
        submitButton.setActionCommand("Submit");
        cancelButton.setActionCommand("Cancel");

        okButton.addActionListener(new ButtonClickListener());
        submitButton.addActionListener(new ButtonClickListener());
        cancelButton.addActionListener(new ButtonClickListener());


    }

    private class ButtonClickListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if( command.equals( "OK" ))  {
                statusLabel.setText("Ok Button clicked.");
            }
            else if( command.equals( "Submit" ) )  {
                statusLabel.setText("Submit Button clicked.");
            }
            else  {
                statusLabel.setText("Cancel Button clicked.");
            }
        }
    }
}
