package com.jz.json;

import java.util.*;

public class JsonCompareResult {
    private JsonCompareMode mode ;
    private List<FieldFailure> fieldFailures = new ArrayList<FieldFailure>();

    public boolean isPassed() {
        return (fieldFailures.size() == 0);
    }

    JsonCompareResult() {

    }

    JsonCompareResult(JsonCompareMode mode) {
        this.mode = mode;
    }

    JsonCompareResult(JsonCompareMode mode, List<FieldFailure> failures) {
        this.mode = mode;
        this.fieldFailures.addAll(failures);
    }

    public static void main(String[] args) {
    }


    public void setMode(JsonCompareMode mode) {
        this.mode = mode;
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


    public JsonCompareResult applyFilter(Filter filter) throws Exception {
        //????????LinkedList???????,????????List<>?????????
        List<FieldFailure> result = new LinkedList<>();

        Map<FieldFailureType, List<FieldFailure>> map = getFieldFailureTypeListMap(fieldFailures);

        if(mode != null && isValidFilter(filter)) {

            List<FieldFailureType> typesToIgnore = filter.types;
            List<String> fieldsToIgnore = filter.fields;

            //apply types
            for(FieldFailureType type : typesToIgnore) {
                if(map.containsKey(type)) {
                    map.remove(type);
                }
            }

            //apply fields
            for(Map.Entry<FieldFailureType, List<FieldFailure>> entry : map.entrySet()) {
                result.addAll(entry.getValue());
            }

            for(String field : fieldsToIgnore) {
                String regex = generateRegex(field);

                Iterator<FieldFailure> itr = result.iterator();
                while(itr.hasNext()) {
                    FieldFailure failure = itr.next();
                    if(failure.getField().matches(regex)) {
                        itr.remove();
                    }
                }
            }
        }

        return new JsonCompareResult(mode, result );
    }

    private String generateRegex(String field) {
        if(field.startsWith("$")) {
            field = "\\$" + field.substring(1);
        }

        String regex = "(.*)(" + (field.replaceAll("(\\[)(\\d{0,})(\\])", "\\\\" + "$1" + "$2" + "\\\\" + "$3" )) + ")(.*)" ;

        return regex;
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

    private boolean isValidFilter(Filter filter) throws Exception {
        Map<FieldFailureType, List<FieldFailure>> map = getFieldFailureTypeListMap(fieldFailures);

        //validate type
        Set<FieldFailureType> set = new HashSet<>();
        for (FieldFailureType type : FieldFailureType.values()) {
            set.add(type);
        }
        for (FieldFailureType type : filter.types) {
            if (!set.contains(type)) {
                throw new Exception("\"" + type + "\"" + "is not a valid Failure type.");
            }
        }

        //validate fields
        for (String field : filter.fields) {
            if (!isValidField(field, mode)) {
                throw new Exception(field + " is not a valid filter" );
            }
        }

        return true;
    }


//    private boolean isValidField(String field, JsonCompareMode mode) {
    private static boolean isValidField(String field, JsonCompareMode mode) {
        if(mode == JsonCompareMode.LENIENT) {
            int index = field.indexOf("[");
            while (index != -1) {
                char c = field.charAt(index + 1);
                if (c != ']') {
                    System.out.println("In " + mode + " mode, " + field + " should have not number between [].");
                    return false;
                }
                field = field.substring(field.indexOf("]") + 1);
                index = field.indexOf("[");
            }
        } else if (mode == JsonCompareMode.STRICT) {
            int index = field.indexOf("[");
            while (index != -1) {
                char c = field.charAt(index + 1);
                if (c == ']') {
                    System.out.println("In " + mode + " mode, " + field + " should have number between [].");
                    return false;
                }
                field = field.substring(field.indexOf("]") + 1);
                index = field.indexOf("[");
            }
        }

        return true;
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
