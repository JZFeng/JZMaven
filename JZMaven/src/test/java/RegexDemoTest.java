import static org.testng.Assert.*;
import org.testng.annotations.Test;
import com.jz.java.regex.RegexDemo.*;

import static com.jz.java.regex.RegexDemo.isValidId;
import static com.jz.java.regex.RegexDemo.isValidQQ;

public class RegexDemoTest {
    @Test
    public static void testIsValidId() {
        assertFalse(isValidId(null));
        assertFalse(isValidId(""));
        assertFalse(isValidId("012345678901234567"));
        assertFalse(isValidId("01234567890123456"));
        assertTrue(isValidId("11234567890123456X"));
        assertTrue(isValidId("212345678901234567"));
    }

    @Test
    public static void testIsValidQQ() {

        assertTrue(isValidQQ("442072482"));
        assertTrue(isValidQQ("999999999"));
        assertTrue(isValidQQ("12345"));
        assertTrue(isValidQQ("1234567890"));
        assertFalse(isValidQQ(null));
        assertFalse(isValidQQ(""));
        assertFalse(isValidQQ("#123455"));
        assertFalse(isValidQQ("  12345"));
        assertFalse(isValidQQ("A012345"));
        assertFalse(isValidQQ("0123456"));
        assertFalse(isValidQQ("4420"));
        assertFalse(isValidQQ("12345678900"));
    }
}
