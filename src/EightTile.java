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
        final TileBoard GOAL_STATE = new TileBoard(); //................................ the ideal configuration board
        TileBoard initialBoard = new TileBoard( new int[][] { //........................ initial board
                {1,2,3},
                {-1,4,5},
                {7,6,8}
        } );

        Queue<TileBoard> open = new LinkedList<>(); //.................................. the queue of open boards
        ArrayList<String> mostEfficientRoute = new ArrayList<>(); //............l....... list to hold most efficient route
        ArrayList<TileBoard> closed = new ArrayList<>(); //............................. list of already closed boards
        boolean isSolved = false; //.................................................... variable for algorithm loop
        int level = 0; //............................................................... variable used to keep track of current level

//        System.out.println( "Hscore: " + initialBoard.hScore() );

        open.add(initialBoard); //...................................................... add initial board to queue

        while( !isSolved ){ //.......................................................... while we've not solved the route
            if( open.isEmpty() ){ //.................................................... if the queue is empty we're done, no solution
                System.out.println( "No solution" );
                isSolved = true;
            }

                System.out.println("\n\n********\n" + open );

            TileBoard n = open.peek(); //.............................................. get the board at top of queue to analyze
                System.out.printf( "%nLevel %d %nCurrent%s", level,n );
            closed.add( open.remove() ); //............................................ add top of queue to closed

            if( n.equals(GOAL_STATE)){ //.............................................. if current board is the goal we're done!
                System.out.println( "Great Success, This is the goal state!" );
                isSolved = true;
            }
            else{
                addAllNodes( open, closed, n ); //.................................... if its not the goal, we have to add all children nodes to queue
                level++; //........................................................... increment which level we're currently on
            }
        }
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

    // Method to add all the child nodes to the queue
    // takes the queue, the closed list, and the current board
    private static void addAllNodes( Queue<TileBoard> open, ArrayList<TileBoard> closed, TileBoard n  ){
        for( String direction: n.getAvailableMoves() ){ //.................................................. loops through all available moves
            TileBoard copyBoard = new TileBoard( n.getBoard() ); //......................................... makes a copy of the current board
            copyBoard.move(direction); //................................................................... makes one of the available moves

            if( !boardInClosed(copyBoard, closed) ){ //..................................................... if the board is not in closed
                open.add( copyBoard ); //................................................................... add it to the queue
            }
        }
    }

    // Method to check if a board is already closed
    // takes in the current board and the closed list
    private static boolean boardInClosed( TileBoard board, ArrayList<TileBoard> closed ) {
        for( TileBoard closedBoard: closed ){ //............................................ loops through each board in the closed list
            if( board.equals(closedBoard )) return true; //................................. if the current board is equal to the one in the closed list it's in closed!
        }

        return false; //.................................................................... if it makes it through all closed and hasn't returned true, its NOT in the closed list
    }
}
