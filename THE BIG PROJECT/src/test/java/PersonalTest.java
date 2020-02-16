import org.junit.Test;
import org.junit.Test.*;
import static org.junit.Assert.*;

public class PersonalTest {

  @Test
  public void testisValidMove() {
    int dimension = 8;
    String[] players = null;
    char figure = ' ';
    String origin = null;
    String destination = null;
    boolean expectedOutput = false;

    assertEquals(false, FoxHoundUtils.isValidMove(0, null,figure, null, null));
  }


}
