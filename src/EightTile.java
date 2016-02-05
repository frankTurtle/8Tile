/**
 * Class EightTile
 * This class is used to test the TileBoard class
 * Created: 02/03/16
 * Updated: 02/05/16
 * Author: Barret J. Nobel
 * Contact: bear.nobel at gmail
 */
public class EightTile {

    public static void main( String[] args ){
        TileBoard board = new TileBoard();
        TileBoard board2 = new TileBoard( new int[][] {
                {1,2,3},
                {8,-1,4},
                {7,6,5}
        } );

        System.out.print( board.equals(board2));
        System.out.print( board );

//        System.out.println( board2 );
//        System.out.println( board2.emptySpaceLocation);
//        board2.move("e");
//        System.out.println( board2 );
//        System.out.println( board2.emptySpaceLocation);
//        System.out.println( board2.getLocationOfEmptySpace() );
    }
}
