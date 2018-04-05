package com.jz.json.jsonpath;

import com.google.common.collect.Sets;
import edu.emory.mathcs.backport.java.util.Arrays;
import org.testng.collections.Lists;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Condition {
    private String left;
    private String right;
    private String operator;
    private static final Set<String> operators = Sets.newHashSet("<", ">", "<=", ">=", "==", "!=", "=~", "in", "nin", "subsetof", "size", "empty");
    private static final Set<String> OperatorsBWConditions = Sets.newHashSet("&&", "||");

    public boolean isValid() {
        if (left == null || left.length() == 0) {
            return false;
        } else if (right == null || right.length() == 0) {
            return false;
        } else if (operator == null || operator.length() == 0) {
            return false;
        } else if (!operators.contains(operator)) {
            return false;
        }

        return true;
    }

    Condition(String left, String operator, String right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    /**
     * Now only support "&&", "||" between conditions
     *
     * @param r sample parameter: "@.category == 'fiction' && @.price < 10 || @.color == \"red\" || @.name size 10";
     * @return sample return: [&&, ||, ||]
     */
    public static List<String> getOperatorsBWConditions(String r) {
        List<String> operatorsBWConditions = new ArrayList<>();
        if (r == null || r.length() == 0 || !r.contains("@.")) {
            return operatorsBWConditions;
        }

        String[] strs = r.split("\\s{0,}&&\\s{0,}|\\s{0,}\\|\\|\\s{0,}");
        List<String> operators = new ArrayList<>();
        for (int i = 0; i < strs.length - 1; i++) {
            String operator = r.substring(r.indexOf(strs[i]) + strs[i].length(), r.indexOf(strs[i + 1])).trim();
            if(OperatorsBWConditions.contains(operator)) {
                operatorsBWConditions.add(operator.trim());
            }
        }

        return operatorsBWConditions;
    }

    /**
     * @param r r = "@.category == 'fiction' && @.price < 10 || @.color == \"red\" || @.name size 10";
     * @return
     */
    public static List<Condition> getConditions(String r) {
        List<Condition> conditions = new ArrayList<>();
        if (r == null || r.length() == 0 || !r.contains("@.")) {
            return conditions;
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
    public static Condition getCondition(String str) {
        Condition condition = null;
        if (str == null || str.length() == 0) {
            return condition;
        }
        if (str.startsWith("@.")) {
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
        } else if (str.length() > 5 && (str.indexOf(" ") != str.lastIndexOf(" "))) {
            // handle cases like "in", "nin" etc
            String[] items = str.split(" {1,}");
            condition = new Condition(items[0].trim(), items[1].trim(), items[2].trim());

        }

        return (condition.isValid()) ? condition : null;

    }

    @Override
    public String toString() {
        return left + " " + operator + " " + right;
    }

    public static void main(String[] args) {
        String r = "@.category == 'fiction' && @.price < 10 || @.color == \\\"red\\\" || @.name size 10";
        List<Condition> conditions = getConditions(r);
        for (Condition c : conditions) {
            System.out.println(c);
        }

        List<String> operatorsBWConditions = getOperatorsBWConditions(r);
        for (String o : operatorsBWConditions) {
            System.out.println(o);
        }
    }

}
