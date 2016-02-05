import java.awt.*;

/**
 * Class TileBoard
 * This class represents a 3x3 grid full of numbers and one negative number
 * The X is indicates a blank spot on the board where the numbers can move freely
 * Created: 02/03/16
 * Updated: 02/05/16
 * Author: Barret J. Nobel
 * Contact: bear.nobel at gmail
 */
public class TileBoard {

    private final int EMPTY_SPACE = -1; //........ variable for the empty space
    private int[][] board; //..................... multidimensional array to the emptySpaceLocation of all spaces on the board
    private Point emptySpaceLocation; //........... emptySpaceLocation of the empty space

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
        this.board = new int[3][3]; //.............................................................................................. instantiates the board to be 3x3

        for( int i = 0; i < board.length; i++ ) //.................................................................................. loops through each row
            System.arraycopy(arrayIn[i], 0, board[i], 0, board[i].length); //....................................................... copies values from array passed in

        this.emptySpaceLocation = new Point( this.getLocationOfEmptySpace(arrayIn).x, this.getLocationOfEmptySpace(arrayIn).y ); //. sets the empty space location
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
    // checks to see if move is valid and then updates the board and empty location
    public void move( String direction ){
        if( isValidMove(direction) ){ //.............................................. if its a valid move
            int x = (int)this.emptySpaceLocation.getX(); //........................... get current x for empty space
            int y = (int)this.emptySpaceLocation.getY(); //........................... get current y for empty space

            switch( direction.toLowerCase() ){ //..................................... switch to determine the direction chosen
                case "n": //.......................................................... move north
                    this.updateBoard( (x-1) , y );
                    break;

                case "e": //.......................................................... move east
                    this.updateBoard( x , (y+1) );
                    break;

                case "s": //.......................................................... move south
                    this.updateBoard( (x+1) , y );
                    break;

                case "w": //.......................................................... move west
                    this.updateBoard( x , (y-1) );
                    break;
            }
        }
        else System.out.println( "\n***Invalid choice, please try again!***\n"); //... if its not valid print an error message
    }

    // Method to update the board values
    // takes the X, Y coordinates to be updated
    // swaps the empty space and with the value
    // in the coordinates
    public void updateBoard( int xUpdate, int yUpdate ){
        this.board[ (int)this.emptySpaceLocation.getX() ][ (int)this.emptySpaceLocation.getY() ] = this.board[ xUpdate ][ yUpdate ]; //.. assigns the empty space the value in the direction were moving
        this.setEmptySpaceLocation( xUpdate, yUpdate ); //............................................................................... update empty space coordinates
        this.board[ xUpdate ][ yUpdate ] = -1; //........................................................................................ set the new empty space in the board array itself
    }

    // Method to determine if a move is valid
    // based on the current location of the empty space coordinates
    // default case is false
    private boolean isValidMove( String direction ){
        switch (direction.toLowerCase()){
            case "n":
                return ( this.emptySpaceLocation.getX() > 0 );

            case "e":
                return ( this.emptySpaceLocation.getX() < 2 );

            case "s":
                return ( this.emptySpaceLocation.getY() < 2 );

            case "w":
                return ( this.emptySpaceLocation.getY() > 0 );

            default:
                return false;
        }
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

        for( int x = 0; x < arrayIn.length; x++ ){ //............. loop through the rows
            for( int y = 0; y < arrayIn[x].length; y++ ){ //...... loop through each column
                if( arrayIn[x][y] < 0 ) //........................ if its the empty one
                    returnPoint.setLocation(x,y); //.............. assign the coordinates
            }
        }

        return returnPoint;
    }

    // Method to set the empty space location from a Point object
    private void setEmptySpaceLocation( Point emptySpaceLocation ){
        this.emptySpaceLocation.setLocation( emptySpaceLocation );
    }

    // Method to set the empty space based on the coordinates
    private void setEmptySpaceLocation( int x, int y ){
        this.emptySpaceLocation.setLocation( x, y );
    }

}
