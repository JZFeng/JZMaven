package com.jz.json;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.google.gson.*;

import static com.jz.json.JsonCompareUtil.*;

public class JsonCompare {

    public static void main(String[] args) throws IOException {
        JsonParser parser = new JsonParser();
        String json = convertFormattedJson2Raw(new File("/Users/jzfeng/Desktop/O.json"));
        JsonObject o1 = parser.parse(json).getAsJsonObject();

        json = convertFormattedJson2Raw(new File("/Users/jzfeng/Desktop/D.json"));
        JsonObject o2 = parser.parse(json).getAsJsonObject();
        String[] strs = new String[]{
                "$._type"
                , "$.errorMessage"
                , "$.extension"
                , "$.listing.termsAndPolicies.logisticsTerms.logisticsPlan[0].minDeliveryEstimate.estimateTreatment"
                , "$.listing.listingProperties[2].propertyValues[0].dateValue"
                , "$.listing.tradingSummary.lastVisitDate"
                , "$.listing.listingLifecycle.scheduledStartDate.value"};
        Set<String> filters = new TreeSet<String>();
        for (String s : strs) {
            filters.add(s);
        }

        System.out.println(compareJson(o1, o2, filters));

    }

    public static JsonCompareResult jsonCompareResult = new JsonCompareResult();

    public static JsonCompareResult compareJson(JsonObject o1, JsonObject o2) {
        JsonCompareResult r = new JsonCompareResult();
        compareJson("", (JsonElement) o1, (JsonElement) o2, r );
        return r;
    }

    //need support wild card. regressExpression?
    //Can define a separate classs called filter , or simply passing JsonPath.
    public static JsonCompareResult compareJson(JsonObject o1, JsonObject o2, Set<String> filters) {
        JsonCompareResult r = new JsonCompareResult();
        compareJson("", (JsonElement) o2, (JsonElement) o1, r);
        return applyFilterstoResult(r, filters);
    }

