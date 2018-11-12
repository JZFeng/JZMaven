import com.jz.collection.StackDemo;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.jz.collection.StackDemo.evaluatePostfix;
import static com.jz.collection.StackDemo.infixtoPostfix;

public class TestInfixToPostfix {

    @Test
    public void testInfixToPostfix()  {
        String[] infixs = new String[]{
                " 101 + 23 *  ( 19 - 5) ",
                "16 / 4 - 3",
                "(16 / 4 - 3)",
                "3*5 - 22 + (16 / 4 - 3)",
                "(213 - 55 * (25 - 8) / ( 2 + 3) + 18 ) - 2"
        };

        String[] expectedPostfixs = new String[]{
                "101 23 19 5 - * +",
                "16 4 / 3 -",
                "16 4 / 3 -",
                "3 5 * 22 - 16 4 / 3 - +",
                "213 55 25 8 - * 2 3 + / - 18 + 2 -"
        };

        int[] expectedNums = new int[]{
                423,
                1,
                1,
                -6,
                42
        };

       for(int i = 0 ; i < infixs.length; i++) {
            String actual  = infixtoPostfix(infixs[i]);
            Assert.assertTrue(expectedPostfixs[i].equals(actual), "\r\n  Actual:" + actual + " \r\nExpected:" + expectedPostfixs[i] + "\r\n");
        }

        for(int i = 0 ; i < expectedPostfixs.length; i++) {
            Assert.assertTrue(expectedNums[i] == evaluatePostfix(expectedPostfixs[i]));
        }

    }

}
