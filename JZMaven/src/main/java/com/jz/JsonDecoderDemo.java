package com.jz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonDecoderDemo {

	public static void main(String[] args) throws IOException {

		String s = convertFormattedJson2Raw(new File("/Users/jzfeng/Desktop/test.json"));
		// System.out.println(s);

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

		br = new BufferedReader(new FileReader("/Users/jzfeng/Desktop/test.json"));
		json = br.readLine();
		br.close();

		Set<String> set = new HashSet<String>();
		set.add("_type");
		set.add("extension");
		System.out.println(isEqual(o1, o2, set));

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
			System.out.println(json);
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
