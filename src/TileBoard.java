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

    private final int EMPTY_SPACE = -1;
    private int[][] board;

    public TileBoard() {
        this.board = new int[][] {
                {1,2,3},
                {8,EMPTY_SPACE,4},
                {7,6,5}
        };
    }

    public TileBoard( int[][] arrayIn ){
        this.board = new int[3][3];

        for( int i = 0; i < board.length; i++ )
            for( int j = 0; j < board[i].length; j++ )
                board[i][j] = arrayIn[i][j];
    }

    public String toString(){
        String returnString = "Current Board";
        for( int i = 0; i < this.board.length; i++ ){
            returnString += "\nRow " + (i+1) + ": ";
            for( int number : this.board[i] ){
                returnString += ( number > 0 ) ? number + " " : "X ";
            }
        }

        return returnString;
    }

}
