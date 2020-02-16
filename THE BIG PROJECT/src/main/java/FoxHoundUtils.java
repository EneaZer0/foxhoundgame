//import com.sun.imageio.plugins.common.SingleTileRenderedImage;
//import org.graalvm.compiler.lir.StandardOp;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Scanner;
import java.lang.reflect.Array;
import java.nio.file.Files;




/**
 * A utility class for the fox hound program.
 * 
 * It contains helper functions to check the state of the game
 * board and validate board coordinates and figure positions.
 */
public class FoxHoundUtils {

    // ATTENTION: Changing the following given constants can 
    // negatively affect the outcome of the auto grading!

    /** Default dimension of the game board in case none is specified. */
    public static final int DEFAULT_DIM = 8;
    /** Minimum possible dimension of the game board. */
    public static final int MIN_DIM = 4;
    /** Maximum possible dimension of the game board. */
    public static final int MAX_DIM = 26;
    /** Symbol to represent a hound figure. */
    public static final char HOUND_FIELD = 'H';
    /** Symbol to represent the fox figure. */
    public static final char FOX_FIELD = 'F';
    /** Symbol to represent an empty space. */
    public static final char DOT_FIELD = '.';

    /** Margins for NUMBERS
     * Sets a the default margin that the numbers will have when the board is displayed
     */
    public  static final String NUMBERS_MARGIN = " ";
    /** Set of the MARGIN OF ALPHABET*/
    /**
     *
     * @param dimension = gets the dimensions of the board
     * @return  = creates an array of letters that will be used in the top and bottom of the board
     */
    public static String alphabet_margin (int dimension){
        String alphabet_margin = "";
        if (dimension == 0) {
            throw new NullPointerException("ERROR, DIMENSIONS CAN'T BE NULL");
        } else {
            if (dimension < 10) {
                alphabet_margin = "  ";
            } else {
                alphabet_margin = "   ";
            }

        }
        return alphabet_margin;
    }

    private static String[] letter_recognition;
    private static String[] number_recognition;
    private static int dimension;
    private static int row;

