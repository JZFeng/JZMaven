import org.testng.Assert;
import org.testng.annotations.Test;
import com.jz.regex.RegexDemo;

import static com.jz.regex.RegexDemo.*;


public class TestisValidQQ {
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
