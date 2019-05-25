
import com.jz.java.regex.Tel;
import org.testng.annotations.Test;
import java.time.LocalTime;
import static org.testng.Assert.*;


public class TelTest {

    @Test
    public static void testParseTelephoneNumber() {

        System.out.println(LocalTime.now());

        assertEquals(new Tel("010", "123456"), Tel.parse("010-123456"));
        assertEquals(new Tel("0123", "12345678"), Tel.parse("0123-12345678"));

        assertNull(Tel.parse("123-12345678"));
        assertNull(Tel.parse("010-0123456"));
        assertNull(Tel.parse("010#12345678"));
        assertTrue("12:23:46".matches("^[0-2]{0,1}[0-9]:[0-6]{0,1}[0-9]:[0-6]{0,1}[0-9]$"));
        assertTrue("01:03:06".matches("^[0-2]{0,1}[0-9]:[0-6]{0,1}[0-9]:[0-6]{0,1}[0-9]$"));
        assertTrue("1:3:16".matches("^[0-2]{0,1}[0-9]:[0-6]{0,1}[0-9]:[0-6]{0,1}[0-9]$"));
        assertTrue("1:03:16".matches("^[0-2]{0,1}[0-9]:[0-6]{0,1}[0-9]:[0-6]{0,1}[0-9]$"));
    }

}
