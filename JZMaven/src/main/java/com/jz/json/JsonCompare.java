package com.jz.json;


/*
Open Question: JsonArray 的filter怎么做？JsonArray的某一个值不需要比。
JsonPaths:
$.store.book[?(@.author =~ /.*RESS/i )]
$.store.book[?(@.price < 10 && @.category == 'fiction')]



 */

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.google.gson.*;

import static com.jz.json.JsonCompareUtil.*;

public class JsonCompare {

    public static void main(String[] args) throws IOException, Exception {

        /*
        String field =  "$.listing.listingLifecycle.scheduledStartDate.value";
        String filter= "$.listing.listingLifecycle.scheduledStartDate.value";
        String regex = filter.replaceAll("(\\[)(\\d{0,})(\\])", "\\\\" + "$1" + "$2" + "\\\\" + "$3" );
        if(regex.startsWith("$")) {
            regex = "\\$" + regex.substring(1);

        }

        System.out.println(regex);
        System.out.println(field);
        System.out.println(field.matches(".*" + regex + ".*"));
*/

        JsonParser parser = new JsonParser();
        String json = convertFormattedJson2Raw(new File("./JZMaven/src/main/java/com/jz/json/testdata/O.json"));
        JsonObject o1 = parser.parse(json).getAsJsonObject();

        json = convertFormattedJson2Raw(new File("./JZMaven/src/main/java/com/jz/json/testdata/D.json"));
        JsonObject o2 = parser.parse(json).getAsJsonObject();


        Filter filter = new Filter(
                new String[]{},
                new String[]{"lastVisitDate", "$.listing.listingLifecycle.scheduledStartDate.value"});

        JsonCompareResult result = compareJson(o1, o2);
        result = result.applyFilter(filter);
        System.out.println(result);



    }


    public static JsonCompareMode mode = JsonCompareMode.LENIENT;
    public static JsonCompareResult jsonCompareResult = new JsonCompareResult(mode);


    public static JsonCompareResult compareJson(JsonObject o1, JsonObject o2) {
//        JsonCompareResult r = new JsonCompareResult();
        JsonCompareResult r = new JsonCompareResult(mode);
        compareJson("", (JsonElement) o1, (JsonElement) o2, r);
        return r;
    }


    private static JsonCompareResult compareJson(String parentLevel, JsonObject o1, JsonObject o2) {
        JsonCompareResult r = new JsonCompareResult(mode);
        compareJson(parentLevel, (JsonElement) o1, (JsonElement) o2, r);
        return r;
    }

    //need support wild card. regressExpression?
    //Can define a separate classs called filter , or simply passing JsonPath.
    public static JsonCompareResult compareJson(JsonObject o1, JsonObject o2, Set<String> filters) {
//        JsonCompareResult r = new JsonCompareResult();
        JsonCompareResult r = new JsonCompareResult(mode);
        compareJson("", (JsonElement) o2, (JsonElement) o1, r);
        return applyFilters(r, filters);
    }


    public static JsonCompareResult compareJsonArray(String parentLevel, JsonArray expected, JsonArray actual) {
//        JsonCompareResult r = new JsonCompareResult();
        JsonCompareResult r = new JsonCompareResult(mode);
        compareJsonArray(parentLevel, expected, actual, r);
        return r;
    }

