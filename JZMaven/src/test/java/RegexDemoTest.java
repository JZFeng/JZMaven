import static org.testng.Assert.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.jz.regex.RegexDemo.*;

import static com.jz.regex.RegexDemo.isValidId;
import static com.jz.regex.RegexDemo.isValidQQ;

public class RegexDemoTest {
    @Test
    public static void testIsValidId() {
        Assert.assertFalse(isValidId(null));
        Assert.assertFalse(isValidId(""));
        Assert.assertFalse(isValidId("012345678901234567"));
        Assert.assertFalse(isValidId("01234567890123456"));
        Assert.assertTrue(isValidId("11234567890123456X"));
        Assert.assertTrue(isValidId("212345678901234567"));
    }

    @Test
    public static void testIsValidQQ() {

        Assert.assertTrue(isValidQQ("442072482"));
        Assert.assertTrue(isValidQQ("999999999"));
        Assert.assertTrue(isValidQQ("12345"));
        Assert.assertTrue(isValidQQ("1234567890"));
        Assert.assertFalse(isValidQQ(null));
        Assert.assertFalse(isValidQQ(""));
        Assert.assertFalse(isValidQQ("#123455"));
        Assert.assertFalse(isValidQQ("  12345"));
        Assert.assertFalse(isValidQQ("A012345"));
        Assert.assertFalse(isValidQQ("0123456"));
        Assert.assertFalse(isValidQQ("4420"));
        Assert.assertFalse(isValidQQ("12345678900"));
    }
}
