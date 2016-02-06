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
    private static ArrayList<JButton> jButtons; //....................................................... list of buttons

    // Default constructor
    // creates a perfect board
    public BoardButtonFactory(){
        this( new int[][] { {1,2,3},
                            {8,-1,4},
                            {7,6,5} }  );
    }

    // Constructor with parameters
    // takes a multidimensional array in and populates the buttons
    // also increases font size for buttons
    public BoardButtonFactory( int[][] labels ){
        jButtons = new ArrayList<>(); //..................................................................... instantiate the ArrayList
        for( int row = 0; row < labels.length; row++ ){ //................................................... loop through each row
            for( int label : labels[row] ){ //............................................................... loop through each column
                JButton addThisButton = ( label > 0 ) ? new JButton( "" + label ) : new JButton( "X" ); //... create a button with label from array - X if its the space
                addThisButton.setFont(new Font("Arial", Font.PLAIN, 40)); //................................. increase font of button
                jButtons.add( addThisButton ); //............................................................ add buttons to ArrayList
            }
        }
    }

    // Method to return the ArrayList full of buttons
    public ArrayList<JButton> getJButtons(){
        return jButtons;
    }
}