    private static void compareJson(String parentLevel, JsonElement o1, JsonElement o2, JsonCompareResult result) {

        if (o1 == null && o2 == null) {
            return;
        }
        if (o1.isJsonNull() && o2.isJsonNull()) {
            return;
        }

        Queue<JsonElementWithLevel> q1 = new LinkedList<JsonElementWithLevel>();
        Queue<JsonElementWithLevel> q2 = new LinkedList<JsonElementWithLevel>();
        q1.offer(new JsonElementWithLevel(o1, "$"));
        q2.offer(new JsonElementWithLevel(o2, "$"));
        FieldFailure failure = new FieldFailure();
        String failureMsg = null;
        //iterate all nodes;
        while (!q1.isEmpty()) {
            int size = q1.size();
            for (int i = 0; i < size; i++) {
                JsonElementWithLevel org = q1.poll();
                JsonElementWithLevel dest = q2.poll();
                String currentLevelOfOrg = org.getLevel();
                String currentLevelOfDest = dest.getLevel();
                JsonElement je1 = org.getJsonElement();
                JsonElement je2 = dest.getJsonElement();

                if (je1.isJsonPrimitive()) {
                    compareJsonPrimitive(currentLevelOfOrg, je1.getAsJsonPrimitive(), je2.getAsJsonPrimitive(), result);
                } else if (je1.isJsonArray()) {
                    if (mode == JsonCompareMode.LENIENT) {
                        currentLevelOfOrg = currentLevelOfOrg + "[]";
                        currentLevelOfDest = currentLevelOfDest + "[]";
                    }
                    JsonArray ja1 = je1.getAsJsonArray();
                    JsonArray ja2 = je2.getAsJsonArray();
                    if (ja1.size() != ja2.size()) {
//                        System.out.println("JsonArrays size are different : " + " " + currentLevelOfOrg + ", size: " + ja1.size() + ", size: " + ja2.size());
                        failureMsg = "Different JsonArray size: " + ja1.size() + " VS " + ja2.size() + "\n\r" + ja1 + ";\n\r" + ja2;
                        failure = new FieldFailure(currentLevelOfOrg, FieldFailureType.DIFFERENT_JSONARRY_SIZE, ja1, ja2, failureMsg);
                        result.addFieldComparisonFailure(failure);
                    } else {
                        compareJsonArray(currentLevelOfOrg, ja1, ja2, result); //休要修改成返回为List<>;
                    }
                } else if (je1.isJsonObject()) {
                    //Compare two JsonObject;
                    JsonObject jo1 = je1.getAsJsonObject();
                    JsonObject jo2 = je2.getAsJsonObject();
                    for (Map.Entry<String, JsonElement> entry : jo1.entrySet()) {
                        String key = entry.getKey();
                        JsonElement value = entry.getValue();
                        String level = currentLevelOfOrg + "." + key;
                        if (parentLevel.startsWith("$") && !level.contains(parentLevel)) {
                            level = parentLevel + level.substring(1);
                        }

                        if (!jo2.has(key)) {
//                            System.out.println("Destination Json does not have key : " + level);
                            failureMsg = "Missing field \"" + level + "\" " + "from actual result.";
                            failure = new FieldFailure(level, FieldFailureType.MISSING_FIELD, jo1, null, failureMsg);
                            result.addFieldComparisonFailure(failure);
                        } else {
                            //only store JsonElements that have same "key";
                            q1.offer(new JsonElementWithLevel(value, level));
                            q2.offer(new JsonElementWithLevel(jo2.get(key), level));
                        }
                    }

                    // Need iterate all nodes in Destination JsonObject.
                    for (Map.Entry<String, JsonElement> entry : jo2.entrySet()) {
                        String key = entry.getKey();
                        JsonElement value = entry.getValue();

                        if (!jo1.has(key)) {
//                            System.out.println("Origin Json does not have key " + currentLevelOfDest + "." + key);
                            failureMsg = "Unexpected field \"" + currentLevelOfDest + "." + key + "\"" + " from actual result.";
                            failure = new FieldFailure(currentLevelOfDest + "." + key, FieldFailureType.UNEXPECTED_FIELD, null, jo2, failureMsg);
                            result.addFieldComparisonFailure(failure);
                        }
                    }
                }
            }
        }
    }


    public static void compareJsonArray(String parentLevel, JsonArray expected, JsonArray actual, JsonCompareResult result) {
        if (expected.size() != actual.size()) {
            String failureMsg = "Different JsonArray size: " + expected.size() + " VS " + actual.size() + "\n\r" + expected + ";\n\r" + actual;
            FieldFailure failure = new FieldFailure(parentLevel, FieldFailureType.DIFFERENT_JSONARRY_SIZE, expected, actual, failureMsg);
            result.addFieldComparisonFailure(failure);
        } else if (expected.size() == 0) {
            return; // Nothing to compare
        }

        if (mode == JsonCompareMode.STRICT) {
            compareJsonArrayWithStrictOrder(parentLevel, expected, actual, result);
        } else if (allSimpleValues(expected)) {
            List<FieldFailure> jsonArrayCompareResult = compareJsonArrayOfJsonPrimitives(parentLevel, expected, actual);
            result.getFieldFailures().addAll(jsonArrayCompareResult);
        } else if (allJsonObjects(expected)) {
            compareJsonArrayOfJsonObjects(parentLevel, expected, actual, result);
        }

    }