    /** FUNCTION TO SET THE DIMENSION OF ALL THE GAME*/
    /**
     *
     * @param dimension = takes the initial dimension at the beginning of the game and checks if it is valid
     * @return = a final dimension that can be used in the rest of the game
     */
    public static int dimension_check (int dimension) {

        /** First, check if the dimension we get is valid
         * if it is smaller than the MIN_DIM or bigger than the
         * MAX_DIM, it must be changed to de DEFAULT_DIM        */
        if (dimension == 0) {
            throw new NullPointerException("ERROR, DIMENSION NOT VALID");
        } else {
            if ((dimension < MIN_DIM) || (dimension > MAX_DIM)) {
                dimension = DEFAULT_DIM;
            }
        }

        return dimension;
    }
    /** FUNCTION TO RECOGNISE THE LETTERS IN PLAYER */
    /**
     *
     * @param players = takes the players array and recognise the letters that appear inside
     * @return = an array of letters which are contained in the players array
     */
    public static String[] letter_recognition (String[] players) {
        String[] letters_recognised = new String[players.length];
        if (players == null) {
            throw new NullPointerException("ERROR, PLAYERS CANT BE NULL");
        } else {
            /** k is a variable used to save the letters in letters_recognised*/
            int k = 0;

            /** First loop split each of the elements of the array
             *  Second loop transform the element to char and compare to
             *      ASCII value of letters to detect the letters.
             *          if letter = true -> transform to String and save
             *          in letters_recognised                              */

            for (String str: players) {

                String [] split = str.split("");

                for (String elements: split) {

                    /** Transform each element to char*/
                    char element_char = elements.charAt(0);

                    /** Compares to ASCII letters values*/
                    if ((element_char >= (char)65) && (element_char < 91)) {
                        //System.out.println(element_char + " DETECTED");
                        letters_recognised[k] = ""+ element_char;
                        k++;

                    } else {
                        //System.out.println(element_char + " IS NOT A LETTER");

                    }
                }
            }
        }

        //System.out.println(Arrays.toString(letters_recognised));

        return letters_recognised;
    }
    /** FUNCTION TO RECOGNISE THE NUMBERS IN PLAYER */
    /**
     *
     * @param players = takes the players array and recognise the numbers that appear inside
     * @return = an array of numbers which are contained in the players array
     */
    public static String[] number_recognition (String[] players) {

        /**
         * The number of elements in each value depends on the length on the dimension
         *  if dimension is < than 10 then there are just 1 LETTER and 1 NUMBER
         *  if dimension is > than 10 then 2 cases:
         *      numbers < 10 will have a 0 in the front -> if the pieces move we must
         *      take that into account
         */

        String[] numbers_recognised = null;
        if (players == null) {
            throw new NullPointerException("ERROR, PLAYERS CANT BE NULL");
        } else {
            /** k is a variable used to save the numbers in numbers_recognised*/
            numbers_recognised = new String[players.length];
            int k = 0;
            boolean numbers_together = false;

            /** First loop split each of the elements of the array
             *  Second loop transform the element to char and compare to
             *      ASCII value of numbers to detect the numbers.
             *          if number = true -> transform to String and save
             *          in numbers_recognised                              */

            for (String str: players) {

                String [] split = str.split("");

                for (String elements: split) {

                    /** Transform each element to char*/
                    char element_char = elements.charAt(0);

                    /** Compares to ASCII */
                    if ((element_char >= (char)48) && (element_char < (char)58)) {

                        /** AÑADIR CASO EN EL QUE TIENES DOS DIGITOS SEGUIDOS (NO SOLO EL CASO DE FOX
                         * HOUNDS SE MUEVEN Y SI LA DIMENSION ES MAYOR DE 10 TIENE 2 DIGITOS TODOS SEGUIDOS!!!
                         */

                        /** Must check if there is a number of 2 digits*/
                        if (numbers_together == false) {

                            // System.out.println(element_char + " DETECTED");
                            numbers_recognised[k] = ""+ element_char;
                            k++;
                            numbers_together = true;

                        } else {
                            /** k gets the value of the previous digit to join them together */
                            k--;

                            // System.out.println(element_char + " DETECTED - IS A SECOND DIGIT");
                            String two_digit_number = numbers_recognised[k] + element_char;
                            numbers_recognised[k] = two_digit_number;
                            //System.out.println(numbers_recognised[k] + " IS A 2 DIGITS NUMBER");

                            /** if there are more 2 digit numbers k must continue summing and numbers_together resets*/
                            k++;
                            numbers_together = false;

                        }


                    } else {
                        //System.out.println(element_char + " IS NOT A LETTER");
                        numbers_together = false;

                    }
                }
            }
        }


        //System.out.println(Arrays.toString(numbers_recognised));

        return numbers_recognised;
    }
    /** FUNCTION TO RECOGNISE THE BLACK CASES IN THE LAST LINE */
    /**
     *
     * @param dimension = takes the dimension to calculate the position where the Fox will be at the beginning
     * @return
     */
    public static int fox_position (int dimension) {
        int position = 0;

        if (dimension == 0) {
            throw new NullPointerException("ERROR, DIMENSION CAN'T BE NULL");
        } else {
            // Black = array with the positions of the black squares were the fox can be
            int[] blacks = new int[dimension/2];
            if (dimension % 2 == 0) {
                for (int i = 0; i < dimension/2; i++) {
                    if (i == 0) {
                        blacks[i] = i;
                    } else {
                        blacks[i] = 2*i;
                    }
                }
            } else {
                for (int i = 0; i < dimension /2; i++) {
                    blacks[i] = 2*i + 1;
                }
            }
            // Check printing the black squares
            //System.out.println(Arrays.toString(blacks));
            position = blacks[blacks.length/2];
            // Check printing the final position of the Fox
            //System.out.println(position);

        }

        return position;
    }
    /** FUNCTION TO SET THE PLAYERS ARRAY INITIAL VALUES */
    /**
     *
     * @param dimension = Takes the dimension value to create the board
     * @return = position_array, which is the players[] array
     */
    public static String[] initialisePositions(int dimension) {
        String[] position_array = null;
        /** Number of pieces on the board, giving the option of possible
         * change of the number of Foxes playing */

        if (dimension < 0) {
            throw new IllegalArgumentException("ERROR: DIMENSIONS MUST BE POSITIVE");
        } else if ( dimension == 0) {
            throw new NullPointerException("ERROR: DIMENSIONS CAN`T BE NULL");
        } else {

            try {
                int number_Hound = (dimension / 2);
                int number_Fox = 1;

                /** Create the String array that returns the positions of the pieces */
                position_array = new String[number_Hound + number_Fox];

                /**_________________ NUMBER SET  ____________________
                 *
                 * The Hounds are set in the first row always
                 * The Fox is set in the last one
                 * Use of Strings to make easier the end built of the position array*/

                String hound_num_initial_position = "1";
                String fox_num_initial_position = String.valueOf(dimension);

                /**________________ LETTER SET _____________________
                 * For this task use ASCII values of the alphabet in order to calculate
                 * the letter easier
                 */

                String[] hound_values = new String[number_Hound];
                /** For loop to get the letters of Hounds
                 * Calculation uses ASCII values and an arithmetic series */
                for (int i = 0; i < number_Hound; i ++) {
                    hound_values[i] = (String.valueOf((char)(66 + 2*i)) + hound_num_initial_position);
                }
                /** Create a for loop to copy the array with all the information of the
                 * position of the Hounds to the final position_array */
                for (int i = 0; i < number_Hound; i++) {
                    position_array[i] = hound_values[i];
                }

                /** Calculating the Fox's letter  */
                int fox_position = fox_position(dimension);
                int fox_letter = 65 + fox_position;

                // Creates the final string value of the Fox (including letter & number)
                String fox_value = String.valueOf((char)fox_letter) + fox_num_initial_position;

                // Add at the end of the array the Fox Value
                position_array[position_array.length-1] = fox_value;
            } catch (NullPointerException error) {
                System.err.println(error.getMessage());
            }

        }



        return  position_array;
    }




