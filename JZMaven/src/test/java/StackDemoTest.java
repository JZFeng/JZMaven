import com.jz.collection.StackDemo;
import org.testng.annotations.*;

import static org.testng.Assert.*;

public class StackDemoTest {


    StackDemo stackDemo;
    static String BEFORE_CLASS = "BEFORE_CLASS";
    static String AFTER_CLASS = "AFTER_CLASS";

    @BeforeClass
    public static void setUpBeforeClass() {
        System.out.println(BEFORE_CLASS);
    }

    @AfterClass
    public static void tearDownAfterClass() {
        System.out.println(AFTER_CLASS);
    }

    @BeforeMethod
    public void setUp() throws Exception {
        stackDemo = new StackDemo();
        System.out.println("                   setUp()");
    }

    @AfterMethod
    public void tearDown() throws Exception {
        System.out.println("                tearDown()");
    }

    @Test
    public void testInfixtoPostfixNoBracket() throws Exception {
        System.out.println("testInfixtoPostfixNoBracket");
        String infix = "16 / 4 - 3";
        String expected = "16 4 / 3 -";
        int result = 1;
        assertEquals(stackDemo.infixtoPostfix(infix), expected);
        assertEquals(stackDemo.evaluatePostfix(stackDemo.infixtoPostfix(infix)), result);
    }

    @Test
    public void testInfixtoPostfixWithBracket() throws Exception {
        System.out.println("testInfixtoPostfixWithBracket");
        String infix = "(16 / 4 - 3)";
        String expected = "16 4 / 3 -";
        int result = 1;
        assertEquals(stackDemo.infixtoPostfix(infix), expected);
        assertEquals(stackDemo.evaluatePostfix(stackDemo.infixtoPostfix(infix)), result);
    }

    @Test
    public void testInfixtoPostfixWithMultipleSpace() throws Exception {
        System.out.println("testInfixtoPostfixWithMultipleSpace");
        String infix = " 101   + 23 *  ( 19 - 5)  ";
        String expected = "101 23 19 5 - * +";
        int result = 423;
        assertEquals(stackDemo.infixtoPostfix(infix), expected);
        assertEquals(stackDemo.evaluatePostfix(stackDemo.infixtoPostfix(infix)), result);
    }


    @Test
    public void testInfixtoPostfixWithoutSpace() throws Exception {
        System.out.println("testInfixtoPostfixWithoutSpace");
        String infix = "3*5-22+(16/4-3)";
        String expected = "3 5 * 22 - 16 4 / 3 - +";
        int result = -6;
        assertEquals(stackDemo.infixtoPostfix(infix), expected);
        assertEquals(stackDemo.evaluatePostfix(stackDemo.infixtoPostfix(infix)), result);
    }


    @Test(timeOut = 2)
    public void testInfixtoPostfixWithMultipleBracket() throws Exception {
        System.out.println("testInfixtoPostfixWithMultipleBracket");
        String infix = "(213 - 55 * (25 - 8) / ( 2 + 3) + 18 ) - 2";
        String expected = "213 55 25 8 - * 2 3 + / - 18 + 2 -";
        int result = 42;
        assertEquals(stackDemo.infixtoPostfix(infix), expected);
        assertEquals(stackDemo.evaluatePostfix(stackDemo.infixtoPostfix(infix)), result);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class, NullPointerException.class})
    private void testInfixtoPostfixNull() {
        stackDemo.infixtoPostfix(null);
    }


    //User data provider;
    @DataProvider(name = "infixToPostfixtestData")
    public static Object[][] infixToPostfixData(){
        return new Object[][]{{"(213 - 55 * (25 - 8) / ( 2 + 3) + 18 ) - 2","213 55 25 8 - * 2 3 + / - 18 + 2 -"},{"3*5-22+(16/4-3)","3 5 * 22 - 16 4 / 3 - +"},{" 101   + 23 *  ( 19 - 5)  ","101 23 19 5 - * +"}};
    }

    @Test(dataProvider = "infixToPostfixtestData", enabled = true, groups = {"Regression"})
    public void testInfixToPostfix(String infix, String postfix) {
        assertTrue(stackDemo.infixtoPostfix(infix).equals(postfix));
    }

}