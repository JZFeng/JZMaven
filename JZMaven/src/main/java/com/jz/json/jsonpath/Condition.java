package com.jz.json.jsonpath;

import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String path = $.store.book[?(@.author=="Evelyn Waugh" && @.price > 12 || @.category == "reference")]
 */
public class Condition implements Filter {
    private String left;
    private String operator;
    private String right;
    private static final Set<String> RELATIONAL_OPERATORS = Sets.newHashSet("<", ">", "<=", ">=", "==", "!=", "=~", "in", "nin", "subsetof", "size", "empty", "notempty");
    private static final Set<String> LOGICAL_OPERATORS = Sets.newHashSet("&&", "||");

    public boolean isValid() {
        if (left == null || left.length() == 0) {
            return false;
        } else if (operator == null || operator.length() == 0) {
            return false;
        } else if (!RELATIONAL_OPERATORS.contains(operator)) {
            return false;
        }

        return true;
    }

    //    Binary operator
    Condition(String left, String operator, String right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    //Unary operator
    Condition(String left, String operator) {
        this.left = left;
        this.operator = operator;
    }

    public String getLeft() {
        return left;
    }

    public String getOperator(){
        return operator;
    }

    public String getRight(){
        return right;
    }

    /**
     * Now only support "&&", "||" between conditions
     *
     * @param r sample parameter: "@.category == 'fiction' && @.price < 10 || @.color == \"red\" || @.name size 10";
     * @return sample return: [&&, ||, ||]
     */
    public static List<String> getOperatorsBWConditions(String r) {
        r = r.trim();
        List<String> operatorsBWConditions = new ArrayList<>();
        if (r == null || r.length() == 0 || !r.contains("@.")) {
            return operatorsBWConditions;
        }

        String[] strs = r.split("\\s{0,}&&\\s{0,}|\\s{0,}\\|\\|\\s{0,}");
        List<String> operators = new ArrayList<>();
        for (int i = 0; i < strs.length - 1; i++) {
            String operator = r.substring(r.indexOf(strs[i]) + strs[i].length(), r.indexOf(strs[i + 1])).trim();
            if (LOGICAL_OPERATORS.contains(operator)) {
                operatorsBWConditions.add(operator.trim());
            }
        }

        return operatorsBWConditions;
    }

    /**
     * @param r sample parameter:  r = "?(@.author=="Evelyn Waugh" && @.price > 12 || @.category == "reference")"
     * @return
     */
    public static List<Condition> getConditions(String r) {
        List<Condition> conditions = new ArrayList<>();
        if (r == null || r.trim().length() == 0 || !r.contains("@.")) {
            return conditions;
        }
        if (r.trim().startsWith("?")) {
            r = r.trim().replaceFirst("(.*\\()((.*))(\\).*)", "$2").trim();
        }

        String[] strs = r.split("\\s{0,}&&\\s{0,}|\\s{0,}\\|\\|\\s{0,}");
        for (String str : strs) {
            conditions.add(getCondition(str));
        }

        return conditions;
    }

    /**
     * @param str sample str like "price < 10" "name size 10"
     * @return instance of Condition
     */
    private static Condition getCondition(String str) {
        str = str.trim();
        Condition condition = null;
        if (str == null || str.length() == 0) {
            return condition;
        }
        if (str.matches("(\\?{0,1}\\s{0,}\\({0,1}@{0,1}\\.{0,1})(.*)")) {
            str = str.replaceFirst("(\\?\\({0,}@\\.{0,})(.*)", "$2");
        }
        if (str.startsWith("@")) {
            str = str.substring(2);
        }


        List<String> fields = new ArrayList<>();
        String regExp = "(\\s{0,}[><=!]{1}[=~]{0,1}\\s{0,})";

        if (str.matches(".*" + regExp + ".*")) {
            Pattern pattern = Pattern.compile(regExp);
            Matcher m = pattern.matcher(str);
            while (m.find()) {
                String[] items = str.split("\\s{0,}[><=!]{1}[=~]{0,1}\\s{0,}");
                condition = new Condition(items[0].trim(), m.group(1).trim(), items[1].trim());
            }
        } else if (str.length() > 5 && str.indexOf(" ") != -1) {
            // handle cases like "in", "nin" etc
            String[] items = str.split(" {1,}");
            if (items.length == 2) {
                condition = new Condition(items[0].trim(), items[1].trim());
            } else if (items.length == 3) {
                condition = new Condition(items[0].trim(), items[1].trim(), items[2].trim());
            }

        }

        return (condition.isValid()) ? condition : null;

    }

    @Override
    public String toString() {
        return left + " " + operator + " " + (right == null ? "" : right);
    }


}
