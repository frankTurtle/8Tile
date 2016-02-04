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
    private int[][] board; //..................... multidimensional array to the location of all spaces on the board

    // Default constructor
    public TileBoard() {
        this.board = new int[][] {
                {1,2,3},
                {8,EMPTY_SPACE,4},
                {7,6,5}
        }; //........................ instantiates the board and populates with default values in the correct order
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

}
