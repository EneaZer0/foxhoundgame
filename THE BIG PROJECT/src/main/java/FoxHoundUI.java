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
    public static void displayBoard(String[] players, int dimension) {
        // TODO implement me

        /** Defining the arrays that will display the NUMBERS and the LETTERS on the chart*/
        String[] numbers_array = new String[dimension];
        String letters_array   = "";
        String only_dots_array = "    ";

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

        /** ________________ LETTERS ARRAY ___________________________
         *  Use ASCII to calculate an array of letters in capital */
        for(int i = 0; i < dimension; i++) {
            String letter = String.valueOf((char) (65 + i));
            letters_array = letters_array + letter;
        }
        // System.out.println(letters_array);

        /** ____________ RECOGNISE POSITION OF PIECES ________________*/
        //System.out.println(Arrays.toString(FoxHoundUtils.letter_recognition(players)));
        //System.out.println(Arrays.toString(FoxHoundUtils.number_recognition(players)));

        /** ___________________ PRINTING THE BOARD ____________________ */
        /** PRINTING ONE FULL BOARD IN MATH VALUES */
        //FoxHoundUtils.griding(FoxHoundUtils.letter_recognition(players), FoxHoundUtils.number_recognition(players), dimension, 0);
        /** THIS FUNCTION PRINTS THE BOARD - IT TAKES GRIDING (IS A ARRAY[][]), LETTERS_ARRAY, NUMBERS_ARRAY & DIMENSION */
        FoxHoundUtils.board(
                FoxHoundUtils.griding(FoxHoundUtils.letter_recognition(players),FoxHoundUtils.number_recognition(players), dimension, 0),
                letters_array, numbers_array, dimension);

        //System.out.println(FoxHoundUtils.line_diagram(FoxHoundUtils.letter_recognition(players), FoxHoundUtils.number_recognition(players), dimension, 1));
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







