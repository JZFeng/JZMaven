package com.jz.json;

import java.lang.reflect.Field;
import java.util.*;

public class JsonCompareResult {
    private List<FieldFailure> fieldFailures = new ArrayList<FieldFailure>();
    private Filter filter = null;

    public boolean isPassed() {
        return (fieldFailures.size() == 0);
    }

    JsonCompareResult() {

    }

    public JsonCompareResult(Filter filter) {
        this.filter = filter;
    }

    JsonCompareResult(List<FieldFailure> fieldFailures) {
        this.fieldFailures.addAll(fieldFailures);
    }

    public boolean addFieldComparisonFailure(FieldFailure failure) {
        return this.fieldFailures.add(failure);
    }

    @Override
    public String toString() {
        return getResultInfo(false);
    }

    public String getResultDetails() {
        return getResultInfo(true);
    }

    public List<FieldFailure> getFieldFailures() {
        return fieldFailures;
    }

    public int totalFailures() {
        return fieldFailures.size();
    }


    public List<FieldFailure> applyFilter(Filter filter) {
        List<FieldFailure> result = new ArrayList<>();

        Map<FieldFailureType, List<FieldFailure>> map = getFieldFailureTypeListMap(fieldFailures);
        List<FieldFailureType> typesToIgnore = filter.types;
        List<String> fieldsToIgnore = filter.fields;

        //apply types
        for(FieldFailureType type : typesToIgnore) {
            if(map.containsKey(type)) {
                map.remove(type);
            }
        }

        //apply fields
        for(String field : fieldsToIgnore) {

        }




        return result;
    }

    private String getResultInfo(boolean withDetails) {
        StringBuilder sb = new StringBuilder();
        sb.append("Total " + fieldFailures.size() + " failures : ");

        Map<FieldFailureType, List<FieldFailure>> map = getFieldFailureTypeListMap(fieldFailures);

        for (Map.Entry<FieldFailureType, List<FieldFailure>> entry : map.entrySet()) {
            FieldFailureType type = entry.getKey();
            List<FieldFailure> failures = entry.getValue();
            int numOfFailures = failures.size();
            sb.append("\n\r" + "****************************************************\n\r");
            sb.append("[Failure Reason = " + type + "; " + "Number of Failures:" + numOfFailures + "]\r\n");
            for (FieldFailure f : failures) {
                if (withDetails) {
                    sb.append(f.toString());
                } else {
                    sb.append(f.getField());
                }
                sb.append("\r\n");
            }
        }

        return sb.toString().trim();
    }

    private Map<FieldFailureType, List<FieldFailure>> getFieldFailureTypeListMap(List<FieldFailure> failures ) {
        Map<FieldFailureType, List<FieldFailure>> map = new HashMap<FieldFailureType, List<FieldFailure>>();
        for (FieldFailure f : failures) {
            FieldFailureType type = f.getFailureType();
            if (!map.containsKey(type)) {
                map.put(type, new ArrayList<FieldFailure>());
            }
            map.get(type).add(f);
        }
        return map;
    }


}
