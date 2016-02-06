/**
 * Class EightTile
 * This class is used to test the TileBoard class
 * Created: 02/03/16
 * Updated: 02/06/16
 * Author: Barret J. Nobel
 * Contact: bear.nobel at gmail
 */

import java.util.*;

public class EightTile {
    private static final TileBoard GOAL_STATE = new TileBoard();
    private final Integer[][] NUMBERS = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, -1}
    };

    // Method to convert Integer to int and shuffle
    private static int[][] toPrimitive(Integer[][] array) {
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

    public static String solve( TileBoard board ){
        final TileBoard GOAL_STATE = new TileBoard(); //................................ the ideal configuration board
        Queue<TileBoard> open = new LinkedList<>(); //.................................. the queue of open boards
        ArrayList<String> mostEfficientRoute = new ArrayList<>(); //.................... list to hold most efficient route
        ArrayList<TileBoard> closed = new ArrayList<>(); //............................. list of already closed boards
        boolean isSolved = false; //.................................................... variable for algorithm loop

        open.add(board); //.............................................................. add initial board to queue

        while( !isSolved ){ //.......................................................... while we've not solved the route
            if( open.isEmpty() ){ //.................................................... if the queue is empty we're done, no solution
                System.out.println( "No solution" );
                break;
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
            return String.format("Great Success, This is the goal state!");
        }
        else
            return getStepsToSolve( mostEfficientRoute );
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
    private static String getStepsToSolve(ArrayList<String> route ){
        String returnString = String.format( "Steps to solve: %d%n", route.size() );
        for( int i = 0; i < route.size(); i++ ){ //....................... loop through all routes and print them
            returnString += String.format( "%d: %s%n", i+1,fullWord(route.get(i)) );
        }
        return returnString;
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
