package com.jz.json;

import java.util.ArrayList;
import java.util.List;

public class JsonCompareResult {
    private boolean isEqual;
    private List<FieldComparisonFailure> fieldFailures = new ArrayList<FieldComparisonFailure>();

    public  boolean isEqual() {
        return (fieldFailures.size() == 0) ;
    }

}
