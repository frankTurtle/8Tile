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
    private static final TileBoard GOAL_STATE = new TileBoard(); //............................ the ideal configuration board

    // Method to solve using the breadthFirst Algorithm
    public static String breadthSolve(TileBoard board ){
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
                addAllNodesBreadth( open, closed, n, mostEfficientRoute ); //.......... if its not the goal, we have to add all children nodes to queue
            }
        }

        if( mostEfficientRoute.size() == 0 ) {
            return "Great Success, This is the goal state!";
        }
        else
            return getStepsToSolve( mostEfficientRoute );
    }

    // Method to solve using the depthFirst algorithm
    public static String depthSolve( TileBoard board ){
        Stack<TileBoard> open = new Stack<>(); //...................................... stack for the depth
        ArrayList<TileBoard> closed = new ArrayList<>(); //............................ closed array
        ArrayList<String> mostEfficientRoute = new ArrayList<>(); //................... list to hold most efficient route
        boolean isSolved = false;
        int currentDepth = 0;
        final int DEPTH_END = 6;

        open.push( board ); //......................................................... push initial board onto stack

        while( !isSolved ){ //......................................................... while its not solved
            if( open.isEmpty() ){ //................................................... if the queue is empty we're done, no solution
                System.out.println( "No solution" );
                break;
            }

            TileBoard n = open.pop(); //............................................... take the first board
            closed.add( n ); //........................................................ put it in closed

            if( n.equals(GOAL_STATE)){ //.............................................. if current board is the goal we're done!
                isSolved = true;
                continue;
            }
            else if( currentDepth > DEPTH_END ){ //.................................... if we're too deep abort
                System.out.println( "Depth limit reached, No solution" );
                break;
            }
            else{ //................................................................... continue with searching
                addAllNodesDepth( open, closed, n, mostEfficientRoute ); //............ add all available nodes to the stack
            }

            currentDepth++; //......................................................... increment the current depth
        }

        if( mostEfficientRoute.size() == 0 ) { //...................................... if the route is empty were at the goal
            return "Great Success, This is the goal state!";
        }
        else //........................................................................ else return the steps to solve
            return getStepsToSolve( mostEfficientRoute );
    }

    // Method to generate all the nodes for the DepthFirst Search
    private static TileBoard[] generateNodes( TileBoard n ){
        TileBoard[] returnNodes = new TileBoard[ n.getAvailableMoves().length ]; //. get the length of the array

        for( int i = 0; i < n.getAvailableMoves().length; i++ ){ //................. loop through each available move
            TileBoard copyBoard = new TileBoard( n.getBoard() ); //................. add to the array
            copyBoard.move( n.getAvailableMoves()[i] );
            returnNodes[i] = copyBoard;
        }
        return returnNodes;
    }

    // Method to remove any objects from the open stack
    private static void removeFromOpen( TileBoard[] nodes, Stack<TileBoard> open ){
        for( TileBoard board : nodes ){ //............................................. loop through each node
            if( open.contains(board) ){ //............................................. if the stack contains the node remove it
                open.remove(board);
            }
        }
    }

    // Method to add all nodes to the Stack that are not in closed
    private static void addAllNodesDepth( Stack<TileBoard> open, ArrayList<TileBoard> closed, TileBoard n, ArrayList<String> route  ){
        String routeString = ""; //..................................................................................................... variable to return the route

        removeFromOpen( generateNodes(n), open ); //.................................................................................... remove all nodes already generated from open

        for( String direction: n.getAvailableMoves() ){ //.............................................................................. loops through all available moves
            TileBoard copyBoard = new TileBoard( n.getBoard() ); //..................................................................... makes a copy of the current board
            copyBoard.move(direction); //............................................................................................... makes one of the available moves
            for( TileBoard closedBoard: closed ) //..................................................................................... loop through each closed board
                if( !copyBoard.equals(closedBoard) ){ //................................................................................ if the board is equal to closed
                    if( copyBoard.hScore() < n.hScore() ){ //........................................................................... and hscore is less than current
                        routeString = direction; //..................................................................................... keep track of the direction
                        open.add( copyBoard ); //....................................................................................... add it to the stack
                    }
                }
        }
        if(!routeString.equals("")) //................................................................................................... as long as the string is not empty add it to route
            route.add( routeString );
    }

    // Method to add all the child nodes to the queue
    // takes the queue, the closed list, and the current board
    private static void addAllNodesBreadth( Queue<TileBoard> open, ArrayList<TileBoard> closed, TileBoard n, ArrayList<String> route ){
        String routeString = "";

        for( String direction: n.getAvailableMoves() ){ //......................................................................... loops through all available moves
            TileBoard copyBoard = new TileBoard( n.getBoard() ); //................................................................ makes a copy of the current board
            copyBoard.move(direction); //.......................................................................................... makes one of the available moves
            if( !boardInOpenAndClose(copyBoard, closed, open) ){ //...................................................................... if the board is not in closed
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
    private static boolean boardInOpenAndClose( TileBoard board, ArrayList<TileBoard> closed, Queue<TileBoard> open ) {
        for( TileBoard closedBoard: closed ){ //............................................ loops through each board in the closed list
            if( board.equals(closedBoard )) return true; //................................. if the current board is equal to the one in the closed list it's in closed!
        }

        LinkedList<TileBoard> openCopy = new LinkedList<>(open);

        for( TileBoard openBoard : openCopy ){
            if( board.equals( openBoard )) return true;
        }

        return false; //.................................................................... if it makes it through all closed and hasn't returned true, its NOT in the closed list
    }

    // Method to print out the steps in order on how to breadthSolve the puzzle
    private static String getStepsToSolve(ArrayList<String> route ){
        String returnString = String.format( "Steps to Solve: %d%n", route.size() );
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
