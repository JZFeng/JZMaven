package com.jz.json;

import java.util.*;

public class JsonCompareResult {
    private List<FieldFailure> fieldFailures = new LinkedList<FieldFailure>();

    public  boolean isPassed() {
        return (fieldFailures.size() == 0) ;
    }

    JsonCompareResult() {

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

    private String getResultInfo(boolean withDetails) {
        StringBuilder sb = new StringBuilder();
        sb.append("Total " + fieldFailures.size() + " failures : ");

        Map<FieldFailureType, List<FieldFailure>> map = new HashMap<FieldFailureType, List<FieldFailure>>();
        for(FieldFailure f : fieldFailures) {
            FieldFailureType type = f.getFailureType();
            if(!map.containsKey(type)) {
                map.put(type, new ArrayList<FieldFailure>());
            }
            map.get(type).add(f);
        }

        for(Map.Entry<FieldFailureType, List<FieldFailure>> entry : map.entrySet()) {
            FieldFailureType type = entry.getKey();
            List<FieldFailure> failures = entry.getValue();
            String failureReason = FieldFailure.getFailureReason(type);
            int numOfFailures = failures.size();
            sb.append("\n\r" + "****************************************************\n\r");
            sb.append("[Failure Reason = " + failureReason + "; "+ "Number of Failures:" + numOfFailures + "]\r\n");
            for(FieldFailure f : failures) {
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
