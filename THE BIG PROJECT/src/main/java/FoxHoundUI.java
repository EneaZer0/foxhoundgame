import java.util.Arrays;
import java.util.Scanner;
import java.util.Objects;

/**
 * A utility class for the fox hound program.
 * 
 * It contains helper functions for all user interface related
 * functionality such as printing menus and displaying the game board.
 */
public class FoxHoundUI {

    /** Number of main menu entries. */
    private static final int MENU_ENTRIES = 2;
    /** Main menu display string. */
    private static final String MAIN_MENU =
        "\n1. Move\n2. Exit\n\nEnter 1 - 2:";

    /** Menu entry to select a move action. */
    public static final int MENU_MOVE = 1;
    /** Menu entry to terminate the program. */
    public static final int MENU_EXIT = 2;

    /** #__________________ DISPLAY THE BOARD __________________# */
    /**
     *
     * @param players = takes the array of the situation of the pieces in the board
     * @param dimension = takes the dimension of the board
     */
    public static void displayBoard(String[] players, int dimension) {
        /** ___________________ PRINTING THE BOARD ____________________ */
        /** PRINTING ONE FULL BOARD IN MATH VALUES */
        //FoxHoundUtils.griding(FoxHoundUtils.letter_recognition(players), FoxHoundUtils.number_recognition(players), dimension, 0);
        /** THIS FUNCTION PRINTS THE BOARD - IT TAKES GRIDING (IS A ARRAY[][]), LETTERS_ARRAY, NUMBERS_ARRAY & DIMENSION */
        FoxHoundUtils.board(
                FoxHoundUtils.griding(FoxHoundUtils.letter_recognition(players),FoxHoundUtils.number_recognition(players), dimension, 0),
                FoxHoundUI.letters_array(dimension), FoxHoundUI.numbers_array(dimension), dimension);

        //System.out.println(FoxHoundUtils.line_diagram(FoxHoundUtils.letter_recognition(players), FoxHoundUtils.number_recognition(players), dimension, 1));
    }
    /** FUNCTION THAT CREATES THE NUMBERS WHICH APPEAR IN EACH ROW */
    /**
     *
     * @param dimension = takes the dimension to create an array till that value
     * @return = an array with all the numbers to be displayed
     */
    public static String[] numbers_array (int dimension) {
        String[] numbers_array = new String[dimension];
        /** ________________ NUMBERS ARRAY ___________________________
         *
         * If the dimension is under 2 digits, then is a simple count up
         *
         * If the dimension is bigger thant 9 (has 2 digits) do the case of
         * digits of only one digit to make them 2
         *
         */
        if (dimension < 10) {
            for (int i = 0; i < dimension; i++) {
                numbers_array[i] = String.valueOf(i + 1); // The numbers start at 1 (not at 0);
            }
        } else {
            for(int i = 0; i < 9; i++) {
                String number = "0" + String.valueOf(i + 1);
                numbers_array[i] = number;
            }
            for(int i = 9; i < dimension ; i++) {
                numbers_array[i] = String.valueOf(i + 1);
            }
        }
        // System.out.println(Arrays.toString(numbers_array));
        return  numbers_array;
    }
    /** FUNCTION THAT CREATES THE STRING OF LETTERS USED IN GENERAL & APPEAR IN EACH COLUMN*/
    /**
     *
     * @param dimension = takes the dimension to calculate the letters used in the displayed board
     * @return = the array with all the letters needed to print the board
     */
    public static String letters_array (int dimension) {
        String letters_array   = "";

        for(int i = 0; i < dimension; i++) {
            String letter = String.valueOf((char) (65 + i));
            letters_array = letters_array + letter;
        }
        // System.out.println(letters_array);

        return letters_array;
    }

    /**
     * Print the main menu and query the user for an entry selection.
     * 
     * @param figureToMove the figure type that has the next move
     * @param stdin a Scanner object to read user input from
     * @return a number representing the menu entry selected by the user
     * @throws IllegalArgumentException if the given figure type is invalid
     * @throws NullPointerException if the given Scanner is null
     */
    public static int mainMenuQuery(char figureToMove, Scanner stdin) {
        Objects.requireNonNull(stdin, "Given Scanner must not be null");
        if (figureToMove != FoxHoundUtils.FOX_FIELD 
         && figureToMove != FoxHoundUtils.HOUND_FIELD) {
            throw new IllegalArgumentException("Given figure field invalid: " + figureToMove);
        }

        String nextFigure = 
            figureToMove == FoxHoundUtils.FOX_FIELD ? "Fox" : "Hounds";

        int input = -1;
        while (input == -1) {
            System.out.println(nextFigure + " to move");
            System.out.println(MAIN_MENU);

            boolean validInput = false;
            if (stdin.hasNextInt()) {
                input = stdin.nextInt();
                validInput = input > 0 && input <= MENU_ENTRIES;
            }

            if (!validInput) {
                System.out.println("Please enter valid number.");
                input = -1; // reset input variable
            }

            stdin.nextLine(); // throw away the rest of the line
        }

        return input;
    }
}