    public static void compareJsonPrimitive(String parentLevel, JsonPrimitive o1, JsonPrimitive o2, JsonCompareResult result) {

        String s1 = o1.getAsString().trim();
        String s2 = o2.getAsString().trim();
        if (!s1.equalsIgnoreCase(s2)) {
//            System.out.println("Two primitive elements are not equal : " + parentLevel + ", " + s1 + " , " + s2);
            String failureMsg = "Unequal Value : " + parentLevel + ", " + s1 + " , " + s2;
            FieldFailure failure = new FieldFailure(parentLevel, FieldFailureType.UNEQUAL_VALUE, o1, o2, failureMsg);
            result.addFieldComparisonFailure(failure);
        }
    }


    protected static void compareJsonArrayWithStrictOrder(String parentLevel, JsonArray expected, JsonArray actual, JsonCompareResult result) {
        for (int j = 0; j < expected.size(); ++j) {
            JsonElement expectedValue = expected.get(j);
            JsonElement actualValue = actual.get(j);
            compareJson(parentLevel + "[" + j + "]", expectedValue, actualValue, result);
//            compareJson(parentLevel + "[]", expectedValue, actualValue, result);
        }
    }

    //要传入level信息，这样可以new一个FieldComparisonFailure()
    protected static void compareJsonArrayOfJsonObjects(String parentLevel, JsonArray expected, JsonArray actual, JsonCompareResult result) {
        String uniqueKey = findUniqueKey(expected);
        FieldFailure failure = new FieldFailure();
        String failureMsg = null;

        if (uniqueKey == null || !isUsableAsUniqueKey(uniqueKey, actual)) {
            List<FieldFailure> jsonArrayCompareResult = recursivelyCompareJsonArray(parentLevel, expected, actual);
            result.getFieldFailures().addAll(jsonArrayCompareResult);
            return;
        }

//        System.out.println("Unique key is " + uniqueKey);
        Map<JsonPrimitive, JsonObject> expectedValueMap = arrayOfJsonObjectToMap(expected, uniqueKey);
        Map<JsonPrimitive, JsonObject> actualValueMap = arrayOfJsonObjectToMap(actual, uniqueKey);
        for (JsonPrimitive id : expectedValueMap.keySet()) {
            if (!actualValueMap.containsKey(id)) {
                failureMsg = "\"" + expectedValueMap.get(id) + "\"" + " is missing from actual JsonArray.";
                failure = new FieldFailure(parentLevel, FieldFailureType.MISSING_JSONARRAY_ELEMENT, expectedValueMap.get(id), null, failureMsg);
                result.getFieldFailures().add(failure);
                continue;
            }
            JsonObject expectedValue = expectedValueMap.get(id);
            JsonObject actualValue = actualValueMap.get(id);
            compareJson(parentLevel,  (JsonElement) expectedValue,(JsonElement) actualValue, result); //或者是currentLevelOfOrg[*] ???
        }
        for (JsonPrimitive id : actualValueMap.keySet()) {
            if (!expectedValueMap.containsKey(id)) {
                failureMsg = "\"" + actualValueMap.get(id) + "\"" + " is unexpected from actual JsonArray.";
                failure = new FieldFailure(parentLevel, FieldFailureType.UNEXPECTED_JSONARRAY_ELEMENT, null, actualValueMap.get(id), failureMsg);
                result.getFieldFailures().add(failure);
            }
        }

    }