    /** ____________________ BUILD AND DISPLAY OF GRID ______________________ */
    /** FUNCTION WHICH BUILDS A LINE AS A DIAGRAM IN NUMBERS*/
    /**
     *
     * @param letter_recognition = array with the letters after being recognised by letter_recognition() function
     * @param number_recognition = array with the numbers after being recognised by number_recognition() function
     * @param dimension = takes the dimension after being checked
     * @param row = takes the value of the row that it is being created
     * @return = returns a line as a math diagram of 0s 1s and 2s, (0 = dots, 1 = Hounds, 2 = Fox)
     */
    public static int[] line_diagram (String[] letter_recognition, String[] number_recognition, int dimension, int row) {

        /** First create the full array */
        int[] the_line = null;

        if (letter_recognition == null ||number_recognition == null || dimension == 0) {
            throw new NullPointerException("ERROR, PARAMETERS CAN'T BE NULL");
        } else {
            the_line = new int[dimension];

            for (int i = 0; i < dimension; i++) {
                the_line[i] = 0;
            }

            //System.out.println(Arrays.toString(the_line));
            int position;
            int piece = 0;

            for (int i = 0; i < number_recognition.length; i++) {
                piece++;
                if (Integer.parseInt(number_recognition[i]) == row ) {

                    char letter_char = letter_recognition[i].charAt(0);
                    position = letter_char - 65;

                    if (piece < letter_recognition.length) {
                        the_line[position] = 1;
                        //System.out.println(Arrays.toString(the_line));
                    } else {
                        the_line[position] = 2;
                        //System.out.println(Arrays.toString(the_line));
                    }


                }

            }
        }


        return the_line;
    }
    /** FUNCTION WHICH BUILDS THE GRID OF LINES - IN MATH VALUES USING LINE_DIAGRAM*/
    /**
     *
     * @param letter_recognition = takes the letters that are inside players array
     * @param number_recognition = takes the numbers that are inside players array
     * @param dimension = takes the dimension of the board
     * @param row = takes an initial value of the row
     * @return = a grid of lines in math values using 0s, 1s and 2s
     */
    public static int[][] griding (String[] letter_recognition, String[] number_recognition, int dimension, int row) {
        int[][] grid = null;

        if (letter_recognition == null ||number_recognition == null ||dimension == 0) {
            throw new NullPointerException("ERROR, PARAMETERS CAN'T BE NULL");
        } else {

            try {
                /** Creates a 2 dimension array to store all the values of the grid*/
                grid = new int[dimension][];

                for (int i = 0; i < dimension; i++) {

                    //System.out.println(Arrays.toString(line_diagram(letter_recognition, number_recognition, dimension, i + 1)));
                    grid[i] = line_diagram(letter_recognition, number_recognition, dimension, i + 1);

                }

            } catch (NullPointerException error) {
                System.err.println(error.getMessage());
            }
        }

        return grid;
    }
    /** DISPLAY GRID - USES MATH VALUES OF GRID AND REPLACE THEM WITH THE FINAL GRID */
    /**
     *
     * @param grid = takes the math version of the matrix grid
     * @param alphabet = gets an array of letters that will be used in the top and bottom
     * @param numbers = gets an array of numbers that will be used in the laterals
     * @param dimension = takes the dimensions of the board
     */
    public static void board (int[][] grid, String alphabet, String[] numbers, int dimension) {

        if (grid == null || alphabet == null || numbers == null || dimension == 0) {
            throw new NullPointerException("ERROR PARAMETERS CAN`T BE NULL");
        } else{
            try {
                String letters = alphabet_margin(dimension) + alphabet;
                System.out.println("\n"+ letters + "  \n");
                String one_line = "";


                /** For every line on the grid */

                int row = 0;

                for (int[] line: grid) {

                    /** Reset the value of the line every time it restarts */
                    one_line = "" + numbers[row] + NUMBERS_MARGIN;

                    /** For every element of the grid substitute it's value to the corresponding piece on the grid */
                    for (int values: line) {

                        if (values == 0) {

                            one_line = one_line + DOT_FIELD;

                        } else if (values == 1) {

                            one_line = one_line + HOUND_FIELD;

                        } else {

                            one_line = one_line + FOX_FIELD;

                        }

                    }

                    one_line = one_line + NUMBERS_MARGIN + numbers[row];
                    row++;
                    System.out.println(one_line);


                }

                System.out.println("\n" + letters + "\n");

            } catch (NullPointerException error ) {
                System.err.println(error.getMessage());
            }
        }

    }




