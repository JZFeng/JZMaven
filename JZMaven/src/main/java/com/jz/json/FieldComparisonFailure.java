package com.jz.json;

import com.google.gson.JsonElement;
import com.jz.json.FieldComparisonFailure;
import com.jz.json.FieldComparisonFailureType;

public class FieldComparisonFailure {
    private String field; //absolute JsonPath
    private FieldComparisonFailureType failureType; //enum
    private JsonElement expected;
    private JsonElement actual;

    FieldComparisonFailure(String field, FieldComparisonFailureType failureType, JsonElement expected, JsonElement actual) {
        this.field = field;
        this.failureType = failureType;
        this.expected = expected;
        this.actual = actual;
    }

    FieldComparisonFailure(String field, FieldComparisonFailureType failureType) {
        this.field = field;
        this.failureType = failureType;
        this.expected = null;
        this.actual = null;
    }

    FieldComparisonFailure() {
        this.field = null;
        this.failureType = null;
        this.expected = null;
        this.actual = null;
    }

    public String getField() {
        return field;
    }

    public FieldComparisonFailureType getFailureType() {
        return failureType;
    }

    public JsonElement getExpected() {
        return expected;
    }

    public JsonElement getActual() {
        return actual;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setFailureType(FieldComparisonFailureType failureType) {
        this.failureType = failureType;
    }

    public void setExpected(JsonElement expected) {
        this.expected = expected;
    }

    public void setActual(JsonElement actual) {
        this.actual = actual;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FIELD : " + field + "\r\n");
//        sb.append("FAILURE REASON : " + getFailureReason(failureType) + "\r\n");
        sb.append("EXPECTED : " + getExpected() + "\r\n");
        sb.append("ACTUAL : " + getActual() + "\r\n");

        return sb.toString().trim();
    }

    public static String getFailureReason(FieldComparisonFailureType failureType) {
        String failureReason = "";
        switch (failureType) {
            case UNEQUAL_VALUE: {
                failureReason = "FieldValueNotIdentical";
                break;
            }
            case MISSING_FIELD: {
                failureReason = "MissingFieldFrom2ndJsonObject";
                break;
            }
            case UNEXPECTED_FIELD: {
                failureReason = "UnexpectedFieldFrom2ndJsonObject";
                break;
            }
            case DIFFERENT_JSONARRY_SIZE: {
                failureReason = "DifferentJsonArraySize";
                break;
            }
            default: {
                failureReason = "UnknownReason";
                break;
            }
        }

        return failureReason;
    }
}
