package com.jz.json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.google.gson.*;
import com.google.sitebricks.client.transport.Json;

public class JsonCompare {

    public static void main(String[] args) throws IOException {

        JsonParser parser = new JsonParser();
        BufferedReader br = new BufferedReader(new FileReader("/Users/jzfeng/Desktop/O.json"));
        String json = br.readLine();
        br.close();
        JsonObject o1 = parser.parse(json).getAsJsonObject();
        o1 = getJsonObjectByKey(o1, "VLS");
        System.out.println(o1);

        br = new BufferedReader(new FileReader("/Users/jzfeng/Desktop/D.json"));
        json = br.readLine();
        br.close();
        JsonObject o2 = parser.parse(json).getAsJsonObject();
        System.out.println(o2);

        compareJson(o1, o2);
        System.out.println("Number of Missing Properties : " + numOfMissingProperties);
        System.out.println("Number of Unequal Value : " + numOfUnequalValue);

    }

    private static int numOfMissingProperties = 0;
    private static int numOfUnequalValue = 0;

    public static void compareJsonPrimitive(JsonElement o1, JsonElement o2) {

    }

    public static void compareJsonArrayofJsonObjects(JsonElement o1, JsonElement o2) {

    }

    public static void compareJsonArrayofJsonPrimitive(JsonElement o1, JsonElement o2) {

    }

    public static void compareJsonArray(JsonElement o1, JsonElement o2) {

    }


/*
要定义一个二元组

所以Queue中放入的就是二元组而不是JsonElement;
根目录为 new JsonElementWithLevel(o1, "#");

  */

    public static void compareJson(JsonObject o1, JsonObject o2) {
        compareJson((JsonElement) o1, (JsonElement) o2);
    }

    public static void compareJson(JsonElement o1, JsonElement o2) {
        if (o1 == null && o2 == null) {
            return;
        }
        if (o1.isJsonNull() && o2.isJsonNull()) {
            return;
        }


        Queue<JsonElementWithLevel> q1 = new LinkedList<JsonElementWithLevel>();
        Queue<JsonElementWithLevel> q2 = new LinkedList<JsonElementWithLevel>();
        q1.offer(new JsonElementWithLevel(o1, "$"));
        q2.offer(new JsonElementWithLevel(o2, "#"));


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
                        System.out.println("Two primitive elements are not equal : " + currentLevelOfOrg + ", " + s1 + " , " + s2);
                        numOfUnequalValue++;
                    }
                } else if (je1.isJsonArray()) {
                    //compare two JsonArray;
                    JsonArray ja1 = je1.getAsJsonArray();
                    JsonArray ja2 = je2.getAsJsonArray();
                    if (ja1.size() != ja2.size()) {
                        System.out.println("JsonArrays are not identical");
                        numOfUnequalValue++;
                    } else {
                        for (int j = 0; j < ja1.size(); j++) {
                            compareJson(ja1.get(j), ja2.get(j));
                        }
                    }
                } else if (je1.isJsonObject()) {
                    //Compare two JsonObject;
                    JsonObject jo1 = je1.getAsJsonObject();
                    JsonObject jo2 = je2.getAsJsonObject();
                    for (Map.Entry<String, JsonElement> entry : jo1.entrySet()) {
                        String key = entry.getKey();
                        JsonElement value = entry.getValue();
                        if (!jo2.has(key)) {
                            System.out.println("Destination Json does not have key : " + currentLevelOfOrg + "." + key);
                            numOfMissingProperties++;
                        } else {
                            //only store JsonElements that have same "key";
                            q1.offer(new JsonElementWithLevel(value, currentLevelOfOrg + "." + key));
                            q2.offer(new JsonElementWithLevel(jo2.get(key), currentLevelOfOrg + "." + key));
                        }
                    }

                    // Need iterate all nodes in Destination JsonObject.
                    for (Map.Entry<String, JsonElement> entry : jo2.entrySet()) {
                        String key = entry.getKey();
                        JsonElement value = entry.getValue();

                        if (!jo1.has(key)) {
                            System.out.println("Origin Json does not have key " + currentLevelOfDest + "." + key);
                            numOfMissingProperties++;
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
    public static String findUniqueKey(JsonArray array) {
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

    public static boolean isUsableAsUniqueKey(String candidate, JsonArray array) {
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
    public static Set<String> getKeys(JsonObject o) {
        Set<String> keys = new TreeSet<String>();
        for (Map.Entry<String, JsonElement> entry : o.entrySet()) {
            keys.add(entry.getKey().trim());
        }

        return keys;
    }

    //JsonPrimitive value as unique key
    public static boolean isSimpleValue(Object o) {
        return !(o instanceof JsonObject) && !(o instanceof JsonArray) && (o instanceof JsonPrimitive);
    }

    // build hashmap
    public static Map<JsonPrimitive, JsonObject> arrayOfJsonObjectToMap(JsonArray array, String uniqueKey) {
        Map<JsonPrimitive, JsonObject> valueMap = new HashMap<JsonPrimitive, JsonObject>();
        for (int i = 0; i < array.size(); ++i) {
            JsonObject jsonObject = (JsonObject) array.get(i);
            JsonPrimitive id = jsonObject.get(uniqueKey).getAsJsonPrimitive();
            valueMap.put(id, jsonObject);
        }
        return valueMap;
    }
}
