package com.jz.json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.google.gson.*;

public class JsonCompare {

    public static void main(String[] args) throws IOException {
        JsonParser parser = new JsonParser();
        String json = convertFormattedJson2Raw(new File("/Users/jzfeng/Desktop/O.json"));
        JsonObject o1 = parser.parse(json).getAsJsonObject();

        json = convertFormattedJson2Raw(new File("/Users/jzfeng/Desktop/D.json"));
        JsonObject o2 = parser.parse(json).getAsJsonObject();
        String[] strs = new String[]{
                "$._type"
                ,"$.errorMessage"
                ,"$.extension"
                ,"$.listing.termsAndPolicies.logisticsTerms.logisticsPlan[0].minDeliveryEstimate.estimateTreatment"
                ,"$.listing.listingProperties[2].propertyValues[0].dateValue"
                ,"$.listing.tradingSummary.lastVisitDate"
                ,"$.listing.listingLifecycle.scheduledStartDate.value"};
        Set<String> filters = new TreeSet<String>();
        for(String s : strs) {
            filters.add(s);
        }

        JsonCompareResult jsonCompareResult = compareJson(o1, o2, filters);
        System.out.println(jsonCompareResult);

    }

    public static JsonCompareResult jsonCompareResult = new JsonCompareResult();

    public static void compareJsonPrimitive(JsonElement o1, JsonElement o2) {

    }

    public static void compareJsonArrayofJsonObjects(JsonElement o1, JsonElement o2) {

    }

    public static void compareJsonArrayofJsonPrimitive(JsonElement o1, JsonElement o2) {

    }

    public static void compareJsonArray(JsonElement o1, JsonElement o2) {
    }

  /*
   */

    public static JsonCompareResult compareJson(JsonObject o1, JsonObject o2) {
        compareJson((JsonElement) o1, (JsonElement)o2, "");
        return applyFilterstoResult(jsonCompareResult, new TreeSet<String>());
    }

    //need support wild card. regressExpression?
    // Can define a separate classs called filter , or simply passing JsonPath.
    public static JsonCompareResult compareJson(JsonObject o1, JsonObject o2, Set<String> filters) {
        compareJson((JsonElement) o1, (JsonElement)o2, "");
        return applyFilterstoResult(jsonCompareResult, filters);
    }

