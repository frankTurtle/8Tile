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
import java.util.*;
import javax.swing.*;

public class EightTileGUI {

    private JPanel gameButtons;
    private JTextArea textField;
    private BoardButtonFactory buttonFactory;
    private ArrayList<JButton> buttonBoardList;
    private JFrame mainFrame;
    private JLabel hintLabel;
    private JLabel hintRemaining;
    private JButton breadthButton;
    private JButton depthButton;
    private int hintLabelCount = 3;

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
        gameButtons = new JPanel(); //................................... panel to hold all buttons in a grid
        gameButtons.setLayout(new GridLayout(3,3)); //................... 3x3 layout
        buttonFactory = new BoardButtonFactory( ); //.................... new button factory object
        buttonBoardList = buttonFactory.getJButtonsAsAList(); //......... instantiate the list of button object for reference
        addButtonsTo( gameButtons, buttonBoardList ); //................. adds all buttons to gameButtons panel and adds listeners

        // ********** RIGHT SIDE *********** //
        JPanel rightSide = new JPanel(); //............................. new panel to hold all items on right side
        rightSide.setLayout( new GridLayout(4,1) ); //.................. 4x1 grid

        // ********** SEARCH TYPE ************ //
        JPanel searchTypes = new JPanel(); //........................... panel to add to right side with search type buttons
        searchTypes.setLayout( new GridLayout(0,2) ); //................ layout for button grid 0x2

        breadthButton = new JButton( "Breadth" );
        depthButton = new JButton( "Depth" );

        breadthButton.setActionCommand( "breadthOrDepth" );
        depthButton.setActionCommand( "breadthOrDepth" );

        breadthButton.addActionListener( new ButtonClickListener() );
        depthButton.addActionListener( new ButtonClickListener() );

        breadthButton.setEnabled( false );
        depthButton.setEnabled( true );

        searchTypes.add( breadthButton ); //................... add buttons to panel
        searchTypes.add( depthButton );

        // ************* TEXT AREA ************* //
        textField = new JTextArea(); //.................................. text area to display results
        textField.setEditable(false); //................................. don't allow user to edit the area

        // ************* HINT LABEL ****************** //
        JPanel hintPanel = new JPanel();
        hintPanel.setLayout( new GridLayout(0,1) );
        String remaining = String.format("Remaining: %d", hintLabelCount);
        hintLabel = new JLabel( "Hint", SwingConstants.CENTER); //........... creates hint label
        hintRemaining = new JLabel( remaining, SwingConstants.CENTER);
        hintLabel.addMouseListener( new HintButtonHover() ); //.............. handles hovering ( displays hint )
        hintLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        hintRemaining.setFont( new Font("Arial", Font.PLAIN, 15) );
        hintPanel.add(hintLabel);
        hintPanel.add(hintRemaining);


        // ************* GENERATE NEW BOARD BUTTON ************* //
        JPanel generateAndSolveButtons = new JPanel();
        generateAndSolveButtons.setLayout( new GridLayout(0,2) );

        JButton generateButton = new JButton("RANDOM");
        JButton solveButton = new JButton( "SOLVE" );
        generateButton.setActionCommand( "random" );
        solveButton.setActionCommand( "solve" );
        generateButton.addActionListener( new ButtonClickListener() );
        solveButton.addActionListener( new ButtonClickListener() );

        generateAndSolveButtons.add( generateButton );
        generateAndSolveButtons.add( solveButton );

        rightSide.add(searchTypes); //.................................... add all components to the panel on the right side
        rightSide.add(textField);
        rightSide.add(hintPanel);
        rightSide.add(generateAndSolveButtons);

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
                    updateButtons(0,0);
                    textField.setText( numberOfStepsLeft(0) );
                    break;

                case "tm":
                    updateButtons(0,1);
                    textField.setText( numberOfStepsLeft(0) );
                    break;

                case "tr":
                    updateButtons(0,2);
                    textField.setText( numberOfStepsLeft(0) );
                    break;

                case "ml":
                    updateButtons(1,0);
                    textField.setText( numberOfStepsLeft(0) );
                    break;

                case "mm":
                    updateButtons(1,1);
                    textField.setText( numberOfStepsLeft(0) );
                    break;

                case "mr":
                    updateButtons(1,2);
                    textField.setText( numberOfStepsLeft(0) );
                    break;

                case "bl":
                    updateButtons(2,0);
                    textField.setText( numberOfStepsLeft(0) );
                    break;

                case "bm":
                    updateButtons(2,1);
                    textField.setText( numberOfStepsLeft(0) );
                    break;

                case "br":
                    updateButtons(2,2);
                    textField.setText( numberOfStepsLeft(0) );
                    break;

                case "random":
                    randomBoard();
                    break;

                case "solve":
                    solveBoard();
                    break;

                case "breadthOrDepth":
                    updateBreadthAndDepthButtons();
                    break;
            }
        }
    }

    // Method to update Breadth and Depth button
    private void updateBreadthAndDepthButtons(){
        if( breadthButton.isEnabled() ){
            breadthButton.setEnabled( false );
            depthButton.setEnabled( true );
        }
        else{
            breadthButton.setEnabled( true );
            depthButton.setEnabled( false );
            System.out.println( "here" );
        }
    }

    // Method to return a string with current steps left to breadthSolve
    private String numberOfStepsLeft( int position ){
        String answer = EightTile.breadthSolve(buttonFactory.getjBoard());
        return answer.split(System.getProperty("line.separator"))[position];
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
        public void mouseEntered(MouseEvent me) { //................................................................. hover over the label
            super.mouseEntered(me);
            try{
                String steps = numberOfStepsLeft(1); //.............................................................. get the first step to solve
                textField.setText(String.format("Pssst ... try heading %s", steps.substring(3,steps.length()))); //.. set it to the text field
            }
            catch ( ArrayIndexOutOfBoundsException e){} //........................................................... index out of bound error when the user has not moved yet so catch it an do nothing

            hintLabel.setBackground( Color.lightGray); //............................................................ change BG color
            hintLabel.setOpaque(true);

            if( hintLabelCount == 0) textField.setText( "No remaining hints left!" ); //............................. if theres no more hints left
            else hintLabelCount--; //................................................................................ subtract one if there are
            hintRemaining.setText( String.format("Remaining: %d", hintLabelCount)  ); //............................. update the label
        }

        @Override
        public void mouseExited(MouseEvent e) { //................................................................... leave hovering
            super.mouseExited(e);
            hintLabel.setBackground( Color.white ); //............................................................... reset color
            hintLabel.setOpaque(false);
            textField.setText( numberOfStepsLeft(0) ); //............................................................ reset text
        }
    }

    // Method to print hte steps to solve the current board
    // called when the solve button is pressed
    // checks to see which search it should use based on the button being active or not
    private void solveBoard(){
        textField.setText( (breadthButton.isEnabled()) ? EightTile.breadthSolve(buttonFactory.getjBoard()) : EightTile.depthSolve(buttonFactory.getjBoard()) );
    }
}
