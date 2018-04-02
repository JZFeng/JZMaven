package com.jz.json;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Utils {

    public static List<JsonElement> jsonArrayToList(JsonArray expected) {
        List<JsonElement> jsonElements = new ArrayList<JsonElement>(expected.size());
        for (int i = 0; i < expected.size(); ++i) {
            jsonElements.add(expected.get(i));
        }
        return jsonElements;
    }

    /**
     * Creates a cardinality map from {@code coll}.
     *
     * @param coll the collection of items to convert
     * @param <T>  the type of elements in the input collection
     * @return the cardinality map
     */
    public static <T> Map<T, Integer> getCardinalityMap(final Collection<T> coll) {
        Map map = new HashMap<T, Integer>();
        for (T item : coll) {
            Integer c = (Integer) (map.get(item));
            if (c == null) {
                map.put(item, new Integer(1));
            } else {
                map.put(item, new Integer(c.intValue() + 1));
            }
        }
        return map;
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
//                    System.out.println("The unique key of JsonArray is ["  + candidate +"]");
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

    // build hashmap, KEY is UniqueKey's Value, VALUE is JsonObject;
    public static Map<JsonPrimitive, JsonObject> arrayOfJsonObjectToMap(JsonArray array, String uniqueKey) {
        Map<JsonPrimitive, JsonObject> valueMap = new HashMap<JsonPrimitive, JsonObject>();
        if (uniqueKey != null) {
            for (int i = 0; i < array.size(); ++i) {
                JsonObject jsonObject = (JsonObject) array.get(i);
                JsonPrimitive id = jsonObject.get(uniqueKey).getAsJsonPrimitive();
                valueMap.put(id, jsonObject);
            }
        }

        return valueMap;
    }

    // find the VLS JsonObject by passing Key = "VLS";
    public static JsonObject getJsonObjectByKey(JsonObject root, String key) {
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

    //    refactor, using try with resource statement
    public static String convertFormattedJson2Raw(File f) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
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

    public static CompareResult applyFilters(CompareResult result, Set<String> filters) {

        if (filters == null || filters.size() == 0) {
            return result;
        }

        List<Failure> failures = result.getFailures();
        Iterator<Failure> itr = failures.iterator();
        while (itr.hasNext()) {
            Failure f = itr.next();
            String field = f.getField();
            if (filters.contains(field)) {
                itr.remove();
            }
        }

        return new CompareResult(failures);
    }

    public static boolean allSimpleValues(JsonArray array) {
        for (int i = 0; i < array.size(); ++i) {
            if (!isSimpleValue(array.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean allJsonObjects(JsonArray array) {
        for (int i = 0; i < array.size(); ++i) {
            if (!(array.get(i) instanceof JsonObject)) {
                return false;
            }
        }
        return true;
    }

    public static List<JsonElementWithLevel> getJsonElementByPath(String path, JsonObject source) {
        List<JsonElementWithLevel> result = new ArrayList<>();
        if (path == null || path.length() == 0 || source == null || source.isJsonNull()) {
            return result;
        }

        Map<String, List<Range>> ranges = generateRanges(path);
        Map<String, List<Range>> matchedRanges = new LinkedHashMap<>();
        String regex = generateRegex(path);

        Queue<JsonElementWithLevel> queue = new LinkedList<JsonElementWithLevel>();
        queue.offer(new JsonElementWithLevel(source, "$"));
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                JsonElementWithLevel org = queue.poll();
                String currentLevel = org.getLevel();
                JsonElement je1 = org.getJsonElement();
                System.out.println(currentLevel);

                if (currentLevel.matches(regex) && isMatched(currentLevel, ranges, matchedRanges)) {
                    result.add(org);
                }

                if (je1.isJsonPrimitive()) {
                    //do nothing
                } else if (je1.isJsonArray()) {
                    JsonArray ja1 = je1.getAsJsonArray();
                    for (int j = 0; j < ja1.size(); j++) {
                        queue.offer(new JsonElementWithLevel(ja1.get(j), currentLevel + "[" + j + "]"));
                    }
                } else if (je1.isJsonObject()) {
                    JsonObject jo1 = je1.getAsJsonObject();
                    for (Map.Entry<String, JsonElement> entry : jo1.entrySet()) {
                        String key = entry.getKey();
                        JsonElement value = entry.getValue();
                        String level = currentLevel + "." + key;
                        queue.offer(new JsonElementWithLevel(value, level));
                    }
                }
            }
        }


        return result;
    }


    /**
     * @param currentLevel  $.courses[i].grade
     * @param ranges
     * @param matchedRanges
     * @return true if i in matchedRange();
     */

    private static boolean isMatched(
            String currentLevel, Map<String, List<Range>> ranges, Map<String, List<Range>> matchedRanges) {

        StringBuilder prefix = new StringBuilder();
        int index = 0;
        while ((index = currentLevel.indexOf('[')) != -1) {
            //update matchedRanges
            prefix.append(currentLevel.substring(0, index) + "[]");
            int i = Integer.parseInt(currentLevel.substring(index + 1, currentLevel.indexOf(']')));

            if (!matchedRanges.containsKey(prefix)) {
                for (Map.Entry<String, List<Range>> entry : ranges.entrySet()) {
                    String key = entry.getKey();
                    List<Range> value = entry.getValue();
                    int idx = prefix.indexOf(key);
                    if (idx != -1 && prefix.toString().substring(idx).equals(key)) {
                        matchedRanges.put(prefix.toString(), value);
                    }
                }
            }

            /*
            ???????
            modules.RETURNS.maxView.value[]
            modules.RETURNS.maxView.value[].value[]
            modules.RETURNS.maxView.value[].value[].textSpans[].text
            */
            boolean tmp = isMatching(matchedRanges, prefix.toString(), i);
            if (tmp) {
                currentLevel = currentLevel.substring(currentLevel.indexOf(']') + 1);
            } else {
                return false;
            }
        }

        return true;
    }


    private static boolean isMatching(Map<String, List<Range>> matchedRanges, String prefix, int i) {
        List<Range> rangeList = matchedRanges.get(prefix.toString().trim());
        if (rangeList != null && rangeList.size() > 0) {
            for (Range range : rangeList) {
                if (i >= range.start && i <= range.end) {
                    return true;
                }
            }
        }

        return false;
    }


    //update matchedRanges per currentLevel
    private static void updateRangesByCurrentLevel(
            String currentLevel, Map<String, List<Range>> ranges, Map<String, List<Range>> matchedRanges) {
        if (!matchedRanges.containsKey(currentLevel)) {
            //minView.actions[]  -> [2,2],[3,3]
            //minView.actions[].[] -> [2,4]
            //$.modules.BINSUMMARY.minView.actions[]
            for (Map.Entry<String, List<Range>> entry : ranges.entrySet()) {
                String key = entry.getKey();
                List<Range> value = entry.getValue();
                int idx = currentLevel.indexOf(key);
                if (idx != -1 && currentLevel.substring(idx).equals(key)) {
                    matchedRanges.put(currentLevel, value);
                }
            }

        }
    }


    private static String generateRegex(String path) {
        if (path.startsWith("$")) {
            path = "\\$" + path.substring(1);
        }

        String regex = "(.*)(" + (path.replaceAll("(\\[)(\\d{0,})(\\])", "\\\\" + "$1" + "\\\\d{1,}" + "\\\\" + "$3")) + ")";

        return regex;
    }

    //  x >= start && x <= end
    public static class Range {
        int start;
        int end;

        Range(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return "Start : " + start + " , end :" + end;
        }
    }

    /**
     * @param path minView.actions[  2 ,  3].action[2:5].URL
     * @return minView.actions[] : [(2,2),(3,3)]
     * minView.actions[].action[] : [(2,5)]
     */
    private static Map<String, List<Range>> generateRanges(String path) {
        Map<String, List<Range>> ranges = new LinkedHashMap<>();
        if (path == null || path.length() == 0) {
            return ranges;
        }

        StringBuilder prefix = new StringBuilder();
        int index = 0;
        while ((index = path.indexOf('[')) != -1) {
            prefix.append(path.substring(0, index) + "[]");
            String r = path.substring(index + 1, path.indexOf(']'));
            List<Range> range = generateRange(r);
            if (range != null && range.size() > 0) {
                ranges.put(prefix.toString().trim(), range);
            }
            path = path.substring(path.indexOf(']') + 1);
        }

        /*String regex = "\\[.*\\]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(path);
        while(matcher.find()) {
        }*/

        return ranges;
    }


    /**
     * @param r String in []
     *          [2]
     *          [-2]
     *          [0,1]
     *          [:2]
     *          [1:2]
     *          [-2:]
     *          [2:]
     */
    private static List<Range> generateRange(String r) {
        List<Range> result = new ArrayList<>();
        r = r.trim();
        if (r == null || r.length() == 0) {
            return result;
        }

        if (r.contains(",")) {
            //[1,3,5]
            String[] strs = r.split("\\s*,\\s*|\\s*:\\s*");
            for (String str : strs) {
                result.add(new Range(Integer.parseInt(str), Integer.parseInt(str)));
            }
        } else if (r.contains(":")) {
            String[] strs = r.split("\\s*,\\s*|\\s*:\\s*");
            if (strs.length == 2) { //[0:3]
                if (strs[0].length() == 0 || strs[0].equalsIgnoreCase("")) {
                    result.add(new Range(0, Integer.parseInt(strs[1]) - 1));
                } else {
                    result.add(new Range(Integer.parseInt(strs[0]), Integer.parseInt(strs[1]) - 1));
                }
            } else if (strs.length == 1) {
                int index = Integer.parseInt(strs[0]);
                if (r.startsWith(":")) { //[ : 2]
                    result.add(new Range(0, index - 1));
                } else {
                    if (index > 0) {
                        result.add(new Range(index, Integer.MAX_VALUE));
                    } else {
                        result.add(new Range(index, -1)); // [-2],for negative range, i - array.length;
                    }
                }
            }
        } else if (r.equalsIgnoreCase("*")) { //[*]
            result.add(new Range(0, Integer.MAX_VALUE));
        } else if (Integer.parseInt(r) >= 0) {
            result.add(new Range(Integer.parseInt(r), Integer.parseInt(r)));
        } else if (Integer.parseInt(r) < 0) { //[-2] ?????
            result.add(new Range(Integer.parseInt(r), Integer.parseInt(r)));
        }

        return result;
    }


    public static void main(String[] args) throws IOException {
        String path = "modules.RETURNS.maxView.value[3].value[0].textSpans[0]";
        JsonParser parser = new JsonParser();
        String json = convertFormattedJson2Raw(new File("/Users/jzfeng/Desktop/O.json"));
        JsonObject o1 = parser.parse(json).getAsJsonObject();

        List<JsonElementWithLevel> res = getJsonElementByPath(path, o1);
        System.out.println("*********");
        for (JsonElementWithLevel e : res) {
            System.out.println(e);
        }

    }

}

