package com.jz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.google.gson.*;

public class JsonDecoderDemo {

    public static void main(String[] args) throws IOException {

        JsonParser parser = new JsonParser();
        BufferedReader br = new BufferedReader(new FileReader("/Users/jzfeng/Desktop/O.json"));
        String json = br.readLine();
        br.close();

        JsonObject root = parser.parse(json).getAsJsonObject();
        JsonObject o1 = getJsonObjectByKey(root, "VLS");

        br = new BufferedReader(new FileReader("/Users/jzfeng/Desktop/D.json"));
        json = br.readLine();
        br.close();
        JsonObject o2 = parser.parse(json).getAsJsonObject();

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
        if(o1.isJsonNull() && o2.isJsonNull()) {
            return;
        }


        Queue<JsonElement> q1 = new LinkedList<JsonElement>();
        Queue<JsonElement> q2 = new LinkedList<JsonElement>();
        q1.offer(o1);
        q2.offer(o2);

        //iterate all nodes;

        while (!q1.isEmpty()) {
            //iterate q1 and q2
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
                            q1.offer(value);
                            q2.offer(jo2.get(key));
//                            compareJson(value, jo2.get(key));
                        }
                    }

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

    private static void iterateJson(JsonElement root) {
        if (root == null) {
            return;
        }

        Queue<JsonElement> q = new LinkedList<JsonElement>();
        q.offer(root);
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                JsonElement je = q.poll();
                if (je.isJsonPrimitive()) {
//					System.out.print(je.getAsJsonPrimitive());
                } else if (je.isJsonArray()) {
                    System.out.print(je.getAsJsonArray());
                } else if (je.isJsonObject()) {
                    JsonObject jo = je.getAsJsonObject();
                    for (Map.Entry<String, JsonElement> entry : jo.entrySet()) {
//						System.out.println(entry.getKey() + " : " + entry.getValue());
                        q.offer(entry.getValue());


                    }
                } else if (je.isJsonNull()) {
                    System.out.print(je.getAsJsonNull());
                }


            }
            System.out.println("该层结束");

        }




/*		if (root.isJsonObject()) {
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

		return res; */

    }


    private static void compareJsons(JsonElement o1, JsonElement o2) {

        Queue<JsonElement> queue = new LinkedList<JsonElement>();
        queue.offer(o1);


        if (o1.isJsonObject()) {

        } else if (o1.isJsonArray()) {

        } else if (o1.isJsonPrimitive()) {

        } else if (o1.isJsonNull()) {

        }


//		if(o1.isJsonObject() && o2.isJsonObject()) {
//			compareJsons(o1)
//		}


//		JsonObject res = new JsonObject();
//		if (root.isJsonObject()) {
//			Queue<JsonObject> queue = new LinkedList<JsonObject>();
//			queue.offer(root);
//			while (!queue.isEmpty()) {
//				int size = queue.size();
//				for (int i = 0; i < size; i++) {
//					JsonObject jo = queue.poll();
//					for (Map.Entry<String, JsonElement> entry : jo.entrySet()) {
//						if (entry.getValue().isJsonObject()) {
//							if (entry.getKey().equalsIgnoreCase(key)) {
//								JsonObject tmp = entry.getValue().getAsJsonObject();
//								return tmp;
//							} else {
//								queue.offer(entry.getValue().getAsJsonObject());
//							}
//						}
//					}
//				}
//			}
//
//		}


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

    public static boolean isEqual(JsonObject o1, JsonObject o2) {
        Set<String> set = new HashSet<String>();
        return isEqual(o1, o2, set);
    }

    public static boolean isEqual(JsonObject o1, JsonObject o2, Set<String> exclusion) {
        if (o1 == null || o2 == null) {
            return true;
        }
        if (!o1.isJsonObject() || !o2.isJsonObject()) {
            return false;
        }

        Set<Map.Entry<String, JsonElement>> set1 = o1.entrySet();
        Set<Map.Entry<String, JsonElement>> set2 = o2.entrySet();

        for (Map.Entry<String, JsonElement> entry : set1) {
            String key = entry.getKey();
            if (!exclusion.contains(key)) {
                if (!o2.has(key)) {
                    return false;
                } else {
                    continue;
                }
            } else {
                continue;
            }

        }

        return true;
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
