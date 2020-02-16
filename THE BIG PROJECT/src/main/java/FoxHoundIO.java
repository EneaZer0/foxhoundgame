/**
 * A utility class for the fox hound program.
 * 
 * It contains helper functions for all file input / output
 * related operations such as saving and loading a game.
 */

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Arrays;

public class FoxHoundIO {

  /** ____________________ TASK 6 - SAVING THE GAME ______________________*/
  // ASK BEFORE AND ENTER THE FILES NAME IN PATH WHEN CALLING THE FUNCTION
  /** FUNCTION TO SAVE THE GAME */
  /**
   *
   * @param players takes players to save them in order
   * @param figure = the figure of the actual move the save the figure of the next move
   * @param path = the path where the user want to save the game / name of the file
   * @return = if the save has been successful
   */
  public static boolean saveGame (String[] players, char figure, Path path) {
    boolean save = false;
    if (players == null || path == null) {
      throw new NullPointerException("ERROR: Players can't be null");
    }
    if (players.length != 5) {
      throw new IllegalArgumentException("ERROR: Dimensions must be 8");
    }
    if (Files.exists(path)) {
      System.err.println("ERROR, THE FILE ALREADY EXISTS");

    } else {
      save = true;

      FileWriter writing = null;
      try {
        writing = new FileWriter(String.valueOf(path));
        writing.write(figure);

        for (int i = 0; i < players.length; i++) {
          writing.write(" " + players[i]);
        }
        System.out.println("The file has been created...");
        writing.close();

      } catch (IOException e) {
        save = false;
        System.err.println(e.getMessage());
        e.printStackTrace();
      }
    }
    return save;
  }
  /** ____________________ TASK 6 - LOADING THE GAME ______________________*/
  /**
   *
   * @param players get the initial players to change them to the loaded ones
   * @param path = the file we are loading
   * @return = the character of the next player move
   */
  public static char loadGame (String[] players, Path path) {
    char figure = ' ';
    if (path == null || players == null) {
      throw new NullPointerException("ERROR, PATH CAN NOT BE NULL");
    } else if (players.length != 5) {
      throw new IllegalArgumentException("ERROR, PLAYERS MUST BE VALID FOR A 8x8 DIMENSION");
    }
    try {
      if (Files.exists(path)) {
        Scanner reader = new Scanner(path);
        String[] players_copy = new String[players.length];
        String line = reader.nextLine();
        boolean valid = false;
        String[] saved_txt = line.split(" ");
        if ((saved_txt.length > 5) && ((saved_txt[0].equals("F")) || (saved_txt[0].equals("H")))){
          valid = true;
        } else {
          System.err.println("ERROR, THE FILE YOU WANT TO LOAD IS NOT A GAME");
          valid = false;
          figure = '#';
        }

        if (valid) {
          if (saved_txt[0].length() != 1){
            figure = '#';
          } else {
            figure = (saved_txt[0]).charAt(0);
          }
          for (int i = 0; i < players.length; i++) {

            if (FoxHoundUtils.coordinate_checker(8, saved_txt[i + 1])){
              players_copy[i] = saved_txt[i+1];
            } else {
              figure = '#';
              valid = false;
              break;
            }
          }
        }
        if (valid) {
          for (int i = 0; i < players.length; i ++) {
            players[i] = players_copy[i];
          }
        }
        reader.close();
      } else {
        throw new IOException("ERROR, THE FILE DOES NOT EXIST");
      }
    } catch (IOException error) {
      System.err.println(error.getMessage());
      figure = '#';
    }

    // System.out.println(Arrays.toString(players));
    return figure;
  }


}