    private static void compareJson(String parentLevel, JsonElement o2, JsonElement o1, JsonCompareResult result) {

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
                    JsonArray ja1 = je1.getAsJsonArray();
                    JsonArray ja2 = je2.getAsJsonArray();
                    if (ja1.size() != ja2.size()) {
//                        System.out.println("JsonArrays size are different : " + " " + currentLevelOfOrg + ", size: " + ja1.size() + ", size: " + ja2.size());
                        failure = new FieldFailure(currentLevelOfOrg, FieldFailureType.DIFFERENT_JSONARRY_SIZE, ja1, ja2);
                        result.addFieldComparisonFailure(failure);
                    } else {
                        compareJsonArrayWithStrictOrder(currentLevelOfOrg, ja1, ja2, result);
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
                            failure = new FieldFailure(level, FieldFailureType.MISSING_FIELD);
                            failure.setExpected(jo1.get(key));
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
                            failure = new FieldFailure(currentLevelOfDest + "." + key, FieldFailureType.UNEXPECTED_FIELD);
                            failure.setActual(jo2.get(key));
                            result.addFieldComparisonFailure(failure);
                        }
                    }
                }
            }
        }
    }

    public static void compareJsonPrimitive(String parentLevel, JsonPrimitive o1, JsonPrimitive o2, JsonCompareResult result) {

        String s1 = o1.getAsString().trim();
        String s2 = o2.getAsString().trim();
        if (!s1.equalsIgnoreCase(s2)) {
            System.out.println("Two primitive elements are not equal : " + parentLevel + ", " + s1 + " , " + s2);
            FieldFailure failure = new FieldFailure(parentLevel, FieldFailureType.UNEQUAL_VALUE, o1, o2);
            result.addFieldComparisonFailure(failure);
        }
    }


    protected static void compareJsonArrayWithStrictOrder(String parentLevel, JsonArray expected, JsonArray actual, JsonCompareResult result) {
        for (int j = 0; j < expected.size(); ++j) {
            JsonElement expectedValue = expected.get(j);
            JsonElement actualValue = actual.get(j);
            compareJson(parentLevel + "[" + j + "]", expectedValue, actualValue, result);
        }
    }

    //要传入level信息，这样可以new一个FieldComparisonFailure()
    protected void compareJsonArrayOfJsonObjects(String currentLevelOfOrg, JsonArray expected, JsonArray actual, JsonCompareResult result) {

        String uniqueKey = findUniqueKey(expected);
        FieldFailure failure = new FieldFailure();

        if (uniqueKey == null || !isUsableAsUniqueKey(uniqueKey, actual)) {
            //recursivelyCompareJSONArray();
        }

        Map<JsonPrimitive, JsonObject> expectedValueMap = arrayOfJsonObjectToMap(expected, uniqueKey);
        Map<JsonPrimitive, JsonObject> actualValueMap = arrayOfJsonObjectToMap(actual, uniqueKey);
        for (JsonPrimitive id : expectedValueMap.keySet()) {
            if (!actualValueMap.containsKey(id)) {
                System.out.println(currentLevelOfOrg + "actual JsonArray w/o " + uniqueKey + "==" + id);
                failure = new FieldFailure(currentLevelOfOrg, FieldFailureType.MISSING_JSONARRAY_ELEMENT, expected, actual);
                result.addFieldComparisonFailure(failure);
                continue;
            }
            JsonObject expectedValue = expectedValueMap.get(id);
            JsonObject actualValue = actualValueMap.get(id);
            compareJson(currentLevelOfOrg, (JsonElement) actualValue, (JsonElement) expectedValue, result); //或者是currentLevelOfOrg[*] ???
        }
        for (JsonPrimitive id : actualValueMap.keySet()) {
            if (!expectedValueMap.containsKey(id)) {
                System.out.println(currentLevelOfOrg + "expected JsonArray w/o " + uniqueKey + "==" + id);
                failure = new FieldFailure(currentLevelOfOrg, FieldFailureType.UNEXPECTED_JSONARRAY_ELEMENT, expected, actual);
                result.addFieldComparisonFailure(failure);
            }
        }

    }

    protected void compareJsonArrayOfJsonPrimitives(String parentLevel, JsonArray expected, JsonArray actual, JsonCompareResult result) {
        Map<JsonElement, Integer> expectedCount = JsonCompareUtil.getCardinalityMap(jsonArrayToList(expected));
        Map<JsonElement, Integer> actualCount = JsonCompareUtil.getCardinalityMap(jsonArrayToList(actual));
        FieldFailure failure = new FieldFailure();
        for (JsonElement o : expectedCount.keySet()) {
            if (!actualCount.containsKey(o)) {
                failure = new FieldFailure(parentLevel, FieldFailureType.MISSING_JSONARRAY_ELEMENT);
                failure.setExpected(o);
                result.addFieldComparisonFailure(failure);
            } else if (!actualCount.get(o).equals(expectedCount.get(o))) {
                failure = new FieldFailure(parentLevel, FieldFailureType.DIFFERENT_OCCURENCE_JSONARRAY_ELEMENT, o, o);
                result.addFieldComparisonFailure(failure);
            }
        }

        for (JsonElement o : actualCount.keySet()) {
            if (!expectedCount.containsKey(o)) {
                failure = new FieldFailure(parentLevel, FieldFailureType.UNEXPECTED_JSONARRAY_ELEMENT);
                failure.setActual(o);
                result.addFieldComparisonFailure(failure);
            }
        }
    }


    // This is expensive (O(n^2) -- yuck), but may be the only resort for some cases with loose array ordering, and no
    // easy way to uniquely identify each element.

//    protected void recursivelyCompareJsonArray(String parentLevel, JsonArray expected, JsonArray actual, JsonCompareResult result)  {
//        Set<Integer> matched = new HashSet<Integer>(); //保存actual里已经被匹配的元素。
//        for (int i = 0; i < expected.size(); ++i) {
//            JsonElement expectedElement = expected.get(i);
//            boolean matchFound = false;
//            for (int j = 0; j < actual.size(); ++j) {
//                JsonElement actualElement = actual.get(j);
//                if (matched.contains(j) || !actualElement.getClass().equals(expectedElement.getClass())) {
//                    continue;
//                }
//                if (expectedElement instanceof JsonObject) {
//                    if (compareJson(((JsonObject) expectedElement).getAsJsonObject(), actualElement.getAsJsonObject()).isPassed()) {
//                        matched.add(j);
//                        matchFound = true;
//                        break;
//                    }
//                } else if (expectedElement instanceof JsonArray) {
//                    if (compareJSON((JsonArray) expectedElement, (JsonArray) actualElement).passed()) {
//                        matched.add(j);
//                        matchFound = true;
//                        break;
//                    }
//                } else if (expectedElement.equals(actualElement)) {
//                    matched.add(j);
//                    matchFound = true;
//                    break;
//                }
//            }
//            if (!matchFound) {
//                FieldFailure failure = new FieldFailure();//need more details;
//                result.addFieldComparisonFailure(failure);
//                return;  //找不到一个元素，就失败？？？ 还是需要遍历所有的元素？？
//            }
//        }
//    }
}
