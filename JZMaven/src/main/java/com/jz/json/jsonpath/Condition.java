package com.jz.json.jsonpath;

import com.google.common.collect.Sets;
import edu.emory.mathcs.backport.java.util.Arrays;

import java.util.HashSet;
import java.util.Set;

public class Condition {
    private String left;
    private String right;
    private String operator;
    private final Set<String> operators = Sets.newHashSet("<",">","<=",">=","==","!=","=~","in","nin","subsetof","size","empty");

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
}