    /** ____________________ TASK 4 - IS VALID MOVE ______________________*/
    /**
     *
     * @param dimension = gets the dimension of the board
     * @param players = get the array where the pieces are already
     * @param figure = gets the character that represents the figure on the board
     * @param origin = gets the original position on the board
     * @param destination = gets the final position on the board
     * @return = returns if the move entered is valid at that state of the game
     */
    public static boolean isValidMove (int dimension, String[] players, char figure, String origin, String destination) {
        boolean isValid = true;
        /** _________________ CHECKING ORIGIN ___________________ */
        /** Check if the coordinates of the origin entered are valid */
        try {
            isValid = coordinate_checker(dimension, origin) && isValid;
        } catch (IllegalArgumentException error) {
            isValid = false;
            System.err.println(error.getMessage());
        } catch (NullPointerException error) {
            isValid = false;
            System.err.println(error.getMessage());
        }
        /** Check if the coordinates of the origin are inside the players array*/
        try {
            isValid = in_players(origin,players) && isValid;
        } catch (NullPointerException error) {
            System.err.println(error.getMessage());
            isValid = false;
        }
        /** Check if the coordinates of the destination entered are valid */
        try {
            isValid = coordinate_checker(dimension, destination) && isValid;
        } catch (IllegalArgumentException error) {
            isValid = false;
            System.err.println(error.getMessage());
        } catch (NullPointerException error) {
            isValid = false;
            System.err.println(error.getMessage());
        }
        /** Check if the the destination is inside players array */
        try {
            isValid = !(in_players(destination,players)) && isValid;
        } catch (NullPointerException error) {
            System.err.println(error.getMessage());
            isValid = false;
        }
        /** Check if the origin is not equal to the destination */
        try{
            if (origin == null) {
                throw new NullPointerException("ERROR, ORIGIN CAN NOT BE NULL");
            } else {
                isValid = !(origin.equals(destination)) && isValid;
            }
        } catch (NullPointerException error) {
            isValid = false;
            System.err.println(error.getMessage());
        }
        /** Check the char of the piece that it is moving */
        try {
            isValid = char_checker(figure, origin, players) && isValid;
        } catch (IllegalArgumentException error) {
            System.err.println(error.getMessage());
            isValid = false;
        } catch (NullPointerException error) {
            System.err.println(error.getMessage());
            isValid = false;
        }
        /** Check if the destination letter is in the diagonal of the origin*/
        try{
            isValid = in_diagonal(origin, destination) && isValid;
        } catch (NullPointerException error) {
            System.err.println(error.getMessage());
            isValid = false;
        }
        /** Check if the movement is only forward of hound */
        try {
            isValid = hound_movement_allowed(figure, origin, destination) && isValid;
        } catch (NullPointerException error) {
            System.err.println(error.getMessage());
            isValid = false;
        } catch (IllegalArgumentException error){
            System.err.println(error.getMessage());
            isValid = false;
        }

        return isValid;
    }
    /** FUNCTION WHICH CHECKS IF A COORDINATE IS VALID */
    /**
     *
     * @param dimension = take the dimension to check if all the values are inside the board
     * @param coordinate = takes the coordinate we want ot check (origin / destination)
     * @return = boolean value saying if the coordinate is valid for that board
     */
    public static boolean coordinate_checker (int dimension, String coordinate) {
        boolean validity = true;
        char letter = ' ';
        int number = 0;

        if (coordinate == null) {
            throw new NullPointerException("ERROR, COORDINATE CAN'T BE NULL");
        } else if (dimension == 0){
            throw new IllegalArgumentException("ERROR, DIMENSION NOT VALID");
        } else {
            /** Check if the letter of the coordinate is valid */
            try {
                letter = FoxHoundUtils.letter_coordinate(coordinate);
                validity = validity && true;

            } catch (IllegalArgumentException error){
                validity = false;
                System.err.println(error.getMessage());
            } catch (NullPointerException error) {
                validity = false;
                System.err.println(error.getMessage());
            }
            /** Check that the letter is inside the possible dimensions */
            if ( 65 <= (int)letter && (int)letter <= (90 - (26 - dimension)) ){
                validity = validity && true;
            } else {
                validity = false;
                System.err.println("ERROR, LETTER " + letter + " IS NOT IN THE RANGE OF THE DIMENSION");
            }
            /** Checking if the number of the coordinate is valid  */
            try {
                number = FoxHoundUtils.number_coordinate(coordinate);
                validity = validity && true;

            } catch (IllegalArgumentException error){
                validity = false;
                System.err.println(error.getMessage());
            } catch (NullPointerException error) {
                validity = false;
                System.err.println(error.getMessage());
            }

            /** Check that the numbers of the coordinate are inside the possible dimensions */
            if ( 1 <= number && number <= dimension) {
                validity = validity && true;
            } else {
                validity = false;
                if (number == 0) {
                    System.err.println("ERROR, NO NUMBER FOUND");
                } else {
                    System.err.println("ERROR, NUMBER " + number + " IS NOT IN THE RANGE OF THE DIMENSION");
                }
            }

            if (validity == false) {
                System.err.println("ERROR, " + coordinate + " IS NOT A VALID COORDINATE");
            }
        }

        return validity;
    }
    /** FUNCTION WHICH CHECKS IF ORIGIN IS IN PLAYERS */
    /**
     *
     * @param origin = takes the origin position of the piece we want to move
     * @param players = take all the positions of the pieces in the actual board
     * @return = compares if the origin input is valid (if it is contained in the players array)
     */
    public static boolean in_players (String origin, String[] players) {
        boolean origin_players = false;

        if (players == null) {
            throw new NullPointerException("ERROR, PLAYERS CAN NOT BE NULL");
        } else {
            for (int i = 0; i < players.length; i++) {
                origin_players = origin_players || (origin.equals(players[i]));
            }
        }

        return origin_players;
    }
    /** FUNCTION WHICH CHECKS IF THE FIGURE OF THE PIECE MOVING IS VALID */
    /**
     *
     * @param figure = gets the figure that we want to check
     * @param origin = check if the origin is valid for that figure (if figure is F origin must be in the last place of players array)
     * @param players = to compare the position of origin and see if the figure can do that move
     * @return = if the figure can do that movement
     */
    public static boolean char_checker (char figure, String origin, String[] players) {
        boolean valid = false;
        int k = 0;

        if (origin == null || players == null) {
            throw new NullPointerException("ERROR, char_checker can't have null parameters");
        } else if (figure == ' ') {
            throw new IllegalArgumentException("ERROR, that figure is nos valid for this function");
        } else {
            for (int i = 0; i < players.length; i++) {
                k = i;
                if (origin.equals(players[i])) {
                    break;
                }
            }
            if (k < players.length - 1) {
                if (figure == 'H') {
                    valid = true;
                } else {
                    System.err.println("THE FIGURE " + figure + " IS NOT VALID");
                }
            } else {
                if (figure == 'F') {
                    valid = true;
                } else {
                    System.err.println("THE FIGURE " + figure + " IS NOT VALID");
                }
            }
        }

        return valid;
    }
    /** FUNCTION WHICH CHECKS IF THE LETTER FROM ORIGIN IS IN A RANGE OF +-1 AND THAT THE ROW IS ALSO CHANGED BY +-1*/
    /**
     *
     * @param origin = takes the origin to get it's number and letter and compare it to the destination
     * @param destination = takes the destination to get it's number and letter and compare them to the origin, must be +-1 values of the origin
     * @return = if the destination is in a diagonal position
     */
    public static boolean in_diagonal (String origin, String destination) {
        boolean valid = true;
        char letter_origin = letter_coordinate(origin);
        int number_origin = number_coordinate(origin);
        char letter_destination = letter_coordinate(destination);
        int number_destination = number_coordinate(destination);

        if (origin == null || destination == null) {
            throw new NullPointerException("ERROR, THE PARAMETERS OF THIS FUNCTION CAN'T BE NULL");
        } else {
            if (((int)letter_destination == (int)letter_origin  + 1 ) || ( (int)letter_destination == (int)letter_origin  - 1 )) {
                valid = valid && true;
            } else {
                valid = false;
                System.err.println("ERROR, LETTERS ARE NOT IN DIAGONAL");
            }

            if ((number_destination == number_origin + 1) || (number_destination == number_origin - 1)) {
                valid = valid && true;
            } else {
                valid = false;
                System.err.println("ERROR, NUMBERS ARE NOT IN DIAGONAL");
            }
        }

        return valid;
    }
    /** FUNCTION TO GET THE LETTERS OF COORDINATES - CAN EXTRACT LETTER FROM ORIGIN AND FROM PLAYERS (CHECK IF PLAYERS OF DESTINATION)*/
    /**
     *
     * @param coordinate = gets the coordinate value (origin or destination) and gets the letter from that coordinate
     * @return = the letter of the coordinate entered
     */
    public static char letter_coordinate (String coordinate) {
        char letter = ' ';
        if (coordinate == null) {
            throw new NullPointerException("ERROR, THE COORDINATE CAN'T BE NULL");
        } else {
            if (coordinate.length() == 2 || coordinate.length() == 3) {
                letter = coordinate.charAt(0);
                if ((int) letter > 90 || 65 > (int) letter) {
                    letter = coordinate.charAt(0);
                    System.err.println("ERROR " + letter + " IS NOT A VALID LETTER");
                }
            } else {
                System.err.println("ERROR " + coordinate + " IS NOT A VALID COORDINATE");
            }
        }

        return letter;
    }
    /** FUNCTION TO GET THE NUMBERS OF COORDINATES - CAN EXTRACT NUMBERS FROM ORIGIN AND FROM PLAYERS (CHECK IF PLAYERS OF DESTINATION)*/
    /**
     *
     * @param coordinate = gets the coordinate value (origin or destination) and gets the number from that coordinate
     * @return = the number value of that coordinate
     */
    public static int number_coordinate (String coordinate) {
        char number1;
        char number2; //in case of two digits use to store the second digit
        String number = "";
        int final_number = 0;

        if (coordinate == null) {
            throw new NullPointerException("ERROR, COORDINATES CAN'T BE NULL (NUMBER COORDINATE)");
        } else {
            // GETTING NUMBER IN CASE DIMENSION IS ONE DIGIT DIMENSION
            if (coordinate.length() == 2) {

                number1 = coordinate.charAt(1);
                // CHECK IF THE NUMBER RECEIVED IS REALLY A NUMBER
                if (48 <= (int) number1 && (int) number1 <= 57) {
                    final_number = Integer.parseInt(String.valueOf(number1));
                } else {
                    throw new IllegalArgumentException("ERROR " + number1 + " IS NOT A NUMBER ");
                }

            } else if (coordinate.length() == 3) /* GETTING NUMBERS IN CASE DIMENSION IS A TWO DIGIT DIMENSION*/ {
                number1 = coordinate.charAt(1);
                number2 = coordinate.charAt(2);

                // CHECK IF THE NUMBERS RECEIVED ARE REALLY NUMBERS
                if ((48 <= (int) number1 && (int) number1 <= 57) && (48 <= (int) number2 && (int) number2 <= 57)) {
                    number = number + number1 + number2;
                    final_number = Integer.parseInt(number);
                } else {
                    throw new IllegalArgumentException("ERROR " + number1 + " OR " + number2 + " ARE NOT NUMBERS");
                }

            } else {

                throw  new IllegalArgumentException("ERROR " + coordinate + " IS NOT A VALID COORDINATE");

            }
        }


        return final_number;
    }
    /** FUNCTION TO CHECK IF THE MOVEMENT OF THE HOUNDS IS JUST FORWARD */
    /**
     *
     * @param figure takes the figure to check if it moves in the correct direction
     * @param origin takes the origin to set the reference
     * @param destination takes the destination to se if it is correct
     * @return validates the movement
     */
    public static boolean hound_movement_allowed(char figure, String origin, String destination) {
        boolean valid = true;
        if (origin == null || destination == null) {
            throw new NullPointerException("ERROR, THE PARAMETERS OF THIS FUNCTION CAN'T BE NULL");
        } else if (figure == ' ') {
            throw new IllegalArgumentException("ERROR, THE FIGURE IS NOT VALID");
        } else {
            if (figure == 'H' && (number_coordinate(destination) == number_coordinate(origin) + 1)){
                valid = true;
            } else if (figure == 'F') {
                valid = true;
            } else if (figure == 'H' && (number_coordinate(destination) < number_coordinate(origin) + 1)){
                valid = false;
                System.err.println("ERROR, HOUNDS CAN ONLY MOVE FORWARD");
            } else {
                valid = false;
                System.err.println("ERROR, PICK A HOUND TO MOVE");
            }
        }

        return valid;
    }




