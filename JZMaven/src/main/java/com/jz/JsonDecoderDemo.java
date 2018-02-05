package com.jz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import com.google.gson.*;

public class JsonDecoderDemo {

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
        /*
        br = new BufferedReader(new FileReader("/Users/jzfeng/Desktop/test.json"));
        json = br.readLine();
        br.close();

        String s = convertFormattedJson2Raw(new File("/Users/jzfeng/Desktop/test.json"));

        JsonObject test = parser.parse(s).getAsJsonObject();
        System.out.println(test);
        */
    }

    private static int numOfMissingProperties = 0;
    private static int numOfUnequalValue = 0;

    public static void compareJson(JsonElement o1, JsonElement o2) {
        if (o1 == null && o2 == null) {
            return;
        }
        if (o1.isJsonNull() && o2.isJsonNull()) {
            return;
        }


        Queue<JsonElement> q1 = new LinkedList<JsonElement>();
        Queue<JsonElement> q2 = new LinkedList<JsonElement>();
        q1.offer(o1);
        q2.offer(o2);

        //iterate all nodes;
        while (!q1.isEmpty()) {
            int size = q1.size();

            for (int i = 0; i < size; i++) {
                JsonElement je1 = q1.poll();
                JsonElement je2 = q2.poll();
                if (je1.isJsonPrimitive()) {
                    //compare two JsonPrimitive
                    String s1 = je1.getAsString().trim();
                    String s2 = je2.getAsString().trim();
                    if (!s1.equalsIgnoreCase(s2)) {
                        System.out.println("Two primitive elements are not equal : " + s1 + " , " + s2);
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
                            System.out.println("Destination Json does not have key : " + "\"" + key + "\"");
                            numOfMissingProperties++;
                        } else {
                            //only store JsonElements that have same "key";
                            q1.offer(value);
                            q2.offer(jo2.get(key));
                        }
                    }

                    // Need iterate all nodes in Destination JsonObject.
                    for (Map.Entry<String, JsonElement> entry : jo2.entrySet()) {
                        String key = entry.getKey();
                        JsonElement value = entry.getValue();
                        if (!jo1.has(key)) {
                            System.out.println("Origin Json does not have key " + key);
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
}
