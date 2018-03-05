package com.jz.json;

import com.google.gson.JsonElement;

public class FieldFailure {
    private String field; //absolute JsonPath
    private FieldFailureType failureType; //enum
    private JsonElement expected;
    private JsonElement actual;

    FieldFailure(String field, FieldFailureType failureType, JsonElement expected, JsonElement actual) {
        this.field = field;
        this.failureType = failureType;
        this.expected = expected;
        this.actual = actual;
    }

    FieldFailure(String field, FieldFailureType failureType) {
        this.field = field;
        this.failureType = failureType;
        this.expected = null;
        this.actual = null;
    }

    FieldFailure() {
        this.field = null;
        this.failureType = null;
        this.expected = null;
        this.actual = null;
    }

    public String getField() {
        return field;
    }

    public FieldFailureType getFailureType() {
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

    public void setFailureType(FieldFailureType failureType) {
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

    public static String getFailureReason(FieldFailureType failureType) {
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
            case MISSING_JSONARRAY_ELEMENT: {
                failureReason = "MissingJsonArrayElement";
                break;
            }
            case UNEXPECTED_JSONARRAY_ELEMENT: {
                failureReason = "UnexpectedJsonArrayElement";
                break;
            }
            case DIFFERENT_OCCURENCE_JSONARRAY_ELEMENT: {
                failureReason = "DifferentOccurenceOfAJsonArrayElement";
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
