import org.testng.Assert;
import org.testng.annotations.Test;
import com.jz.regex.RegexDemo.*;

import static com.jz.regex.RegexDemo.isValidId;

public class TestisValidId {
    @Test
    public static void testIsValidId() {
        Assert.assertFalse(isValidId(null));
        Assert.assertFalse(isValidId(""));
        Assert.assertFalse(isValidId("012345678901234567"));
        Assert.assertFalse(isValidId("01234567890123456"));
        Assert.assertTrue(isValidId("11234567890123456X"));
        Assert.assertTrue(isValidId("212345678901234567"));
    }
}
