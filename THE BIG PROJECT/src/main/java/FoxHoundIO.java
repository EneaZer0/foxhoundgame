/**
 * A utility class for the fox hound program.
 * 
 * It contains helper functions for all file input / output
 * related operations such as saving and loading a game.
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Arrays;

public class FoxHoundIO {

  /*
  public static void creator_file (String[] players, char figure) {
    FileWriter flwritter = null;
    try {
      flwritter = new FileWriter("Save.txt");
      BufferedWriter bfwritter = new BufferedWriter(flwritter);
      for (int i = 0; i < players.length; i++) {
        bfwritter.write(players[i] + " ");
      }
      bfwritter.write(figure);
      bfwritter.close();
      System.out.printf("The file has been created...");


    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (flwritter != null) {
        try {
          flwritter.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
  */

  /*FileWriter flwritter = null;

    if (!(path.equals("Saver.txt"))) {

      System.err.println("There is already a saved game");

    } else {
      try {
        flwritter = new FileWriter("Save.txt");
        BufferedWriter bfwritter = new BufferedWriter(flwritter);
        for (int i = 0; i < players.length; i++) {
          bfwritter.write(players[i] + " ");
        }
        bfwritter.write(figure);
        bfwritter.close();
        System.out.printf("The file has been created...");

      } catch (IOException e) {
        save = false;
        System.err.println("Error in the buffer");
        e.printStackTrace();
      } finally {
        if (flwritter != null) {
          try {
            flwritter.close();
          } catch (IOException e) {
            save = false;
            System.err.println("Error closing the file writer");
            e.printStackTrace();
          }
        }
      }
    }*/


  // ASK BEFORE AND ENTER THE FILES NAME IN PATH WHEN CALLING THE FUNCTION
  public static boolean saveGame (String[] players, char figure, Path path) {
    if (players == null || path == null){
      throw new NullPointerException("ERROR: Players can't be null");
    }
    if (players.length != 5) {
      throw new IllegalArgumentException("ERROR: Dimensions must be 8");
    }
    boolean save = true;
    FileWriter writing = null;
    try {
      writing = new FileWriter(String.valueOf(path));
      for (int i = 0; i < players.length; i++) {
        writing.write(players[i] + " ");
      }
      writing.write(figure);
      System.out.printf("The file has been created...");
      writing.close();

    } catch (IOException e) {
      save = false;
      System.err.println("Error in the buffer");
      e.printStackTrace();
    }

    return save;
  }

  public static boolean prepareSave (String[] players, char figure, Scanner input) throws IOException {
    System.out.println("Please enter the name of the file you want to save: ");
    String file_name = input.nextLine();
    boolean txt_included = file_name.contains(".txt");
    if (txt_included == false) {
      file_name = file_name + ".txt";
    }
    boolean saved = false;
    if (Files.exists(Paths.get(file_name))) {
      System.err.println("ERROR, THE FILE ALREADY EXISTS");
      prepareSave(players,figure,input);
    } else {
      Path path = Paths.get(file_name);
      saved = saveGame(players, figure, path);
    }
    return saved;
  }
}