    /** ____________________ TASK 4 - POSITION QUERY ______________________*/
    /** FUNCTION TO CHECK IF THE INPUT COORDINATES ARE IN THE RANGE OF THE DIMENSION*/
    /**
     *
     * @param dimension = compare the values of the coordinates with the dimension
     * @param coordinates = get the array of coordinates of the user
     * @return = a boolean indicating if its necessary ask again for coordinates
     */
    public static boolean input_coordinates_valid (int dimension, String[] coordinates) {
        boolean valid = false;
        if (dimension == 0 || coordinates == null) {
            throw new NullPointerException("ERROR, PARAMETERS CAN'T BE NULL");
        } else {
            valid = true;
            if (coordinates.length != 2) {
                valid = false;
            }

            // CHECK LETTERS
            for (int i = 0; i < coordinates.length; i++) {

                if (!((int)coordinates[i].charAt(0) <= 90 -(26 - dimension) && (int)coordinates[i].charAt(0) >= 65 )) {
                    valid = false;
                    System.err.println("ERROR: Please enter valid coordinate pair separated by space.");
                    break;
                } else {
                    valid = true;
                }
                // CHECK NUMBERS
                if (number_coordinate(coordinates[i]) > dimension) {
                    valid = false;
                    System.err.println("ERROR: Please enter valid coordinate pair separated by space.");
                    break;
                } else {
                    valid = true;
                }
            }
        }



        return valid;
    }
    /** FUNCTION TO CHANGE THE PLAYERS ARRAY WITH THE NEW POSITION */
    /**
     *
     * @param players = takes the initial position of the pieces
     * @param positionQuery = takes the coordinates that the user decided for that move
     * @return = a new players array with the new positions
     */
    public static String[] new_players_position (String[] players, String[] positionQuery) {

        if (players == null ||positionQuery == null) {
            throw new NullPointerException("ERROR, PARAMETERS CAN'T BE NULL");
        } else {
            int k = 0;
            for (int i = 0; i < players.length; i++) {
                k = i;
                if (positionQuery[0].equals(players[i])){
                    break;
                }
            }

            players[k] = positionQuery[1];

        }

        return players;
    }
    /** FUNCTION TO MAKE A FULL MOVEMENT (POSITION_QUERY - NEW_PLAYERS_POSITION - IS_VALID_MOVE - IS_FOX_WIN) */
    /**
     *
     * @param players = takes the players to set the initial conditions of the move
     * @param dimension = to do all the checking on the inside functions which require it
     * @param input = the input of the user
     * @param figure = the turn, sets who is moving the piece
     * @return = a new players array with the new movement implemented
     */
    public static String[] make_the_step (String[] players, int dimension, Scanner input, char figure) {

        if (players == null || dimension == 0 || figure == ' ' || input == null){
            throw new NullPointerException("ERROR, PARAMETERS CAN'T BE NLL");
        } else {
            try {
                String positionQuery_string = Arrays.toString(FoxHoundUI.positionQuery(dimension,input));
                //System.out.println(positionQuery_string);
                positionQuery_string = positionQuery_string.substring(1);
                positionQuery_string = positionQuery_string.replaceAll("]", "").trim();
                positionQuery_string = positionQuery_string.replaceAll(",", "").trim();
                //System.out.println(positionQuery_string);
                String[] positionQuery_array = positionQuery_string.split(" ");

                if (isValidMove(dimension,players,figure,positionQuery_array[0],positionQuery_array[1]) == true) {
                    FoxHoundUtils.new_players_position(players,positionQuery_array);
                } else {
                    make_the_step(players,dimension,input,figure);
                }
                /** CHECK IF THE FOX WINS ON THIS MOVEMENT */
                if (figure == 'F') {
                    try{
                        isFoxWin(positionQuery_array[1]);
                    } catch (NullPointerException error) {
                        System.err.println(error.getMessage());
                    }
                }
                if (figure == 'H') {
                    try {
                        isHoundWin(players, dimension);
                    } catch (NullPointerException error){
                        System.err.println(error.getMessage());
                    }

                }
            } catch (NullPointerException error ){
                System.err.println(error.getMessage());
            }

        }

        return players;
    }