    private static void compareJson(JsonElement o1, JsonElement o2, String parentLevel) {

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
        FieldComparisonFailure failure = new FieldComparisonFailure();
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
                    //compare two JsonPrimitive
                    String s1 = je1.getAsString().trim();
                    String s2 = je2.getAsString().trim();
                    if (!s1.equalsIgnoreCase(s2)) {
//                        System.out.println("Two primitive elements are not equal : " + currentLevelOfOrg + ", " + s1 + " , " + s2);
                        failure = new FieldComparisonFailure(currentLevelOfOrg, FieldComparisonFailureType.UNEQUAL_VALUE, je1, je2);
                        jsonCompareResult.addFieldComparisonFailure(failure);
                    }
                } else if (je1.isJsonArray()) {
                    //compare two JsonArray;
                    //update parentLevel
                    JsonArray ja1 = je1.getAsJsonArray();
                    JsonArray ja2 = je2.getAsJsonArray();
                    if (ja1.size() != ja2.size()) {
//                        System.out.println("JsonArrays size are different : " + " " + currentLevelOfOrg + ", size: " + ja1.size() + ", size: " + ja2.size());
                        failure = new FieldComparisonFailure(currentLevelOfOrg, FieldComparisonFailureType.DIFFERENT_JSONARRY_SIZE, ja1, ja2);
                        jsonCompareResult.addFieldComparisonFailure(failure);
                    } else {
                        for (int j = 0; j < ja1.size(); j++) {
                            compareJson(ja1.get(j), ja2.get(j), currentLevelOfOrg + "[" + j + "]");
                        }
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
                            failure = new FieldComparisonFailure(level, FieldComparisonFailureType.MISSING_FIELD );
                            failure.setExpected(jo1.get(key));
                            jsonCompareResult.addFieldComparisonFailure(failure);
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
                            failure = new FieldComparisonFailure(currentLevelOfDest + "." + key, FieldComparisonFailureType.UNEXPECTED_FIELD);
                            failure.setActual(jo2.get(key));
                            jsonCompareResult.addFieldComparisonFailure(failure);
                        }
                    }
                }
            }
        }
    }


    // find the VLS JsonObject by passing Key = "VLS";
    private static JsonObject getJsonObjectByKey(JsonObject root, String key) {
        JsonObject res = new JsonObject();
        if (root.isJsonObject()) {
            Queue<JsonObject> queue = new LinkedList<JsonObject>();
            queue.offer(root);
            while (!queue.isEmpty()) {
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    JsonObject jo = queue.poll();
                    for (Map.Entry<String, JsonElement> entry : jo.entrySet()) {
                        if (entry.getValue().isJsonObject()) {
                            if (entry.getKey().equalsIgnoreCase(key)) {
                                JsonObject tmp = entry.getValue().getAsJsonObject();
                                return tmp;
                            } else {
                                queue.offer(entry.getValue().getAsJsonObject());
                            }
                        }
                    }
                }
            }
        }

        return res;

    }

    private static String convertFormattedJson2Raw(File f) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(f));
        String json = br.readLine();
        StringBuilder sb = new StringBuilder();
        while (json != null && json.length() > 0) {
            json = json.trim();
            while (json.startsWith("\t")) {
                json = json.replaceFirst("\t", "");
            }
            sb.append(json);
            json = br.readLine();
        }
        br.close();

        return sb.toString().trim();
    }


    /**
     * Searches for the unique key of the {@code expected} JSON array.
     *
     * @param array the array to find the unique key of
     * @return the unique key if there's any, otherwise null
     */
    private static String findUniqueKey(JsonArray array) {
        // Find a unique key for the object (id, name, whatever)
        if (array.size() > 0 && (array.get(0) instanceof JsonObject)) {
            JsonObject o = ((JsonObject) array.get(0)).getAsJsonObject();
            Set<String> keys = getKeys(o);
            for (String candidate : keys) {
                if (isUsableAsUniqueKey(candidate, array)) {
                    return candidate;
                }
            }
        }

        return null;
    }


    /**
     * @param candidate is usable as a unique key if every element in the
     * @param array     is a JSONObject having that key, and no two values are the same.
     * @return true if the candidate can work as a unique id across array
     */

    private static boolean isUsableAsUniqueKey(String candidate, JsonArray array) {
        Set<JsonElement> seenValues = new HashSet<JsonElement>();
        for (int i = 0; i < array.size(); i++) {
            JsonElement item = array.get(i);
            if (item instanceof JsonObject) {
                JsonObject o = (JsonObject) item;
                if (o.has(candidate)) {
                    JsonElement value = o.get(candidate);
                    if (isSimpleValue(value) && !seenValues.contains(value)) {
                        seenValues.add(value);
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        return true;
    }

    //get keys from a JsonObject
    private static Set<String> getKeys(JsonObject o) {
        Set<String> keys = new TreeSet<String>();
        for (Map.Entry<String, JsonElement> entry : o.entrySet()) {
            keys.add(entry.getKey().trim());
        }

        return keys;
    }

    //JsonPrimitive value as unique key
    private static boolean isSimpleValue(Object o) {
        return !(o instanceof JsonObject) && !(o instanceof JsonArray) && (o instanceof JsonPrimitive);
    }

    // build hashmap
    private static Map<JsonPrimitive, JsonObject> arrayOfJsonObjectToMap(JsonArray array, String uniqueKey) {
        Map<JsonPrimitive, JsonObject> valueMap = new HashMap<JsonPrimitive, JsonObject>();
        for (int i = 0; i < array.size(); ++i) {
            JsonObject jsonObject = (JsonObject) array.get(i);
            JsonPrimitive id = jsonObject.get(uniqueKey).getAsJsonPrimitive();
            valueMap.put(id, jsonObject);
        }
        return valueMap;
    }

    private static JsonCompareResult applyFilterstoResult(JsonCompareResult result, Set<String> filters) {

        if(filters == null || filters.size() == 0 ) {
            return result;
        }

        List<FieldComparisonFailure> failures = result.getFieldFailures();
        Iterator<FieldComparisonFailure> itr = failures.iterator();
        while(itr.hasNext()) {
            FieldComparisonFailure f = itr.next();
            String field = f.getField();
            if(filters.contains(field)) {
                itr.remove();
            }
        }

        return new JsonCompareResult(failures);
    }

}
