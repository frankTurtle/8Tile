/**
 * Class EightTileGUI
 * This class to give it a GUI and user interaction
 * Created: 02/05/16
 * Updated: 02/06/16
 * Author: Barret J. Nobel
 * Contact: bear.nobel at gmail
 */

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class EightTileGUI {

    private JPanel gameButtons;
    private JTextArea textField;
    private BoardButtonFactory buttonFactory;
    private ArrayList<JButton> buttonBoardList;
    private JFrame mainFrame;
    private JLabel hintLabel;

    public EightTileGUI(){
        prepareGUI();
    }

    public static void main(String[] args){
        EightTileGUI swingControlDemo = new EightTileGUI();
    }

    // Method to setup the GUI
    // Frame is 500x500
    private void prepareGUI(){
        mainFrame = new JFrame("EightTile Game"); //.................... creates a frame to hold everything
        mainFrame.setSize(500,500); //......................................... sets frame size
        mainFrame.setLayout(new GridLayout(0, 2)); //.......................... there's two sections, game buttons and everything else

        mainFrame.addWindowListener(new WindowAdapter() { //................... listener to close game
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });

        // ******** LEFT SIDE ************ //
        gameButtons = new JPanel(); //.................................. panel to hold all buttons in a grid
        gameButtons.setLayout(new GridLayout(3,3)); //.................. 3x3 layout
        buttonFactory = new BoardButtonFactory(); //.................... new button factory object
        buttonBoardList = buttonFactory.getJButtonsAsAList(); //........ instantiate the list of button object for reference
        addButtonsTo( gameButtons, buttonBoardList ); //................ adds all buttons to gameButtons panel and adds listeners

        // ********** RIGHT SIDE *********** //
        JPanel rightSide = new JPanel(); //............................. new panel to hold all items on right side
        rightSide.setLayout( new GridLayout(4,1) ); //.................. 4x1 grid

        // ********** SEARCH TYPE ************ //
        JPanel searchTypes = new JPanel(); //........................... panel to add to right side with search type buttons
        searchTypes.setLayout( new GridLayout(0,2) ); //................ layout for button grid 0x2
        searchTypes.add( new JButton("Breadth") ); //................... add buttons to panel
        searchTypes.add( new JButton("Depth") );

        // ************* TEXT AREA ************* //
        textField = new JTextArea(); //.................................. text area to display results
        textField.setEditable(false); //................................. don't allow user to edit the area

        // ************* HINT LABEL ****************** //
        hintLabel = new JLabel("Hint", SwingConstants.CENTER); //........ creates hint label
        hintLabel.addMouseListener( new HintButtonHover() ); //.......... handles hovering ( displays hint )

        // ************* GENERATE NEW BOARD BUTTON ************* //
        JButton generateButton = new JButton("RANDOM");
        generateButton.setActionCommand( "random" );
        generateButton.addActionListener( new ButtonClickListener() );

        rightSide.add(searchTypes); //.................................... add all components to the panel on the right side
        rightSide.add(textField);
        rightSide.add(hintLabel);
        rightSide.add(generateButton);

        mainFrame.add(gameButtons); //.................................... add the right and left panels to the mainFrame
        mainFrame.add(rightSide);

        mainFrame.setVisible(true); //.................................... display it!
    }

    // Method to deal with the gamepad buttons being clicked
    // updates the game board and available options
    private class ButtonClickListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            switch( e.getActionCommand() ){
                case "tl":
                    textField.setText( "TL button clicked");
                    updateButtons(0,0);
                    break;

                case "tm":
                    textField.setText( "TM button clicked");
                    updateButtons(0,1);
                    break;

                case "tr":
                    textField.setText( "Tr button clicked");
                    updateButtons(0,2);
                    break;

                case "ml":
                    textField.setText( "ml button clicked");
                    updateButtons(1,0);
                    break;

                case "mm":
                    textField.setText( "mM button clicked");
                    updateButtons(1,1);
                    break;

                case "mr":
                    textField.setText( "mr button clicked");
                    updateButtons(1,2);
                    break;

                case "bl":
                    textField.setText( "bl button clicked");
                    updateButtons(2,0);
                    break;

                case "bm":
                    textField.setText( "bm button clicked");
                    updateButtons(2,1);
                    break;

                case "br":
                    textField.setText( "br button clicked");
                    updateButtons(2,2);
                    break;

                case "random":
                    randomBoard();
                    break;
            }
        }
    }

    // Method called to update the buttons for the gameboard
    private void updateButtons( int x, int y ){
        for( JButton btn : buttonBoardList )gameButtons.remove(btn); //.......... removes all current buttons
        TileBoard tempBoard = new TileBoard( buttonFactory.getLabels() ); //..... creates a temporary board
        tempBoard.move( directionFromEmpty(new Point(x,y)) ); //................. moves the temporary board the direction the user pressed
        buttonFactory = new BoardButtonFactory( tempBoard ); //.................. update the button factory to newest moved coordinates
        buttonBoardList = buttonFactory.getJButtonsAsAList(); //................. update list of buttons

        addButtonsTo( gameButtons, buttonBoardList ); //......................... add all new buttons back onto the board

        gameButtons.revalidate(); //............................................. update and repaint all buttons
        gameButtons.repaint();
    }

    // Method to generate a random board
    private void randomBoard(){
        final Integer[][] NUMBERS = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, -1}
        };
        for( JButton btn : buttonBoardList )gameButtons.remove(btn); //............ removes all current buttons
        TileBoard tempBoard = new TileBoard( TileBoard.toPrimitive(NUMBERS) ); //.. creates a temporary board
        buttonFactory = new BoardButtonFactory( tempBoard ); //.................... update the button factory to newest moved coordinates
        buttonBoardList = buttonFactory.getJButtonsAsAList(); //................... update list of buttons

        addButtonsTo( gameButtons, buttonBoardList ); //........................... add all new buttons back onto the board

        gameButtons.revalidate(); //............................................... update and repaint all buttons
        gameButtons.repaint();
    }

    // Method to add all buttons from a list to the panel
    // also adds the listeners for each button
    private void addButtonsTo( JPanel panel, ArrayList<JButton> buttonList ){
        for( JButton addBtn: buttonList ){ //................................. loop through each button in list
            addBtn.addActionListener( new ButtonClickListener() ); //......... add listener
            panel.add( addBtn ); //........................................... add to panel to be displayed
        }
    }

    // Method to return a string with the direction the user clicked on
    private String directionFromEmpty( Point location ){
        Point currentEmpty = new Point( buttonFactory.getjBoard().getLocationOfEmptySpace() ); //............ the location of the current empty point

        for( int row = 0; row < buttonFactory.getJButtonsAsArray().length; row++ ){ //....................... loop through each button board
            for( int column = 0; column < buttonFactory.getJButtonsAsArray()[row].length; column++ ){
                JButton currentButton = buttonFactory.getJButtonsAsArray()[row][column]; //.................. temporary button of currently indexed

                if( currentButton.isEnabled() ){ //.......................................................... if its enabled, determine direction in relation to empty point
                    if( currentEmpty.x > location.x ) {
                        return "n";
                    }
                    else if( currentEmpty.x < location.x ){
                        return "s";
                    }
                    else if( currentEmpty.y > location.y ){
                        return "w";
                    }

                    else if( currentEmpty.y < location.y ){
                        return "e";
                    }
                }
            }
        }

        return null;
    }

    // Method to handle mouse hover over the hint label
    private class HintButtonHover extends MouseAdapter{

        @Override
        public void mouseEntered(MouseEvent me) { //....... hover over the label
            super.mouseEntered(me);
            textField.setText("Mouse Entered");
            hintLabel.setBackground( Color.lightGray); //.. change BG color
            hintLabel.setOpaque(true);
        }

        @Override
        public void mouseExited(MouseEvent e) { //........ leave hovering
            super.mouseExited(e);
            textField.setText("Exit");
            hintLabel.setBackground( Color.white ); //.... reset color
            hintLabel.setOpaque(false);
        }
    }
}