    protected static List<FieldFailure> compareJsonArrayOfJsonPrimitives(String parentLevel, JsonArray expected, JsonArray actual) {
        List<FieldFailure> result = new ArrayList<>();
        Map<JsonElement, Integer> expectedCount = JsonCompareUtil.getCardinalityMap(jsonArrayToList(expected));
        Map<JsonElement, Integer> actualCount = JsonCompareUtil.getCardinalityMap(jsonArrayToList(actual));
        FieldFailure failure = new FieldFailure();
        String failureMsg = null;

        for (JsonElement o : expectedCount.keySet()) {
            if (!actualCount.containsKey(o)) {
                failureMsg = "Missing JsonArray Element " + o + " in actual JsonArray";
                failure = new FieldFailure(parentLevel, FieldFailureType.MISSING_JSONARRAY_ELEMENT, o, null, failureMsg);
                failure.setExpected(o);
                result.add(failure);
            } else if (!actualCount.get(o).equals(expectedCount.get(o))) {
                failureMsg = "Occurrence of " + o + " : " + expectedCount.get(o) + " VS " + actualCount.get(o);
                failure = new FieldFailure(parentLevel, FieldFailureType.DIFFERENT_OCCURENCE_JSONARRAY_ELEMENT, o, o, failureMsg);
                result.add(failure);
            }
        }

        for (JsonElement o : actualCount.keySet()) {
            if (!expectedCount.containsKey(o)) {
                failureMsg = "Unexpected JsonArray Element " + o + " in actual JsonArray";
                failure = new FieldFailure(parentLevel, FieldFailureType.UNEXPECTED_JSONARRAY_ELEMENT, null, o, failureMsg);
                failure.setActual(o);
                result.add(failure);
            }
        }

        return result;
    }


    // This is expensive (O(n^2) -- yuck), but may be the only resort for some cases with loose array ordering, and no
    // easy way to uniquely identify each element.

    protected static List<FieldFailure> recursivelyCompareJsonArray(String parentLevel, JsonArray expected, JsonArray actual) {
        List<FieldFailure> result = new ArrayList<>();
        Set<Integer> matched = new HashSet<Integer>(); //保存actual里已经被匹配的元素。
        for (int i = 0; i < expected.size(); ++i) {
            JsonElement expectedElement = expected.get(i);
            boolean matchFound = false;
            for (int j = 0; j < actual.size(); ++j) {
                JsonElement actualElement = actual.get(j);
                if (matched.contains(j) || !actualElement.getClass().equals(expectedElement.getClass())) {
                    continue;
                }
                if (expectedElement instanceof JsonObject) {
                    JsonCompareResult r =compareJson(parentLevel, expectedElement.getAsJsonObject(), actualElement.getAsJsonObject());
                    //要把所有结果的记录下来，返回之前de-dupe.
                    result.addAll(r.getFieldFailures());

                    if (r.isPassed() == true) {
                        matched.add(j);
                        matchFound = true;
                        break;
                    }
                } else if (expectedElement instanceof JsonArray) {
                    if (compareJsonArray(parentLevel, (JsonArray) expectedElement, (JsonArray) actualElement).isPassed()) {
                        matched.add(j);
                        matchFound = true;
                        break;
                    }
                } else if (expectedElement.equals(actualElement)) {
                    matched.add(j);
                    matchFound = true;
                    break;
                }
            }
            if (!matchFound) {
                String failureMsg = "\"" + expectedElement + "\"" + " is missing from actual JsonArray.";
                FieldFailure failure = new FieldFailure(parentLevel, FieldFailureType.UNEXPECTED_JSONARRAY_ELEMENT, expectedElement, null, failureMsg);
                result.add(failure);
            }
        }

        for (int j = 0; j < actual.size(); j++) {
            if (matched.contains(j)) {
                continue;
            }

            JsonElement actualElement = actual.get(j);
            String failureMsg = "\"" + actualElement + "\"" + " is unexpected from actual JsonArray.";
            FieldFailure failure = new FieldFailure(parentLevel, FieldFailureType.MISSING_JSONARRAY_ELEMENT, null, actualElement, failureMsg);
            result.add(failure);
        }

//        result = dedupleJsonArrayCompareResult(result);

        return result;
    }

    /*
    不确定是否真的需要这个filter过滤一下,VLS那个Json，比较下来没有问题。
     */
    private static List<FieldFailure> dedupleJsonArrayCompareResult(List<FieldFailure> result) {
        Set<String> set = new HashSet<>();
  /*      Iterator<FieldFailure> itr = result.iterator();
        while(itr.hasNext()) {
            FieldFailure failure = itr.next();
            String field = failure.getField();
            for(String s : set) {
                if(s.contains(field)) {
                    itr.remove();
                    break;
                }
            }
            set.add(field);
        }*/

        return result;
    }

}
