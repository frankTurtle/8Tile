import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

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
        final TileBoard GOAL_STATE = new TileBoard();
        TileBoard initialBoard = new TileBoard( new int[][] {
                {1,2,3},
                {-1,4,5},
                {7,6,8}
        } );

//        for( String move : initialBoard.getAvailableMoves() )System.out.println( move );

        Queue<TileBoard> open = new LinkedList<>();
        ArrayList<TileBoard> closed = new ArrayList<>();
        open.add(initialBoard);
        boolean isSolved = false;
        int count = 0;

        System.out.println( "Hscore: " + initialBoard.hScore() );

//        while( !isSolved ){
//            if( open.isEmpty() ){
//                System.out.println( "No solution" );
//                isSolved = true;
//            }
//
//                System.out.println("\n\n********\n" + open );
//
//            TileBoard n = open.peek();
//                System.out.printf( "%nCount %d %nCurrent%s", count,n );
//            closed.add( open.remove() );
//            count++;
//
//            if( n.equals(GOAL_STATE)){
//                System.out.println( "Great Success, This is the goal state!" );
//                isSolved = true;
//            }
//            else{
//                addAllNodes( open, closed, n );
//            }
//        }
////        open.add( initialBoard);
//
////        for( TileBoard print : open ) System.out.println( print );
//
////        System.out.println( "\n***" + open.remove() );
//
//
////        System.out.print( goalState.equals(initialBoard));
////        System.out.print( goalState );
//
////        System.out.println( initialBoard );
////        System.out.println( initialBoard.emptySpaceLocation);
////        initialBoard.move("e");
////        System.out.println( initialBoard );
////        System.out.println( initialBoard.emptySpaceLocation);
////        System.out.println( initialBoard.getLocationOfEmptySpace() );
    }

    private static void addAllNodes( Queue<TileBoard> open, ArrayList<TileBoard> closed, TileBoard n  ){
        for( String direction: n.getAvailableMoves() ){
            TileBoard copyBoard = new TileBoard( n.getBoard() );
            copyBoard.move(direction);

            if( !boardInClosed(copyBoard, closed) ){
                open.add( copyBoard );
            }
        }
    }

    private static boolean boardInClosed( TileBoard board, ArrayList<TileBoard> closed ) {
        for( TileBoard closedBoard: closed ){
            if( board.equals(closedBoard )) return true;
        }

        return false;
    }
}
