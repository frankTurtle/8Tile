import javax.sound.midi.SysexMessage;
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
                {1,3,4},
                {8,2,5},
                {7,6,-1}
        } );

        Queue<TileBoard> open = new LinkedList<>(); //.................................. the queue of open boards
        ArrayList<String> mostEfficientRoute = new ArrayList<>(); //............l....... list to hold most efficient route
        ArrayList<TileBoard> closed = new ArrayList<>(); //............................. list of already closed boards
        boolean isSolved = false; //.................................................... variable for algorithm loop

        open.add(initialBoard); //...................................................... add initial board to queue

        while( !isSolved ){ //.......................................................... while we've not solved the route
            if( open.isEmpty() ){ //.................................................... if the queue is empty we're done, no solution
                System.out.println( "No solution" );
                isSolved = true;
            }

            TileBoard n = open.peek(); //.............................................. get the board at top of queue to analyze
            closed.add( open.remove() ); //............................................ add top of queue to closed

            if( n.equals(GOAL_STATE)){ //.............................................. if current board is the goal we're done!
                isSolved = true;
            }
            else{
                addAllNodes( open, closed, n, mostEfficientRoute ); //................. if its not the goal, we have to add all children nodes to queue
            }
        }

        if( mostEfficientRoute.size() == 0 ) {
            System.out.println("Great Success, This is the goal state!");
        }
        else printStepsToSolve( mostEfficientRoute );
    }

    // Method to add all the child nodes to the queue
    // takes the queue, the closed list, and the current board
    private static void addAllNodes( Queue<TileBoard> open, ArrayList<TileBoard> closed, TileBoard n, ArrayList<String> route ){
        String routeString = "";

        for( String direction: n.getAvailableMoves() ){ //......................................................................... loops through all available moves
            TileBoard copyBoard = new TileBoard( n.getBoard() ); //................................................................ makes a copy of the current board
            copyBoard.move(direction); //.......................................................................................... makes one of the available moves

            if( !boardInClosed(copyBoard, closed) ){ //............................................................................ if the board is not in closed
                if( copyBoard.hScore() < n.hScore() ){
                    routeString = direction; //.................................................................................... keep track of the direction
                    open.add( copyBoard ); //...................................................................................... add it to the queue
                }
            }
        }

        if(!routeString.equals("")) //............................................................................................. as long as the string is not empty add it to route
            route.add( routeString );
    }

    // Method to check if a board is already closed
    // takes in the current board and the closed list
    private static boolean boardInClosed( TileBoard board, ArrayList<TileBoard> closed ) {
        for( TileBoard closedBoard: closed ){ //............................................ loops through each board in the closed list
            if( board.equals(closedBoard )) return true; //................................. if the current board is equal to the one in the closed list it's in closed!
        }

        return false; //.................................................................... if it makes it through all closed and hasn't returned true, its NOT in the closed list
    }

    // Method to print out the steps in order on how to solve the puzzle
    private static void printStepsToSolve( ArrayList<String> route ){
        System.out.printf( "Steps to solve: %d%n", route.size()); //...... a header
        for( int i = 0; i < route.size(); i++ ){ //....................... loop through all routes and print them
            System.out.printf( "%d: %s%n", i+1,fullWord(route.get(i)) );
        }
    }

    // Method to return the full word based on the direction
    private static String fullWord( String direction ){
        switch( direction ){
            case "n":
                return "North";
            case "e":
                return "East";
            case "s":
                return "South";
            case "w":
                return "West";
        }

        return "";
    }
}
