/**
 * A utility class for the fox hound program.
 * 
 * It contains helper functions for all file input / output
 * related operations such as saving and loading a game.
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;


public class FoxHoundIO {

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


  public static boolean saveGame (String[] players, char figure, Path path) {
    boolean save = true;
    FileWriter flwritter = null;

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
    }

    return save;
  }


}
