package com.jz.json;

import com.google.gson.JsonElement;

public class FieldComparisonFailure {
    private String field; //absolute JsonPath
    private JsonElement expected;
    private JsonElement actual;
    private String failureReason; //can use enum;
}
