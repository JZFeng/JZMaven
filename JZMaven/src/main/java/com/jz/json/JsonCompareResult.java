package com.jz.json;

import java.util.*;

public class JsonCompareResult {
    private List<FieldComparisonFailure> fieldFailures = new LinkedList<FieldComparisonFailure>();

    public  boolean isEqual() {
        return (fieldFailures.size() == 0) ;
    }

    JsonCompareResult() {

    }

    JsonCompareResult(List<FieldComparisonFailure> fieldFailures) {
        this.fieldFailures.addAll(fieldFailures);
    }

    public boolean addFieldComparisonFailure(FieldComparisonFailure failure) {
        return this.fieldFailures.add(failure);
    }

    @Override
    public String toString() {
        return getResultInfo(false);
    }

    public String getResultDetails() {
        return getResultInfo(true);
    }

    public List<FieldComparisonFailure> getFieldFailures() {
        return fieldFailures;
    }

    private String getResultInfo(boolean withDetails) {
        StringBuilder sb = new StringBuilder();
        sb.append("Total " + fieldFailures.size() + " failures : ");

        Map<FieldComparisonFailureType, List<FieldComparisonFailure>> map = new HashMap<FieldComparisonFailureType, List<FieldComparisonFailure>>();
        for(FieldComparisonFailure f : fieldFailures) {
            FieldComparisonFailureType type = f.getFailureType();
            if(!map.containsKey(type)) {
                map.put(type, new ArrayList<FieldComparisonFailure>());
            }
            map.get(type).add(f);
        }

        for(Map.Entry<FieldComparisonFailureType, List<FieldComparisonFailure>> entry : map.entrySet()) {
            FieldComparisonFailureType type = entry.getKey();
            List<FieldComparisonFailure> failures = entry.getValue();
            String failureReason = FieldComparisonFailure.getFailureReason(type);
            int numOfFailures = failures.size();
            sb.append("\n\r" + "****************************************************\n\r");
            sb.append("[Failure Reason = " + failureReason + "; "+ "Number of Failures:" + numOfFailures + "]\r\n");
            for(FieldComparisonFailure f : failures) {
                if(withDetails) {
                    sb.append(f.toString());
                } else {
                    sb.append(f.getField());
                }
                sb.append("\r\n");
            }
        }

        return sb.toString().trim();
    }



}
