package com.jz.collection;

import java.util.*;

//prefix/infix/postfix
//Infix to postfix
public class StackDemo {

    public static void main(String[] args) {


        String infix = "101 + 23 *  ( 19 - 5) ";
        int result = 101 + 23 * (19 - 5);
        System.out.println("Infix is : " + infix);
        System.out.println("Expected Result : " + result + "\r\n");

        String postfix = infixtoPostfix(infix);
        System.out.println("Postfix is : " + postfix);
        System.out.println("Actual Result : " + evaluatePostfix("23 19 5 - *"));

    }

    private static final Set<Character> chars = new HashSet<>(Arrays.asList('+', '-', '*', '/', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '(', ')'));
    private static final Set<Character> digits = new HashSet<>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));
    private static final Set<Character> operators = new HashSet<>(Arrays.asList('+', '-', '*', '/'));


    //stack overflow method
    private static int increase(int num) {
        return increase(num) + 1;
    }


    //infix to postfix, using stack to store operators
    public static String infixtoPostfix(String infix) {
        if (infix == null || infix.length() == 0 || !isValidInfix(infix)) {
            return "";
        }


        infix = removeSpace(infix);
        int length = infix.length();

        StringBuilder sb = new StringBuilder();

        //stack to store operators
        Deque<Character> stack = new LinkedList<>();

        //define operator priority;
        Map<Character, Integer> priorities = new HashMap<>();
        priorities.put('+', 1);
        priorities.put('-', 1);
        priorities.put('*', 2);
        priorities.put('/', 2);

        // iterate the string
        for (int i = 0; i < length; i++) {

            char c = infix.charAt(i);

            //convert rules to implementation;
            if (digits.contains(c)) { //output digits
                sb.append(c);
                if (i + 1 != length && operators.contains(infix.charAt(i + 1))) {
                    sb.append(" ");  //if the next character is operator, appending SPACE
                }
            } else if (c == '(') {
                stack.push(c);   // if it's '(' (highest priority), push into stack;
            } else if (c == ')') { // if it's ')', pop and output all the operators down to '(';
                while (!stack.isEmpty() && !stack.peek().equals('(')) {
                    sb.append(" " + stack.pop() + " ");
                }
                if (!stack.isEmpty()) {
                    stack.pop(); // pop '(' as a pair of parenthesis but not output left parenthesis;
                }
            } else {
                while (!stack.isEmpty() && !stack.peek().equals(('(')) && priorities.get(c) <= priorities.get(stack.peek())) {
                    // if top element is NOT '(' , plus the top element's priority is higher, pop and output the stack element.
                    sb.append(stack.pop() + " ");
                }

                stack.push(c);
            }

        }

        //when string comes to the end, output the whole stack;
        while (!stack.isEmpty()) {
            sb.append((sb.charAt(sb.length() - 1) == ' ') ? (stack.pop()) : (" " + stack.pop()));
        }

        return sb.toString().trim();
    }


    //evaluating postfix by using stack to store operands;
//    101 23 19 5 - * +
    public static int evaluatePostfix(String postfix) {
        if (postfix == null || postfix.length() == 0) {
            System.out.println("invalid postfix string.");
        }

        postfix = postfix.trim();
        String[] strs = postfix.split(" ");

        Deque<Integer> stack = new LinkedList<>();

        for (String str : strs) {
            if (str != null && digits.contains(str.charAt(0))) {
                stack.push(Integer.valueOf(str));
            } else {
                if (stack.size() >= 2) {
                    int right = stack.pop();
                    int left = stack.pop();
                    char operator = str.charAt(0);
                    stack.push(operate(operator, left, right));
                }
            }
        }

        return stack.pop();
    }

    private static String removeSpace(String str) {
        str = str.trim();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c != ' ') {
                sb.append(c);
            }
        }

        return sb.toString();
    }


    private static boolean isValidInfix(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }

        str = str.trim();

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == ' ') {
                continue;
            }
            if (!chars.contains(c)) {
                return false;
            }
        }

        return true;
    }

    private static int operate(char operator, int left, int right) {
        int result = 0;
        switch (operator) {
            case '+':
                result = left + right;
                break;
            case '-':
                result = left - right;
                break;
            case '*':
                result = left * right;
                break;
            case '/':
                result = left / right;
                break;
            default:
                System.out.println("Warning: Operator '" + operator + "' is NOT supported.");
        }

        return result;
    }

    //Only keep the last SPACE;
    private static String removeExcessiveSpace(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        str = str.trim();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < str.length() - 1; i++) {
            char c = str.charAt(i);
            if (c == ' ' && str.charAt(i + 1) == ' ') {
                continue;
            } else {
                sb.append(c);
            }
        }

        sb.append(str.charAt(str.length() - 1));


        return sb.toString().trim();

    }

}
