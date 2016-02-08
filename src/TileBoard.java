/**
 * Class TileBoard
 * This class represents a 3x3 grid full of numbers and one negative number
 * The X is indicates a blank spot on the board where the numbers can move freely
 * Created: 02/03/16
 * Updated: 02/06/16
 * Author: Barret J. Nobel
 * Contact: bear.nobel at gmail
 */

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class TileBoard {

    private final int EMPTY_SPACE = -1; //........ variable for the empty space
    private int[][] board; //..................... multidimensional array to the emptySpaceLocation of all spaces on the board
    private Point emptySpaceLocation; //........... emptySpaceLocation of the empty space


    // Default constructor
    public TileBoard() {
        this.board = new int[][] {
                {1,2,3},
                {8,-1,4},
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
        String returnString = "Current Board"; //.................................... create String to return

        for( int i = 0; i < this.board.length; i++ ){ //............................. loops through each row
            returnString += "\nRow " + (i+1) + ": "; //.............................. prints a header for each row with the row number
            for( int number : this.board[i] ){ //.................................... loops through each column
                returnString += ( number > 0 ) ? number + " " : "X "; //............. concatenate the number, and put an X if its the empty space
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

            switch( direction ){ //................................................... switch to determine the direction chosen
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
        this.board[ xUpdate ][ yUpdate ] = EMPTY_SPACE; //............................................................................... set the new empty space in the board array itself
    }

    // Method to determine if a move is valid
    // based on the current location of the empty space coordinates
    // default case is false
    public boolean isValidMove( String direction ){
        switch (direction){
            case "n":
                return ( this.emptySpaceLocation.getX() > 0 );

            case "e":
                return ( this.emptySpaceLocation.getY() < 2 );

            case "s":
                return ( this.emptySpaceLocation.getX() < 2 );

            case "w":
                return ( this.emptySpaceLocation.getY() > 0 );

            default:
                return false;
        }
    }

    // Method to return the emptySpaceLocation of the empty space
    // calls the overridden method and passes in the current board
    public Point getLocationOfEmptySpace(){
        return this.getLocationOfEmptySpace( this.board );
    }

    // Method to return the location of the empty space
    // coordinate system is cartesian plane with x as the column and y as the row
    // once the value is less than zero thats the empty space
    public Point getLocationOfEmptySpace( int[][] arrayIn ){
        Point returnPoint = new Point( 0,0 ); //.................. point object to return

        for( int x = 0; x < arrayIn.length; x++ ){ //............. loop through the rows
            for( int y = 0; y < arrayIn[x].length; y++ ){ //...... loop through each column
                if( arrayIn[x][y] < 0 ) //........................ if its the empty one
                    returnPoint.setLocation(x,y); //.............. assign the coordinates
            }
        }

        return returnPoint;
    }

    // Method to set the empty space based on the coordinates
    public void setEmptySpaceLocation( int x, int y ){
        this.emptySpaceLocation.setLocation( x, y );
    }

    // Method to compare if two objects are equal
    public boolean equals( Object testThisBoard ){
        if( this == testThisBoard )return true; //..................................................... if it is itself then it equals itself ... GO FIGURE!
        if( !(testThisBoard instanceof TileBoard) ) return false; //................................... if its not even an object of this class it's clearly not going to be equal

        TileBoard compareTo = (TileBoard)testThisBoard; //............................................. cast the object to check instance variables
        boolean answer = this.emptySpaceLocation.getX() == compareTo.emptySpaceLocation.getX() && //... empty space x coordinate
                this.emptySpaceLocation.getY() == compareTo.emptySpaceLocation.getY(); //.............. empty space y coordinate

        for( int i = 0; i < this.board.length; i++ ) { //.............................................. check the array's within the board
            if( !(Arrays.equals(this.board[i], compareTo.board[i]) ) ) return false;
        }

        return answer;
    }

    // Method that returns an array of the active moves available
    public String[] getAvailableMoves(){
        String moves = "";
        String[] allMoves = { "n", "e", "s", "w" };
        for( String direction : allMoves ){
            if( this.isValidMove( direction ) ){
                moves += direction + " ";
            }
        }

        return moves.split(" ");

    }

    // Method to return the board
    public int[][] getBoard() {
        int[][] returnBoard = new int[3][3];

        for( int i = 0; i < returnBoard.length; i++ )
            System.arraycopy(this.board[i], 0, returnBoard[i], 0, returnBoard[i].length);

        return returnBoard;
    }

    // Method to give me the H-Score
    // score is calculated by:
    // 1 point per spot out of place
    public int hScore(){
        TileBoard goalBoard = new TileBoard(); //............................................ ideal board configuration to compare to
        int returnScore = 0; //.............................................................. variable to hold the score

        for( int x = 0; x < this.board.length; x++ ){ //..................................... loop through each row
            for( int y = 0; y < this.board[x].length; y++ ){ //.............................. loop through each column
                if( this.board[x][y] != goalBoard.getBoard()[x][y] ){ //..................... if they don't match
                    returnScore += this.determinePoints( this.board[x][y], x, y ); //........ increase score
                }
            }
        }

        return returnScore;
    }

    // Method to determine the points off the current number is
    // takes the number being checked, and coordinates it's at
    // finds the difference in current location and location it's supposed to be
    // default points are 0
    private int determinePoints(int num, int x, int y ){
        switch( num ) {
            case 1:
                return Math.abs((0 - x)) + Math.abs((0 - y));

            case 2:
                return Math.abs((0 - x)) + Math.abs((1 - y));

            case 3:
                return Math.abs((0 - x)) + Math.abs((2 - y));

            case 4:
                return Math.abs((1 - x)) + Math.abs((2 - y));

            case 5:
                return Math.abs((2 - x)) + Math.abs((2 - y));

            case 6:
                return Math.abs((2 - x)) + Math.abs((1 - y));

            case 7:
                return Math.abs((2 - x)) + Math.abs((0 - y));

            case 8:
                return Math.abs((1 - x)) + Math.abs((0 - y));
        }
        return 0;
    }

    // Method to convert Integer to int and shuffle
    public static int[][] toPrimitive(Integer[][] array) {
        int[][] result = new int[array.length][array.length]; //. multidimensional array to hold the converted result
        ArrayList<Integer> temp = new ArrayList<>(); //.......... temp ArrayList to put all values into
        for (int i = 0; i < array.length; i++) { //.............. add all values from array passed in into tmp
            for( Integer num : array[i] ){
                temp.add( num );
            }
        }

        Collections.shuffle( temp ); //......................... shuffle tmp
        int index = temp.size() - 1; //......................... get size of tmp

        for (int i = 0; i < array.length; i++) { //............. add each element of tmp into the array to return
            for( int j = 0; j < array[i].length; j++ ){
                result[i][j] = temp.remove( index );
                index--;
            }
        }
        return result;
    }

}
