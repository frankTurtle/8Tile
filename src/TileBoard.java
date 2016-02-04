import java.awt.*;

/**
 * Class TileBoard
 * This class represents a 3x3 grid full of numbers and one negative number
 * The X is indicates a blank spot on the board where the numbers can move freely
 * Created: 02/03/16
 * Updated: 02/03/16
 * Author: Barret J. Nobel
 * Contact: bear.nobel at gmail
 */
public class TileBoard {

    private final int EMPTY_SPACE = -1; //........ variable for the empty space
    private int[][] board; //..................... multidimensional array to the emptySpaceLocation of all spaces on the board
    private Point emptySpaceLocation; //.......... emptySpaceLocation of the empty space

    // Default constructor
    public TileBoard() {
        this.board = new int[][] {
                {1,2,3},
                {8,EMPTY_SPACE,4},
                {7,6,5}
        }; //....................................................... instantiates the board and populates with default values in the correct order

        this.emptySpaceLocation = new Point( 1,1 );
    }

    // Constructor with parameters
    // takes in a multidimensional array
    // instantiates local board with values passed in
    // Formatted as:
    // Current Board
    // Row 1: 1 2 3
    // Row 2: 8 X 4
    // Row 3: 7 6 5
    public TileBoard( int[][] arrayIn ){
        this.board = new int[3][3]; //........................................ instantiates the board to be 3x3

        for( int i = 0; i < board.length; i++ ) //............................ loops through each row
            System.arraycopy(arrayIn[i], 0, board[i], 0, board[i].length); //. copies values from array passed in

        this.emptySpaceLocation = new Point( this.getLocationOfEmptySpace(arrayIn).x, this.getLocationOfEmptySpace(arrayIn).y );
    }

    // Overridden toString
    // prints the board as it is currently
    public String toString(){
        String returnString = "Current Board"; //......................... create String to return

        for( int i = 0; i < this.board.length; i++ ){ //.................. loops through each row
            returnString += "\nRow " + (i+1) + ": "; //................... prints a header for each row with the row number
            for( int number : this.board[i] ){ //......................... loops through each column
                returnString += ( number > 0 ) ? number + " " : "X "; //.. concatenate the number, and put an X if its the empty space
            }
        }

        return returnString;
    }

    // Method to move swap the number with an empty space in the corresponding direction
    public void move( String direction ){
        //TODO: implement
    }

    // Method to determine if a move is valid
    private boolean isValidMove( String direction ){
        boolean isValid = false;

        switch (direction.toLowerCase()){
            case "north":

                break;

            case "east":
                break;

            case "south":
                break;

            case "west":
                break;

            default:
                System.err.println( "Invalid choice, please try again");
                break;
        }

        return true;
    }

    // Method to return the emptySpaceLocation of the empty space
    // calls the overridden method and passes in the current board
    private Point getLocationOfEmptySpace(){
        return this.getLocationOfEmptySpace( this.board );
    }

    // Method to return the location of the empty space
    // coordinate system is cartesian plane with x as the column and y as the row
    // once the value is less than zero thats the empty space
    private Point getLocationOfEmptySpace( int[][] arrayIn ){
        Point returnPoint = new Point( 0,0 ); //.................. point object to return

        for( int y = 0; y < arrayIn.length; y++ ){ //............. loop through the rows
            for( int x = 0; x < arrayIn[y].length; x++ ){ //...... loop through each column
                if( arrayIn[y][x] < 0 ) //........................ if its the empty one
                    returnPoint.setLocation(x,y); //.............. assign the coordinates
            }
        }

        return returnPoint;
    }

    // Method to set the empty space
    private void setEmptySpaceLocation( Point emptySpaceLocation ){
        this.emptySpaceLocation.setLocation( emptySpaceLocation );
    }

}
