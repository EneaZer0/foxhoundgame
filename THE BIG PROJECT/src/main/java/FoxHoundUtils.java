import java.util.Arrays;

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

    /** Margins for NUMBERS */
    public  static final String NUMBERS_MARGIN = " ";
    /** Set of the MARGIN OF ALPHABET*/

    public static String alphabet_margin (int dimension){
        String alphabet_margin = "";

        if (dimension < 10) {
            alphabet_margin = "  ";
        } else {
            alphabet_margin = "    ";
        }

        return alphabet_margin;
    }


    private static String[] letter_recognition;
    private static String[] number_recognition;
    private static int dimension;
    private static int row;

    // HINT Write your own constants here to improve code readability ...

    //public static String positionCalculator(int dimension, String previous_position) {    }
    // Next_position must be implemented in the future

    /** FUNCTION TO SET THE DIMENSION OF ALL THE GAME*/
    public static int dimension_check (int dimension) {

        /** First, check if the dimension we get is valid
         * if it is smaller than the MIN_DIM or bigger than the
         * MAX_DIM, it must be changed to de DEFAULT_DIM        */
        if ((dimension < MIN_DIM) || (dimension > MAX_DIM)) {
            dimension = DEFAULT_DIM;
        }

        return dimension;
    }

    /** FUNCTION TO RECOGNISE THE LETTERS IN PLAYER */
    public static String[] letter_recognition (String[] players) {

        String[] letters_recognised = new String[players.length];

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

        //System.out.println(Arrays.toString(letters_recognised));

        return letters_recognised;
    }
    /** FUNCTION TO RECOGNISE THE NUMBERS IN PLAYER */
    public static String[] number_recognition (String[] players) {

        /**
         * The number of elements in each value depends on the length on the dimension
         *  if dimension is < than 10 then there are just 1 LETTER and 1 NUMBER
         *  if dimension is > than 10 then 2 cases:
         *      numbers < 10 will have a 0 in the front -> if the pieces move we must
         *      take that into account
         */

        String[] numbers_recognised = new String[players.length];

        /** k is a variable used to save the numbers in numbers_recognised*/
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

        //System.out.println(Arrays.toString(numbers_recognised));

        return numbers_recognised;
    }


    public static String[] initialisePositions(int dimension) {

        /** Number of pieces on the board, giving the option of possible
         * change of the number of Foxes playing */
        int number_Hound = (dimension / 2);
        int number_Fox = 1;

        /** Create the String array that returns the positions of the pieces */
        String[] position_array = new String[number_Hound + number_Fox];

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
        int fox_letter = 65 + (dimension/2); //Set the initial letter value to 'A' in ASCII + the position of Fox

        // Creates the final string value of the Fox (including letter & number)
        String fox_value = String.valueOf((char)fox_letter) + fox_num_initial_position;

        // Add at the end of the array the Fox Value
        position_array[position_array.length-1] = fox_value;

        return  position_array;
    }

    // Create a function to recognise the elements in the players array
    //public static String[][] recognise_position(int dimension, String[] players) {

    //The best option is to separate letters from numbers -> create 2 arrays and then work with rows and columns




    /** ____________________ BUILD AND DISPLAY OF GRID ______________________ */

    /** FUNCTION THAT BUILDS A LINE AS A DIAGRAM IN NUMBERS*/
    public static int[] line_diagram (String[] letter_recognition, String[] number_recognition, int dimension, int row) {

        /** First create the full array */
        int[] the_line = new int[dimension];

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

        return the_line;
    }
    /** FUNCTION TO BUILT THE GRID OF LINES - IN MATH VALUES USING LINE_DIAGRAM*/
    public static int[][] griding (String[] letter_recognition, String[] number_recognition, int dimension, int row) {

        /** Creates a 2 dimension array to store all the values of the grid*/
        int[][] grid = new int[dimension][];

        for (int i = 0; i < dimension; i++) {

            //System.out.println(Arrays.toString(line_diagram(letter_recognition, number_recognition, dimension, i + 1)));
            grid[i] = line_diagram(letter_recognition, number_recognition, dimension, i + 1);

        }

        return grid;
    }
    /** DISPLAY GRID - USES MATH VALUES OF GRID AND REPLACE THEM WITH THE FINAL GRID */
    public static void board (int[][] grid, String alphabet, String[] numbers, int dimension) {

        String letters = alphabet_margin(dimension) + alphabet;
        System.out.println("\n"+ letters + "\n");
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

    }

}
