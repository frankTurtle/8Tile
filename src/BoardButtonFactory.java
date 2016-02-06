/**
 * Class BoardButtonFactory
 * This class creates all the buttons for the gamefield
 * Created: 02/05/16
 * Updated: 02/05/16
 * Author: Barret J. Nobel
 * Contact: bear.nobel at gmail
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BoardButtonFactory {
    private static ArrayList<JButton> jButtons; //................. list of buttons
    private static JButton[][] jButtonsArray; //................... array of buttons
    private static int[][] labelsArray; //......................... array of labels
    private static TileBoard jBoard; //............................ TileBoard representation

    // Default constructor
    // creates a perfect board
    public BoardButtonFactory(){
        this( new TileBoard() );
    }

    // Constructor with parameters
    // takes a multidimensional array in and populates the buttons
    // also increases font size for buttons
    public BoardButtonFactory( int[][] labels ){
        jButtons = new ArrayList<>(); //................................................................................................. instantiate the ArrayList
        jButtonsArray = new JButton[3][3]; //............................................................................................ instantiate the Array
        jBoard = new TileBoard( labels ); //............................................................................................. instantiate the TileBoard
        labelsArray = new int[3][3];

        String[][] actionCommands = new String[][]{
                {"tl", "tm", "tr"},
                {"ml", "mm", "mr"},
                {"bl", "bm", "br"},
        };

        for( int row = 0; row < labels.length; row++ ){ //............................................................................... loop through each row
            for( int column = 0; column < labels[row].length; column++ ){
                JButton addThisButton = ( labels[row][column] > 0 ) ? new JButton( "" + labels[row][column] ) : new JButton( "X" ); //... create a button with label from array - X if its the space
                addThisButton.setFont(new Font("Arial", Font.PLAIN, 40)); //............................................................. increase font of button
                addThisButton.setEnabled( enableButton(labels, row, column) ); //........................................................ only enables buttons allowed to move to
                if( !addThisButton.isEnabled() && labels[row][column] > 0 ) addThisButton.setFont( new Font( "Arial", Font.ITALIC, 25));
                addThisButton.setActionCommand( actionCommands[row][column] ); //........................................................ set the action command
                jButtons.add( addThisButton ); //........................................................................................ add buttons to ArrayList
                jButtonsArray[row][column] = addThisButton; //........................................................................... add buttons to Array
                labelsArray[row][column] = labels[row][column]; //....................................................................... keeps track of the labels
            }
        }
    }

    // Constructor that take in a TileBoard object
    // it then creates the buttons from that
    public BoardButtonFactory( TileBoard board ){
        this( board.getBoard() );
    }

    // Method to return the ArrayList full of buttons
    public ArrayList<JButton> getJButtonsAsAList(){
        return jButtons;
    }

    // Method to retun as multidimensional array
    public JButton[][] getJButtonsAsArray(){
       return jButtonsArray;
    }

    // Method to return the labels array
    public int[][] getLabels(){
        return labelsArray;
    }

    // Method to return the TileBoard object
    public TileBoard getjBoard(){
        return jBoard;
    }

    // Method to only enable buttons allowed to move to
    private boolean enableButton(int[][] labels, int row, int column ){
        TileBoard board = new TileBoard( labels ); //................................ new board
        Point currentLocation = new Point( row, column ); //......................... current location

        if( board.getLocationOfEmptySpace().x == currentLocation.x &&
            board.getLocationOfEmptySpace().y == currentLocation.y )return false; //. if its the current location disable it

        for( int i = 0; i < board.getAvailableMoves().length; i++) { //.............. loop through the available moves
            TileBoard tempBoard = new TileBoard( labels ); //........................ create a temp board
            tempBoard.move( board.getAvailableMoves()[i] ); //....................... move in temp board available moves
            Point tempBlank = tempBoard.getLocationOfEmptySpace(); //................ the new blank of the temp board

            if( currentLocation.equals(tempBlank) ) //............................... if they're the same enable the button
                return true;
        }
        return false; //............................................................. if none are true don't enable the button
    }
}
