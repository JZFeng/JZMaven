import com.jz.java.regex.ID;
import org.testng.annotations.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.testng.Assert.assertTrue;

public class IDTest {
  @Test
  public void testValidID() {
    assertTrue(ID.isValidId("110110190010109999"));
    assertTrue(ID.isValidId("99999919001010999X"));
    assertTrue(ID.isValidId("888888201111118888"));

    assertFalse(ID.isValidId(null));
    assertFalse(ID.isValidId(""));
    assertFalse(ID.isValidId("      "));
    assertFalse(ID.isValidId("9999999999999"));
    assertFalse(ID.isValidId("9999999999999999"));
    assertFalse(ID.isValidId("9999999999999999 "));
    assertFalse(ID.isValidId("A99999999999999999"));
    assertFalse(ID.isValidId("11011019001010999A"));
    assertFalse(ID.isValidId("010110190010109999"));
    assertFalse(ID.isValidId("1101101900101099999"));
  }
}
