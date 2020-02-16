//import com.sun.imageio.plugins.common.SingleTileRenderedImage;

import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Arrays;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

/** 
 * The Main class of the fox hound program.
 * 
 * It contains the main game loop where main menu interactions
 * are processed and handler functions are called.
  */
public class FoxHoundGame {

    /** 
     * This scanner can be used by the program to read from
     * the standard input. 
     * 
     * Every scanner should be closed after its use, however, if you do
     * that for StdIn, it will close the underlying input stream as well
     * which makes it difficult to read from StdIn again later during the
     * program.
     * 
     * Therefore, it is advisable to create only one Scanner for StdIn 
     * over the course of a program and only close it when the program
     * exits. Additionally, it reduces complexity. 
     */
    private static final Scanner STDIN_SCAN = new Scanner(System.in);


    /**
     * Swap between fox and hounds to determine the next
     * figure to move.
     * 
     * @param currentTurn last figure to be moved
     * @return next figure to be moved
     */
    private static char swapPlayers(char currentTurn) {
        if (currentTurn == FoxHoundUtils.FOX_FIELD) {
            return FoxHoundUtils.HOUND_FIELD;
        } else {
            return FoxHoundUtils.FOX_FIELD;
        }
    }

    /**
     * The main loop of the game. Interactions with the main
     * menu are interpreted and executed here.
     * 
     * @param dim the dimension of the game board
     * @param players current position of all figures on the board in board coordinates
     */
    private static void gameLoop(int dim, String[] players) {
        if (dim == 0 || players == null) {
            throw new NullPointerException("ERROR, PARAMETERS CAN`T BE NULL");
        } else {
            // start each game with the Fox
            char turn = FoxHoundUtils.FOX_FIELD;
            boolean exit = false;
            while(!exit) {
                System.out.println("\n#################################");
                try{
                    FoxHoundUI.displayBoard(players, dim);
                } catch (NullPointerException error) {
                    System.err.println(error.getMessage());
                }

                int choice = FoxHoundUI.mainMenuQuery(turn, STDIN_SCAN);

                // handle menu choice
                switch(choice) {
                    case FoxHoundUI.MENU_MOVE:
                        try {
                            FoxHoundUtils.make_the_step(players,dim,STDIN_SCAN,turn);
                            turn = swapPlayers(turn);
                        } catch (NullPointerException error) {
                            System.err.println(error.getMessage());
                        }
                        break;
                    case FoxHoundUI.MENU_SAVE:
                        try {
                            FoxHoundIO.saveGame(players, turn, FoxHoundUI.fileQuery(STDIN_SCAN));
                        } catch (IllegalArgumentException error) {
                            System.err.println(error.getMessage());
                        } catch (NullPointerException error) {
                            System.err.println(error.getMessage());
                        }
                        break;
                    case FoxHoundUI.MENU_LOAD:
                        try {
                            FoxHoundIO.loadGame(players,FoxHoundUI.fileQuery(STDIN_SCAN));
                        } catch (NullPointerException error) {
                            System.err.println(error.getMessage());
                        } catch (IllegalArgumentException error) {
                            System.err.println(error.getMessage());
                        }
                        break;
                    case FoxHoundUI.MENU_EXIT:
                        exit = true;
                        break;

                    default:
                        System.err.println("ERROR: invalid menu choice: " + choice);

                }
            }
        }

    }

    /**
     * Entry method for the Fox and Hound game. 
     * 
     * The dimensions of the game board can be passed in as
     * optional command line argument.
     * 
     * If no argument is passed, a default dimension of 
     * {@value FoxHoundUtils#DEFAULT_DIM} is used. 
     * 
     * Dimensions must be between {@value FoxHoundUtils#MIN_DIM} and 
     * {@value FoxHoundUtils#MAX_DIM}.
     * 
     * @param args contain the command line arguments where the first can be
     * board dimensions.
     */
    public static void main(String[] args) {

        int dimension = FoxHoundUtils.DEFAULT_DIM;

        /** Check if the argument is empty or not and then check if the dimension is valid for all the cases */
        if (args.length != 0) {
            dimension = Integer.parseInt(args[0]);
        }
        try {
            dimension = FoxHoundUtils.dimension_check(dimension);
        } catch (NullPointerException error) {
            System.err.println(error.getMessage());
            dimension = FoxHoundUtils.DEFAULT_DIM;
        }
        try {
            String[] players = FoxHoundUtils.initialisePositions(dimension);
            gameLoop(dimension, players);
        } catch (NullPointerException error) {
            System.err.println(error.getMessage());
        } catch (IllegalArgumentException error) {
            System.err.println(error.getMessage());
        }


        // Close the scanner reading the standard input stream       
        STDIN_SCAN.close();
    }
}