    /** ____________________ TASK 5 - WINNING THE GAME ______________________*/
    /** FUNCTION TO CHECK IF THE FOX WINS */
    /**
     *
     * @param fox_position = gets the destination of the fox
     * @return = if the fox wins or not in this movement
     */
    public static boolean isFoxWin (String fox_position) {
        boolean fox_win = false;

        if (fox_position == null) {
            throw new NullPointerException("ERROR, THE PARAMETER CAN'T BE NULL");
        } else {
            if (number_coordinate(fox_position) == 1) {
                fox_win =true;
                System.out.println("The Fox wins!");

            } else {
                System.out.println("The Fox did not win yet");
            }
        }


        return fox_win;
    }
    /** FUNCTION TO CHECK IF THE HOUNDS WINS */
    /**
     *
     * @param players = takes all the positions of the pieces on the board to evaluate if the hounds win or not
     * @param dimension = takes the dimensions of the board to set the limits
     * @return
     */
    public static boolean isHoundWin(String[] players, int dimension) {
        boolean fox_win = true;
        if (dimension < 4) {
            throw new IllegalArgumentException("ERROR: THE DIMENSION IS NOT VALID");
        }

        if (players == null || dimension == 0) {
            throw new NullPointerException("ERROR: THE PLAYERS CAN NOT BE NULL");
        } else {

            fox_win = false;
            String fox_coordinates = players[players.length-1];
            char fox_letter = letter_coordinate(fox_coordinates);
            int fox_number = number_coordinate(fox_coordinates);
            String[] destination = new String[4];

            // two coordinates with letter  +1 and two coordinates with letter -1
            // numbers two numbers +1 and two numbers -1
            destination[0] = "" + (char)((int)fox_letter + 1) + (fox_number + 1); // right top corner
            destination[1] = "" + (char)((int)fox_letter + 1) + (fox_number - 1); // right bottom corner
            destination[2] = "" + (char)((int)fox_letter - 1) + (fox_number + 1); // left top corner
            destination[3] = "" + (char)((int)fox_letter - 1) + (fox_number - 1); // left bottom corner

            // Check if the fox can do a valid move in its position
            for (int i = 0; i < destination.length; i++) {
                try {
                    fox_win = (isValidMove(dimension,players,'F',fox_coordinates,destination[i])) || fox_win;
                } catch (NullPointerException error) {
                    System.err.println(error.getMessage());
                    fox_win = false;
                }

            }
            if (!fox_win) {
                System.out.println("The Hounds win!");
            } else {
                System.out.println("The Hounds do not win yet");
            }


        }

        return !fox_win;
    }

}
